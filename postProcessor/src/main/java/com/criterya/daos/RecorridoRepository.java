package com.criterya.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criterya.model.Recorrido;

public interface RecorridoRepository extends JpaRepository<Recorrido, Integer>, RecorridoRepositoryCustom {

}
