package com.farmer.service;

import com.farmer.mapper.TbItemDescMapper;
import com.farmer.mapper.TbItemMapper;
import com.farmer.pojo.*;
import com.farmer.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        @Date 2018/8/4 14:26
        @Discreption 查询商品描述
    */
    public TbItemDesc getItemDescByID(long itemId) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        return itemDesc;
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
        //补全TbItem
        TbItem tbItem=assembleTbItem(item);
        //向商品表插入数据
        itemMapper.insert(tbItem);
        //补全TbItemDesc
        TbItemDesc itemDesc=assembleTbItemDesc(desc, item);
        //6、向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        //7farmerResult.ok()
        return FarmerResult.ok();
    }
    /**
        @Author sintai_zx
        @Date 2018/8/3 9:58
        @Discreption 补全TbItem
    */
    private TbItem assembleTbItem(TbItem item) {

        //生成商品id
        long itemId= item.getId()!=null?item.getId():IDUtils.genItemId();
        //补全TbItem对象的属性
        item.setId(itemId);
        //商品状态1-正常，2-下架，3-删除
        item.setStatus(Const.ProductStatusEnum.ON_SALE.getCode());
        //这一段可以直接在sql.xml中修改 使用sql的时间函数now()
//        Date date = new Date();
//        item.setCreated(date);
//        item.setUpdated(date);
        return item;
    }

    /**
        @Author sintai_zx
        @Date 2018/8/3 10:01
        @Discreption 补全TbItemDesc
    */
    private TbItemDesc assembleTbItemDesc(String desc, TbItem item) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
//        itemDesc.setCreated(item.getCreated());
//        itemDesc.setUpdated(item.getUpdated());
        return itemDesc;
    }

    /**
        @Author sintai_zx
        @Date 2018/8/3 11:17
        @Discreption 编辑商品
    */
    public FarmerResult editItem(TbItem item, String desc) {
        TbItem tbItem=assembleTbItem(item);
        itemMapper.updateByPrimaryKeySelective(tbItem);
        TbItemDesc tbItemDesc = assembleTbItemDesc(desc,item);
        itemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        return FarmerResult.ok();
    }

    /**
     * @Author sintai_zx
     * @Date 2018/8/3 12:28
     * @Discreption 删除商品
     */
    public FarmerResult deleteItemByIds(String itemIds) {
        List<String> itemIdList = Splitter.on(",").splitToList(itemIds);
       //空值判断
        if(CollectionUtils.isEmpty(itemIdList)){
            return FarmerResult.build(400,"参数不能为空");
        }
        int itemCount = itemMapper.deleteByItemIds(itemIdList);
        int itemDescCount = itemDescMapper.deleteByItemIds(itemIdList);
        if (itemCount > 0 && itemDescCount > 0) {
            return FarmerResult.ok();
        }
        return FarmerResult.build(400, "删除失败");
    }


    /**
     * 上下架
     * @param itemId
     * @param status
     * @return
     */
    public FarmerResult setSaleStatus(long itemId,Integer status){
      return FarmerResult.ok();
    }
}
