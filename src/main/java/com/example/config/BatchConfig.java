package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.tasklet.RetryTasklet;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private RetryTasklet retryTasklet;
	
	/**
	 * Stepの作成
	 * @return
	 */
	@Bean
	public Step retryTaskletStep() {
		return stepBuilderFactory.get("RetryTaskletStep")
								.tasklet(retryTasklet)
								.build();
	}
	
	
	/**
	 * jobの作成
	 * @return
	 */
	@Bean
	public Job retryTaskletJob() {
		return jobBuilderFactory.get("RetryTaskletJob")
								.incrementer(new RunIdIncrementer())
								.start(retryTaskletStep())
								.build();
	}

}
