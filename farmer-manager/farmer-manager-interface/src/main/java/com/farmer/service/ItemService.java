package com.farmer.service;

import com.farmer.pojo.EasyUiDataGridResult;
import com.farmer.pojo.FarmerResult;
import com.farmer.pojo.TbItem;
import com.farmer.pojo.TbItemDesc;

public interface ItemService {

    public TbItem getItemById(long itemId);
    public TbItemDesc getItemDescByID(long itemId);
    public EasyUiDataGridResult getItemList(int page, int rows);
    public FarmerResult addItem(TbItem item, String desc);
    public FarmerResult editItem(TbItem item, String desc);

    public FarmerResult deleteItemByIds(String itemIds);
}
