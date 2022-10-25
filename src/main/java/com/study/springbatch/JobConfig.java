package com.study.springbatch;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").start(step1()).next(step2()).build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {

            this.printJobParameters(contribution, chunkContext);

            System.out.println("step1 was executed");
            return RepeatStatus.FINISHED;
        }).build();
    }


    private void printJobParameters(StepContribution contribution, ChunkContext chunkContext) {

        // 디버그 모드로 확인해 볼 것
        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
        jobParameters.getString("name");
        jobParameters.getLong("seq");
        jobParameters.getDate("date");
        jobParameters.getDouble("age");

        // JobParameters를 확인할 수 있는 다른 방법 -> 위의 방법을 추천
        Map<String, Object> jobParametersMap = chunkContext.getStepContext().getJobParameters();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {

            System.out.println("step2 was executed");
            // throw new RuntimeException();
            return RepeatStatus.FINISHED;
        }).build();
    }

}
