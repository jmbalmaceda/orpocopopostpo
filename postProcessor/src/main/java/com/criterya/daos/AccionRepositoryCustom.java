package com.criterya.daos;

import com.criterya.model.Accion;

public interface AccionRepositoryCustom {
	Accion getAccionToca();
	Accion getAccionCompra();
	Accion getAccionDevuelve();
}
