package com.criterya.daos;

import java.util.List;

import com.criterya.model.Recorrido;
import com.criterya.model.Video;

public interface RecorridoRepositoryCustom {
	List<Recorrido> getRecorridos(Integer firstId, Integer lastId, Video video);
}
