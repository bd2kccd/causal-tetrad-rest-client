package edu.pitt.dbmi.ccd.rest.client.dto.data;

import java.util.Date;

/**
 *
 * Aug 20, 2016 12:17:02 AM
 *
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 *
 */
public class DataFile {

    private Long id;

    private String name;

    private Date creationTime;

    private Date lastModifiedTime;

    private long fileSize;

    private String md5checkSum;

    private DataFileSummary fileSummary;

    public DataFile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMd5checkSum() {
        return md5checkSum;
    }

    public void setMd5checkSum(String md5checkSum) {
        this.md5checkSum = md5checkSum;
    }

    public DataFileSummary getFileSummary() {
        return fileSummary;
    }

    public void setFileSummary(DataFileSummary fileSummary) {
        this.fileSummary = fileSummary;
    }

}
