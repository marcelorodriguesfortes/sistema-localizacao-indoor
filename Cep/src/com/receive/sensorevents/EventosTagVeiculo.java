package com.receive.sensorevents;

public class EventosTagVeiculo {

	String placa;
    String id_dispositivo;
    float latitude;
    float longitude;
    int tempo_inicial;
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getId_dispositivo() {
		return id_dispositivo;
	}
	public void setId_dispositivo(String id_dispositivo) {
		this.id_dispositivo = id_dispositivo;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public int getTempo_inicial() {
		return tempo_inicial;
	}
	public void setTempo_inicial(int tempo_inicial) {
		this.tempo_inicial = tempo_inicial;
	}
	@Override
	public String toString() {
		return "EventosTagVeiculo [placa=" + placa + ", id_dispositivo="
				+ id_dispositivo + ", latitude=" + latitude + ", longitude="
				+ longitude + ", tempo_inicial=" + tempo_inicial + "]";
	}
	
	
   
	
}
