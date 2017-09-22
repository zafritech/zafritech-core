/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zafritech.core.services.SnapshotService;

/**
 *
 * @author LukeS
 */
@Component
public class ScheduledTasks {
    
    @Autowired
    private SnapshotService snapshotService;
 
    @Scheduled(cron = "${zafritech.snapshots.cron-expression}")
    public void performSnapshotsCronTask() {
        
        snapshotService.CreateSnapshots();
    }
}
