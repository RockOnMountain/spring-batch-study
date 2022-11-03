package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SimpleJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    /*
        @Autowired(required = false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
     */

    /*
        JobBuilderFactory 아키텍처
        https://s3.us-west-2.amazonaws.com/secure.notion-static.com/1617f82d-8e64-4a48-acf5-8f16385d4589/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221102%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221102T233713Z&X-Amz-Expires=86400&X-Amz-Signature=d168b0731888dc0fb264701225396142ac8b86e5b473ab84cee73fa46b7a0fb9&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject

        JobBuilderFactory 상속구조
        https://s3.us-west-2.amazonaws.com/secure.notion-static.com/003fdfc6-724a-434b-8ae9-bbdb8fb588e3/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221102%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221102T233847Z&X-Amz-Expires=86400&X-Amz-Signature=08be826a13565b850ff6ecc7f75a554724bf54ab3a4622717f9cb77054d93dec&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject
     */

    /*
        SimpleJob 은 JobBuilderHelper 를 상속받는다.
        JobBuilderHelper 에는 CommonJobProperties 가 있는데 여기에 listener, validator 등에 대한 정보가 담긴다.
     */
    @Bean
    public Job job() {
        return jobBuilderFactory.get("simpleJob")
                .start(step1())
                .next(step2())
                .incrementer(new RunIdIncrementer())
                .validator(parameters -> {})
                .listener(new JobExecutionListener() {

                    @Override
                    public void beforeJob(JobExecution jobExecution) {

                    }


                    @Override
                    public void afterJob(JobExecution jobExecution) {

                    }
                })
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            System.out.println("step1 executed");
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
            chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
            contribution.setExitStatus(ExitStatus.STOPPED);
            System.out.println("step2 executed");
            return RepeatStatus.FINISHED;
        }).build();
    }

}
