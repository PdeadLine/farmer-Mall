package com.farmer.service;

import com.farmer.mapper.TbItemCatMapper;
import com.farmer.pojo.EasyUITreeNode;
import com.farmer.pojo.TbItemCat;
import com.farmer.pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * create by sintai_zx
 * 2018/8/1 16:42
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
    @Autowired
    private TbItemCatMapper itemCatMapper;
  
    /**
        @Author sintai_zx
        @Date 2018/8/1 17:05
        @Discreption 使用父节点查询分类节点
    */
    @Override
    public List<EasyUITreeNode> getCatList(long parentId) {
        //1.根据parentId查询节点
        TbItemCatExample example=new TbItemCatExample();
        //设置查询条件
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCatList=itemCatMapper.selectByExample(example);
        //2、组装EasyUITreeNode对象
        List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCatList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");

            easyUITreeNodeList.add(node);
        }
        //3、返回结果
        return easyUITreeNodeList;
    }
}
