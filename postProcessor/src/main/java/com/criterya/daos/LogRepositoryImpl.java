package com.criterya.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.criterya.model.Blob;
import com.criterya.model.Log;

public class LogRepositoryImpl implements LogRepositoryCustom{
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private BlobRepository blobRepository;
	
	public Integer getUltimoBlobId(Log log){
		Integer salida = null;
		List<Log> resultList = entityManager.createQuery("FROM Log l WHERE l.idLog > :id ORDER BY l.idLog ASC", Log.class).setParameter("id", log.getIdLog()).setMaxResults(1).getResultList();
		if (resultList!=null && !resultList.isEmpty())
			salida = resultList.get(0).getUltimoBlobIdLogAnterior();
		else{
			salida = entityManager.createQuery("SELECT MAX(b.id) FROM Blob b", Integer.class).getSingleResult();
		}
		return salida;
	}
	
	public Integer getPrimerBlobId(Log log){
		Integer salida = null;
		List<Blob> resultList = entityManager.createQuery("FROM Blob b WHERE b.id > :id ORDER BY b.id ASC", Blob.class).setParameter("id", log.getUltimoBlobIdLogAnterior()).setMaxResults(1).getResultList();
		if (resultList!=null && !resultList.isEmpty())
			salida = resultList.get(0).getId();
		return salida;
	}

	@Override
	public List<Integer> getBlobsId(Log log) {
		Integer firstId = getPrimerBlobId(log);
		Integer lastId = getUltimoBlobId(log);
		if (lastId==null)
			lastId = Integer.MAX_VALUE;
		List<Integer> blobs = blobRepository.getPeopleId(firstId, lastId);
		return blobs;
	}
}
