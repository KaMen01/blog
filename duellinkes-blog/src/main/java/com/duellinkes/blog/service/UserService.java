package com.duellinkes.blog.service;

import com.duellinkes.blog.commons.bo.UserInfo;
import com.duellinkes.blog.commons.utils.JwtUtils;
import com.duellinkes.blog.config.AuthProperties;
import com.duellinkes.blog.mapper.UserMapper;
import com.duellinkes.blog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthProperties authProperties;

    /**
     * 用户登陆授权
     * @param phone
     * @param password
     * @return
     */
    public String authentication(String phone, String password) {
        User user = new User();
        user.setPassword(password);
        user.setPhone(phone);

        User user1 = userMapper.selectOne(user);
        if (user1 == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo(user1.getId(), user1.getUsername());
        String token = null;
        try {
            token = JwtUtils.generateToken(userInfo, authProperties.getPrivateKey(), authProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
