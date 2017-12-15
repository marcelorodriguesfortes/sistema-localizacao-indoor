package com.example.pub_car;
// CoreDX DDL Generated code.  Do not modify - modifications may be overwritten.
import com.toc.coredx.DDS.*;
import java.nio.*;
import java.nio.charset.*;


public final class StringMsgTypeSupport implements TypeSupport{
  
  public ReturnCode_t register_type(DomainParticipant dp, String type_name) {
    if (dp.check_version( "3", "6", "21" ) != 0) {
      System.out.println( "WARNING: StringMsg TypeSupport version does not match CoreDX Library version.");
      System.out.println( "This may cause software instability or crashes.");
    }
    return dp.register_type(this, type_name);
  }

  public String get_type_name()   { return "StringMsg"; }
  public long   getCTypeSupport() { return cTypeSupport; }

  public StringMsgTypeSupport() {
    StringMsg tmp = new StringMsg();
    cTypeSupport = DomainParticipant.createTypeSupport(this, 
                     getClass().getName(), tmp.getClass().getName());
  }

  // ------------------------------------------------------
  // implementation

  public DataReader   create_datareader(Subscriber sub, TopicDescription td, 
                                        SWIGTYPE_p__DataReader jni_dr) {
    return new StringMsgDataReader(sub, td, jni_dr);
  }
  public DataWriter   create_datawriter(Publisher  pub, Topic topic, SWIGTYPE_p__DataWriter jni_dw ) {
    return new StringMsgDataWriter(pub, topic, jni_dw);
  }

  // marshal variants
  public int     marshall ( ByteBuffer out_stream,  StringMsg src ) {
    int size = 0;
    if ( out_stream == null ) {
      size = marshal_size_core(out_stream, size, src);
      size += 4; // for CDR 'header' 
    } else {
      int offset = 0;
      out_stream.clear();
      if ((1 & 0x01)==0) out_stream.order(ByteOrder.BIG_ENDIAN);
      else               out_stream.order(ByteOrder.LITTLE_ENDIAN);
      
      // add CDR 'header' 
      out_stream.put((byte)0x00);
      out_stream.put((byte)1);
      out_stream.put((byte)0x00); // flags
      out_stream.put((byte)0x00); // flags
      
      marshal_core(out_stream, offset, src);
      size = out_stream.position();
    }
    return size;
  }

  public int     marshal_size_core ( ByteBuffer out_stream, int size, StringMsg src ) {
    // src.identificador
    size = (size+3) & 0xfffffffc;// align 4
    size += 4; // length
    if (src.identificador == null) size += 1;
    else {
      try {
        byte[] sbytes = src.identificador.getBytes("UTF-8");
        size += sbytes.length + 1;
      } catch(Exception e) {
        size += 1;
      }
    }
    // src.msg
    size = (size+3) & 0xfffffffc;// align 4
    size += 4; // length
    if (src.msg == null) size += 1;
    else {
      try {
        byte[] sbytes = src.msg.getBytes("UTF-8");
        size += sbytes.length + 1;
      } catch(Exception e) {
        size += 1;
      }
    }
    // src.softwareResourse
    size = (size+3) & 0xfffffffc;// align 4
    size += 4; // length
    if (src.softwareResourse == null) size += 1;
    else {
      try {
        byte[] sbytes = src.softwareResourse.getBytes("UTF-8");
        size += sbytes.length + 1;
      } catch(Exception e) {
        size += 1;
      }
    }
    // src.hardwareResourse
    size = (size+3) & 0xfffffffc;// align 4
    size += 4; // length
    if (src.hardwareResourse == null) size += 1;
    else {
      try {
        byte[] sbytes = src.hardwareResourse.getBytes("UTF-8");
        size += sbytes.length + 1;
      } catch(Exception e) {
        size += 1;
      }
    }
    return size;
  }

