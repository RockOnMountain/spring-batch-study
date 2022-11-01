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
public class JobLauncherConfig {

    /*
        JobLauncher

        - 배치 Job 을 실행시키는 역할을 한다
        - Job 과 JobParameters 를 인자로 받으며 배치작업을 실행 후 JobExecution 을 반환
        - 스프링 부트 배치가 구동이 되면 JobLauncher 빈이 자동으로 생성된다.

        Job 실행

        - JobLauncher.run(job, jobParameters)
        - 스프링 부트 배치에서는 JobLauncherApplicationRunner 가 JobLauncher 를 자동적으로 실행시킨다

        동기적 실행 vs 비동기적 실행

        - 동기적 실행
            - taskExecutor 를 SyncTaskExecutor 로 설정한 경우 (기본값임)
            - 배치처리가 끝난 뒤 client 에게 JobExecution 을 반환
            - 스케줄러에 의한 배치처리에 적합 -> 배치 처리 시간이 길어도 상관 없는 경우

        - 비동기적 실행
            - taskExecutor 가 SimpleAsyncTaskExecutor 로 설정한 경우
            - 기본적인 JobExecutor 를 획득한 후 Client 에게 반환 -> 그 후 배치처리
            - HTTP 요청에 의한 배치처리에 적합 - 배치처리 시간이 길 경우 응답이 늦어지지 않도록 하기 위해

        https://s3.us-west-2.amazonaws.com/secure.notion-static.com/4358cfca-16a3-4fc8-ae9d-ab98f223620f/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221101%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221101T010324Z&X-Amz-Expires=86400&X-Amz-Signature=0c2eb41bf30a81ae96f39bd371fdb4b44153a5492dffc8b6b0c255139f253a03&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Untitled.png%22&x-id=GetObject
     */


    /*
        스프링배치가 시작되면 실행되는 순서
        1. BatchAutoConfiguration 의 JobLauncherApplicationRunner 빈
        2. JobLauncherApplicationRunner > run() > execute() > jobLauncher.run(job, jobParameters)
     */

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").start(step1()).next(step2()).build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            Thread.sleep(3000);
            return RepeatStatus.FINISHED;
        }).build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
            return RepeatStatus.FINISHED;
        }).build();
    }

}
