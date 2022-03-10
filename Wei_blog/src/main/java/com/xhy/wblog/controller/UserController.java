package com.xhy.wblog.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.xhy.wblog.controller.result.Code;
import com.xhy.wblog.controller.result.PublicResult;
import com.xhy.wblog.controller.vo.UserVo;
import com.xhy.wblog.domain.User;
import com.xhy.wblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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
    public PublicResult get(Integer id) {
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

    // 验证码
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 创建Kaptcha对象
        DefaultKaptcha dk = new DefaultKaptcha();
        // 验证码配置
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("kaptcha.properties")) {

            Properties properties = new Properties();
            properties.load(is);
            Config config = new Config(properties);
            dk.setConfig(config);
        }

        // 验证码字符串
        String code = dk.createText();

        // 将其字符串保存到session中
        HttpSession session = request.getSession();
        session.setAttribute("code", code.toLowerCase());

        // 将验证码字符串转换成验证码图片
        BufferedImage img = dk.createImage(code);
        response.setContentType("image/jpeg");
        ImageIO.write(img, "jpg", response.getOutputStream());

    }


}
