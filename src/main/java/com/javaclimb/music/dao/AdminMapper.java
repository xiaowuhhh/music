package com.javaclimb.music.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 验证密码是否正确
 */

@Repository
public interface AdminMapper {
   int verifyPassword(@Param("username") String username, @Param("password") String password);

}
