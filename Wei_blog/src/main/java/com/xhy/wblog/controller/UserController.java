package com.xhy.wblog.controller;


import com.xhy.wblog.controller.result.Code;
import com.xhy.wblog.controller.result.PublicResult;
import com.xhy.wblog.controller.vo.UserVo;
import com.xhy.wblog.domain.User;
import com.xhy.wblog.service.UserService;
import com.xhy.wblog.system.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  使用Restful的风格进行数据交互。
 *  @RestController 这个包含了Json字符串转换
 *  @RequestMapping("/users")   路径：在ctx下拼接 /users
 */

@RestController
@RequestMapping("/users")
public class UserController {

    /*
    我们统一一下
    这个Restful风格
    我们就只使用传统的 Get请求 和Post请求
    读取： Get请求
    写入： Post请求
     */

    // 自动注入service
    @Autowired
    private UserService service;


    @GetMapping("list")
    public PublicResult list() {
        List<User> users = service.list();
        if (true) throw new BusinessException("查询出错了", Code.QUERY_ERROR);

        return new PublicResult(Code.QUERY_OK, users, "查询成功！");
    }

    @RequestMapping("get")
    public PublicResult get(@PathVariable Integer id) {
        User user = service.get(id);
        if (user == null) throw new BusinessException("查询出错了", Code.QUERY_ERROR);
        return new PublicResult(Code.QUERY_OK, user, "查询成功！");
    }

    @RequestMapping("save")
    public PublicResult save(@PathVariable User user) {
        if (service.save(user)) throw new BusinessException("保存失败", Code.SAVE_ERROR);
        return new PublicResult(Code.SAVE_OK, "保存成功");
    }

    @RequestMapping("/update")
    public PublicResult update(@RequestBody User user) {
        if (service.save(user)) throw new BusinessException("更新失败", Code.UPDATE_ERROR);
        return new PublicResult(Code.UPDATE_OK, "更新成功");
    }

    @PostMapping("delete/{id}")
    public PublicResult delete(@PathVariable Integer id) {
        if (service.remove(id)) throw new BusinessException("删除失败", Code.DELETE_ERROR);
        return new PublicResult(Code.DELETE_OK, "删除成功成功");
    }


}
