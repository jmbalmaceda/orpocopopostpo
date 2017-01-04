package com.criterya.daos;

import java.util.List;

import com.criterya.model.Blob;

public interface BlobRepositoryCustom {
	List<Integer> getPeopleId(Integer firstId, Integer lastId);
	List<Blob> getPickupByPeriodUsingId(Integer firstId, Integer lastId);
	List<Blob> getPickupByPeriodUsingId(Integer firstId);
	List<Blob> getBlobs(Integer idPerson, Integer firstBlobId, Integer lastBlobId);
	Blob getOneWithTime(Integer id);
}
