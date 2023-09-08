package cn.edu.bjut.reggie.service.impl;

import cn.edu.bjut.reggie.entity.OrderDetail;
import cn.edu.bjut.reggie.mapper.OrderDetailMapper;
import cn.edu.bjut.reggie.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}