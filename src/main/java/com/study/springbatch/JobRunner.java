package com.study.springbatch;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job job;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*
            JobParameters 와 JobInstance 는 1:1 관계
            JobParameters 는 Map 을 감싼 일급컬렉션 이다.
         */

        /*
            JobInstance JobExecution JobExecutionParam 관계들
            1) JobParameters 와 JobInstance 는 1:1 관계가 맞다.
            2) JobInstance 와 JobExecution 은 1:N 관계이다.
            3) JobExecution 과 JobExecutionParam 은 1:1 관계이다.
            4) 주의해야할 점은 JobParameters 와 JobExecutionParam 은 다르다는 것이다.

            JobInstance 가 Completed 인 경우는 JobExecution 이 더 이상 생기지 않는다.
            그러나 JobInstance 가 Failed 인 경우는 JobExecution 이 더 생긴다.
            참고) https://backtony.github.io/assets/img/post/spring/batch/2/2-6.PNG
         */

        /*
            JobParameters jobParameters =
                new JobParametersBuilder().addString("name", "user1").addLong("seq", 3L).addDouble("age", 16.7)
                        .addDate("date", new Date()).toJobParameters();
         */

        JobParameters jobParameters =
                new JobParametersBuilder().addLong("seq", 2L)
                        .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
