package com.farmer.content.service;


import com.farmer.pojo.EasyUiDataGridResult;
import com.farmer.pojo.FarmerResult;
import com.farmer.pojo.TbContent;

import java.util.List;

public interface ContentService {

    public EasyUiDataGridResult getPageContentListByCatId(Integer page, Integer rows, Long catId);

    public List<TbContent> getContentListByCid(Long cId);

    public FarmerResult saveContent(TbContent content);

    public FarmerResult deleteContent(String ids);

    public FarmerResult editContent(TbContent tbContent);
}
