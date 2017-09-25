/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

/**
 *
 * @author LukeS
 */
public class FolderTreeDao {
    
    private Long id;
    private Long pId;
    private String name;
    private boolean open;
    private boolean isParent;
    private boolean click;
    private String icon;
    private Long linkId;

    public FolderTreeDao() {
        
    }

    public FolderTreeDao(Long id, Long pId, String name, boolean open, boolean isParent, boolean click) {
        
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.isParent = isParent;
        this.click = click;
    }

    public FolderTreeDao(Long id, Long pId, String name, boolean open, boolean isParent, boolean click, Long linkId) {
        
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.isParent = isParent;
        this.click = click;
        this.linkId = linkId;
    }

    public FolderTreeDao(Long id, Long pId, String name, boolean open, boolean isParent, boolean click, String icon, Long linkId) {
        
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.isParent = isParent;
        this.click = click;
        this.icon = icon;
        this.linkId = linkId;
    }

    @Override
    public String toString() {
        
        return "FolderTreeDao{" + "id=" + getId() + ", pId=" + getpId() + ", name=" + 
                getName() + ", open=" + isOpen() + ", isParent=" + isIsParent() + ", click=" + 
                isClick() + ", linkId=" + getLinkId() + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }
}
