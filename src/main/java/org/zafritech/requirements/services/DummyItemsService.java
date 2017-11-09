/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services;

import java.util.List;
import org.zafritech.requirements.data.dao.DummyItemDao;
import org.zafritech.requirements.data.domain.Item;

/**
 *
 * @author LukeS
 */
public interface DummyItemsService {
    
    List<Item> createDummyItems(DummyItemDao dao);
}
