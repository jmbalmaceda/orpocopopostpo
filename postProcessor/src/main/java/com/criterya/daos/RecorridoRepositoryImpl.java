package com.criterya.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

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
