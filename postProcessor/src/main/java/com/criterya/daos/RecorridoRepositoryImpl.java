package com.criterya.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.criterya.PostProcessorCommons;
import com.criterya.model.Accion;
import com.criterya.model.Blob;
import com.criterya.model.Interaccion;
import com.criterya.model.Recorrido;
import com.criterya.model.Video;

public class RecorridoRepositoryImpl implements RecorridoRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BlobRepository blobRepository;
	@Autowired
	private AccionRepository accionRepository;

	private Recorrido getRecorrido(Integer idPerson, Integer firstBlobId, Integer lastBlobId, Video video){
		List<Blob> blobs = blobRepository.getBlobs(idPerson, firstBlobId, lastBlobId);
		if (blobs==null || blobs.isEmpty())
			return null;
		Recorrido recorrido = new Recorrido();
		Blob firstBlob = blobs.get(0);
		Blob lastBlob = blobs.get(blobs.size()-1);
		Integer duracion = Long.valueOf(lastBlob.getCurrent_time().getTime() - firstBlob.getCurrent_time().getTime()).intValue();
		recorrido.setDuracion(duracion );
		recorrido.setFrameEntrada(firstBlob.getFrame());
		recorrido.setFrameSalida(lastBlob.getFrame());
		recorrido.setHorarioEntrada(firstBlob.getCurrent_time());
		recorrido.setHorarioSalida(lastBlob.getCurrent_time());
		recorrido.setIdPerson(idPerson);
		recorrido.setSexo(PostProcessorCommons.MUJER);
		recorrido.setEdad(40);
		recorrido.setVideo(video);
		List<Interaccion> interacciones = new ArrayList<>();
		recorrido.setInteracciones(interacciones );
		/* Calcular entrada de la persona */
		int entradaIzq = 0;
		int entradaDer = 0;
		for(int i=1; i<PostProcessorCommons.MIN_COUNT_OF_BLOBS; i++){
			Blob b1 = blobs.get(i-1);
			Blob b2 = blobs.get(i);
			if (b1.getBlob_x() > b2.getBlob_x())
				entradaDer++;
			else if (b1.getBlob_x() < b2.getBlob_x())
				entradaIzq++;
		}
		if (entradaIzq>entradaDer)
			recorrido.setSentidoEntrada(PostProcessorCommons.IZQUIERDA);
		else
			recorrido.setSentidoEntrada(PostProcessorCommons.DERECHA);
		recorrido.setX(blobs.get(0).getBlob_x());
		recorrido.setY(blobs.get(0).getBlob_y());

		/* Calcular salida de la persona */
		int salidaIzq = 0;
		int salidaDer = 0;
		for(int i=blobs.size()-PostProcessorCommons.MIN_COUNT_OF_BLOBS+1; i<blobs.size(); i++){
			Blob b1 = blobs.get(i-1);
			Blob b2 = blobs.get(i);
			if (b1.getBlob_x() > b2.getBlob_x())
				salidaIzq++;
			else if (b1.getBlob_x() < b2.getBlob_x())
				salidaDer++;
		}
		if (salidaDer>salidaIzq)
			recorrido.setSentidoSalida(PostProcessorCommons.DERECHA);
		else
			recorrido.setSentidoSalida(PostProcessorCommons.IZQUIERDA);

		/* Calcular promedio de los DEPTH */
		/* Calcular las interacciones con la góndola */
		Integer sumaAlturas = 0;
		boolean enGondola = false;
		List<Blob> blobsEnGondola = new ArrayList<>();
		for (Blob blob : blobs) {
			sumaAlturas += blob.getBlob_depth();
			if (blob.getIdHand()!=null){
				if (!enGondola){
					enGondola = true;
				}
				blobsEnGondola.add(blob);
			} else if (blob.getIdHand()==null){
				if (enGondola){
					enGondola = false;
					if (blobsEnGondola.size()>PostProcessorCommons.MIN_COUNT_OF_BLOBS_PICKUP){
						// Obtener la zona de la interacción
						// Obtener la acción
						Accion accion = accionRepository.getAccionToca();
						List<Accion> acciones = new ArrayList<>();
						acciones.add(accion);
						// Agregar la interacción
						Interaccion interaccion = new Interaccion();
						Blob primerBlob = blobsEnGondola.get(0);
						interaccion.setFrameInicio(primerBlob.getFrame());
						interaccion.setX(primerBlob.getBlob_x());
						interaccion.setY(primerBlob.getBlob_y());
						interaccion.setFrameFin(blobsEnGondola.get(blobsEnGondola.size()-1).getFrame());
						interaccion.setAcciones(acciones);
						interaccion.setHorario(blobsEnGondola.get(0).getCurrent_time());
						interacciones.add(interaccion);
					}
					blobsEnGondola.clear();
				}
			}
		}
		recorrido.setAltura(sumaAlturas/blobs.size());

		return recorrido ;
	}

	@Override
	public List<Recorrido> getRecorridos(Integer firstId, Integer lastId, Video video) {
		List<Recorrido> recorridos = new ArrayList<>();
		Blob firstBlob = blobRepository.findOne(firstId);
		Blob lastBlob = blobRepository.findOne(lastId);
		List<Integer> blobsId = blobRepository.getPeopleId(firstId, lastId);
		for (Integer blobId : blobsId) {
			Recorrido recorrido = getRecorrido(blobId, firstBlob.getId(), lastBlob.getId(), video);
			recorridos.add(recorrido );
		}
		return recorridos ;
	}

	@Override
	public List<Recorrido> getRecorridos(Date dateFrom, Date dateTo) {
		List<Recorrido> salida = entityManager.createQuery("FROM Recorrido r WHERE r.horarioEntrada BETWEEN :dateFrom AND :dateTo ORDER BY r.horarioEntrada", Recorrido.class)
				.setParameter("dateFrom", dateFrom)
				.setParameter("dateTo", dateTo)
				.getResultList();
		return salida;
	}

	@Override
	public Recorrido loadInteracciones(Recorrido recorridoSeleccionado) {
		if (recorridoSeleccionado.getId()!=null){
			EntityGraph<Recorrido> graph = this.entityManager.createEntityGraph(Recorrido.class);
			graph.addAttributeNodes("interacciones");
			Map<String, Object> hints = new HashMap<String, Object>();
			hints.put("javax.persistence.loadgraph", graph);
			Recorrido salida = this.entityManager.find(Recorrido.class, recorridoSeleccionado.getId(), hints);
			return salida;
		}
		if (recorridoSeleccionado.getInteracciones()==null){
			recorridoSeleccionado.setInteracciones(new ArrayList<Interaccion>());
		}
		return recorridoSeleccionado;
	}

}
