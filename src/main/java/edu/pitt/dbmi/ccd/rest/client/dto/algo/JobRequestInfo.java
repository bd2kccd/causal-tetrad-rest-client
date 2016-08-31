package edu.pitt.dbmi.ccd.rest.client.dto.algo;

import java.util.Date;

/**
 * 
 * Aug 31, 2016 12:48:31 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class JobRequestInfo {

    private Long id;

    private String algorithmName;

    private Date addedTime;

    private String resultFileName;

    private String errorResultFileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public String getErrorResultFileName() {
        return errorResultFileName;
    }

    public void setErrorResultFileName(String errorResultFileName) {
        this.errorResultFileName = errorResultFileName;
    }

}
