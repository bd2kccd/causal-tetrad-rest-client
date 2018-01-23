package edu.pitt.dbmi.ccd.rest.client.dto.algo;

/**
 *
 * Aug 24, 2016 6:09:21 PM
 *
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 *
 */
public class AlgorithmInfo {

    private int id;
    private String name;
    private String description;
    private boolean requireTest;
    private boolean requireScore;
    private boolean acceptKnowledge;

    public AlgorithmInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public boolean isRequireTest() {
		return requireTest;
	}

	public void setRequireTest(boolean requireTest) {
		this.requireTest = requireTest;
	}

	public boolean isRequireScore() {
		return requireScore;
	}

	public void setRequireScore(boolean requireScore) {
		this.requireScore = requireScore;
	}

	public boolean isAcceptKnowledge() {
		return acceptKnowledge;
	}

	public void setAcceptKnowledge(boolean acceptKnowledge) {
		this.acceptKnowledge = acceptKnowledge;
	}

}
