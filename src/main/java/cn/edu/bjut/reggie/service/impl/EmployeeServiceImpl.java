package cn.edu.bjut.reggie.service.impl;

import cn.edu.bjut.reggie.entity.Employee;
import cn.edu.bjut.reggie.mapper.EmployeeMapper;
import cn.edu.bjut.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
