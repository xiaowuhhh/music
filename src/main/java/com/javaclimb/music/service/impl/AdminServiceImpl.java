package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.AdminMapper;
import com.javaclimb.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员service实现类
 */
    @Service
    public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    /**
     * >0就是在数据库中查到数据，返回true
     * 没查到数据就是0，会返回false
     */
    public boolean verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username,password)>0;

    }
}
