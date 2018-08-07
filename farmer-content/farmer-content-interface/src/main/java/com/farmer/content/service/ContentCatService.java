package com.farmer.content.service;

import com.farmer.pojo.EasyUITreeNode;

import java.util.List;

/**
 * create by sintai zx
 */
public interface ContentCatService {
    public List<EasyUITreeNode> getContentCatListById(Long parentId);
}
