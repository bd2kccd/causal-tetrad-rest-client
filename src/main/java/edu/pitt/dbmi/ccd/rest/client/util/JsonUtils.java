package edu.pitt.dbmi.ccd.rest.client.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.JobRequestInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFile;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFileSummary;

/**
 * 
 * Aug 25, 2016 1:38:47 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class JsonUtils {

    public static Set<DataFile> parseJSONArrayToDataFiles(String jsonResponse) {
	Set<DataFile> dataFiles = new HashSet<>();

	JSONArray jArray = new JSONArray(jsonResponse);
	for (int i = 0; i < jArray.length(); i++) {
	    dataFiles.add(parseJSONObjectToDataFile(jArray.getJSONObject(i)));
	}

	return dataFiles;
    }

    public static DataFile parseJSONObjectToDataFile(JSONObject jObj) {
	long id = jObj.getLong("id");
	String name = jObj.get("name").toString();
	long creationTime = jObj.getLong("creationTime");
	long lastModifiedTime = jObj.getLong("lastModifiedTime");
	long fileSize = jObj.getLong("fileSize");

	DataFile dataFile = new DataFile();
	dataFile.setId(id);
	dataFile.setName(name);
	dataFile.setCreationTime(new Date(creationTime));
	dataFile.setLastModifiedTime(new Date(lastModifiedTime));
	dataFile.setFileSize(fileSize);

	JSONObject fileSummary = jObj.getJSONObject("fileSummary");
	String md5checkSum = fileSummary.getString("md5checkSum");
	String variableType = fileSummary.get("variableType") instanceof String ? fileSummary
		.getString("variableType") : null;
	String fileDelimiter = fileSummary.get("fileDelimiter") instanceof String ? fileSummary
		.getString("fileDelimiter") : null;
	Integer numOfRows = fileSummary.get("numOfRows") instanceof Integer ? fileSummary
		.getInt("numOfRows") : null;
	Integer numOfColumns = fileSummary.get("numOfColumns") instanceof Integer ? fileSummary
		.getInt("numOfColumns") : null;

	DataFileSummary dataFileSummary = new DataFileSummary();
	dataFileSummary.setMd5checkSum(md5checkSum);
	dataFileSummary.setVariableType(variableType);
	dataFileSummary.setFileDelimiter(fileDelimiter);
	dataFileSummary.setNumOfRows(numOfRows);
	dataFileSummary.setNumOfColumns(numOfColumns);
	dataFile.setFileSummary(dataFileSummary);

	System.out.println("id: " + id);
	System.out.println("name: " + name);
	System.out.println("creationTime: " + dataFile.getCreationTime());
	System.out.println("lastModifiedTime: "
		+ dataFile.getLastModifiedTime());
	System.out.println("fileSize: " + fileSize);
	System.out.println("md5checkSum: " + md5checkSum);
	System.out.println("variableType: " + variableType);
	System.out.println("fileDelimiter: " + fileDelimiter);
	System.out.println("numOfRows: " + numOfRows);
	System.out.println("numOfColumns: " + numOfColumns);

	System.out.println("----------------------------------------");

	return dataFile;
    }

    public static DataFile parseJSONObjectToDataFile(String jsonResponse) {
	return parseJSONObjectToDataFile(new JSONObject(jsonResponse));
    }

    public static Set<AlgorithmInfo> parseJSONArrayToAlgorithmInfos(
	    String jsonResponse) {
	Set<AlgorithmInfo> algoInfos = new HashSet<>();

	JSONArray jArray = new JSONArray(jsonResponse);
	for (int i = 0; i < jArray.length(); i++) {
	    algoInfos.add(parseJSONObjectToAlgorithmInfo(jArray
		    .getJSONObject(i)));
	}

	return algoInfos;
    }

    public static AlgorithmInfo parseJSONObjectToAlgorithmInfo(JSONObject jObj) {
	int id = jObj.getInt("id");
	String name = jObj.getString("name");
	String description = jObj.getString("description");

	AlgorithmInfo algoInfo = new AlgorithmInfo();
	algoInfo.setId(id);
	algoInfo.setName(name);
	algoInfo.setDescription(description);

	System.out.println("Algorithm id: " + id);
	System.out.println("Algorithm name: " + name);
	System.out.println("Algorithm description: " + description);
	System.out.println("----------------------------------------");

	return algoInfo;
    }
    
    public static JobRequestInfo parseJSONObjectToJobRequestInfo(String jsonResponse) {
	JSONObject jObj = new JSONObject(jsonResponse);
	
	long id = jObj.getLong("id");
	String algorithmName = jObj.getString("algorithmName");
	long addedTime = jObj.getLong("addedTime");
	String resultFileName = jObj.getString("resultFileName");
	String errorResultFileName = jObj.getString("errorResultFileName");
	
	JobRequestInfo jobRequestInfo = new JobRequestInfo();
	jobRequestInfo.setId(id);
	jobRequestInfo.setAlgorithmName(algorithmName);
	jobRequestInfo.setAddedTime(new Date(addedTime));
	jobRequestInfo.setResultFileName(resultFileName);
	jobRequestInfo.setErrorResultFileName(errorResultFileName);
	
	System.out.println("Job id: " + id);
	System.out.println("Algorithm name: " + algorithmName);
	System.out.println("Added date: " + jobRequestInfo.getAddedTime());
	System.out.println("Result FileName: " + resultFileName);
	System.out.println("Error Result FileName: " + errorResultFileName);
	System.out.println("----------------------------------------");

	return jobRequestInfo;
    }
}
