package com.farmer.search.service;

import com.farmer.pojo.FarmerResult;
import com.farmer.pojo.SearchResult;

public interface SearchItemService {

    public FarmerResult importItemToIndex();

    public SearchResult search(String q,int page,int rows)throws Exception;
}
