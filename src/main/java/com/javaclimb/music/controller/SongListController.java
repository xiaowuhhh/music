package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.SongList;
import com.javaclimb.music.service.SongListService;
import com.javaclimb.music.utils.Consts;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/*歌单控制类*/

@RestController
@RequestMapping(value = "/songList")
public class SongListController {

    @Autowired
    SongListService songListService;

/*添加歌单*/
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String title = request.getParameter("title").trim();
        String pic = request.getParameter("pic").trim();
        String style = request.getParameter("style").trim();
        String introduction = request.getParameter("introduction").trim();

        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setStyle(style);
        songList.setIntroduction(introduction);
        boolean flag = songListService.insert(songList);

        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"歌单保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"保存失败");
        return jsonObject;
    }


    /*修改歌单*/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id");
        String title = request.getParameter("title").trim();
        String pic = request.getParameter("pic").trim();
        String style = request.getParameter("style").trim();
        String introduction = request.getParameter("introduction").trim();

        SongList songList = new SongList();
        songList.setId(Integer.parseInt(id));
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setStyle(style);
        songList.setIntroduction(introduction);
        boolean flag = songListService.update(songList);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"歌单保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"歌单保存失败");
        return jsonObject;
    }

    /*删除歌单*/
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public boolean deleteSongList(HttpServletRequest request){
        String id = request.getParameter("id");
        return songListService.delete(Integer.parseInt(id));
    }

    /*查询全部歌单*/
    @RequestMapping(value = "/allSongList",method = RequestMethod.GET)
    public Object getSongList(){
        return songListService.allSongList();
    }

    /*根据歌单标题精确查询歌单列表*/
    @RequestMapping(value = "/getListOfTitle",method = RequestMethod.GET)
    public Object songListOfTitle(HttpServletRequest request){
        String title = request.getParameter("title");
        return songListService.songListOfTitle("%"+title+"%");
    }

    /*根据歌单标题模糊查询歌单列表
    @RequestMapping(value = "/likeTitle",method = RequestMethod.GET)
    public Object likeTitle(HttpServletRequest request){
        String title = request.getParameter("title");
        return songListService.likeTitle(title);
    }*/

    /*根据歌单风格模糊查询歌单列表
    @RequestMapping(value = "/style",method = RequestMethod.GET)
    public Object likeStyle(HttpServletRequest request){
        String style = request.getParameter("style");
        return songListService.likeStyle(style);
    }*/

}
