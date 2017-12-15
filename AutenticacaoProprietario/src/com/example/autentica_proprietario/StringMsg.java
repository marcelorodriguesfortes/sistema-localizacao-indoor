package com.example.autentica_proprietario;

// CoreDX DDL Generated code.  Do not modify - modifications may be overwritten.



public class StringMsg {
  
  // instance variables
  public String identificador;
  public String msg;
  public String id_dispositivo;
  public String placa;
  public String latitude;
  public String longitude;
  
  // constructors
  public StringMsg() { }
  public StringMsg( String __f1, String __f2, String __f3, String __f4, String __f5, String __f6 ) {
    identificador = __f1;
    msg = __f2;
    id_dispositivo = __f3;
    placa = __f4;
    latitude = __f5;
    longitude = __f6;
  }
  
  public StringMsg init() { 
    identificador = new String();
    msg = new String();
    id_dispositivo = new String();
    placa = new String();
    latitude = new String();
    longitude = new String();
    return this;
  }

  public void clear() {
    identificador = null;
    msg = null;
    id_dispositivo = null;
    placa = null;
    latitude = null;
    longitude = null;
  }
  
  public void copy( StringMsg from ) {
    this.identificador = from.identificador;
    this.msg = from.msg;
    this.id_dispositivo = from.id_dispositivo;
    this.placa = from.placa;
    this.latitude = from.latitude;
    this.longitude = from.longitude;
  }
  
}; // StringMsg
