package com.criterya;

import java.text.SimpleDateFormat;

public class PostProcessorCommons {
	/**
	 * Cantidad mínima de cuadros que deben existir para que se lo considere como una persona
	 */
	public static final Integer MIN_COUNT_OF_BLOBS = 3;
	/**
	 * Cantidad mínima de cuadros que deben existir para que se considere contacto con la góndola
	 */
	public static final int MIN_COUNT_OF_BLOBS_PICKUP = 3;
	
	public static final String IZQUIERDA = "Izquierda";
	public static final String DERECHA = "Derecha";
	
	public static final String HOMBRE = "Hombre";
	public static final String MUJER = "Mujer";
	
	public static final String ACCION_TOCA = "toca";
	public static final String DESCRIPCION_ACCION_TOCA = "El cliente introduce su mano dentro de la góndola";
	public static final String ACCION_COMPRA = "compra";
	public static final String DESCRIPCION_ACCION_COMPRA = "El cliente saca un producto de la góndola";
	public static final String ACCION_DEVUELVE = "devuelve";
	public static final String DESCRIPCION_ACCION_DEVUELVE = "El cliente devuelve un producto a la góndola";
	
	public static SimpleDateFormat fechaFormatter = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat horaFormatter = new SimpleDateFormat("HH:mm:ss");
}
