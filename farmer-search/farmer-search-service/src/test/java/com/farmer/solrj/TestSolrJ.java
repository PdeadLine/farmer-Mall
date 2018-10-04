package com.farmer.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

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
}