  public int     marshal_core ( ByteBuffer out_stream, int offset, StringMsg src ) {
    // src.identificador
    while((offset & 0x03) != 0) { offset++; out_stream.put((byte)0x00); } // align 4
    if (src.identificador == null) {
     out_stream.putInt(1);
    }
    else {
      try {
        byte[] sbytes = src.identificador.getBytes("UTF-8");
        out_stream.putInt(sbytes.length+1);
        out_stream.put(sbytes);
        offset += sbytes.length;
      } catch(Exception e) {
        out_stream.putInt(1);
      }
    }
    out_stream.put((byte)0);
    offset += 4 + 1;
    // src.msg
    while((offset & 0x03) != 0) { offset++; out_stream.put((byte)0x00); } // align 4
    if (src.msg == null) {
     out_stream.putInt(1);
    }
    else {
      try {
        byte[] sbytes = src.msg.getBytes("UTF-8");
        out_stream.putInt(sbytes.length+1);
        out_stream.put(sbytes);
        offset += sbytes.length;
      } catch(Exception e) {
        out_stream.putInt(1);
      }
    }
    out_stream.put((byte)0);
    offset += 4 + 1;
    // src.softwareResourse
    while((offset & 0x03) != 0) { offset++; out_stream.put((byte)0x00); } // align 4
    if (src.softwareResourse == null) {
     out_stream.putInt(1);
    }
    else {
      try {
        byte[] sbytes = src.softwareResourse.getBytes("UTF-8");
        out_stream.putInt(sbytes.length+1);
        out_stream.put(sbytes);
        offset += sbytes.length;
      } catch(Exception e) {
        out_stream.putInt(1);
      }
    }
    out_stream.put((byte)0);
    offset += 4 + 1;
    // src.hardwareResourse
    while((offset & 0x03) != 0) { offset++; out_stream.put((byte)0x00); } // align 4
    if (src.hardwareResourse == null) {
     out_stream.putInt(1);
    }
    else {
      try {
        byte[] sbytes = src.hardwareResourse.getBytes("UTF-8");
        out_stream.putInt(sbytes.length+1);
        out_stream.put(sbytes);
        offset += sbytes.length;
      } catch(Exception e) {
        out_stream.putInt(1);
      }
    }
    out_stream.put((byte)0);
    offset += 4 + 1;
    return offset;
  }

  public int     marshall_fixed_size (  ) { return 0; }
  public int     marshall_key ( ByteBuffer out_stream,  StringMsg src ) {
    int size = 0;
    if ( out_stream == null ) {
      size += 4; // for CDR 'header' 
    } else {
      int offset = 0;
      out_stream.clear();
      if ((1 & 0x01)==0) out_stream.order(ByteOrder.BIG_ENDIAN);
      else               out_stream.order(ByteOrder.LITTLE_ENDIAN);
      
      // add CDR 'header' 
      out_stream.put((byte)0x00);
      out_stream.put((byte)1);
      out_stream.put((byte)0x00); // flags
      out_stream.put((byte)0x00); // flags
      
      size = out_stream.position();
    }
    return size;
  }

  public int     marshall_key_hash ( ByteBuffer out_stream,  StringMsg src ) {
    int size = 0;
    if ( out_stream == null ) {
    } else {
      int offset = 0;
      out_stream.clear();
      out_stream.order(ByteOrder.BIG_ENDIAN);
      
      size = out_stream.position();
    }
    return size;
  }

  public boolean key_must_hash (  ) { return false; }

  // unmarshal variants
  public int     unmarshall ( StringMsg t, ByteBuffer data, int s )    {
    int offset = 0;
    data.get();                      // skip the first byte 
    byte encoding = data.get();      // data encoding CDR / ENDIAN 
    data.getShort();                 // unused flags (2 bytes)
    if ((encoding & 0x01)==0)  data.order(ByteOrder.BIG_ENDIAN);
    else                       data.order(ByteOrder.LITTLE_ENDIAN);

    if ((encoding & 0xFE)==0) { // CDR encoding
      unmarshal_core(t, data, offset, s);
    }
    return 1; // 1==success
  }

  public int     unmarshal_core ( StringMsg t, ByteBuffer data, int offset, int s )    {
    // t.identificador
    {
      while((offset & 0x03) != 0) { offset++; data.position(data.position()+1); }// align 4
      int    slen   = data.getInt()-1;  // skip trailing null
      byte[] sbytes = new byte[slen];
      data.get(sbytes, 0, slen);
      data.get(); // skip trailing null
      try {;
        t.identificador   = new String(sbytes, "UTF-8");
      } catch(java.io.UnsupportedEncodingException e) {
        t.identificador   = new String(); }
      offset += 4 + slen + 1;
    }
    // t.msg
    {
      while((offset & 0x03) != 0) { offset++; data.position(data.position()+1); }// align 4
      int    slen   = data.getInt()-1;  // skip trailing null
      byte[] sbytes = new byte[slen];
      data.get(sbytes, 0, slen);
      data.get(); // skip trailing null
      try {;
        t.msg   = new String(sbytes, "UTF-8");
      } catch(java.io.UnsupportedEncodingException e) {
        t.msg   = new String(); }
      offset += 4 + slen + 1;
    }
    // t.softwareResourse
    {
      while((offset & 0x03) != 0) { offset++; data.position(data.position()+1); }// align 4
      int    slen   = data.getInt()-1;  // skip trailing null
      byte[] sbytes = new byte[slen];
      data.get(sbytes, 0, slen);
      data.get(); // skip trailing null
      try {;
        t.softwareResourse   = new String(sbytes, "UTF-8");
      } catch(java.io.UnsupportedEncodingException e) {
        t.softwareResourse   = new String(); }
      offset += 4 + slen + 1;
    }
    // t.hardwareResourse
    {
      while((offset & 0x03) != 0) { offset++; data.position(data.position()+1); }// align 4
      int    slen   = data.getInt()-1;  // skip trailing null
      byte[] sbytes = new byte[slen];
      data.get(sbytes, 0, slen);
      data.get(); // skip trailing null
      try {;
        t.hardwareResourse   = new String(sbytes, "UTF-8");
      } catch(java.io.UnsupportedEncodingException e) {
        t.hardwareResourse   = new String(); }
      offset += 4 + slen + 1;
    }
    return offset; // >0==success
  }

