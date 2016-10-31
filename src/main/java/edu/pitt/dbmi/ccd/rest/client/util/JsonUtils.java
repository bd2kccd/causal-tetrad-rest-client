package edu.pitt.dbmi.ccd.rest.client.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.cmu.tetrad.graph.Edge;
import edu.cmu.tetrad.graph.EdgeListGraphSingleConnections;
import edu.cmu.tetrad.graph.Endpoint;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.graph.GraphNode;
import edu.cmu.tetrad.graph.Node;
import edu.cmu.tetrad.graph.NodeType;
import edu.cmu.tetrad.graph.Triple;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.JobInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.ResultFile;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFile;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFileSummary;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;

/**
 * 
 * Aug 25, 2016 1:38:47 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class JsonUtils {

    public static JsonWebToken parseJSONObjectToJsonWebToken(JSONObject jObj){
	JsonWebToken jsonWebToken = new JsonWebToken();
	
	int userId = jObj.getInt("userId");
	String jwt = jObj.getString("jwt");
	long issuedTime = jObj.getLong("issuedTime");
	long lifetime = jObj.getLong("lifetime");
	long expireTime = jObj.getLong("expireTime");
	
	jsonWebToken.setUserId(userId);
	jsonWebToken.setJwt(jwt);
	jsonWebToken.setIssuedTime(new Date(issuedTime));
	jsonWebToken.setLifetime(lifetime);
	jsonWebToken.setExpireTime(new Date(expireTime));
	
	return jsonWebToken;
    }
    
    public static JsonWebToken parseJSONObjectToJsonWebToken(String jsonResponse){
	return parseJSONObjectToJsonWebToken(new JSONObject(jsonResponse));
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

	JSONObject fileSummary = jObj.getJSONObject("fileSummary");
	String variableType = fileSummary.get("variableType") instanceof String ? fileSummary
		.getString("variableType") : null;
	String fileDelimiter = fileSummary.get("fileDelimiter") instanceof String ? fileSummary
		.getString("fileDelimiter") : null;
	Integer numOfRows = fileSummary.get("numOfRows") instanceof Integer ? fileSummary
		.getInt("numOfRows") : null;
	Integer numOfColumns = fileSummary.get("numOfColumns") instanceof Integer ? fileSummary
		.getInt("numOfColumns") : null;

	DataFileSummary dataFileSummary = new DataFileSummary();
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

    public static JobInfo parseJSONObjectToJobInfo(String jsonResponse) {
	JSONObject jObj = new JSONObject(jsonResponse);
	return parseJSONObjectToJobInfo(jObj);
    }
    
    public static JobInfo parseJSONObjectToJobInfo(JSONObject jObj) {
	long id = jObj.getLong("id");
	String algorithmName = jObj.getString("algorithmName");
	int status = jObj.getInt("status");
	long addedTime = jObj.getLong("addedTime");
	String resultFileName = jObj.getString("resultFileName");
	String resultJsonFileName = jObj.getString("resultJsonFileName");
	String errorResultFileName = jObj.getString("errorResultFileName");
	
	JobInfo jobInfo = new JobInfo();
	jobInfo.setId(id);
	jobInfo.setAlgorithmName(algorithmName);
	jobInfo.setStatus(status);
	jobInfo.setAddedTime(new Date(addedTime));
	jobInfo.setResultFileName(resultFileName);
	jobInfo.setResultJsonFileName(resultJsonFileName);
	jobInfo.setErrorResultFileName(errorResultFileName);
	
	System.out.println("Job id: " + id);
	System.out.println("Algorithm name: " + algorithmName);
	System.out.println("Status: " + status);
	System.out.println("Added date: " + jobInfo.getAddedTime());
	System.out.println("Result FileName: " + resultFileName);
	System.out.println("Result Json FileName: " + resultJsonFileName);
	System.out.println("Error Result FileName: " + errorResultFileName);
	System.out.println("----------------------------------------");

	return jobInfo;
    }
    
    public static List<JobInfo> parseJSONArrayToJobInfos(String jsonResponse){
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
	System.out.println("lastModifiedTime: "
		+ resultFile.getLastModifiedTime());
	System.out.println("fileSize: " + fileSize);

	System.out.println("----------------------------------------");

	return resultFile;
    }

    public static ResultFile parseJSONObjectToResultFile(String jsonResponse) {
	return parseJSONObjectToResultFile(new JSONObject(jsonResponse));
    }

    public static Graph parseJSONObjectToTetradGraph(String jsonResponse) {
	return parseJSONObjectToTetradGraph(new JSONObject(jsonResponse));
    }
    
    public static Graph parseJSONObjectToTetradGraph(JSONObject jObj) {
	// Node
	List<Node> nodes = parseJSONArrayToTetradNodes(jObj.getJSONArray("nodes"));
	EdgeListGraphSingleConnections graph = new EdgeListGraphSingleConnections(nodes);
	
	// Edge
	Set<Edge> edges = parseJSONArrayToTetradEdges(graph, jObj.getJSONArray("edgesSet"));
	for(Edge edge : edges){
	    graph.addEdge(edge);
	}
	
	// ambiguousTriples
	Set<Triple> ambiguousTriples = parseJSONArrayToTetradTriples(jObj.getJSONArray("ambiguousTriples"));
	for(Triple triple : ambiguousTriples){
		graph.addAmbiguousTriple(triple.getX(), triple.getY(), triple.getZ());
	}
	
	// underLineTriples
	Set<Triple> underLineTriples = parseJSONArrayToTetradTriples(jObj.getJSONArray("underLineTriples"));
	for(Triple triple : underLineTriples){
		graph.addUnderlineTriple(triple.getX(), triple.getY(), triple.getZ());
	}
	
	// dottedUnderLineTriples
	Set<Triple> dottedUnderLineTriples = parseJSONArrayToTetradTriples(jObj.getJSONArray("dottedUnderLineTriples"));
	for(Triple triple : dottedUnderLineTriples){
		graph.addDottedUnderlineTriple(triple.getX(), triple.getY(), triple.getZ());
	}
	
	// stuffRemovedSinceLastTripleAccess
	boolean stuffRemovedSinceLastTripleAccess = jObj.getBoolean("stuffRemovedSinceLastTripleAccess");
	graph.setStuffRemovedSinceLastTripleAccess(stuffRemovedSinceLastTripleAccess);
	
	// highlightedEdges
	Set<Edge> highlightedEdges = parseJSONArrayToTetradEdges(graph, jObj.getJSONArray("highlightedEdges"));
	for(Edge edge : highlightedEdges){
	    graph.setHighlighted(edge, true);
	}
	
	return graph;
    }
    
    public static Set<Triple> parseJSONArrayToTetradTriples(JSONArray jArray) {
	Set<Triple> triples = new HashSet<>();
	
	for(int i=0;i<jArray.length();i++){
	    Triple triple = parseJSONArrayToTetradTriple(jArray.getJSONObject(i));
	    triples.add(triple);
	}
	
	return triples;
    }
    
    public static Triple parseJSONArrayToTetradTriple(JSONObject jObj) {
	Node x = parseJSONObjectToTetradNode(jObj.getJSONObject("x"));
	Node y = parseJSONObjectToTetradNode(jObj.getJSONObject("y"));
	Node z = parseJSONObjectToTetradNode(jObj.getJSONObject("z"));
	
	return new Triple(x, y, z);
    }
    
    public static Set<Edge> parseJSONArrayToTetradEdges(Graph graph, JSONArray jArray) {
	Set<Edge> edges = new HashSet<>();
	
	for(int i=0;i<jArray.length();i++){
	   Edge edge =  parseJSONObjectToTetradEdge(graph, jArray.getJSONObject(i));
	   edges.add(edge);
	}
	
	return edges;
    }
    
    public static Edge parseJSONObjectToTetradEdge(Graph graph, JSONObject jObj) {
	Node node1 = graph.getNode(jObj.getJSONObject("node1").getString("name"));
	Node node2 = graph.getNode(jObj.getJSONObject("node2").getString("name"));
	Endpoint endpoint1 = Endpoint.TYPES[jObj.getJSONObject("endpoint1").getInt("ordinal")];
	Endpoint endpoint2 = Endpoint.TYPES[jObj.getJSONObject("endpoint2").getInt("ordinal")];
	return new Edge(node1, node2, endpoint1, endpoint2);
    }
    
    public static List<Node> parseJSONArrayToTetradNodes(JSONArray jArray) {
	List<Node> nodes = new ArrayList<>();
	
	for(int i=0;i<jArray.length();i++){
	    Node node = parseJSONObjectToTetradNode(jArray.getJSONObject(i));
	    nodes.add(node);
	}
	
	return nodes;
    }
    
    public static Node parseJSONObjectToTetradNode(JSONObject jObj) {
	JSONObject nodeType = jObj.getJSONObject("nodeType");
	int ordinal = nodeType.getInt("ordinal");
	int centerX = jObj.getInt("centerX");
	int centerY = jObj.getInt("centerY");
	String name = jObj.getString("name");
	
	GraphNode graphNode = new GraphNode(name);
	graphNode.setNodeType(NodeType.TYPES[ordinal]);
	graphNode.setCenter(centerX, centerY);
	
	return graphNode;
    }
    
}
