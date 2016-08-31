package edu.pitt.dbmi.ccd.rest.client.dto.algo;

import java.util.Map;

/**
 * 
 * Aug 25, 2016 2:20:41 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class AlgorithmParamRequest {

    private long dataFileId;
    private Map<String, Object> dataValidation;
    private Map<String, Object> AlgorithmParameters;
    private Map<String, Object> jvmOptions;

    public long getDataFileId() {
	return dataFileId;
    }

    public void setDataFileId(long dataFileId) {
	this.dataFileId = dataFileId;
    }

    public Map<String, Object> getDataValidation() {
	return dataValidation;
    }

    public void setDataValidation(Map<String, Object> dataValidation) {
	this.dataValidation = dataValidation;
    }

    public Map<String, Object> getAlgorithmParameters() {
	return AlgorithmParameters;
    }

    public void setAlgorithmParameters(Map<String, Object> algorithmParameters) {
	AlgorithmParameters = algorithmParameters;
    }

    public Map<String, Object> getJvmOptions() {
	return jvmOptions;
    }

    public void setJvmOptions(Map<String, Object> jvmOptions) {
	this.jvmOptions = jvmOptions;
    }
}
