/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.Link;

/**
 *
 * @author LukeS
 */
public interface LinkRepository extends CrudRepository<Link, Long> {
    
    List<Link> findBySrcItem(Item item);
    
    List<Link> findByDstItem(Item item);
}
