package com.xhy.wblog.service;

import com.xhy.wblog.controller.result.PublicResult;
import com.xhy.wblog.controller.vo.RegisterVo;
import com.xhy.wblog.controller.vo.LoginVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

// 事务管理: 默认 只读
@Transactional(readOnly = true)
public interface UserService {


    //用email来查找 ，@Transactional(readOnly = false)：保存东西最好写上
    @Transactional(readOnly = false)
    public PublicResult register(RegisterVo registerVo);

    // 用户登录
    Map<String, Object> login(LoginVo bean) throws Exception;

}
