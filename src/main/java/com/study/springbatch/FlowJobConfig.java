package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FlowJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job2() {
        return jobBuilderFactory.get("flowJob").start(flow()).next(step5()).end().build();
    }


    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        return flowBuilder.start(step3()).next(step4()).end();
    }


    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3").tasklet((contribution, chunkContext) -> {
            System.out.println("step3 executed");
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4").tasklet((contribution, chunkContext) -> {
            System.out.println("step4 executed");
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5").tasklet((contribution, chunkContext) -> {
            System.out.println("step5 executed");
            return RepeatStatus.FINISHED;
        }).build();
    }

}
