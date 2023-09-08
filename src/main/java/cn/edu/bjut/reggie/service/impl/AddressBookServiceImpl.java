package cn.edu.bjut.reggie.service.impl;

import cn.edu.bjut.reggie.entity.AddressBook;
import cn.edu.bjut.reggie.mapper.AddressBookMapper;
import cn.edu.bjut.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
