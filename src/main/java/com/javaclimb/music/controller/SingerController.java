package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.service.SingerService;
import com.javaclimb.music.utils.Consts;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@CrossOrigin
@RestController
@RequestMapping(value = "/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;

    /*添加歌手*/
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSinger (HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String sex = request.getParameter("sex").trim();
        String pic = request.getParameter("pic").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();
        /*生日转换成Date格式*/
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*保存歌手到对象中*/
        Singer singer = new Singer();
        //singer.setId(Integer.parseInt(id));
        singer.setSex(new Byte(sex));
        singer.setName(name);
        singer.setPic(pic);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        boolean flag = singerService.insert(singer);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功！");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加失败~");
        return jsonObject;
    }

    /*修改歌手*/
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String sex = request.getParameter("sex").trim();
        String pic = request.getParameter("pic").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();

        /*生日转换成Date格式*/
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Singer singer = new Singer();
        singer.setId(Integer.parseInt(id));
        singer.setPic(pic);
        singer.setSex(new Byte(sex));
        singer.setName(name);
        singer.setIntroduction(introduction);
        singer.setLocation(location);
        singer.setBirth(birthDate);

        boolean flag = singerService.update(singer);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"修改失败");
            return jsonObject;
    }


    /*删除歌手*/
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        boolean flag = singerService.delete(Integer.parseInt(id));
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"删除成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"查无此人！");
        return jsonObject;
    }


    /*根据主键查询所有对象*/
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.POST)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return singerService.selectByPrimaryKey(Integer.parseInt(id));
    }



    /*查询所有歌手*/
    @RequestMapping(value = "/allSinger",method = RequestMethod.GET)
    public Object allSinger() {
        return singerService.allSinger();
    }


    /*根据歌手名模糊查询*/
    @RequestMapping(value = "/singerOfName",method = RequestMethod.POST)
    public Object singerOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return singerService.singerOfName("%"+name+"%");
    }

    /*根据歌手性别查询*/
    @RequestMapping(value = "/singerOfSex",method = RequestMethod.POST)
    public Object singerOfSex(HttpServletRequest request){
        String sex = request.getParameter("sex").trim();
        return singerService.singerOfSex(Integer.parseInt(sex));
    }


    /*更新歌手图片*/
    @RequestMapping(value = "avatar/update",method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file")MultipartFile avatroFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if (avatroFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
        return jsonObject;
        }


        String fileName = System.currentTimeMillis()+avatroFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+
                System.getProperty("file.separator")+"singerPic";
        File file1 = new File(filePath);
        if (!file1.exists()){
          file1.mkdir();
        }
        //实际文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //数据库文件地址
        String storeAvatorPath = "/img/singerPic/"+fileName;
        try {
            avatroFile.transferTo(dest);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            boolean flag = singerService.update(singer);
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
}
