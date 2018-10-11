package com.farmer.pojo;


import java.io.Serializable;
import java.util.List;

/**
* @Description: 搜索结果-实体类
* @CopyRight (C)
* @author: SintaiZixun
* @date: 2018/10/10-11:33
*/
public class SearchResult implements Serializable {
    private long totalPages;
    private long recordCount;
    private List<SearchItem> itemList;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
