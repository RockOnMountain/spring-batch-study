package com.study.springbatch.dbSchema;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class DBJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }


    private Step step1() {
        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            System.out.println("step1 was executed");
            return RepeatStatus.FINISHED;
        }).build();
    }


    private Step step2() {
        return stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
            System.out.println("step2 was executed");
            return RepeatStatus.FINISHED;
        }).build();
    }

}
