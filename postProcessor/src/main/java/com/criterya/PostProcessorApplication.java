package com.criterya;

import java.util.Calendar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.criterya.daos.LogRepository;
import com.criterya.model.Log;
import com.criterya.view.AppWindow;;

@SpringBootApplication
public class PostProcessorApplication {
	public static ConfigurableApplicationContext context;
	
	public static ConfigurableApplicationContext getContext(){
		return context;
	}

	private static void cargarDB(){
		/* Logs */
		LogRepository logRepository = context.getBean(LogRepository.class);
		Log log1 = new Log();
		log1.setIdLog(0);
		log1.setFecha(Calendar.getInstance().getTime());
		log1.setTexto("Log de prueba");
		log1.setUltimoBlobIdLogAnterior(0);
		log1.setVideoRgb("video_test.avi");
		logRepository.save(log1);
		log1 = new Log();
		log1.setIdLog(1);
		log1.setFecha(Calendar.getInstance().getTime());
		log1.setTexto("Log de prueba2");
		log1.setUltimoBlobIdLogAnterior(0);
		log1.setVideoRgb("video_test_2.avi");
		logRepository.save(log1);
	}
	
	
	public static void main(String[] args) {
		context = new SpringApplicationBuilder(PostProcessorApplication.class).headless(false).run(args);
		cargarDB();
		AppWindow app = context.getBean(AppWindow.class);
		app.show();
	}
}
