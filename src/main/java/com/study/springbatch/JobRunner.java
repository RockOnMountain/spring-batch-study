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

        // JobParameters jobParameters = new JobParametersBuilder().addString("name", "user2").toJobParameters();
        JobParameters jobParameters =
                new JobParametersBuilder().addString("name", "user1").addLong("seq", 2L).addDate("date", new Date())
                        .addDouble("age", 16.5).toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
