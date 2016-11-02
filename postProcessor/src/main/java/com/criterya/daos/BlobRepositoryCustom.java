package com.criterya.daos;

import java.util.List;

import com.criterya.model.Blob;

public interface BlobRepositoryCustom {
	List<Blob> getPickupByPeriodUsingId(Integer firstId, Integer lastId);
	List<Blob> getPickupByPeriodUsingId(Integer firstId);
}
