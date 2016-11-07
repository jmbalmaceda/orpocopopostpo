package com.criterya.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.criterya.PostProcessorCommons;
import com.criterya.model.Blob;
import com.criterya.model.Recorrido;
import com.criterya.model.Video;

public class RecorridoRepositoryImpl implements RecorridoRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private BlobRepository blobRepository;
	
	private Recorrido getRecorrido(Integer idPerson, Integer firstBlobId, Integer lastBlobId, Video video){
		List<Blob> blobs = blobRepository.getBlobs(idPerson, firstBlobId, lastBlobId);
		if (blobs==null || blobs.isEmpty())
			return null;
		Recorrido recorrido = new Recorrido();
		Blob firstBlob = blobs.get(0);
		Blob lastBlob = blobs.get(blobs.size()-1);
		Integer duracion = Long.valueOf(lastBlob.getCurrent_time().getTime() - firstBlob.getCurrent_time().getTime()).intValue();
		recorrido.setDuracion(duracion );
		recorrido.setFrameEntrada(firstBlob.getFrame());
		recorrido.setFrameSalida(lastBlob.getFrame());
		recorrido.setHorarioEntrada(firstBlob.getCurrent_time());
		recorrido.setHorarioSalida(lastBlob.getCurrent_time());
		recorrido.setIdPerson(idPerson);
		recorrido.setVideo(video);
		/* Calcular entrada de la persona */
		int entradaIzq = 0;
		int entradaDer = 0;
		for(int i=1; i<PostProcessorCommons.MIN_COUNT_OF_BLOBS; i++){
			Blob b1 = blobs.get(i-1);
			Blob b2 = blobs.get(i);
			if (b1.getBlob_x() > b2.getBlob_x())
				entradaIzq++;
			else if (b1.getBlob_x() < b2.getBlob_x())
				entradaDer++;
		}
		if (entradaIzq>entradaDer)
			recorrido.setSentidoEntrada(PostProcessorCommons.IZQUIERDA);
		else
			recorrido.setSentidoEntrada(PostProcessorCommons.DERECHA);
		
		/* Calcular salida de la persona */
		int salidaIzq = 0;
		int salidaDer = 0;
		for(int i=blobs.size()-PostProcessorCommons.MIN_COUNT_OF_BLOBS+1; i<blobs.size(); i++){
			Blob b1 = blobs.get(i-1);
			Blob b2 = blobs.get(i);
			if (b1.getBlob_x() > b2.getBlob_x())
				salidaIzq++;
			else if (b1.getBlob_x() < b2.getBlob_x())
				salidaDer++;
		}
		if (salidaDer>salidaIzq)
			recorrido.setSentidoSalida(PostProcessorCommons.DERECHA);
		else
			recorrido.setSentidoSalida(PostProcessorCommons.IZQUIERDA);
		
		/* Calcular promedio de los DEPTH */
		Integer sumaAlturas = 0;
		for (Blob blob : blobs) {
			sumaAlturas += blob.getBlob_depth();
		}
		recorrido.setAltura(sumaAlturas/blobs.size());
		
		return recorrido ;
	}
	
	@Override
	public List<Recorrido> getRecorridos(Integer firstId, Integer lastId, Video video) {
		List<Recorrido> recorridos = new ArrayList<>();
		Blob firstBlob = blobRepository.findOne(firstId);
		Blob lastBlob = blobRepository.findOne(lastId);
		List<Integer> blobsId = blobRepository.getPeopleId(firstId, lastId);
		for (Integer blobId : blobsId) {
			Recorrido recorrido = getRecorrido(blobId, firstBlob.getId(), lastBlob.getId(), video);
			recorridos.add(recorrido );
		}
		return recorridos ;
	}

}
