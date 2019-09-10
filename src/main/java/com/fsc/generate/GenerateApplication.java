package com.fsc.generate;

import common.config.tools.config.ConfigTools3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.fsc.generate"} )
public class GenerateApplication {
	public static void main(String[] args) {
		ConfigTools3.load();
		SpringApplication.run(GenerateApplication.class, args);
	}
}
