package com.javaclimb.music.service;

import com.javaclimb.music.domain.Song;

import java.util.List;
/*歌曲service接口*/
public interface SongService {
    public boolean insert(Song song);
    public boolean update(Song song);
    public boolean delete(Integer id);
    /* 根据id主键查询整个对象*/
    public Song selectByPrimaryKey(Integer id);

    /*查询所有歌单*/
    public List<Song> allSong ();

    /*根据歌名模糊查询列表*/
    public List<Song> songOfName(String name);

    /*根据歌手id查询*/
    public List<Song> songOfSingerId(Integer singerId);
}
