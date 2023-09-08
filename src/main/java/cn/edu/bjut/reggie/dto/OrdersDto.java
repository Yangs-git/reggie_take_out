package cn.edu.bjut.reggie.dto;

import cn.edu.bjut.reggie.entity.OrderDetail;
import cn.edu.bjut.reggie.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

}
