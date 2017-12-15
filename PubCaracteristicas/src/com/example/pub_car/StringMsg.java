package com.example.pub_car;

// CoreDX DDL Generated code.  Do not modify - modifications may be overwritten.



public class StringMsg {
  
  // instance variables
  public String identificador;
  public String msg;
  public String softwareResourse;
  public String hardwareResourse;
  
  // constructors
  public StringMsg() { }
  public StringMsg( String __f1, String __f2, String __f3, String __f4 ) {
    identificador = __f1;
    msg = __f2;
    softwareResourse = __f3;
    hardwareResourse = __f4;
  }
  
  public StringMsg init() { 
    identificador = new String();
    msg = new String();
    softwareResourse = new String();
    hardwareResourse = new String();
    return this;
  }

  public void clear() {
    identificador = null;
    msg = null;
    softwareResourse = null;
    hardwareResourse = null;
  }
  
  public void copy( StringMsg from ) {
    this.identificador = from.identificador;
    this.msg = from.msg;
    this.softwareResourse = from.softwareResourse;
    this.hardwareResourse = from.hardwareResourse;
  }
  
}; // StringMsg
