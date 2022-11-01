package com.study.springbatch;

import javax.sql.DataSource;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBatchConfigurer extends BasicBatchConfigurer {

    /*
        JobRepository 설정을 커스터마이징 할 수 있다.

        - @EnableBatchProcessing 어노테이션만 선언하면 JobRepository 가 자동으로 빈 등록됨
        - BatchConfigurer 인터페이스를 구현하거나 BasicBatchConfigurer 를 상속해서 JobRepository 설정을 커스터마이징 할 수 있다.

            - JDBC 방식으로 설정 - JobRepositoryFactoryBean
                - 내부적으로 AOP 기술을 통해 트랜잭션 처리를 해주고 있음
                - 트랜잭션 isolation 의 기본값은 SERIALIZABLE 로 최고 수준이고, 다른 레벨 (READ_COMMITTED, REPEATABLE_READ)로 지정 가능
                - 메타테이블의 Table Prefix 변경 가능, 기본 값은 BATCH_

            - In Memory 방식으로 설정 - MapJobRepositoryFactoryBean
                - 성능 등의 이유로 도메인 오브젝트를 굳이 데이터베이스에 저장하고 싶지 않을 경우
                - 보통 Test 나 프로토타입의 빠른 개발이 필요할 때 사용
     */


    protected CustomBatchConfigurer(BatchProperties properties, DataSource dataSource,
            TransactionManagerCustomizers transactionManagerCustomizers) {
        super(properties, dataSource, transactionManagerCustomizers);
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {

        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();

        jobRepositoryFactoryBean.setTransactionManager(this.getTransactionManager());
        jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        // jobRepositoryFactoryBean.setTablePrefix("SYSTEM_");

        return jobRepositoryFactoryBean.getObject();
    }
}
