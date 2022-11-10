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
        incrementer

        기존의 JobParameters 변경없이 Job 을 여러번 실행하고자 할 때 사용
        내가 설정한 parameters 외에 id 라는 파라미터를 설정 후 계속 증가시키므로 같은 Job 이라도 계속 실행시킬 수 있다.
     */
    @Bean
    public Job job() {
        return jobBuilderFactory.get("simpleJob")
                .start(step1())
                .next(step2())
                .incrementer(new RunIdIncrementer())
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
