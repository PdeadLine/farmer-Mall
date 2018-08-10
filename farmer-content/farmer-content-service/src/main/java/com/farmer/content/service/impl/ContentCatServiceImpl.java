package com.farmer.content.service.impl;

import com.farmer.content.service.ContentCatService;
import com.farmer.mapper.TbContentCategoryMapper;
import com.farmer.pojo.EasyUITreeNode;
import com.farmer.pojo.FarmerResult;
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

    /**
        @Author sintai_zx
        @Date 2018/8/8 14:44
        @Discreption 新增内容节点
    */
    public FarmerResult addContentCategory(Long parentId,String name) {
        // 1、接收两个参数：parentId、name
        // 2、向tb_content_category表中插入数据。
        // a)创建一个TbContentCategory对象
        TbContentCategory category=new TbContentCategory();
        // b)补全TbContentCategory对象的属性
        category.setIsParent(false);
        category.setName(name);
        category.setParentId(parentId);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        category.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        category.setStatus(1);
        // c)向tb_content_category表中插入数据
        contentCategoryMapper.insert(category);
        // 3、判断父节点的isparent是否为true，不是true需要改为true。
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            //更新父节点
            parentNode.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        // 4、需要主键返回。
        // 5、返回TaotaoResult，其中包装TbContentCategory对象
        return FarmerResult.ok(category);
    }

    /**
        @Author sintai_zx
        @Date 2018/8/9 8:23
        @Discreption 重命名内容节点
    */
    public FarmerResult updateContentCatById(Long id, String name) {
        if (id != null && name != null) {
            TbContentCategory contentCategory=new TbContentCategory();
            contentCategory.setId(id);
            contentCategory.setName(name);
            int rowsResult=contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
            if (rowsResult > 0) {
                return FarmerResult.ok();
            }
        }
        return FarmerResult.build(100, "参数不能为空");

    }
    /**
        @Author sintai_zx
        @Date 2018/8/9 9:23
        @Discreption 删除子节点
    */
    public FarmerResult deleteContentCatById(Long id) {
        TbContentCategory contentCategory=contentCategoryMapper.selectByPrimaryKey(id);

        //        2、判断父节点下是否还有子节点，如果没有需要把父节点的isparent改为false
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(contentCategory.getParentId());
        List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
        if (contentCategoryList.size() <= 1) {
         TbContentCategory parentContentCat=new TbContentCategory();
         parentContentCat.setIsParent(false);
         parentContentCat.setId(contentCategory.getParentId());
            contentCategoryMapper.updateByPrimaryKeySelective(parentContentCat);
        }
//      3、如果删除的是父节点，子节点要级联删除。
//        两种解决方案：
//        1）如果判断是父节点不允许删除。
//        2）递归删除。

        if (contentCategory.getIsParent()) {
            return FarmerResult.build(100, "该节点下还有子节点不允许直接删除");
        }
//      根据id删除记录。
        contentCategoryMapper.deleteByPrimaryKey(id);
        return FarmerResult.ok();
    }
}
