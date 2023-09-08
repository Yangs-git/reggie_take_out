package cn.edu.bjut.reggie.service.impl;

import cn.edu.bjut.reggie.entity.ShoppingCart;
import cn.edu.bjut.reggie.mapper.ShoppingCartMapper;
import cn.edu.bjut.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
