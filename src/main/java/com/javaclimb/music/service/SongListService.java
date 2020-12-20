package com.javaclimb.music.service;

import com.javaclimb.music.domain.SongList;
import java.util.List;

public interface SongListService {
    public boolean insert(SongList songList);
    public boolean update(SongList songList);
    public boolean delete(Integer id);

    /* 根据id主键查询整个对象*/
    public SongList selectByPrimaryKey(Integer id);

    /*查询所有歌单*/
    public List<SongList> allSongList();

    /*根据歌单标题精确查询歌单列表*/
    public List<SongList> songListOfTitle(String title);

    /*根据歌单标题模糊查询歌单列表*/
    public List<SongList> likeTitle(String title);

    /*根据歌单风格模糊查询歌单列表*/
    public List<SongList> likeStyle(String style);

}
