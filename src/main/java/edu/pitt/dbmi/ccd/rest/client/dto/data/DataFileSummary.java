 package edu.pitt.dbmi.ccd.rest.client.dto.data;

/**
 * 
 * Aug 20, 2016 12:19:08 AM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class DataFileSummary {

    private String variableType;

    private String fileDelimiter;

    private Integer numOfRows;

    private Integer numOfColumns;

    public DataFileSummary(){
	
    }
    
    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public String getFileDelimiter() {
        return fileDelimiter;
    }

    public void setFileDelimiter(String fileDelimiter) {
        this.fileDelimiter = fileDelimiter;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getNumOfColumns() {
        return numOfColumns;
    }

    public void setNumOfColumns(Integer numOfColumns) {
        this.numOfColumns = numOfColumns;
    }
    
}
