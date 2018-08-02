package com.farmer.service;

import com.farmer.mapper.TbItemDescMapper;
import com.farmer.mapper.TbItemMapper;
import com.farmer.pojo.*;
import com.farmer.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * create by sintai_zx
 * 2018/7/31 10:45
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    /**
        @Author sintai_zx
        @Date 2018/8/1 11:34
        @Discreption 通过商品id查询
    */
    public TbItem getItemById(long itemId) {
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        return tbItem;
    }
    /**
        @Author sintai_zx
        @Date 2018/8/1 11:33
        @Discreption 后台商品列表分页
    */
    public EasyUiDataGridResult getItemList(int page,int rows) {
        //设置分页信息
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> tbItemList = itemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItemList);
        //返回结果对象
        EasyUiDataGridResult result = new EasyUiDataGridResult();
        result.setTotal((int)pageInfo.getTotal());
        result.setRows(tbItemList);
        return result;
    }
    /**
        @Author sintai_zx
        @Date 2018/8/2 16:44
        @Discreption 添加商品
    */
    public FarmerResult addItem(TbItem item, String desc) {
        //生成商品id
        long itemId= IDUtils.genItemId();
        //补全TbItem对象的属性
        item.setId(itemId);
        //商品状态1-正常，2-下架，3-删除
        item.setStatus((byte)1);
       //这一段可以直接在sql.xml中修改 使用sql的时间函数now()
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //向商品表插入数据
        itemMapper.insert(item);
        //4、创建一给TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
        // 5、补全TbItemDesc的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //6、向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        //7farmerResult.ok()
        return FarmerResult.ok();
    }
}
