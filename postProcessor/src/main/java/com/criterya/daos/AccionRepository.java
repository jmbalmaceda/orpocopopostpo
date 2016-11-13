package com.criterya.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criterya.model.Accion;

public interface AccionRepository extends JpaRepository<Accion, Integer>, AccionRepositoryCustom {
	List<Accion> findByNombre(String nombre);
}
