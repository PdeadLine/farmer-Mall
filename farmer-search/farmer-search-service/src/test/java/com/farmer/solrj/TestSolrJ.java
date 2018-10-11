package com.farmer.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestSolrJ {

    @Test
    public void testAddDocument() throws Exception {
        //solrServer对象
        //指定solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.88.127:8080/solr/zixun/");
        //创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档添加域（属性），必须有id值，域必须在schema中定义好
        document.addField("id","Test01");
        document.addField("item_title","测试添加索引一号");
        document.addField("item_price",9999);
        //把文档对象写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public void deleteDocumentById() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.88.127:8080/solr/zixun/");
        solrServer.deleteById("Test01");
        solrServer.commit();
    }

    @Test
    public void deleteDocumentByQuery() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.88.127:8080/solr/zixun/");
        solrServer.deleteByQuery("zixunName:资讯管理课");
        solrServer.commit();
    }

    @Test
    public void searchDocument() throws  Exception {
        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.88.127:8080/solr/zixun/");
        //创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件、过滤条件、分页条件、排序条件、高亮；
       // solrQuery.set("q", "*:*");
        solrQuery.setQuery("手机");
        //分页条件
        solrQuery.setStart(0);
        solrQuery.setRows(10);
        //设置默认搜索域
        solrQuery.set("df", "item_keywords");
        //高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");

        // 执行查询，得到一个response对象
        QueryResponse response=solrServer.query(solrQuery);
        //取查询结果
        SolrDocumentList solrDocumentList=response.getResults();
        //取查询结果总记录数
        System.out.println("查询结果总记录数："+solrDocumentList.getNumFound());

        for (SolrDocument solrDocument : solrDocumentList) {

            //取高亮显示
            //取高亮集合
            Map<String,Map<String, List<String>>> highLignting=response.getHighlighting();
            List<String> list=highLignting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = "";
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }

            System.out.println(solrDocument.get("id"));
            System.out.println(itemTitle);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
            System.out.println("<===============================================>");
        }



    }
}
