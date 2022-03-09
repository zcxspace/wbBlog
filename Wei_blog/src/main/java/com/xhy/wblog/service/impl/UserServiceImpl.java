package com.xhy.wblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhy.wblog.dao.UserDao;
import com.xhy.wblog.domain.User;
import com.xhy.wblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    // 自动初始化改dao
    @Autowired
    private UserDao dao;

    // 下面是CRUD的基本实现

    @Override
    public boolean save(User user) {
        return dao.insert(user) > 0;
    }

    @Override
    public boolean update(User user) {
        return dao.updateById(user) > 0;
    }

    @Override
    public boolean remove(Integer id) {
        return dao.deleteById(id) > 0;
    }

    @Override
    public User get(Integer id) {
        return dao.selectById(id);
    }

    @Override
    public List<User> list() {

         // 这个可以做条件查询，具体mybatis-plus的使用，自行百度。无条件就是查询全部
         QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        return dao.selectList(queryWrapper);
    }
}
