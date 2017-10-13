/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.PlotSeriesDao;
import org.zafritech.requirements.enums.ItemStatus;
import org.zafritech.core.services.SnapshotService;

/**
 *
 * @author LukeS
 */
@RestController
public class SnapshotRestController {
   
    @Autowired
    private SnapshotService snapshotService;
 
    @RequestMapping("/api/snapshots/requirements/{projectId}")
    public @ResponseBody Object[] getSnapshotsList(@PathVariable(value = "projectId") Long projectId, Model model) {
        
        PlotSeriesDao openSeries = snapshotService.getPlotSeries(projectId, ItemStatus.ITEM_STATUS_OPEN.name(), "Open");
        PlotSeriesDao verifiedSeries = snapshotService.getPlotSeries(projectId, ItemStatus.ITEM_STATUS_VERIFIED.name(), "Verified");
        PlotSeriesDao closedSeries = snapshotService.getPlotSeries(projectId, ItemStatus.ITEM_STATUS_CLOSED.name(), "Closed");
        
        return new Object[]{openSeries, verifiedSeries, closedSeries};
    }
}