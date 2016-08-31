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

}
