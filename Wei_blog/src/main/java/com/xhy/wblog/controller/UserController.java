package com.xhy.wblog.controller;


import com.xhy.wblog.controller.result.Code;
import com.xhy.wblog.controller.result.PublicResult;
import com.xhy.wblog.controller.vo.UserVo;
import com.xhy.wblog.domain.User;
import com.xhy.wblog.service.UserService;
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


    @RequestMapping("list")
    public PublicResult list() {
        try {
            List<User> users = service.list();
            return new PublicResult(Code.QUERY_OK, users, "查询成功");
        } catch (Exception e) {
            // 来到这说明失败了
            e.printStackTrace();
            return new PublicResult(Code.QUERY_ERROR, "查询失败");
        }

    }

    @RequestMapping("get")
    public PublicResult get(@RequestBody Integer id) {
        try {
            User user = service.get(id);
            return new PublicResult(Code.QUERY_OK, user, "查询成功！");
        } catch (Exception e) {
            // 来到这说明失败了
            e.printStackTrace();
            return new PublicResult(Code.QUERY_ERROR, "查询失败");
        }
    }

    @RequestMapping("save")
    public PublicResult save(@RequestBody User user) {
        try {
            service.save(user);
            return new PublicResult(Code.SAVE_OK, "保存成功");
        } catch (Exception e) { //  来到这说明失败了
            e.printStackTrace();
            return new PublicResult(Code.SAVE_ERROR, "保存失败");
        }
    }

    @RequestMapping("/update")
    public PublicResult update(@RequestBody User user) {
        try {
            service.update(user);
            return new PublicResult(Code.UPDATE_OK, "更新成功");
        } catch (Exception e) {
            // 来到这说明失败了
            e.printStackTrace();
            return new PublicResult(Code.UPDATE_ERROR, "更新失败");
        }

    }

    @RequestMapping("delete")
    public PublicResult delete(Integer id) {

        try {
            service.remove(id);
            return new PublicResult(Code.DELETE_OK, "删除成功");
        } catch (Exception e) {
            // 失败了
            e.printStackTrace();
            return new PublicResult(Code.DELETE_ERROR, "删除失败");
        }

    }


}
