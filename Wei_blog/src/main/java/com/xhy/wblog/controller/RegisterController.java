package com.xhy.wblog.controller;


import com.xhy.wblog.controller.result.Code;
import com.xhy.wblog.controller.result.PublicResult;
import com.xhy.wblog.controller.vo.RegisterVo;
import com.xhy.wblog.domain.User;
import com.xhy.wblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  使用Restful的风格进行数据交互。
 *  @RestController 这个包含了Json字符串转换
 *  @RequestMapping("/users")   路径：在ctx下拼接 /users
 */


@RestController
@RequestMapping("/users")
public class RegisterController{

    //自动注入service
    @Autowired
    private UserService service;


    //注册功能
    @RequestMapping("registeruser")
    public PublicResult registerUser(@RequestBody RegisterVo registerVo){
        try {
            User user = service.selectByEmail(registerVo.getEmail());
            System.out.println(user);
            if(user!=null){//如果不为空，证明已经被注册
                return new PublicResult(Code.SAVE_ERROR, "该邮件已经被注册!");
            }else{//不然就创建user类，插入数据库
                user = new User();
                user.setEmail(registerVo.getEmail());
                user.setPassword(registerVo.getPassword());
                Calendar calendar= Calendar.getInstance();
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                user.setCreatedTime(dateFormat.parse(dateFormat.format(calendar.getTime())));
                user.setGender(1);
                service.save(user);
                return new PublicResult(Code.SAVE_OK,user,"注册成功!");
            }
        } catch (Exception e) {
            // 来到这说明失败了
            e.printStackTrace();
            return new PublicResult(Code.QUERY_ERROR, "查询失败");
        }
    }

}