  public int     unmarshall_key( StringMsg t, ByteBuffer data, int s ) {
    int offset = 0;
    data.get();                      // skip the first byte 
    byte encoding = data.get();      // data encoding CDR / ENDIAN 
    data.getShort();                 // unused flags (2 bytes)
    if ((encoding & 0x01)==0)  data.order(ByteOrder.BIG_ENDIAN);
    else                       data.order(ByteOrder.LITTLE_ENDIAN);

    if ((encoding & 0xFE)==0) { // CDR encoding
    }
    return 1; // 1==success
  }

  public int     unmarshall_key_hash( StringMsg t, ByteBuffer data, int s ) {
    int offset = 0;
    data.order(ByteOrder.BIG_ENDIAN);
    return 0;
  }

  public int gen_typecode( ByteBuffer b ) {
    String tc_0000 = 
        "\n\u0000\u0000\u0000\u00b6\u0000\u0000\u0000\n\u0000\u0000\u0000\u0053\u0074\u0072\u0069" + 
        "\u006e\u0067\u004d\u0073\u0067\u0000\u0000\u0000\u0004\u0000\u0000\u0000\u0026\u0000\u0000\u0000" + 
        "\u000e\u0000\u0000\u0000\u0069\u0064\u0065\u006e\u0074\u0069\u0066\u0069\u0063\u0061\u0064\u006f" + 
        "\u0072\u0000\u0000\u0000\u00ff\u00ff\u0000\u0000\r\u0000\u0000\u0000\u0006\u0000\u0000\u0000" + 
        "\u00ff\u00ff\u00ff\u00ff\u001e\u0000\u0000\u0000\u0004\u0000\u0000\u0000\u006d\u0073\u0067\u0000" + 
        "\u0000\u0000\u00ff\u00ff\u0000\u0000\u0000\u0000\r\u0000\u0000\u0000\u0006\u0000\u0000\u0000" + 
        "\u00ff\u00ff\u00ff\u00ff\u002a\u0000\u0000\u0000\u0011\u0000\u0000\u0000\u0073\u006f\u0066\u0074" + 
        "\u0077\u0061\u0072\u0065\u0052\u0065\u0073\u006f\u0075\u0072\u0073\u0065\u0000\u0000\u00ff\u00ff" + 
        "\u0000\u0000\u0000\u0000\r\u0000\u0000\u0000\u0006\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff" + 
        "\u002a\u0000\u0000\u0000\u0011\u0000\u0000\u0000\u0068\u0061\u0072\u0064\u0077\u0061\u0072\u0065" + 
        "\u0052\u0065\u0073\u006f\u0075\u0072\u0073\u0065\u0000\u0000\u00ff\u00ff\u0000\u0000\u0000\u0000" + 
        "\r\u0000\u0000\u0000\u0006\u0000\u0000\u0000\u00ff\u00ff\u00ff\u00ff";
    byte[] tc_data = new byte[ 188 ];
    int j = 0;
    for(int i=0; i < tc_0000.length(); i++) {
      tc_data[j++] = (byte)tc_0000.charAt(i);
    }
    if (b != null) b.put(tc_data, 0, 188);
    return tc_data.length;
  }

  public int get_typecode_enc( ) {
    return (1 & 0x01);
  }

  public int get_encoding( ) {
    return 0x0;
  }

  public int get_decoding( ) {
    return 0x0;
  }

  // key field operations
  public boolean has_key (  ) { return false; }

  // <type> operations
  public StringMsg         alloc ()              { return new StringMsg(); }
  public void       clear (StringMsg instance)   { instance.clear(); }
  public void       destroy (StringMsg instance) { /* noop */ }
  public void       copy (StringMsg to, StringMsg from) { to.copy(from); }
  public boolean    get_field( String fieldname, CoreDX_FieldDef fdef ) { 
    return false;
  }
  private long cTypeSupport = 0;
}; // StringMsg
