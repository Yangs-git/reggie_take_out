package cn.edu.bjut.reggie.service.impl;

import cn.edu.bjut.reggie.entity.OperateLog;
import cn.edu.bjut.reggie.mapper.OperateLogMapper;
import cn.edu.bjut.reggie.service.OperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {
}
