/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.Snapshot;
import org.zafritech.core.data.domain.SnapshotValue;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.SnapshotRepository;
import org.zafritech.core.data.repositories.SnapshotValueRepository;
import org.zafritech.core.enums.SnapshotType;
import org.zafritech.requirements.enums.ItemStatus;

/**
 *
 * @author LukeS
 */
@Component
public class SnaphotsInit {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private SnapshotRepository snapshotRepository;
    
    @Autowired
    private SnapshotValueRepository snapshotValueRepository;
    
    @Transactional
    public void init() {
        
        Calendar calendar = new GregorianCalendar(2016,04,21);
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add); 

        for (int i=1; i<15; i++) {

            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DATE, 1);
            calendar.add(Calendar.DATE, -1);

            for (Project project : projects) {

                // Initialise Projects' Progress
                initialiseProjectProgress(project, calendar);

                // Initialise Requirements' Status
                initialiseRequirementsStatus(project, calendar);
            }

            calendar.add(Calendar.MONTH, 1);
        }
    }
    
    private void initialiseRequirementsStatus(Project project, Calendar calendar) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Snapshot snapshot = new Snapshot(project, SnapshotType.SNAPSHOT_TYPE_REQUIREMENT); 
        snapshot.setSnapshotDate(calendar.getTime());  
        snapshot = snapshotRepository.save(snapshot);

        SnapshotValue openValue = new SnapshotValue("Requirements", ItemStatus.ITEM_STATUS_OPEN.name(), countRequirements("STATUS_OPEN").toString(), "Integer");
        openValue.setCreationTime(calendar.getTime());
        snapshotValueRepository.save(openValue);
        snapshot.getSnapshotValues().add(openValue);

        SnapshotValue verifiedValue = new SnapshotValue("Requirements", ItemStatus.ITEM_STATUS_VERIFIED.name(), countRequirements("STATUS_VERIFIED").toString(), "Integer");
        verifiedValue.setCreationTime(calendar.getTime());
        snapshotValueRepository.save(verifiedValue);
        snapshot.getSnapshotValues().add(verifiedValue);

        SnapshotValue closedValue = new SnapshotValue("Requirements", ItemStatus.ITEM_STATUS_CLOSED.name(), countRequirements("STATUS_CLOSED").toString(), "Integer");
        closedValue.setCreationTime(calendar.getTime());
        snapshotValueRepository.save(closedValue);
        snapshot.getSnapshotValues().add(closedValue);

        snapshot = snapshotRepository.save(snapshot);

        Logger.getLogger(SnaphotsInit.class.getName()).log(Level.INFO, snapshot.toString());
    }
    
    private void initialiseProjectProgress(Project project, Calendar calendar) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Snapshot snapshot = new Snapshot(project, SnapshotType.SNAPSHOT_TYPE_PROJECT);
        
        SnapshotValue progressValue = new SnapshotValue("Projects", "PROJECT_PROGRESS", String.valueOf(findProjectProgress()), "Float");
        progressValue.setCreationTime(calendar.getTime());
//        snapshotValueRepository.save(progressValue);
        snapshot.getSnapshotValues().add(progressValue);
        
        snapshot = snapshotRepository.save(snapshot);
        
        Logger.getLogger(SnaphotsInit.class.getName()).log(Level.INFO, snapshot.toString());
    }
    
    private Integer countRequirements(String status) {
        
        Random rand = new Random();
        
        int Low = 20;
        int High = 200;
        
        return rand.nextInt(High - Low) + Low;
    }
    
    private float findProjectProgress() {

        float minX = 10.0f;
        float maxX = 100.0f;

        Random rand = new Random();

        return rand.nextFloat() * (maxX - minX) + minX;
    }
}
