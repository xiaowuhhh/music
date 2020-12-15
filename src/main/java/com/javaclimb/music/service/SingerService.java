package com.javaclimb.music.service;

import com.javaclimb.music.domain.Singer;

import java.util.List;

public interface SingerService {

        public boolean insert (Singer singer);

        public boolean update(Singer singer);

        public boolean delete (Integer id);

        /* 根据id主键查询整个对象*/
        public Singer selectByPrimaryKey(Integer id);

        /*查询所有歌手*/
        public List<Singer> allSinger ();

        /*根据歌手名模糊查询列表*/
        public List<Singer> singerOfName(String name);

        /*根据男女查询*/
        public List<Singer> singerOfSex(Integer sex);
    }
