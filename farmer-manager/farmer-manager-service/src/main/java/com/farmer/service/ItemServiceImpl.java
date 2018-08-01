package com.farmer.service;

import com.farmer.mapper.TbItemMapper;
import com.farmer.pojo.EasyUiDataGridResult;
import com.farmer.pojo.TbItem;
import com.farmer.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by sintai_zx
 * 2018/7/31 10:45
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;

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
}
