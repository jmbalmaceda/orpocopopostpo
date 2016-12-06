package com.criterya.daos;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.criterya.model.Interaccion;

public class InteraccionRepositoryImpl implements InteraccionRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Interaccion loadAcciones(Interaccion interaccion) {
		EntityGraph<Interaccion> graph = this.entityManager.createEntityGraph(Interaccion.class);
		graph.addAttributeNodes("acciones");
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.loadgraph", graph);
		Interaccion salida = this.entityManager.find(Interaccion.class, interaccion.getId(), hints);
		return salida;
	}

}
