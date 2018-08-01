package com.farmer.service;

import com.farmer.pojo.EasyUiDataGridResult;
import com.farmer.pojo.TbItem;

public interface ItemService {

    public TbItem getItemById(long itemId);
    public EasyUiDataGridResult getItemList(int page, int rows);
}
