package com.farmer.service;

import com.farmer.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

    public List<EasyUITreeNode> getCatList(long parentId);
}
