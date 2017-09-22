/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zafritech.core.data.dao.PageNavigationDao;
import org.zafritech.core.services.CommonService;
import org.zafritech.requirements.data.dao.SearchDao;
import org.zafritech.requirements.data.domain.Item;

/**
 *
 * @author LukeS
 */
@Repository
@Transactional
public class ItemSearch {
    
    @Autowired
    private CommonService commonService;
     
    @PersistenceContext
    private EntityManager entityManager;
    
    public SearchDao search(String text, Long pageSize, Long page) {
        
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
    
        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Item.class).get();
    
        // a very basic query by keywords
        org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("itemValue", "sysId", "identifier")
                                                                     .matching(text)
                                                                     .createQuery();
        
        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Item.class);
        
        // execute search and return results (sorted by relevance as default)
        @SuppressWarnings("unchecked")
                
        int size = jpaQuery.getResultSize();
        jpaQuery.setFirstResult((pageSize.intValue() * (page.intValue() - 1))); 
        jpaQuery.setMaxResults(pageSize.intValue());

        SearchDao searchResults = new SearchDao();
        PageNavigationDao navigator = commonService.getPageNavigator(size, pageSize.intValue(), page.intValue());
        List<Item> list = jpaQuery.getResultList();
        
        searchResults.setItemsList(list);
        searchResults.setResultsSize(size);
        searchResults.setPageSize(pageSize.intValue()); 
        searchResults.setCurrentPage(page.intValue());
        searchResults.setLastPage(navigator.getLastPage()); 
        searchResults.setPageList(navigator.getPageList()); 
        searchResults.setLastDisplayed(navigator.getPageCount());
        
        return searchResults;
    }
}
