package cn.edu.bjut.reggie.service.impl;

import cn.edu.bjut.reggie.entity.User;
import cn.edu.bjut.reggie.mapper.UserMapper;
import cn.edu.bjut.reggie.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
