package com.javaclimb.music.dao;


import com.javaclimb.music.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * @Repository注解便属于最先引入的一批，它用于将数据访问层 (DAO 层 ) 的类
 * 标识为 Spring Bean。具体只需将该注解标注在 DAO类上即可。同时，
 * 为了让 Spring 能够扫描类路径中的类并识别出 @Repository 注解，
 * 需要在 XML 配置文件中启用Bean 的自动扫描功能，
 */
public interface SingerMapper {

    public int insert (Singer singer);

    public int update(Singer singer);

    public int delete (Integer id);

    /* 根据id主键查询整个对象*/
    public Singer selectByPrimaryKey(Integer id);

    /*查询所有歌手*/
    public List<Singer> allSinger ();

    /*根据歌手名模糊查询列表*/
    public List<Singer> singerOfName(String name);

    /*根据男女查询*/
    public List<Singer> singerOfSix(Integer sex);
}
