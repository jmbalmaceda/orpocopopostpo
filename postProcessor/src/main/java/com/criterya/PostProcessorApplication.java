package com.criterya;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.criterya.daos.BlobRepository;
import com.criterya.daos.LogRepository;
import com.criterya.model.Log;

@SpringBootApplication
public class PostProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostProcessorApplication.class, args);
	}
	
	@Bean
	public String demo(BlobRepository repository, LogRepository logDao) {
		List<Log> logs = logDao.findAll();
		for (Log log : logs) {
			System.out.println(log.getUbicacionVideo() + " - " +log.getNombreVideo()+" - " +log.getUltimoBlobIdLogAnterior() + " - "+logDao.getLastBlobId(log));
		}
		return "ads";
	}
}
