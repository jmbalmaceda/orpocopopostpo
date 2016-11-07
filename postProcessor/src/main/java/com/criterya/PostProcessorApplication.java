package com.criterya;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.criterya.daos.LogRepository;
import com.criterya.daos.RecorridoRepository;
import com.criterya.model.Log;
import com.criterya.model.Recorrido;
import com.criterya.model.Video;

@SpringBootApplication
public class PostProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostProcessorApplication.class, args);
	}
	
	@Bean
	public String demo(LogRepository logDao, RecorridoRepository recorridoDao) {
		List<Log> logs = logDao.findAll();
		for (Log log : logs) {
			Integer firstBlobId = logDao.getPrimerBlobId(log);
			Integer lastBlobId = logDao.getUltimoBlobId(log);
			Video video = new Video();
			video.setFechaInicio(log.getFecha());
			video.setUbicacion(log.getUbicacionVideo());
			video.setNombre(log.getNombreVideo());
			List<Recorrido> recorridos = recorridoDao.getRecorridos(firstBlobId, lastBlobId, video);
			System.out.println("Log "+log.getTexto()+": "+recorridos.size());
		}
		return "ads";
	}
}
