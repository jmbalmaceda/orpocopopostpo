package com.criterya.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.criterya.model.Log;

public class LogRepositoryImpl implements LogRepositoryCustom{
	@PersistenceContext
	private EntityManager entityManager;
	
	public Integer getLastBlobId(Log log){
		Integer salida = null;
		List<Log> resultList = entityManager.createQuery("FROM Log l WHERE l.idLog > :id ORDER BY l.idLog ASC", Log.class).setParameter("id", log.getIdLog()).setMaxResults(1).getResultList();
		if (resultList!=null && !resultList.isEmpty())
			salida = resultList.get(0).getUltimoBlobIdLogAnterior();
		return salida ;
	}
}
