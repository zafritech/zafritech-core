package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_COUNTRIES")
public class Country implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
       
    private String uuId;
    
    private String countryName;
    
    @Column(unique = true, nullable = false)
    private String iso2;
    
    @Column(unique = true, nullable = false)
    private String iso3;
    
    private String isoNum;
    
    private String tld;
    
    private String dialCode;
    
    private String capital;
    
    private String flag;

    public Country() {
        
    }

    @Override
    public String toString() {
        
        return "Country{" + "id=" + id + ", uuId=" + uuId + ", countryName=" + 
                countryName + ", iso2=" + iso2 + ", iso3=" + iso3 + ", isoNum=" + 
                isoNum + ", tld=" + tld + ", dialCode=" + 
                dialCode + ", capital=" + capital + ", flag=" + flag + '}';
    }
    
    public Country(String countryName, String isoCode2, String dialCode) {
        
        this.uuId = UUID.randomUUID().toString();
        this.iso2 = isoCode2;
        this.countryName = countryName;
        this.dialCode = dialCode;
    }

    public Country(String countryName, 
                   String isoCode2, 
                   String isoCode3, 
                   String isoNumericCode, 
                   String tld, 
                   String dialCode, 
                   String capital,
                   String flag) {
        
        this.uuId = UUID.randomUUID().toString();
        this.countryName = countryName;
        this.iso2 = isoCode2;
        this.iso3 = isoCode3;
        this.isoNum = isoNumericCode;
        this.tld = tld;
        this.dialCode = dialCode;
        this.capital = capital;
        this.flag = flag;
    }

    public Long getId() {
        return id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
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

