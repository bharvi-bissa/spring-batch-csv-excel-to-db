package com.etl.springbatchexcelcsvtodb.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.etl.springbatchexcelcsvtodb.batch.JobCompletionListener;
import com.etl.springbatchexcelcsvtodb.batch.UserExcelItemReader;
import com.etl.springbatchexcelcsvtodb.model.User;

/**
 * @author bharvi
 *
 */
@Configuration
public class SpringBatchConfig {

	private static final String ETL_EXCEL_LOAD = "etl-excel-load";
	private static final String EXCEL_TO_DB_JOB = "excel-to-db-job";
	private static final String DELIMITER = ",";
	private static final String DEPT = "dept";
	private static final String AGE = "age";
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String CSV_READER = "CSV-Reader";
	private static final String SRC_MAIN_RESOURCES_USERS_CSV = "src/main/resources/users.csv";
	private static final String ETL_FILE_LOAD = "etl-file-load";
	private static final String CSV_TO_DB_JOB = "csv-to-db-job";
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	
	@Bean
	public Job csvToDbJob(ItemReader<User> itemReader, ItemProcessor<User, User> itemProcessor,
			ItemWriter<User> itemWriter) {

		return jobBuilderFactory
				.get(CSV_TO_DB_JOB)
				.listener(new JobCompletionListener())
				.incrementer(new RunIdIncrementer())
				.start(csvToDbStep(itemReader, itemProcessor, itemWriter))
				.build();
	}
	
	@Bean
	public Job excelToDbJob(UserExcelItemReader userExcelItemReader, ItemProcessor<User, User> itemProcessor,
			ItemWriter<User> itemWriter) {
		return jobBuilderFactory
				.get(EXCEL_TO_DB_JOB)
				.listener(new JobCompletionListener())
				.incrementer(new RunIdIncrementer())
				.start(excelToDbStep(userExcelItemReader, itemProcessor, itemWriter))
				.build();
	}
	
	private Step excelToDbStep(UserExcelItemReader userExcelItemReader, ItemProcessor<User, User> itemProcessor,
			ItemWriter<User> itemWriter) {
		return stepBuilderFactory
				.get(ETL_EXCEL_LOAD)
				.<User, User>chunk(3)
				.reader(userExcelItemReader)
				.processor(itemProcessor)
				.writer(itemWriter).build();
	}

	public Step csvToDbStep(ItemReader<User> itemReader, ItemProcessor<User, User> itemProcessor,
			ItemWriter<User> itemWriter) {

		return stepBuilderFactory
				.get(ETL_FILE_LOAD)
				.<User, User>chunk(3)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter).build();

	}

	@Bean
	public FlatFileItemReader<User> itemReader() {

		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new FileSystemResource(SRC_MAIN_RESOURCES_USERS_CSV));
		flatFileItemReader.setName(CSV_READER);
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	public LineMapper<User> lineMapper() {

		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(DELIMITER);
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(ID, NAME, AGE, DEPT);

		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}

}
