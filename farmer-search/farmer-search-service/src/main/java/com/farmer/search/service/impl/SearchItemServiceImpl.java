package com.farmer.search.service.impl;


import com.farmer.pojo.FarmerResult;
import com.farmer.pojo.SearchItem;
import com.farmer.pojo.SearchResult;
import com.farmer.search.dao.SearchDao;
import com.farmer.search.mapper.SearchItemMapper;
import com.farmer.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("searchItemService")
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SearchDao searchDao;
    @Autowired
    private SolrServer solrServer;

    /**
    * @Description:商品数据导入索引库
    * @CopyRight (C)
    * @author: SintaiZixun
    * @date: 2018/10/5-10:55
    */
    @Override
    public FarmerResult importItemToIndex() {
        try {
            //1、查询所有商品数据
            List<SearchItem> searchItemList = searchItemMapper.getItemList();
            //2、遍历商品添加到索引库
            for (SearchItem searchItem : searchItemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档对象添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                document.addField("item_desc", searchItem.getItem_desc());
                //把文档写入索引库
                solrServer.add(document);
            }
            //3、提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            FarmerResult.build(500, "数据导入失败！");
        }
        //4、返回成功信息
        return FarmerResult.ok();
    }

    /**
     *
     * @param queryString 查询条件
     * @param page 页码
     * @param rows 每页显示的记录数
     * @return
     * @throws Exception
     */

    public SearchResult search(String queryString, int page, int rows)throws Exception  {
//        1、创建一个SolrQuery对象。
        SolrQuery query = new SolrQuery();
//        2、设置查询条件
        query.setQuery(queryString);
//        3、设置分页条件
        query.setStart((page-1)*rows);
        query.setRows(rows);
//        4、需要指定默认搜索域。
        query.set("df", "item_title");
//        5、设置高亮
        query.setHighlight(true);
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
//        6、执行查询，调用SearchDao。得到SearchResult
        SearchResult result=searchDao.search(query);
//        7、需要计算总页数。
        long recordCount = result.getRecordCount();
        long pageCount = recordCount / rows;
        if (recordCount % rows > 0) {
            pageCount++;
        }
        result.setTotalPages(pageCount);
//        8、返回SearchResult
        return result;
    }

}
