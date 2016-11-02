package com.criterya.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criterya.model.Blob;

public interface BlobRepository extends JpaRepository<Blob, Integer>, BlobRepositoryCustom {
	List<Blob> findByFrame(Integer frame);
}
