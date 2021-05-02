package com.etl.springbatchexcelcsvtodb.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobCompletionListener extends JobExecutionListenerSupport {

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println(jobExecution.getJobInstance().getJobName() + " Job Finished ! Time to verify the results");
		}

		if (jobExecution.getStatus() == BatchStatus.FAILED) {
			System.out.println("...Job Failed..");
		}
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println(jobExecution.getJobInstance().getJobName()+" Job is Starting...");
	}

}
