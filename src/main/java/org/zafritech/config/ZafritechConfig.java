/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author LukeS
 */
@Component
@ConfigurationProperties(prefix = "zafritech")
public class ZafritechConfig {
    
    private Application application;
    
    private Admin adminuser;
    
    private Paths paths;
    
    private Snapshots snapshots;
    
    public static class Application {
        
        private String companyName;
        
        private String appName;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }
    }
    
    public static class Admin {
        
        private String email;
        
        private String username;
        
        private String firstname;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
    }
    
    public static class Paths {

        private String dataDir;

        private String imagesDir;

        private String uploadDir;

        public String getDataDir() {
            return dataDir;
        }

        public void setDataDir(String dataDir) {
            this.dataDir = dataDir;
        }

        public String getImagesDir() {
            return imagesDir;
        }

        public void setImagesDir(String imagesDir) {
            this.imagesDir = imagesDir;
        }

        public String getUploadDir() {
            return uploadDir;
        }

        public void setUploadDir(String uploadDir) {
            this.uploadDir = uploadDir;
        }
    }

     public static class Snapshots {
         
         private String cronExpression;

        public String getCronExpression() {
            return cronExpression;
        }

        public void setCronExpression(String cronExpression) {
            this.cronExpression = cronExpression;
        }
     }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Admin getAdminuser() {
        return adminuser;
    }

    public void setAdminuser(Admin adminuser) {
        this.adminuser = adminuser;
    }

    public Paths getPaths() {
        return paths;
    }

    public void setPaths(Paths paths) {
        this.paths = paths;
    }

    public Snapshots getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(Snapshots snapshots) {
        this.snapshots = snapshots;
    }
}
