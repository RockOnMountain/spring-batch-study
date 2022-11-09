package com.study.springbatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.ObjectUtils;

public class CustomJobParametersValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {

        if(ObjectUtils.isEmpty(parameters.getString("name"))) {
            throw new JobParametersInvalidException("name parameter is not found");
        }

    }
}
