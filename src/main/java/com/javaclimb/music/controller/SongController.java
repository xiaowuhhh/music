package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.SongService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(value = "/song")
public class SongController {
@Autowired
    private SongService songService;

    @CrossOrigin
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

    @CrossOrigin
    @RequestMapping(value = "/singer/detail",method = RequestMethod.GET)
    public Object songOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId").trim();
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }
/*更新歌曲信息*/
    @CrossOrigin
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();

        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String lyric = request.getParameter("lyric").trim();

        Song song = new Song();
        song.setId(Integer.parseInt(id));
        song.setName(name);
        song.setLyric(lyric);
        song.setIntroduction(introduction);

        boolean flag1 = songService.update(song);
        if (flag1){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }

    @CrossOrigin
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public boolean deleteSong (HttpServletRequest request){
        String id = request.getParameter("id");
        return songService.delete(Integer.parseInt(id));
    }

    @CrossOrigin
    @RequestMapping(value = "/img/update",method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file")MultipartFile avatroFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
       if (avatroFile.isEmpty()){
           jsonObject.put(Consts.CODE,0);
           jsonObject.put(Consts.MSG,"头像更新失败");
           return jsonObject;
       }

       String fileName = System.currentTimeMillis()+avatroFile.getOriginalFilename();
       String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+
               System.getProperty("file.separator")+"songPic";
          File file2 = new File(filePath);
          if (!file2.exists()){
              file2.mkdir();
          }
          File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath = "/img/songPic/"+fileName;
        try {
            avatroFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);

            boolean flag = songService.update(song);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"图片上传成功！");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"图片上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"图片上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

/*更新歌曲*/
    @CrossOrigin
    @RequestMapping(value = "/url/update",method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file")MultipartFile avatroFile,HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id");

        if (avatroFile.isEmpty()) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲保存失败");
            return jsonObject;

        }
        String fileName = System.currentTimeMillis()+avatroFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file3 = new File(filePath);
        if (!file3.exists()) {
            file3.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/song/"+fileName;
        try {
            avatroFile.transferTo(dest);
            Song song = new Song();
            song.setId(Integer.parseInt(id));
            song.setUrl(storeAvatorPath);

            boolean flag = songService.update(song);
            if (flag) {
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"音乐更新成功！");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"音乐更新失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"音乐更新失败"+e.getMessage());
        } finally {
            return jsonObject;
        }
    }


/*
    @CrossOrigin
    @RequestMapping(value = "/url/update",method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file")MultipartFile avatroFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if (avatroFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"音乐更新失败");
            return jsonObject;
        }

        String fileName = System.currentTimeMillis()+avatroFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        File file2 = new File(filePath);
        if (!file2.exists()){
            file2.mkdir();
        }
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        String storeAvatorPath = "/song/"+fileName;
        try {
            avatroFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeAvatorPath);

            boolean flag = songService.update(song);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"音乐上传成功！");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"音乐上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"音乐上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }*/

}
