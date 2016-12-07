package com.criterya.daos;

import com.criterya.model.Interaccion;

public interface InteraccionRepositoryCustom {
	Interaccion loadAcciones(Interaccion interaccion);
	void agregarAccion(Interaccion interaccion, String accion);
	void quitarAccion(Interaccion interaccion, String accion);
}
