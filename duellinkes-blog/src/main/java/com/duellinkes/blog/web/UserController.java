package com.duellinkes.blog.web;

import com.duellinkes.blog.commons.utils.CookieUtils;
import com.duellinkes.blog.config.AuthProperties;
import com.duellinkes.blog.pojo.User;
import com.duellinkes.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthProperties authProperties;

    @PostMapping("/login")
    public ResponseEntity<Boolean> authentication(
            @RequestBody User user1,
            HttpServletRequest request,
            HttpServletResponse response){
        String token = userService.authentication(user1.getPhone(),user1.getPassword());

        //若不匹配，则返回错误
        if(StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//        System.out.println(request.getRequestURL().toString());
        //匹配，则返回userInfo的token
        CookieUtils.setCookie(request,response,authProperties.getCookieName(),token,authProperties.getExpire()*60);
        return ResponseEntity.ok(true);

    }

}
