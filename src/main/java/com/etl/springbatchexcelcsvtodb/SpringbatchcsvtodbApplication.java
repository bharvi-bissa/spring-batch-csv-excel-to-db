package com.etl.springbatchexcelcsvtodb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableBatchProcessing
@RestController
public class SpringbatchcsvtodbApplication {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job csvToDbJob;
	
	@Autowired
	Job excelToDbJob;

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchcsvtodbApplication.class, args);
	}

	@RequestMapping("/jobLaunchercsv")
	public BatchStatus handle() throws Exception {
		JobExecution jobExecution = jobLauncher.run(csvToDbJob, new JobParameters(getJobParamsMap()));
		
		while (jobExecution.isRunning()) {
			System.out.println("...");
		}
		return jobExecution.getStatus();
	}
	
	@RequestMapping("/jobLauncherexcel")
	public BatchStatus handleExcel() throws Exception {
		JobExecution jobExecution = jobLauncher.run(excelToDbJob, new JobParameters(getJobParamsMap()));
		
		while (jobExecution.isRunning()) {
			System.out.println("...");
		}
		return jobExecution.getStatus();
	}

	private Map<String, JobParameter> getJobParamsMap() {
		Map<String, JobParameter> jobParams = new HashMap<>();
		jobParams.put("time", new JobParameter(System.currentTimeMillis()));
		return jobParams;
	}
	

}
