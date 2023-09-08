package cn.edu.bjut.reggie.service.impl;

import cn.edu.bjut.reggie.entity.DishFlavor;
import cn.edu.bjut.reggie.mapper.DishFlavorMapper;
import cn.edu.bjut.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
