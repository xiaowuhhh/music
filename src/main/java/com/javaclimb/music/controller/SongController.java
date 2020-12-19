package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.SongService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(value = "/song")
public class SongController {
@Autowired
    private SongService songService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile){
        JSONObject jsonObject = new JSONObject();
        String singerId = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String pic = "/img/songPic.timg.jpg";
        String lyric = request.getParameter("lyric").trim();
        String introduction = request.getParameter("introduction").trim();
        if (mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲上传失败");
            return jsonObject;
        }
            String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
            String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";

           File file1 = new File(filePath);
           if (!file1.exists()){
               file1.mkdir();
           }
           File dest = new File(filePath+System.getProperty("file.separator")+fileName);
           String storeUrlPath = "/song/"+fileName;
        try {
            mpFile.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setPic(pic);
            song.setLyric(lyric);
            song.setIntroduction(introduction);
            song.setUrl(storeUrlPath);
            boolean flag = songService.insert(song);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"音乐保存成功！");
                jsonObject.put("avator",storeUrlPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲保存失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲保存失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }


    @RequestMapping(value = "/singer/detail",method = RequestMethod.GET)
    public Object getSongOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId").trim();
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }

}
