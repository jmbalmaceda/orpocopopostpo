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
	
	public static void setStatus(String status){
		context.getBean(AppWindow.class).setStatus(status);
	}

	private static void cargarDB(){
		/* Logs */
		LogRepository logRepository = context.getBean(LogRepository.class);
		Log log1 = new Log();
		log1.setIdLog(0);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, 8); // corresponde a Septiembre
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		log1.setFecha(cal.getTime());
		log1.setTexto("Log de prueba");
		log1.setUltimoBlobIdLogAnterior(105600);
		log1.setVideoRgb("D:\\Videos_SantaRosa\\VideoRGB2016-9-1___9-0.avi");
		logRepository.save(log1);
	}
	
	
	public static void main(String[] args) {
		context = new SpringApplicationBuilder(PostProcessorApplication.class).headless(false).run(args);
		cargarDB();
		AppWindow app = context.getBean(AppWindow.class);
		app.show();
	}
}
