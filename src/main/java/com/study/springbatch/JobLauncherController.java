package com.study.springbatch;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JobLauncherController {

    private final Job job;
    private final JobLauncher jobLauncher;
    private final BasicBatchConfigurer basicBatchConfigurer;


    @PostMapping("/batch")
    public String launch(@RequestBody Member member)
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters =
                new JobParametersBuilder().addString("id", member.getId()).addDate("date", new Date())
                        .toJobParameters();

        jobLauncher.run(job, jobParameters);

        return "batch completed";
    }


    @PostMapping("/async/batch")
    public String asyncLaunch(@RequestBody Member member)
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters =
                new JobParametersBuilder().addString("id", member.getId()).addDate("date", new Date())
                        .toJobParameters();

        // 비동기적으로 실행시키려면 jobLauncher 를 주입받아서 사용하면 안되고 아래의 방법을 사용해야 된다.
        SimpleJobLauncher simpleJobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());

        simpleJobLauncher.run(job, jobParameters);

        return "batch completed";
    }


}
