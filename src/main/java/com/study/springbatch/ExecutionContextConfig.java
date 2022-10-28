package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ExecutionContextConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExecutionContextTasklet1 executionContextTasklet1;
    private final ExecutionContextTasklet2 executionContextTasklet2;
    private final ExecutionContextTasklet3 executionContextTasklet3;
    private final ExecutionContextTasklet4 executionContextTasklet4;

     /*
        ExecutionContext

        https://s3.us-west-2.amazonaws.com/secure.notion-static.com/c4b402cc-50af-4e6e-9f27-446428097a5f/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221027%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221027T233957Z&X-Amz-Expires=86400&X-Amz-Signature=fc202432944ca287cf37afa109b61f1f81cc819ee22f461af780fcae4b352e55&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject

        - Map 형식으로 저장되는 저장소라고 생각하면 된다.

        JobExecution 의 ExecutionContext

        - JobExecution 과 1대1 대응
        - 바로 전의 JobExecution 이 있으면 그 JobExecution 의 ExecutionContext 정보를 DB(BATCH_JOB_EXECUTION_CONTEXT) 에서
          가져오고 그 후 새로 업데이트를 하여 새로운 JobExecution 과 함께 저장된다.
        - Job 안의 각 Step 들에 대해서는 공유가 된다.

        StepExecution 의 ExecutionContext

        - StepExecution 과 1대1 대응
        - Step 들 끼리는 공유가 되지 않는다.
     */


    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").start(step1()).next(step2()).next(step3()).next(step4()).build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet(executionContextTasklet1).build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet(executionContextTasklet2).build();
    }


    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3").tasklet(executionContextTasklet3).build();
    }


    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4").tasklet(executionContextTasklet4).build();
    }


}
