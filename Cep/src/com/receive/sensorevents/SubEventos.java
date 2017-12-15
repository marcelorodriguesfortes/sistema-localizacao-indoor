package com.receive.sensorevents;
/****************************************************************
 *
 *  file:  HelloSub.java
 *  desc:  Provides a simple Java 'hello world' DDS subscriber.
 *         This publishing application will send data
 *         to the example 'hello world' subscribing 
 *         application.
 * 
 ****************************************************************
 *
 *   Copyright(C) 2009-2013 Twin Oaks Computing, Inc
 *   All rights reserved.   Castle Rock, CO 80108
 *
 *****************************************************************
 *
 *  This file is provided by Twin Oaks Computing, Inc 
 *  as an example. It is provided in the hope that it will be 
 *  useful but WITHOUT ANY WARRANTY; without even the implied 
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 *  PURPOSE. TOC Inc assumes no liability or responsibilty for 
 *  the use of this information for any purpose.
 *  
 ****************************************************************/

import java.util.EventListener;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationDBRef;
import com.espertech.esper.client.ConfigurationDBRef.MetadataOriginEnum;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.toc.coredx.DDS.*;

public class SubEventos {

	public static void main(String[] args) {
		
		/* -----------ESPER--------------*/
		SimpleLayout layout = new SimpleLayout();
        ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel((Level) Level.WARN);
        
        ConfigurationDBRef dbConfig = new ConfigurationDBRef();
        dbConfig.setDriverManagerConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bd_estacionamento","root","123");

        Configuration engineConfig = new Configuration();
        engineConfig.addDatabaseReference("database_alias", dbConfig);
        engineConfig.addEventType("EventosSensorPresenca", EventosSensorPrecenca.class.getName());
        engineConfig.addEventType("EventosTagVeiculo", EventosTagVeiculo.class.getName());

        EPServiceProvider esperEngine = EPServiceProviderManager.getDefaultProvider(engineConfig);
        
        
        String statement =  "SELECT sensorPresenca.placa, sensorPresenca.id_dispositivo," +
        					"tagVeiculo.latitude, tagVeiculo.longitude, tagVeiculo.placa, tagVeiculo.tempo_inicial" +
        					" FROM EventosSensorPresenca.win:time(5 sec) AS sensorPresenca," +
        								  "EventosTagVeiculo.win:time(5 sec) AS tagVeiculo , " +			
        								  "sql:database_alias ['select id , latitude, longitude from sensor'] AS s " +
        								  "WHERE  sensorPresenca.placa = tagVeiculo.placa AND s.latitude = tagVeiculo.latitude " +
        								  "AND s.longitude = tagVeiculo.longitude " +
        								  "AND s.id = sensorPresenca.id_dispositivo"; 

		EPStatement queryEngineObject = esperEngine.getEPAdministrator().createEPL(statement);  
		
		//Associando o listner com a query
		queryEngineObject.addListener(new Listener());
		final EPRuntime cepRT = esperEngine.getEPRuntime();
		
		
		//--------------------------------------------------------
		
		//Subscriber que receberá os dados enviados do smartphone
		class TestDataReaderListenerSmartphone implements DataReaderListener {
			
			public long get_nil_mask() {
				return 0;
			}

			public void on_requested_deadline_missed(DataReader dr,
					RequestedDeadlineMissedStatus status) {

			};

			public void on_requested_incompatible_qos(DataReader dr,
					RequestedIncompatibleQosStatus status) {

			};

			public void on_sample_rejected(DataReader dr,
					SampleRejectedStatus status) {
			};

			public void on_liveliness_changed(DataReader dr,
					LivelinessChangedStatus status) {
				TopicDescription td = dr.get_topicdescription();

			}

			public void on_subscription_matched(DataReader dr,
					SubscriptionMatchedStatus status) {

			}

			public void on_sample_lost(DataReader dr, SampleLostStatus status) {
			};

			public void on_data_available(DataReader dr) {

				StringMsgDataReader string_dr = (StringMsgDataReader) dr;
				StringMsgSeq samples = new StringMsgSeq();
				SampleInfoSeq si = new SampleInfoSeq();
				ReturnCode_t retval = string_dr.take(samples, si, 100,
						coredx.DDS_ANY_SAMPLE_STATE, coredx.DDS_ANY_VIEW_STATE,
						coredx.DDS_ANY_INSTANCE_STATE);

				if (retval == ReturnCode_t.RETCODE_OK) {
					if (samples.value == null)
						System.out.println(" samples.value = null");
					else {
						for (int i = 0; i < samples.value.length; i++) {
					
							if (si.value[i].valid_data){
								
							    final EventosTagVeiculo eventosTagVeiculo = new EventosTagVeiculo();
							    eventosTagVeiculo.setTempo_inicial(samples.value[i].tempo_inicial);
							    eventosTagVeiculo.setId_dispositivo(samples.value[i].id_smartphone); 
							    eventosTagVeiculo.setPlaca(samples.value[i].placa);  
							    eventosTagVeiculo.setLatitude(4); 
							    eventosTagVeiculo.setLongitude(5);
							    System.out.println("Evento do smartphone\n");
							    //System.out.println("Tempo inicial: "+samples.value[i].tempo_inicial + "\n");
							    
							    //enviando os dados do smartphone para o sistema de CEP
							    cepRT.sendEvent(eventosTagVeiculo);
							
							}
						}
					}
					string_dr.return_loan(samples, si);
				} else {
				}

			};
		};
		
	//Subscriber que receberá os dados do sesor de presença	
	class TestDataReaderListenerSensorPresence implements DataReaderListener {
		
			public long get_nil_mask() {
				return 0;
			}

			public void on_requested_deadline_missed(DataReader dr,
					RequestedDeadlineMissedStatus status) {

			};

			public void on_requested_incompatible_qos(DataReader dr,
					RequestedIncompatibleQosStatus status) {

			};

			public void on_sample_rejected(DataReader dr,
					SampleRejectedStatus status) {
			};

			public void on_liveliness_changed(DataReader dr,
					LivelinessChangedStatus status) {
				TopicDescription td = dr.get_topicdescription();

			}

			public void on_subscription_matched(DataReader dr,
					SubscriptionMatchedStatus status) {

			}

			public void on_sample_lost(DataReader dr, SampleLostStatus status) {
			};

			public void on_data_available(DataReader dr) {
				int count = 0;
				StringMsgDataReader string_dr = (StringMsgDataReader) dr;
				StringMsgSeq samples = new StringMsgSeq();
				SampleInfoSeq si = new SampleInfoSeq();
				ReturnCode_t retval = string_dr.take(samples, si, 100,
						coredx.DDS_ANY_SAMPLE_STATE, coredx.DDS_ANY_VIEW_STATE,
						coredx.DDS_ANY_INSTANCE_STATE);

				if (retval == ReturnCode_t.RETCODE_OK) {
					if (samples.value == null)
						System.out
								.println(" samples.value = null");
					else {
						for (int i = 0; i < samples.value.length; i++) {
					
							if (si.value[i].valid_data){
								count++;
								final EventosSensorPrecenca EventossensorPresenca = new EventosSensorPrecenca();
							    EventossensorPresenca.setId_dispositivo(1);
							    EventossensorPresenca.setPlaca("OGU-8976");
							    System.out.println("Evento do sensor de presença\n" );
							    System.out.println("Contador de presença" + count + "\n" );
							    
							    //Enviando os dados do sensor de presença para o sistema de CEP
							    cepRT.sendEvent(EventossensorPresenca);
							}
						}
					}
					string_dr.return_loan(samples, si);
				} else {
				}

			};
		};

		//O código a seguir é responsável por criar os subscribers para os eventos do smartphone e do sensor de presença
		System.out.println("STARTING -------------------------");
		DomainParticipantFactory dpf = DomainParticipantFactory.get_instance();
		DomainParticipant dp = null;

		System.out.println("CREATE PARTICIPANT ---------------");
		dp = dpf.create_participant(0, null, null,0);

		System.out.println("REGISTERING TYPE -----------------");
		StringMsgTypeSupport ts = new StringMsgTypeSupport();
		ReturnCode_t retval = ts.register_type(dp, null); 

		System.out.println("CREATE TOPIC ---------------------");
		Topic topicoAtenticacaoUsuario = dp.create_topic("AutenticacaoProprietario", ts.get_type_name(), null, null, 0); 
		Topic topicoSensorPresenca = dp.create_topic("SensorPresenca", ts.get_type_name(), null, null, 0);

		System.out.println("CREATE SUBSCRIBER----------------");
		SubscriberQos sub_qos = null;
		SubscriberListener sub_listener = null;
		Subscriber sub = dp.create_subscriber(sub_qos, sub_listener, 0);

		System.out.println("CREATE DATAREADER ----------------"+"\n");
		DataReaderQos dr_qos = new DataReaderQos();
		sub.get_default_datareader_qos(dr_qos);
		dr_qos.entity_name.value = "JAVA_DR";
		dr_qos.history.depth = 10;
		
		DataReaderListener dr_listener_smartphone = new TestDataReaderListenerSmartphone();
		DataReaderListener dr_listener_sensor_precenca = new TestDataReaderListenerSensorPresence();
		
		StringMsgDataReader dr_smartphone = (StringMsgDataReader) sub.create_datareader(topicoAtenticacaoUsuario, dr_qos, dr_listener_smartphone, coredx.getDDS_ALL_STATUS());
		StringMsgDataReader dr_sensor_presenca = (StringMsgDataReader) sub.create_datareader(topicoSensorPresenca, dr_qos, dr_listener_sensor_precenca, coredx.getDDS_ALL_STATUS());
		

		while (true) {
			try {
				Thread.currentThread().sleep(5000); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
};
