package com.farmer.content.service.impl;

import com.farmer.content.service.ContentCatService;
import com.farmer.mapper.TbContentCategoryMapper;
import com.farmer.pojo.EasyUITreeNode;
import com.farmer.pojo.TbContentCategory;
import com.farmer.pojo.TbContentCategoryExample;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by sintai_zx
 * 2018/8/7 14:45
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 查询内容列表
     * @param parentId
     * @return
     */
    public List<EasyUITreeNode> getContentCatListById(Long parentId) {
        List<EasyUITreeNode> easyUITreeNodeList= Lists.newArrayList();
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> resultList=contentCategoryMapper.selectByExample(example);
        for (TbContentCategory tbContentCategory : resultList) {
            EasyUITreeNode node=new EasyUITreeNode();
            node.setText(tbContentCategory.getName());
            node.setId(tbContentCategory.getId());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            easyUITreeNodeList.add(node);
        }
        return easyUITreeNodeList;
    }
}
