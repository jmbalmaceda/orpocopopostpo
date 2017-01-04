package com.criterya.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.criterya.PostProcessorCommons;
import com.criterya.model.Blob;

public class BlobRepositoryImpl implements BlobRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Integer> getPeopleId(Integer firstId, Integer lastId){
		List<Integer> salida = entityManager.createQuery("SELECT p.idPerson FROM Blob p WHERE p.id>=:firstId and p.id<=:lastId GROUP BY p.idPerson HAVING COUNT(p)>:countMin "
				+"ORDER BY p.id ASC"
				, Integer.class)
				.setParameter("firstId", firstId)
				.setParameter("lastId", lastId)
				.setParameter("countMin", PostProcessorCommons.MIN_COUNT_OF_BLOBS.longValue())
				.getResultList();
		return salida;
	}

	@Override
	public List<Blob> getPickupByPeriodUsingId(Integer firstId, Integer lastId) {
		List<Blob> salida = entityManager.createQuery("FROM Blob p WHERE p.id>=:firstId and p.id<=:lastId ORDER BY p.id", Blob.class)
				.setParameter("firstId", firstId)
				.setParameter("lastId", lastId)
				.getResultList();
		return salida;
	}

	@Override
	public List<Blob> getPickupByPeriodUsingId(Integer firstId) {
		List<Blob> salida = entityManager.createQuery("FROM Blob p WHERE p.id>=:firstId ORDER BY p.id", Blob.class)
				.setParameter("firstId", firstId)
				.getResultList();
		return salida;
	}

	@Override
	public List<Blob> getBlobs(Integer idPerson, Integer firstBlobId,
			Integer lastBlobId) {
		List<Blob> salida = entityManager.createQuery("FROM Blob b WHERE b.idPerson=:idPerson AND b.id>=:firstBlobId AND b.id<=:lastBlobId ORDER BY b.id ASC", Blob.class)
				.setParameter("idPerson", idPerson)
				.setParameter("firstBlobId", firstBlobId)
				.setParameter("lastBlobId", lastBlobId)
				.getResultList();
		return salida;
	}

	@Override
	@Transactional
	public Blob getOneWithTime(Integer id) {
		Blob salida = entityManager.createQuery("From Blob b WHERE b.id=:id", Blob.class)
				.setParameter("id", id)
				.getSingleResult();
		salida.getCurrent_time();
		return salida;
	}
}
