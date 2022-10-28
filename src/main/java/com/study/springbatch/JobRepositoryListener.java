package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JobRepositoryListener implements JobExecutionListener {

    /*
         JobRepository

         - 배치 작업 중의 정보를 저장하는 저장소 역할
         - Job 이 언제 수행되었고, 언제 끝났으며, 몇 번이 실행되었고 실행에 대한 결과 등의 배치 작업의 수행과 관련된 모든 meta data를 저장함

         - SimpleJobRepository(JobRepository 구현체) 의 구조를 한번 살펴볼 것
         https://s3.us-west-2.amazonaws.com/secure.notion-static.com/3188bc6f-fd5c-480e-ada2-dc9662c67b00/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221028%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221028T005541Z&X-Amz-Expires=86400&X-Amz-Signature=d4216cebcfce69045baeba3f0eaf9a7016a4411cbb173b58c27a4acca066f779&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject
     */

    private final JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }


    @Override
    public void afterJob(JobExecution jobExecution) {

        String jobName = jobExecution.getJobInstance().getJobName();

        JobParameters jobParameters = new JobParametersBuilder().addLong("seq", 1L).toJobParameters();
        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);

        if(lastJobExecution != null) {
            for(StepExecution stepExecution : lastJobExecution.getStepExecutions()) {

                BatchStatus batchStatus = stepExecution.getStatus();
                ExitStatus exitStatus = stepExecution.getExitStatus();

                System.out.println("batchStatus = " + batchStatus);
                System.out.println("exitStatus = " + exitStatus);
                System.out.println("stepName = " + stepExecution.getStepName());
            }
        }
    }
}
