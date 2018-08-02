package com.farmer.controller;

import com.farmer.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * create by sintai_zx
 * 2018/8/2 14:10
 */
@Controller
public class PictureController {
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map multiPicUpload(MultipartFile uploadFile) {
        try{
            //接收上传文件
            //取扩展名
            String originalName=uploadFile.getOriginalFilename();
            String extName = originalName.substring(originalName.lastIndexOf(".") +1);
            //上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
            String url=fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
            url=IMAGE_SERVER_URL+url;
            //返回结果图片URL(格式简单，直接使用map装配)
            Map result = new HashMap();
            result.put("error", 0);
            result.put("url", url);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error", 1);
            result.put("message", "上传图片失败！！！");
            return result;
        }

    }
}
