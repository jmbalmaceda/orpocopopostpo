package com.criterya.daos;

import java.util.Date;
import java.util.List;

import com.criterya.model.Recorrido;
import com.criterya.model.Video;

public interface RecorridoRepositoryCustom {
	List<Recorrido> getRecorridos(Integer firstId, Integer lastId, Video video);
	List<Recorrido> getRecorridos(Date dateFrom, Date dateTo);
	Recorrido loadInteracciones(Recorrido recorridoSeleccionado);
}
