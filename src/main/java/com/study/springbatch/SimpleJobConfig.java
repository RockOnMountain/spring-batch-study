package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
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
        JobParametersValidator 는
        - SimpleJobLauncher 에서 한번
        - Job 이 실행되기 전에 한번 (SimpleJob -> AbstractJob 에서 한번)
        이렇게 총 2번 실행이 된다.
     */
    @Bean
    public Job job() {
        return jobBuilderFactory.get("simpleJob")
                .start(step1())
                .next(step2())
                .next(step3())
    //            .validator(new CustomJobParametersValidator())
                .validator(new DefaultJobParametersValidator(new String[] {"name", "date"}, new String[] {"count"}))
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


    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3").tasklet((contribution, chunkContext) -> {
            System.out.println("step3 executed");
            return RepeatStatus.FINISHED;
        }).build();
    }

}
