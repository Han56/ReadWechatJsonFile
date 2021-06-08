package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import utils.UpdateData;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author https://github.com/Han56
 * @date 2021/6/6 20:01
 * File Class 的相关操作
 */
public class FileTest {


    public static void main(String[] args) {
        // 读取某一文件夹下的所有文件的文件名
        String dirPath = "C:/Users/13352/Desktop/jsonFiles/IntelMining";
        List<String> jsonFilesPath = getFileAbsolutePath(dirPath);
        for (String s : jsonFilesPath) {
            readJsonFile(s);
        }
    }

    //通过文件夹路径获取到该文件夹下的所有JSON文件的绝对路径并存在列表中，返回给主方法
    public static List<String> getFileAbsolutePath(String dirPath){
        List<String> fileNames = new ArrayList<>();
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        //使用 tempList 存储所有 JSON文件的绝对路径
        for (int i=0;i< tempList.length;i++){
            if (tempList[i].isFile()){
                fileNames.add(tempList[i].toString());
            }
        }
        System.out.println("获取到该文件夹下的所有JSON文件的绝对路径");
        return fileNames;
    }

    /**
     * 读取json文件，返回json串
     * @param fileName
     */
    public static void readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            UpdateData updateData = new UpdateData();
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            //jsonStr获取到的是json文件内的所有值
            jsonStr = sb.toString();
            //将jsonStr转换为 json 文件对象
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            String generalMsgListStr = jsonObject.get("general_msg_list").toString();
            JSONObject generalMsgListObject = JSONObject.parseObject(generalMsgListStr);
            String listStr = generalMsgListObject.get("list").toString();
            JSONArray list = generalMsgListObject.getJSONArray("list");
            for (int i = 0;i< list.size();i++){
                JSONObject key = (JSONObject)list.get(i);
                if (key.get("app_msg_ext_info").toString().isEmpty()){
                    break;
                }else {
                    String appMsgExtInfoStr = key.get("app_msg_ext_info").toString();
                    JSONObject appMsgExtInfoObject = JSONObject.parseObject(appMsgExtInfoStr);
                    System.out.println("文章标题:"+appMsgExtInfoObject.get("title")
                            +"  作者:"+appMsgExtInfoObject.get("author")
                            +"  封面图片url:"+appMsgExtInfoObject.get("cover")
                            +"  摘要:"+appMsgExtInfoObject.get("digest")
                            +"  内容url:"+appMsgExtInfoObject.get("content_url"));
                    //如果内容url为空，则认为该条信息无用
                    if (!appMsgExtInfoObject.get("content_url").toString().isEmpty()){
                        //每次存储一条数据至数据库
                        updateData.updateSql(appMsgExtInfoObject.get("author").toString(),
                                appMsgExtInfoObject.get("title").toString(),
                                appMsgExtInfoObject.get("cover").toString(),
                                appMsgExtInfoObject.get("digest").toString(),
                                appMsgExtInfoObject.get("content_url").toString());
                    }
                }

            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}