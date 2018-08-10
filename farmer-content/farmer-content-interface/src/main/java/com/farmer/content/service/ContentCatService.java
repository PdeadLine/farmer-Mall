package com.farmer.content.service;

import com.farmer.pojo.EasyUITreeNode;
import com.farmer.pojo.FarmerResult;

import java.util.List;

/**
 * create by sintai zx
 */
public interface ContentCatService {

    public List<EasyUITreeNode> getContentCatListById(Long parentId);

    public FarmerResult addContentCategory(Long parentId, String name);

    public FarmerResult updateContentCatById(Long id, String name);

    public FarmerResult deleteContentCatById(Long id);
}
