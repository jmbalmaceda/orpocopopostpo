package com.criterya;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.criterya.view.AppWindow;;

@SpringBootApplication
public class PostProcessorApplication {
	public static ConfigurableApplicationContext context;
	
	public static ConfigurableApplicationContext getContext(){
		return context;
	}

	public static void main(String[] args) {
		context = new SpringApplicationBuilder(PostProcessorApplication.class).headless(false).run(args);
		AppWindow app = context.getBean(AppWindow.class);
		app.show();
	}
}
