package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongMapper {
    public int insert(Song song);
    public int update(Song song);
    public int delete(Integer id);
    /* 根据id主键查询整个对象*/
    public Song selectByPrimaryKey(Integer id);

    /*查询所有歌单*/
    public List<Song> allSong ();

    /*根据歌名模糊查询列表*/
    public List<Song> songOfName(String name);

    /*根据歌手id查询*/
    public List<Song> songOfSingerId(Integer singerId);
}
