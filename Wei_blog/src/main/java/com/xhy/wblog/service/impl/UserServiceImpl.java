package com.xhy.wblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xhy.wblog.dao.UserDao;
import com.xhy.wblog.domain.User;
import com.xhy.wblog.service.UserService;
import com.xhy.wblog.utils.md5.Md5;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 登录
    @Override
    public Map<String, Object> login(User bean) throws Exception {
        // 条件查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", bean.getEmail());
        User user = dao.selectOne(queryWrapper);

        // 返回的map集合
        Map<String, Object> map = new HashMap<>();
        if (user != null) { // 代表改有改用户
            // 验证密码
            boolean verify = Md5.verify(bean.getPassword(), Md5.md5key, user.getPassword());
            if (verify) { // 代表是真
                map.put("msg", "登陆成功");
                map.put("user", user);
                map.put("flag", true);
            } else {
                map.put("msg", "密码错误");
                map.put("flag", false);
            }
        } else {
            map.put("msg", "用户名不存在");
            map.put("flag", false);
        }
        return map;

    }

    //注册用--用email查找user
    @Override
    public User selectByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("email",email);
        return dao.selectOne(wrapper);
    }
}
