package com.example.Pub_Caracteristicas;


// CoreDX DDL Generated code.  Do not modify - modifications may be overwritten.
import com.toc.coredx.DDS.*;
import java.nio.*;
import java.nio.charset.*;


public final class StringMsgTypeSupport implements TypeSupport{
  
  public ReturnCode_t register_type(DomainParticipant dp, String type_name) {
    if (dp.check_version( "3", "6", "8" ) != 0) {
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
      size = out_stream.position();
    }
    return size;
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
    }
    return 1; // 1==success
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
    byte[] tc_data = { 
(byte)0x0a, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x5e, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0a, (byte)0x00, (byte)0x00, (byte)0x00, (byte)'S', (byte)'t', (byte)'r', (byte)'i', (byte)'n', (byte)'g', (byte)'M', (byte)'s', (byte)'g', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x1e, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0x00, (byte)0x00, (byte)0x00, (byte)'m', (byte)'s', (byte)'g', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0d, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x06, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0x26, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0e, (byte)0x00, (byte)0x00, (byte)0x00, (byte)'i', (byte)'d', (byte)'e', (byte)'n', (byte)'t', (byte)'i', (byte)'f', (byte)'i', (byte)'c', (byte)'a', (byte)'d', (byte)'o', (byte)'r', (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x0d, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x06, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,     };
    if (b != null) b.put(tc_data, 0, tc_data.length);
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
