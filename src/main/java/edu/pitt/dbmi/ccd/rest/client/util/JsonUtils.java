package edu.pitt.dbmi.ccd.rest.client.util;

import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.IndTestInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.JobInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.ResultFile;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.ScoreInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFile;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFileSummary;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataType;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * Aug 25, 2016 1:38:47 PM
 *
 * @author Chirayu Kong Wongchokprasitti, PhD (chw20@pitt.edu)
 *
 */
public class JsonUtils {

    public static JsonWebToken parseJSONObjectToJsonWebToken(JSONObject jObj) {
        JsonWebToken jsonWebToken = new JsonWebToken();

        int userId = jObj.getInt("userId");
        String jwt = jObj.getString("jwt");
        long issuedTime = jObj.getLong("issuedTime");
        long lifetime = jObj.getLong("lifetime");
        long expireTime = jObj.getLong("expireTime");
        String[] wallTime = null;

        if (!jObj.isNull("wallTime")) {
            JSONArray wallTimeArray = jObj.getJSONArray("wallTime");
            if (wallTimeArray != null && wallTimeArray.length() > 0) {
                wallTime = new String[wallTimeArray.length()];
                for (int index = 0; index < wallTimeArray.length(); index++) {
                    Object obj = wallTimeArray.get(index);
                    wallTime[index] = obj.toString();
                }
            }
        }

        jsonWebToken.setUserId(userId);
        jsonWebToken.setJwt(jwt);
        jsonWebToken.setIssuedTime(new Date(issuedTime));
        jsonWebToken.setLifetime(lifetime);
        jsonWebToken.setExpireTime(new Date(expireTime));
        jsonWebToken.setWallTime(wallTime);

        return jsonWebToken;
    }
    
    public static JsonWebToken parseJSONObjectToJsonWebToken(String jsonResponse) {
        return parseJSONObjectToJsonWebToken(new JSONObject(jsonResponse));
    }

    public static ScoreInfo parseJSONArrayToScore(JSONObject jObj){
    	String id = jObj.getString("id");
    	String name = jObj.getString("name");
    	Set<DataType> supportedDataTypes = parseJSONArrayToDataTypes(jObj.getJSONArray("supportedDataTypes"));
    	
    	ScoreInfo scoreInfo = new ScoreInfo();
    	scoreInfo.setId(id);
    	scoreInfo.setName(name);
    	scoreInfo.setSupportedDataTypes(supportedDataTypes);
    	
    	System.out.println("id: " + id);
        System.out.println("name: " + name);
        for(DataType dataType : supportedDataTypes){
        	System.out.println("supportedDataType: " + dataType);
        }
    	
    	return scoreInfo;
    }
    
    
    public static Set<ScoreInfo> parseJSONArrayToScores(String jsonResponse) {
    	Set<ScoreInfo> scores = new HashSet<>();
    	
    	JSONArray jArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jArray.length(); i++) {
        	scores.add(parseJSONArrayToScore(jArray.getJSONObject(i)));
        }
        
