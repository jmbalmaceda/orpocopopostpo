package com.criterya;

import java.util.Iterator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.criterya.daos.LogDao;
import com.criterya.daos.BlobRepository;
import com.criterya.model.Log;
import com.criterya.model.Blob;

@SpringBootApplication
public class PostProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostProcessorApplication.class, args);
	}
	
	@Bean
	public String demo(BlobRepository repository, LogDao logDao) {
		System.out.println(repository.count());
		Iterable<Blob> iterable = repository.getPickupByPeriodUsingId(2187399,2187499);
		Iterator<Blob> it = iterable.iterator();
		for (int i=0; i<100 && it.hasNext(); i++) {
			Blob p = it.next();
			System.out.println(p.getId() + " - "+p.getFrame()+ " - " +p.getBlob_id()+ " - " +p.getBlob_x()+ " - " +p.getCount_blobs()+ " - " +p.getCurrent_time());
		}
		
		Iterator<Log> it2 = logDao.findAll().iterator();
		Log log;
		while(it2.hasNext()){
			log = it2.next();
			System.out.println(log.getFecha()+" - "+log.getIdLog()+" - "+log.getTexto()+" - "+log.getVideoRgb());
		}
		return "ads";
	}
}
