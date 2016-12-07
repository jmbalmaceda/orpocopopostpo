package com.criterya.daos;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.criterya.model.Accion;
import com.criterya.model.Interaccion;

public class InteraccionRepositoryImpl implements InteraccionRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private InteraccionRepository interaccionRepository;
	
	@Override
	public Interaccion loadAcciones(Interaccion interaccion) {
		EntityGraph<Interaccion> graph = this.entityManager.createEntityGraph(Interaccion.class);
		graph.addAttributeNodes("acciones");
		Map<String, Object> hints = new HashMap<String, Object>();
		hints.put("javax.persistence.loadgraph", graph);
		Interaccion salida = this.entityManager.find(Interaccion.class, interaccion.getId(), hints);
		return salida;
	}

	@Override
	@Transactional
	public void agregarAccion(Interaccion interaccion, String accion) {
		Accion accionAux = entityManager.createQuery("FROM Accion a WHERE a.nombre=:accion", Accion.class).setParameter("accion", accion).getSingleResult();
		Interaccion interaccionAux = interaccionRepository.findOne(interaccion.getId());
		interaccionAux.getAcciones().add(accionAux);
		interaccionRepository.save(interaccionAux);
	}

	@Override
	@Transactional
	public void quitarAccion(Interaccion interaccion, String accion) {
		Accion accionAux = entityManager.createQuery("FROM Accion a WHERE a.nombre=:accion", Accion.class).setParameter("accion", accion).getSingleResult();
		Interaccion interaccionAux = interaccionRepository.findOne(interaccion.getId());
		interaccionAux.getAcciones().remove(accionAux);
		interaccionRepository.save(interaccionAux);
	}

}
