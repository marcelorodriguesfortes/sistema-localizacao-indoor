package com.receive.sensorevents;
import java.util.Calendar;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.toc.coredx.DDS.DataWriterListener;
import com.toc.coredx.DDS.DataWriterQos;
import com.toc.coredx.DDS.DomainParticipant;
import com.toc.coredx.DDS.DomainParticipantFactory;
import com.toc.coredx.DDS.Publisher;
import com.toc.coredx.DDS.PublisherListener;
import com.toc.coredx.DDS.PublisherQos;
import com.toc.coredx.DDS.ReturnCode_t;
import com.toc.coredx.DDS.Topic;


public class Listener implements UpdateListener {
		//PubEvento pub =  new PubEvento();
	
	DomainParticipantFactory dpf;
	static ReturnCode_t retval ;
    static StringMsgDataWriter   dw;
    DomainParticipant dp = null;
    StringMsgTypeSupport ts;
    Topic top;
    StringMsg data = new StringMsg();
     
	
	public Listener(){
		dpf = DomainParticipantFactory.get_instance();
	    dp = dpf.create_participant(0, null, null, 0);
	    ts = new StringMsgTypeSupport();
	    retval = ts.register_type(dp, null); //"StringMsg"); 
	    
	    top = dp.create_topic("TopicoSubscribeSaida", ts.get_type_name(),null, null, 0); // no listener
	      
	    PublisherListener  pub_listener = null;
	    Publisher          pub          = dp.create_publisher(null, pub_listener, 0);  
	    
	    DataWriterQos dw_qos = new DataWriterQos();
	    pub.get_default_datawriter_qos(dw_qos);
	    dw_qos.entity_name.value = "JAVA_DW";
	    DataWriterListener dw_listener = null;
	    dw = (StringMsgDataWriter) pub.create_datawriter(top, dw_qos,    dw_listener,   0);
	    
	   
	}
	
	int count = 0;

		
        public void update(EventBean[] newData, EventBean[] oldData) {
        	//Obtendo a placa e publicando
        	EventBean event = newData[0];
        	
        	
        	
        	System.out.println("\n"+"--------------------------------------"+"\n");
        	System.out.println("-----Publicando evento complexo!------"+"\n");
        	System.out.println("--------------------------------------"+"\n");
        	
        	
        	final String placa = (String) event.get("tagVeiculo.placa");
        
    	    
    	    try{	  
    	    	  data.msg = placa;
       		      data.tempo_inicial = (int) event.get("tagVeiculo.tempo_inicial");;
       		      //System.out.println(tempoInicial);
			      retval = dw.write ( data, null );
			      
			      //Calendar  tempoFinal = Calendar.getInstance();
			      //int tempoF = (int) tempoFinal.getTimeInMillis();
			      //int t = (int) (tempoF - tempoInicial);
			      
			      //System.out.println("tempo de inicial: " + tempoInicial);
			      //System.out.println("tempo de final: " + tempoF);
			      //System.out.println("tempo de processamento: " + t);
			      count++;
			      System.out.println("Liberar Veículo: " + placa +"\n");
			      System.out.println("Liberar Veículo: "+ count +"\n");
			      
			      if ( retval != ReturnCode_t.RETCODE_OK )
			    	  System.out.println( "   ====  DDS_DataWriter_write() error... "); 
    		  }catch(Exception e){
    			  System.out.println("Erro ao enviar a mensagem");
    		  }
            	    
	           
        }
    }
 