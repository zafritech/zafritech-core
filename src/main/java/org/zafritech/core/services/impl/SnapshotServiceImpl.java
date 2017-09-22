/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.PlotSeriesDao;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.Snapshot;
import org.zafritech.core.data.domain.SnapshotValue;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.SnapshotRepository;
import org.zafritech.core.data.repositories.SnapshotValueRepository;
import org.zafritech.requirements.enums.ItemStatus;
import org.zafritech.core.enums.SnapshotType;
import org.zafritech.core.services.SnapshotService;

/**
 *
 * @author LukeS
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {

    @Autowired
    private SnapshotRepository snapshotRepository;

    @Autowired
    private SnapshotValueRepository snapshotValueRepository;

    @Autowired
    private ProjectRepository projectRepository;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void CreateSnapshots() {
        
        System.out.println("Creating data snapshots at " + DATE_FORMAT.format(new Date()));
        
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add); 
        
        for (Project project : projects) {

            getProjectSnapshots(project);
        }
    }
    
    @Override
    public PlotSeriesDao getPlotSeries(Long projectId, String statusName, String seriesLabel) {
        
        Project project = projectRepository.findOne(projectId);
        List<Snapshot> snapshots = snapshotRepository.findAllByProjectOrderBySnapshotDateAsc(project);
        
        List<Object[]> seriesItems = new ArrayList<Object[]>();
        
         for (Snapshot snapshot : snapshots) {
             
             for (SnapshotValue value : snapshot.getSnapshotValues()) {
                 
                 String statusVariable = value.getComponentVariable();
                 
                 if (statusVariable.equalsIgnoreCase(statusName)) {
                     
                     seriesItems.add(new Object[]{value.getCreationTime(), Integer.valueOf(value.getValue())});
                 }
             }
         }
        
        return new PlotSeriesDao(seriesLabel, seriesItems);
    }
    
    private void getProjectSnapshots(Project project) {
        
        Snapshot snapshot = new Snapshot(project, SnapshotType.SNAPSHOT_TYPE_REQUIREMENT); 
        snapshot = snapshotRepository.save(snapshot);

        snapshot.getSnapshotValues().add(snapshotValueRepository.save(new SnapshotValue("Requirements", ItemStatus.ITEM_STATUS_OPEN.name(), countRequirements("STATUS_OPEN").toString(), "Integer")));
        snapshot.getSnapshotValues().add(snapshotValueRepository.save(new SnapshotValue("Requirements", ItemStatus.ITEM_STATUS_VERIFIED.name(), countRequirements("STATUS_VERIFIED").toString(), "Integer")));
        snapshot.getSnapshotValues().add(snapshotValueRepository.save(new SnapshotValue("Requirements", ItemStatus.ITEM_STATUS_CLOSED.name(), countRequirements("STATUS_CLOSED").toString(), "Integer")));

        snapshot = snapshotRepository.save(snapshot);
        
        System.out.println(snapshot);
    }
    
    private Integer countRequirements(String status) {
        
        Random rand = new Random();
        
        int Low = 20;
        int High = 200;
        
        return rand.nextInt(High - Low) + Low;
    }
}
