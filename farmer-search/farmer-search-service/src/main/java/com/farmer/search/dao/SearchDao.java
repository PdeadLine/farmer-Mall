package com.farmer.search.dao;


import com.farmer.pojo.SearchItem;
import com.farmer.pojo.SearchResult;
import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @Description: 查询索引库DAO
* @CopyRight (C)
* @author: SintaiZixun
* @date: 2018/10/10-16:15
*/
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;


    /**
    * @Description: 根据query条件进行查询
    * @CopyRight (C)
    * @author: SintaiZixun
    * @date: 2018/10/11-7:55
    */
    public SearchResult search(SolrQuery query) throws Exception{
        SearchResult searchResult = new SearchResult();
        List<SearchItem> itemLIst = Lists.newArrayList();
       //根据query对象进行查询
        QueryResponse solrResponse=solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList =  solrResponse.getResults();
        //取查询总记录数
        searchResult.setRecordCount(solrDocumentList.getNumFound());
        //将查询结果装配SearchItem对象中
        for (SolrDocument solrDocument : solrDocumentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) solrDocument.get("id"));
            //取高亮显示字段
            Map<String, Map<String, List<String>>> highlighting = solrResponse.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list !=null && list.size()>0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(title);
            searchItem.setCategory_name((String)solrDocument.get("item_category_name"));
            searchItem.setPrice((long)solrDocument.get("item_price"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setSell_point((String)solrDocument.get("item_sell_point"));

            itemLIst.add(searchItem);
        }
        //将查询结果添加到SearchResult中
        searchResult.setItemList(itemLIst);
        //返回
        return searchResult;
    }

}
