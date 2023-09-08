package cn.edu.bjut.reggie.controller;


import cn.edu.bjut.reggie.anno.Log;
import cn.edu.bjut.reggie.common.R;
import cn.edu.bjut.reggie.dto.DishDto;
import cn.edu.bjut.reggie.entity.Category;
import cn.edu.bjut.reggie.entity.Dish;
import cn.edu.bjut.reggie.entity.DishFlavor;
import cn.edu.bjut.reggie.entity.Employee;
import cn.edu.bjut.reggie.service.CategoryService;
import cn.edu.bjut.reggie.service.DishFlavorService;
import cn.edu.bjut.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Log
    @PostMapping
    @CacheEvict(value = "dishCache", allEntries = true)
    public R<String> add(@RequestBody DishDto dishDto){
        log.info("dish: {}", dishDto.toString());
//        String key = "dish_" + dishDto.getCategoryId() + "_1";
//        redisTemplate.delete(key);
        dishService.saveWithFlavor(dishDto);

        return R.success("添加成功");
    }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }


    @Log
    @DeleteMapping
    @CacheEvict(value = "dishCache", allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);
//        for(Long id : ids){
//            Dish dish = dishService.getById(id);
//            String key = "dish_" + dish.getCategoryId() + "_1";
//            redisTemplate.delete(key);
//        }
        dishService.removeByIds(ids);

        return R.success("套餐数据删除成功");
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @Log
    @PutMapping
    @CacheEvict(value = "dishCache", allEntries = true)
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
//        String key = "dish_" + dishDto.getCategoryId() + "_" + dishDto.getStatus();
//        redisTemplate.delete(key);
        dishService.updateWithFlavor(dishDto);

        return R.success("修改菜品成功");
    }

    @GetMapping("/list")
    @Cacheable(value = "dishCache", key = "#dish.getCategoryId() + '_' + #dish.getStatus()")
    public R<List<DishDto>> list(Dish dish){
        List<DishDto> dishDtoList = null;
//        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();

//        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        //缓存存在
//        if(dishDtoList != null)
//            return R.success(dishDtoList);

        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        //缓存不存在
//        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }

    @Log
    @PostMapping("/status/{status}")
    @CacheEvict(value = "dishCache", allEntries = true)
    public R<String> updateStatus(@PathVariable Integer status, @RequestParam List<Long> ids){
        log.info("status: {}, ids: {}", status, ids);
        LambdaUpdateWrapper<Dish> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.in(ids != null, Dish::getId, ids);
        queryWrapper.set(Dish::getStatus, status);
        dishService.update(queryWrapper);

        return R.success("修改成功");
    }
}
