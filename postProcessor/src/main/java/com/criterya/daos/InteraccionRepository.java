package com.criterya.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criterya.model.Interaccion;

public interface InteraccionRepository extends JpaRepository<Interaccion, Integer>, InteraccionRepositoryCustom {

}
