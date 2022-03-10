package com.xhy.wblog.controller;

import com.xhy.wblog.controller.result.PublicResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  使用Restful的风格进行数据交互。
 *  @RestController 这个包含了Json字符串转换
 *  @RequestMapping("/users")   路径：在ctx下拼接 /users
 */

@RestController
@RequestMapping("/users")
public class LoginController {



}
