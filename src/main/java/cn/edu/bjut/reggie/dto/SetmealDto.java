package cn.edu.bjut.reggie.dto;

import cn.edu.bjut.reggie.entity.Setmeal;
import cn.edu.bjut.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
