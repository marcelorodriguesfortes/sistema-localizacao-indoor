package com.example.pub_car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.toc.coredx.DDS.DataReader;
import com.toc.coredx.DDS.DataReaderListener;
import com.toc.coredx.DDS.DataReaderQos;
import com.toc.coredx.DDS.DataWriterListener;
import com.toc.coredx.DDS.DataWriterQos;
import com.toc.coredx.DDS.DomainParticipant;
import com.toc.coredx.DDS.DomainParticipantFactory;
import com.toc.coredx.DDS.DomainParticipantQos;
import com.toc.coredx.DDS.LivelinessChangedStatus;
import com.toc.coredx.DDS.Publisher;
import com.toc.coredx.DDS.PublisherListener;
import com.toc.coredx.DDS.PublisherQos;
import com.toc.coredx.DDS.RequestedDeadlineMissedStatus;
import com.toc.coredx.DDS.RequestedIncompatibleQosStatus;
import com.toc.coredx.DDS.ReturnCode_t;
import com.toc.coredx.DDS.SampleInfoSeq;
import com.toc.coredx.DDS.SampleLostStatus;
import com.toc.coredx.DDS.SampleRejectedStatus;
import com.toc.coredx.DDS.Subscriber;
import com.toc.coredx.DDS.SubscriberListener;
import com.toc.coredx.DDS.SubscriberQos;
import com.toc.coredx.DDS.SubscriptionMatchedStatus;
import com.toc.coredx.DDS.Topic;
import com.toc.coredx.DDS.TopicDescription;
import com.toc.coredx.DDS.coredx;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySub  extends Activity implements DataReaderListener{
	
	public static DomainParticipantFactory dpf = null;
	public static DomainParticipant dp = null;
	public static DomainParticipantQos dp_qos = new DomainParticipantQos();
	public static Subscriber sub = null;
	public static Publisher pub = null;
	public static MulticastLock     mcastLock = null;
	ReturnCode_t retval;
	Handler handler = new Handler();
	
	

	
	@TargetApi(Build.VERSION_CODES.DONUT)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//super.onCreate(savedInstanceState);
	
		 WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		 mcastLock = wifi.createMulticastLock("TocShapes");
		 mcastLock.acquire();
	
		 // open CoreDX DDS license file:
		    BufferedReader br = null;
		    String license = new String("<");
		    try {
		      br = new BufferedReader(new InputStreamReader(this.getAssets().open("coredx_dds.lic")));
		    } catch (IOException e) {
		      Log.i("", e.getMessage());
		    }
		   
		    if (br!=null){ 
				String ln;
				try {
					while ((ln = br.readLine()) != null) 
						license = new String(license + ln + "\n");
				} catch (IOException e) {
				  Log.i("", e.getMessage());
				}
		      }
		    
		    license = new String(license + ">");
		    
		    dpf = DomainParticipantFactory.get_instance();
			dpf.set_license(license);
		    
		    
		    class TestDataReaderListener implements DataReaderListener  {
	
			      Runtime run = Runtime.getRuntime();		
			      public long get_nil_mask() { 
			    	  return 0;
			      }
	
			      public void on_requested_deadline_missed(DataReader dr,  RequestedDeadlineMissedStatus status) { 
			    	  System.out.println("     REQUESTED DEADLINE MISSED    "); 
			      };
	
			      public void on_requested_incompatible_qos(DataReader dr, RequestedIncompatibleQosStatus status) { 
			    	  System.out.println("    REQUESTED INCOMPAT QOS   "); 
			    	  System.out.println("    dr      = " + dr);
				
			      };
	
			      public void on_sample_rejected(DataReader dr,   SampleRejectedStatus status) { 
			      };
	
			      public void on_liveliness_changed(DataReader dr, LivelinessChangedStatus status){
			    	  TopicDescription   td = dr.get_topicdescription();
			      }
	
			      public void on_subscription_matched(DataReader dr,   SubscriptionMatchedStatus status)  { 
			    	  TopicDescription   td = dr.get_topicdescription();
			      }
	
			      public void on_sample_lost(DataReader dr, 
							 SampleLostStatus status) { 
			      };
	
	
			      public void on_data_available(DataReader dr)
			      { 
						TopicDescription td = dr.get_topicdescription();
			
						StringMsgDataReader string_dr = (StringMsgDataReader)dr;
						StringMsgSeq     samples = new StringMsgSeq();
						SampleInfoSeq si      = new SampleInfoSeq();
						ReturnCode_t  retval  = string_dr.take(samples, si, 100, coredx.DDS_ANY_SAMPLE_STATE, coredx.DDS_ANY_VIEW_STATE,  coredx.DDS_ANY_INSTANCE_STATE);
			
						final EditText edit_text = (EditText) findViewById(R.id.edtResults);
						
						 
						if (retval == ReturnCode_t.RETCODE_OK)
						  {
						    
							for (int i = 0; i < samples.value.length; i++) {
									final String message = new String(samples.value[i].msg);
									System.out.println();
									new Thread(){
										 public void run(){
											 handler.post(new Runnable() {
												
												@Override
												public void run() {
													// TODO Auto-generated method stub
													edit_text.setText( message);
													toast(message);
												}
											});
											 
										 }    
									  }.start();
				
					         }
					            
						     
						    string_dr.return_loan(samples, si);
						  }else{
							  System.out.println("Erro ao receber mensagem");
						  }
						
			   };
		    };



		    System.out.println("CREATE PARTICIPANT ---------------");
		    dp = dpf.create_participant(0, null, null, 	0);
		    
		     if (dp == null){
				android.util.Log.e("CoreDX DDS", "Unable to create DomainParticipant.");
	
				new AlertDialog.Builder(this)
				  .setTitle("CoreDX DDS Shapes Error")
				  .setMessage("Unable to create DomainParticipant.\n(Bad License?)")
				  .setNeutralButton("Close", new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dlg, int s) { 
				    	  
				      } }).show();
		      }else{
			
					    System.out.println("REGISTERING TYPE -----------------");
					    StringMsgTypeSupport ts = new StringMsgTypeSupport();
					    retval = ts.register_type(dp, null); 
					    
					    sub = dp.create_subscriber(null, null, 0);
					         
					    System.out.println("CREATE TOPIC ---------------------");
					    Topic              top          = dp.create_topic("novoTopico", ts.get_type_name(),   null,  null, 0); 
					      
					    System.out.println("CREATE SUBSCRIBER ----------------");
					    SubscriberQos       sub_qos      = null;
					    SubscriberListener  sub_listener = null;
					    Subscriber          sub          = dp.create_subscriber(sub_qos, sub_listener, 0);  
			 
					    System.out.println("CREATE DATAREADER ----------------");
					    DataReaderQos dr_qos = new DataReaderQos();
					    sub.get_default_datareader_qos(dr_qos);
					    dr_qos.entity_name.value = "JAVA_DR";
					    dr_qos.history.depth = 10;
					    DataReaderListener dr_listener = new TestDataReaderListener();
					    StringMsgDataReader   dr = (StringMsgDataReader) sub.create_datareader(top,  dr_qos, dr_listener, coredx.getDDS_ALL_STATUS());
			        
					    
					    new Thread(){
							  public void run(){   
								 while ( true ) {
								      try { 
								    	  Thread.currentThread().sleep(5000);   // 5 second sleep
								      } catch (Exception e) {
								    	  e.printStackTrace();
								      }
								  }			
							  }    
					    }.start();
				   }
			
		     //setContentView(R.layout.activity_main);
		    
	   }
	
	   public void toast (String texto){
		   Toast toast = Toast.makeText(this,texto ,90000);
	        toast.show();   
	   }
		
	   @Override
		public long get_nil_mask() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	   @Override
		public void on_data_available(DataReader arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void on_liveliness_changed(DataReader arg0,
				LivelinessChangedStatus arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void on_requested_deadline_missed(DataReader arg0,
				RequestedDeadlineMissedStatus arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void on_requested_incompatible_qos(DataReader arg0,
				RequestedIncompatibleQosStatus arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void on_sample_lost(DataReader arg0, SampleLostStatus arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void on_sample_rejected(DataReader arg0, SampleRejectedStatus arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void on_subscription_matched(DataReader arg0,
				SubscriptionMatchedStatus arg1) {
			// TODO Auto-generated method stub
			
		}
	  
  }