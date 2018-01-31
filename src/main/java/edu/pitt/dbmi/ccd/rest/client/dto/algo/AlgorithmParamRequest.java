package edu.pitt.dbmi.ccd.rest.client.dto.algo;

import java.util.Set;

/**
 *
 * Aug 25, 2016 2:20:41 PM
 *
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 *
 */
public class AlgorithmParamRequest {

	private String algoId;
	
	private String testId = null;
	
	private String scoreId = null;
	
    private long datasetFileId;
    
    private Long priorKnowledgeFileId = null;
    
    private Boolean skipDataValidation = null;

    private Set<AlgoParameter> algoParameters;

    private JvmOptions jvmOptions;

    private Set<HpcParameter> hpcParameters;

    public String getAlgoId() {
		return algoId;
	}

	public void setAlgoId(String algoId) {
		this.algoId = algoId;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getScoreId() {
		return scoreId;
	}

	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}

	public long getDatasetFileId() {
        return datasetFileId;
    }

    public void setDatasetFileId(long dataFileId) {
        this.datasetFileId = dataFileId;
    }

    public Long getPriorKnowledgeFileId() {
		return priorKnowledgeFileId;
	}

	public void setPriorKnowledgeFileId(Long priorKnowledgeFileId) {
		this.priorKnowledgeFileId = priorKnowledgeFileId;
	}

	public Boolean getSkipDataValidation() {
		return skipDataValidation;
	}

	public void setSkipDataValidation(Boolean skipDataValidation) {
		this.skipDataValidation = skipDataValidation;
	}

	public Set<AlgoParameter> getAlgoParameters() {
		return algoParameters;
	}

	public void setAlgoParameters(Set<AlgoParameter> algoParameters) {
		this.algoParameters = algoParameters;
	}

	public JvmOptions getJvmOptions() {
		return jvmOptions;
	}

	public void setJvmOptions(JvmOptions jvmOptions) {
		this.jvmOptions = jvmOptions;
	}

	public Set<HpcParameter> getHpcParameters() {
		return hpcParameters;
	}

	public void setHpcParameters(Set<HpcParameter> hpcParameters) {
		this.hpcParameters = hpcParameters;
	}

}
