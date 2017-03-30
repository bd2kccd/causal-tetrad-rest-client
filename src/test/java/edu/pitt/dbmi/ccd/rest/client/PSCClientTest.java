package edu.pitt.dbmi.ccd.rest.client;

import edu.pitt.dbmi.ccd.rest.client.dto.algo.AlgorithmParamRequest;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.JobInfo;
import edu.pitt.dbmi.ccd.rest.client.dto.algo.ResultFile;
import edu.pitt.dbmi.ccd.rest.client.dto.data.DataFile;
import edu.pitt.dbmi.ccd.rest.client.dto.user.JsonWebToken;
import edu.pitt.dbmi.ccd.rest.client.service.algo.AbstractAlgorithmRequest;
import edu.pitt.dbmi.ccd.rest.client.service.algo.AlgorithmService;
import edu.pitt.dbmi.ccd.rest.client.service.data.DataUploadService;
import edu.pitt.dbmi.ccd.rest.client.service.data.RemoteDataFileService;
import edu.pitt.dbmi.ccd.rest.client.service.jobqueue.JobQueueService;
import edu.pitt.dbmi.ccd.rest.client.service.result.ResultService;
import edu.pitt.dbmi.ccd.rest.client.service.user.UserService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Ignore;

/**
 * Unit test for simple App.
 */
@Ignore
public class PSCClientTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PSCClientTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(PSCClientTest.class);
    }

    public void testApp() throws Exception {
        final String username = "chw20@pitt.edu";
        final String password = "kongman20";
        final String scheme = "https";
        final String hostname = "ccd2.vm.bridges.psc.edu";
        final int port = 443;

        RestHttpsClient restClient = new RestHttpsClient(username, password,
                scheme, hostname, port);

        UserService userService = new UserService(restClient, scheme, hostname,
                port);
        // JWT token is valid for 1 hour
        JsonWebToken jsonWebToken = userService.requestJWT();

        DataUploadService dataUploadService = new DataUploadService(restClient,
                4, scheme, hostname, port);
        Path file = Paths.get("/Users/kong/Documents/DBMI/tetrad/causal-cmd/test/data/diff_delim/sim_data_20vars_100cases.txt");
        dataUploadService.startUpload(file, jsonWebToken);

        RemoteDataFileService remoteDataService = new RemoteDataFileService(
                restClient, scheme, hostname, port);
        int progress;
        while ((progress = dataUploadService.getUploadJobStatus(file
                .toAbsolutePath().toString())) < 100) {
            System.out.println("Upload Progress: " + progress + "%");
            Thread.sleep(500);
        }

        Set<DataFile> dataFiles = remoteDataService
                .retrieveDataFileInfo(jsonWebToken);
        long id = -1;
        for (DataFile dataFile : dataFiles) {

            System.out.println(dataFile.getName() + "\t" + dataFile.getFileSummary().getVariableType());

            if (dataFile.getName().equalsIgnoreCase(
                    file.getFileName().toString())) {
                id = dataFile.getId();
                String variableType = "continuous";
                String fileDelimiter = "tab";

                remoteDataService.summarizeDataFile(id, variableType,
                        fileDelimiter, jsonWebToken);
            }
        }

        // Show a list of algorithms
        AlgorithmService algorithmService = new AlgorithmService(restClient,
                scheme, hostname, port);

        // Set<AlgorithmInfo> algoInfos =
        algorithmService.listAllAlgorithms(jsonWebToken);

        // Run GFCI continuous
        String algorithmName = AbstractAlgorithmRequest.GFCI;
        AlgorithmParamRequest paramRequest = new AlgorithmParamRequest();
        paramRequest.setDatasetFileId(id);

        Map<String, Object> DataValidation = new HashMap<>();
        DataValidation.put("skipNonzeroVariance", false);
        DataValidation.put("skipUniqueVarName", false);
        // DataValidation.put("skipCategoryLimit", false);
        paramRequest.setDataValidation(DataValidation);

        Map<String, Object> AlgorithmParameters = new HashMap<>();
        AlgorithmParameters.put("alpha", 0.01);
        AlgorithmParameters.put("maxDegree", 100);
        AlgorithmParameters.put("penaltyDiscount", 4.0);
        AlgorithmParameters.put("faithfulnessAssumed", true);
        AlgorithmParameters.put("verbose", true);
        paramRequest.setAlgorithmParameters(AlgorithmParameters);

        Map<String, Object> JvmOptions = new HashMap<>();
        JvmOptions.put("maxHeapSize", 100);
        paramRequest.setJvmOptions(JvmOptions);

        JobQueueService jobQueueService = new JobQueueService(restClient,
                scheme, hostname, port);

        // Submit a job
        JobInfo jobInfo = jobQueueService.addToRemoteQueue(algorithmName,
                paramRequest, jsonWebToken);

        // Get all active jobs
        List<JobInfo> jobInfos = jobQueueService.getActiveJobs(jsonWebToken);
        for (JobInfo job : jobInfos) {
            System.out.println("JobId: " + job.getId() + "\tStatus: "
                    + job.getStatus());
        }

        // Get job's status
        JobInfo job;
        while ((job = jobQueueService.getJobStatus(jobInfo.getId(),
                jsonWebToken)) != null) {
            Thread.sleep(50000);
        }

        ResultService resultService = new ResultService(restClient, scheme,
                hostname, port);

        // Get all results
        Set<ResultFile> resultFiles = resultService
                .listAlgorithmResultFiles(jsonWebToken);

        Set<ResultFile> resultComparisonFiles = resultService
                .listAlgorithmResultComparisonFiles(jsonWebToken);

        // Download result
        System.out.println("Result File: "
                + resultService.downloadAlgorithmResultFile(
                        jobInfo.getResultFileName(), jsonWebToken));
        System.out.println("Json Result File: "
                + resultService.downloadAlgorithmResultFile(
                        jobInfo.getResultJsonFileName(), jsonWebToken));

        assertTrue(true);
    }
}
