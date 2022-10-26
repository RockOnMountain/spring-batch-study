package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class StepConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").start(step1()).next(step2()).build();
    }


    @Bean
    public Step step1() {

        /*
            - Batch Job 을 구성하는 독립적인 하나의 단계로서 실제 배치 처리를 정의하고 컨트롤하는데 필요한 모든 정보를 가지고 있는 도메인 객체
            - 단순한 단일 태스크 뿐 아니라 입력과 처리 그리고 출력과 관련된 복잡한 비즈니스 로직을 포함하는 모든 설정들을 담고 있다.
            - 배치 작업을 어떻게 구성하고 실행할지 Job 의 세부 작업을 Task 기반으로 설정하고 명세해 놓은 객체
            - 모든 Job 은 하나 이상의 step 으로 구성됨
         */

        // JobLauncher 에서 Job 을 실행하는 것부터 Step -> tasklet 실행(execute)까지 흐름을 한번 봐 볼 것!!!
        return stepBuilderFactory.get("step1").tasklet(new CustomTasklet()).build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {

            /*
                StepExecution
                - Step 에 대한 한번의 시도를 의미하는 객체로서 Step 실행 중에 발생한 정보들을 저장하고 있는 개체
                - Step 이 매번 시도될ㄷ 때마다 생성되며 각 Step 별로 생성된다.
                - Job 이 재시작 하더라도 이미 성공적으로 완료된 Step 은 재실행되지 않고 실패한 Step 만 재실행된다.
                    - Job 이 재실행 될 때 성공한 Step 에 대해서는 StepExecution 이 생성되지 않는다.
                - JobExecution 과의 관계
                    - Step 의 StepExecution 이 모두 정상적으로 완료되어야 JobExecution 이 정상적으로 완료된다.
                    - Step 의 StepExecution 중 하나라도 실패하면 JobExecution 은 실패한다.


                BATCH_STEP_EXECUTION
                - JobExecution 과 StepExecution 은 1대다 관계
                - 하나의 Job 에 여러개의 Step 으로 구성했을 때 각 StepExecution 은 하나의 JobExecution 을 부모로 가진다.
             */
            System.out.println("step2 was executed");
            // throw new RuntimeException();
            return RepeatStatus.FINISHED;
        }).build();
    }

}