        return scores;
    }

    public static IndTestInfo parseJSONArrayToIndTest(JSONObject jObj){
    	String id = jObj.getString("id");
    	String name = jObj.getString("name");
    	Set<DataType> supportedDataTypes = parseJSONArrayToDataTypes(jObj.getJSONArray("supportedDataTypes"));
    	
    	IndTestInfo indTestInfo = new IndTestInfo();
    	indTestInfo.setId(id);
    	indTestInfo.setName(name);
    	indTestInfo.setSupportedDataTypes(supportedDataTypes);
    	
    	System.out.println("id: " + id);
        System.out.println("name: " + name);
        for(DataType dataType : supportedDataTypes){
        	System.out.println("supportedDataType: " + dataType);
        }
    	
    	return indTestInfo;
    }
    
    public static Set<IndTestInfo> parseJSONArrayToIndTests(String jsonResponse) {
    	Set<IndTestInfo> tests = new HashSet<>();
    	
    	JSONArray jArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jArray.length(); i++) {
        	tests.add(parseJSONArrayToIndTest(jArray.getJSONObject(i)));
        }
        
        return tests;
    }
    
    public static Set<DataType> parseJSONArrayToDataTypes(JSONArray jArray){
    	Set<DataType> dataTypes = new HashSet<>();
    	
    	for (int i = 0; i < jArray.length(); i++) {
        	String _dataType = jArray.getString(i);
        	DataType dataType = DataType.continuous; // Continuous by default
        	if(_dataType.equalsIgnoreCase("discrete")){
        		dataType = DataType.discrete;
        	}else if(_dataType.equalsIgnoreCase("mixed")){
        		dataType = DataType.mixed;
        	}
        	dataTypes.add(dataType);
        }
        
        return dataTypes;
    }
    
    public static Set<DataType> parseJSONArrayToDataTypes(String jsonResponse) {
    	return parseJSONArrayToDataTypes(new JSONArray(jsonResponse));
    }
    
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
        String md5checkSum = jObj.getString("md5checkSum");

        DataFile dataFile = new DataFile();
        dataFile.setId(id);
        dataFile.setName(name);
        dataFile.setCreationTime(new Date(creationTime));
        dataFile.setLastModifiedTime(new Date(lastModifiedTime));
        dataFile.setFileSize(fileSize);
        dataFile.setMd5checkSum(md5checkSum);

        System.out.println("id: " + id);
        System.out.println("name: " + name);
        System.out.println("creationTime: " + dataFile.getCreationTime());
        System.out.println("lastModifiedTime: " + dataFile.getLastModifiedTime());
        System.out.println("fileSize: " + fileSize);
        System.out.println("md5checkSum: " + md5checkSum);

        if (!jObj.isNull("fileSummary")) {
            JSONObject fileSummary = jObj.getJSONObject("fileSummary");
            String variableType = fileSummary.get("variableType") instanceof String
                    ? fileSummary.getString("variableType") : null;
            String fileDelimiter = fileSummary.get("fileDelimiter") instanceof String
                    ? fileSummary.getString("fileDelimiter") : null;
            Integer numOfRows = fileSummary.get("numOfRows") instanceof Integer ? fileSummary.getInt("numOfRows")
                    : null;
            Integer numOfColumns = fileSummary.get("numOfColumns") instanceof Integer
                    ? fileSummary.getInt("numOfColumns") : null;

            DataFileSummary dataFileSummary = new DataFileSummary();
            dataFileSummary.setVariableType(variableType);
            dataFileSummary.setFileDelimiter(fileDelimiter);
            dataFileSummary.setNumOfRows(numOfRows);
            dataFileSummary.setNumOfColumns(numOfColumns);
            dataFile.setFileSummary(dataFileSummary);

            System.out.println("variableType: " + variableType);
            System.out.println("fileDelimiter: " + fileDelimiter);
            System.out.println("numOfRows: " + numOfRows);
            System.out.println("numOfColumns: " + numOfColumns);
        }

        System.out.println("----------------------------------------");

        return dataFile;
    }

    public static DataFile parseJSONObjectToDataFile(String jsonResponse) {
        return parseJSONObjectToDataFile(new JSONObject(jsonResponse));
    }

    public static Set<AlgorithmInfo> parseJSONArrayToAlgorithmInfos(String jsonResponse) {
        Set<AlgorithmInfo> algoInfos = new HashSet<>();

        JSONArray jArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jArray.length(); i++) {
            algoInfos.add(parseJSONObjectToAlgorithmInfo(jArray.getJSONObject(i)));
        }

        return algoInfos;
    }

    public static AlgorithmInfo parseJSONObjectToAlgorithmInfo(JSONObject jObj) {
        String id = jObj.getString("id");
        String name = jObj.getString("name");
        String description = jObj.getString("description");
        boolean requireTest = jObj.getBoolean("requireTest");
        boolean requireScore = jObj.getBoolean("requireScore");
        boolean acceptKnowledge = jObj.getBoolean("acceptKnowledge");

        AlgorithmInfo algoInfo = new AlgorithmInfo();
        algoInfo.setId(id);
        algoInfo.setName(name);
        algoInfo.setDescription(description);
        algoInfo.setRequireTest(requireTest);
        algoInfo.setRequireScore(requireScore);
        algoInfo.setAcceptKnowledge(acceptKnowledge);

        System.out.println("Algorithm id: " + id);
        System.out.println("Algorithm name: " + name);
        System.out.println("Algorithm description: " + description);
        System.out.println("Algorithm requireTest: " + requireTest);
        System.out.println("Algorithm requireScore: " + requireScore);
        System.out.println("Algorithm acceptKnowledge: " + acceptKnowledge);
        System.out.println("----------------------------------------");

        return algoInfo;
    }

    public static JobInfo parseJSONObjectToJobInfo(String jsonResponse) {
        JSONObject jObj = new JSONObject(jsonResponse);
        return parseJSONObjectToJobInfo(jObj);
    }

    public static JobInfo parseJSONObjectToJobInfo(JSONObject jObj) {
        long id = jObj.getLong("id");
        String algoId = jObj.getString("algoId");
        int status = jObj.getInt("status");
        long addedTime = jObj.getLong("addedTime");
        String resultFileName = jObj.getString("resultFileName");
        String resultJsonFileName = jObj.getString("resultJsonFileName");
        String errorResultFileName = jObj.getString("errorResultFileName");

        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(id);
        jobInfo.setAlgoId(algoId);
        jobInfo.setStatus(status);
        jobInfo.setAddedTime(new Date(addedTime));
        jobInfo.setResultFileName(resultFileName);
        jobInfo.setResultJsonFileName(resultJsonFileName);
        jobInfo.setErrorResultFileName(errorResultFileName);

        System.out.println("Job id: " + id);
        System.out.println("Algorithm id: " + algoId);
        System.out.println("Status: " + status);
        System.out.println("Added date: " + jobInfo.getAddedTime());
        System.out.println("Result FileName: " + resultFileName);
        System.out.println("Result Json FileName: " + resultJsonFileName);
        System.out.println("Error Result FileName: " + errorResultFileName);
        System.out.println("----------------------------------------");

        return jobInfo;
    }

    public static List<JobInfo> parseJSONArrayToJobInfos(String jsonResponse) {
        List<JobInfo> jobInfos = new ArrayList<JobInfo>();

        JSONArray jArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jArray.length(); i++) {
            jobInfos.add(parseJSONObjectToJobInfo(jArray.getJSONObject(i)));
        }

        return jobInfos;
    }

    public static Set<ResultFile> parseJSONArrayToResultFiles(String jsonResponse) {
        Set<ResultFile> resultFiles = new HashSet<>();

        JSONArray jArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jArray.length(); i++) {
            resultFiles.add(parseJSONObjectToResultFile(jArray.getJSONObject(i)));
        }

        return resultFiles;
    }

    public static ResultFile parseJSONObjectToResultFile(JSONObject jObj) {
        String name = jObj.get("name").toString();
        long creationTime = jObj.getLong("creationTime");
        long lastModifiedTime = jObj.getLong("lastModifiedTime");
        long fileSize = jObj.getLong("fileSize");

        ResultFile resultFile = new ResultFile();
        resultFile.setName(name);
        resultFile.setCreationTime(new Date(creationTime));
        resultFile.setLastModifiedTime(new Date(lastModifiedTime));
        resultFile.setFileSize(fileSize);

        System.out.println("name: " + name);
        System.out.println("creationTime: " + resultFile.getCreationTime());
        System.out.println("lastModifiedTime: " + resultFile.getLastModifiedTime());
        System.out.println("fileSize: " + fileSize);

        System.out.println("----------------------------------------");

        return resultFile;
    }

    public static ResultFile parseJSONObjectToResultFile(String jsonResponse) {
        return parseJSONObjectToResultFile(new JSONObject(jsonResponse));
    }

}
