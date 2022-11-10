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
        preventRestart
        SimpleJobLauncher -> job.isRestartable = false 이므로 아예 실행이 되지 않음

        preventRestart 를 걸지 않고 완료된 Job 을 다시 실행하는 경우는
        1. job.isRestartable = true 여서 이쪽은 통과가 되지만
        2. jobExecution = jobRepository.createJobExecution(job.getName(), jobParameters)
            - 이 부분에서 검증부분이 있는데 jobExecution status 가 completed 이면 예외를 내뱉는다


        정리하면 preventRestart 를 걸면 jobExecution 을 생성하기도 전에 예외를 내뱉는다.
     */

    @Bean
    public Job job() {
        return jobBuilderFactory.get("simpleJob")
                .start(step1())
                .next(step2())
                .preventRestart()
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
            System.out.println("step2 executed");
            return RepeatStatus.FINISHED;
        }).build();
    }

}
