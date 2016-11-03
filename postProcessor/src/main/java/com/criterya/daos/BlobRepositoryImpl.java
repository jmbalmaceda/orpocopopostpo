package com.criterya.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.criterya.model.Blob;

public class BlobRepositoryImpl implements BlobRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Blob> getPickupByPeriodUsingId(Integer firstId, Integer lastId) {
		List<Blob> salida = entityManager.createQuery("FROM Blob p WHERE p.id>=:firstId and p.id<=:lastId ORDER BY p.id", Blob.class).setParameter("firstId", firstId).setParameter("lastId", lastId).getResultList();
		return salida;
	}

	@Override
	public List<Blob> getPickupByPeriodUsingId(Integer firstId) {
		List<Blob> salida = entityManager.createQuery("FROM Blob p WHERE p.id>=:firstId ORDER BY p.id", Blob.class).setParameter("firstId", firstId).getResultList();
		return salida;
	}
}
