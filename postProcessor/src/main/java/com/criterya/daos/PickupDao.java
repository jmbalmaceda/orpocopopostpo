package com.criterya.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criterya.model.Pickup;

public interface PickupDao extends JpaRepository<Pickup, Long> {
	List<Pickup> findByFrame(Long frame);
}
