package com.farmer.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * create by sintai_zx
 * 2018/8/1 11:23
 */
public class EasyUiDataGridResult implements Serializable{
    private Integer total;
    private List<?> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
