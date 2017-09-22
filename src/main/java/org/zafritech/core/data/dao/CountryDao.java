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
public class CountryDao {
    
    private String countryName;
    
    private String iso2;
    
    private String iso3;
    
    private String isoNum;
    
    private String tld;
    
    private String dialCode;
    
    private String capital;
    
    private String flag;

    @Override
    public String toString() {
        
        return "CountriesDao{" + "countryName=" + getCountryName() + ", iso2=" + 
                getIso2() + ", iso3=" + getIso3() + ", isoNum=" + getIsoNum() + ", tld=" + 
                getTld() + ", dialCode=" + getDialCode() + ", capital=" + 
                getCapital() + ", flag=" + getFlag() + '}';
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIsoNum() {
        return isoNum;
    }

    public void setIsoNum(String isoNum) {
        this.isoNum = isoNum;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
