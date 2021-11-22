package io.spring.functaskdemo;

import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableTask
public class FunctaskdemoApplication {

	@Autowired
	private  ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(FunctaskdemoApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(Function localUpperCase) {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args){
				FunctionCatalog catalog = context.getBean(FunctionCatalog.class);
				Function<String, String> function = catalog.lookup("upperCaseFunction");
				System.out.println(function.apply("Simple Function Jar"));
				System.out.println(localUpperCase.apply("Local Function"));
			}
		};
	}

	@Bean
	public Function<String, String> localUpperCase() {
		return value -> value.toUpperCase();
	}
}
