package com.criterya.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.criterya.PostProcessorCommons;
import com.criterya.model.Accion;

public class AccionRepositoryImpl implements AccionRepositoryCustom {

	@Autowired
	AccionRepository accionRepository;
	
	@Override
	public Accion getAccionToca() {
		List<Accion> aux = accionRepository.findByNombre(PostProcessorCommons.ACCION_TOCA);
		Accion accion = null;
		if (aux==null || aux.isEmpty()){
			accion = new Accion();
			accion.setNombre(PostProcessorCommons.ACCION_TOCA);
			accion.setDescripcion(PostProcessorCommons.DESCRIPCION_ACCION_TOCA);
			accion = accionRepository.save(accion);
		}else{
			accion = aux.get(0);
		}
		return accion;
	}

	@Override
	public Accion getAccionCompra() {
		List<Accion> aux = accionRepository.findByNombre(PostProcessorCommons.ACCION_COMPRA);
		Accion accion = null;
		if (aux==null || aux.isEmpty()){
			accion = new Accion();
			accion.setNombre(PostProcessorCommons.ACCION_COMPRA);
			accion.setDescripcion(PostProcessorCommons.DESCRIPCION_ACCION_COMPRA);
			accion = accionRepository.save(accion);
		}else{
			accion = aux.get(0);
		}
		return accion;
	}

	@Override
	public Accion getAccionDevuelve() {
		List<Accion> aux = accionRepository.findByNombre(PostProcessorCommons.ACCION_DEVUELVE);
		Accion accion = null;
		if (aux==null || aux.isEmpty()){
			accion = new Accion();
			accion.setNombre(PostProcessorCommons.ACCION_DEVUELVE);
			accion.setDescripcion(PostProcessorCommons.DESCRIPCION_ACCION_DEVUELVE);
			accion = accionRepository.save(accion);
		}else{
			accion = aux.get(0);
		}
		return accion;
	}

}
