package com.farmer.content.service.impl;

import com.farmer.content.service.ContentService;
import com.farmer.jedis.JedisClient;
import com.farmer.mapper.TbContentMapper;
import com.farmer.pojo.EasyUiDataGridResult;
import com.farmer.pojo.FarmerResult;
import com.farmer.pojo.JsonUtils;
import com.farmer.pojo.TbContent;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * create by sintai_zx
 * 2018/8/9 13:27
 */
@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;
    /**
        @Author sintai_zx
        @Date 2018/8/9 13:52
        @Discreption 根据内容分类id返回分页内容列表
    */
    public EasyUiDataGridResult getPageContentListByCatId(Integer page, Integer rows, Long catId) {
        PageHelper.startPage(page,rows);
        List<TbContent> contentList=contentMapper.selectByCategoryId(catId);
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contentList);
        EasyUiDataGridResult result=new EasyUiDataGridResult();
        result.setRows(contentList);
        result.setTotal((int)pageInfo.getTotal());
        return result;
    }

    public List<TbContent> getContentListByCid(Long cId) {
        //先查询缓存
        //添加缓存不能影响正常业务逻辑
        try {
            String json = jedisClient.hget(INDEX_CONTENT, cId + "");
            //json转换成list
            if (StringUtils.isNotBlank(json)) {
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //根据cid查询内容列表
        //执行查询
        List<TbContent> tbContentList=contentMapper.selectByCategoryId(cId);
        try {
            jedisClient.hset(INDEX_CONTENT, cId + "", JsonUtils.objectToJson(tbContentList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tbContentList;

    }



    /**
        @Author sintai_zx
        @Date 2018/8/10 9:59
        @Discreption 新增内容- - - 同步缓存
    */
    public FarmerResult saveContent(TbContent content) {
        //补全日期
        content.setCreated(new Date());
        content.setUpdated(new Date());
        int rowsCount=contentMapper.insertSelective(content);
        //同步缓存
        jedisClient.hdel(INDEX_CONTENT,content.getId().toString());
        if (rowsCount > 0) {
            return FarmerResult.ok();
        }
        return FarmerResult.build(100, "新增内容失败");
    }
    /**
        @Author sintai_zx
        @Date 2018/8/10 10:00
        @Discreption 编辑内容
    */
    public FarmerResult editContent(TbContent tbContent) {
        int rowsResult = contentMapper.updateByPrimaryKeySelective(tbContent);
        //同步缓存
        jedisClient.hdel(INDEX_CONTENT,tbContent.getId().toString());
        if (rowsResult > 0) {
            return FarmerResult.ok();
        }
        return FarmerResult.build(100, "编辑内容失败");
    }
    /**
        @Author sintai_zx
        @Date 2018/8/10 10:16
        @Discreption 删除内容
    */
    public FarmerResult deleteContent(String ids) {
       List<String> idsList= Splitter.on(",").splitToList(ids);
        //空值判断
        if(CollectionUtils.isEmpty(idsList)){
            return FarmerResult.build(400,"参数不能为空");
        }
        int rowsResutl = contentMapper.deleteByIdsList(idsList);
        //同步缓存
        for (String s : idsList) {
            //同步缓存
            jedisClient.hdel(INDEX_CONTENT,s);
        }
        if (rowsResutl > 0) {
            return FarmerResult.ok();
        }
        return FarmerResult.build(100, "删除失败");
    }
}
