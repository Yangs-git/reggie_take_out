package cn.edu.bjut.reggie.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OperateLog implements Serializable {
    private Long id;
    private Long operateUser;
    private LocalDateTime operateTime;
    private String className;
    private String methodName;
    private String methodParams;
    private String returnValue;
    private Long costTime;
}
