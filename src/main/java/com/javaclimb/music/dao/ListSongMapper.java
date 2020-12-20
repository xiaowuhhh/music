package com.javaclimb.music.dao;

import com.javaclimb.music.domain.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListSongMapper {
    public int insert (ListSong listSong);

    public int update(ListSong listSong);

    public int delete (Integer id);

    /* 根据id主键查询整个对象*/
    public ListSong selectByPrimaryKey(Integer id);

    /*查询所有歌单歌曲*/
    public List<ListSong> allListSong ();

    /*根据歌单id查询所有歌曲*/
    public List<ListSong> listSongOfSongListId(Integer songListId);
}
