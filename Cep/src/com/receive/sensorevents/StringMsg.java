package com.receive.sensorevents;

// CoreDX DDL Generated code.  Do not modify - modifications may be overwritten.



public class StringMsg {
  
  // instance variables
  public int tempo_inicial;
  public String msg;
  public String id_smartphone;
  public String id_sensor;
  public String placa;
  public String sensor_placa;
  public float longitude;
  public float latitude;
  
  // constructors
  public StringMsg() { }
  public StringMsg( int __f1, String __f2, String __f3, String __f4, String __f5, String __f6, float __f7, float __f8 ) {
    tempo_inicial = __f1;
    msg = __f2;
    id_smartphone = __f3;
    id_sensor = __f4;
    placa = __f5;
    sensor_placa = __f6;
    longitude = __f7;
    latitude = __f8;
  }
  
  public StringMsg init() { 
    msg = new String();
    id_smartphone = new String();
    id_sensor = new String();
    placa = new String();
    sensor_placa = new String();
    return this;
  }

  public void clear() {
    msg = null;
    id_smartphone = null;
    id_sensor = null;
    placa = null;
    sensor_placa = null;
  }
  
  public void copy( StringMsg from ) {
    this.tempo_inicial = from.tempo_inicial;
    this.msg = from.msg;
    this.id_smartphone = from.id_smartphone;
    this.id_sensor = from.id_sensor;
    this.placa = from.placa;
    this.sensor_placa = from.sensor_placa;
    this.longitude = from.longitude;
    this.latitude = from.latitude;
  }
  
}; // StringMsg
