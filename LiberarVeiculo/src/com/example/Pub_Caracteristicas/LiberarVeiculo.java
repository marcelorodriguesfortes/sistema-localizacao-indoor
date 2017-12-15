package com.example.Pub_Caracteristicas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.helloworld.R;
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
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings.Secure;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

	@SuppressLint("NewApi")
	public class LiberarVeiculo extends ActionBarActivity {
		static Handler handler = new Handler();
		public static DomainParticipantFactory dpf = null;
		public static DomainParticipant dp = null;
		public static DomainParticipantQos dp_qos = new DomainParticipantQos();
		public static Subscriber sub = null;
		public static Publisher pub = null;
		public static MulticastLock mcastLock = null;
		ReturnCode_t retval;
		StringMsgDataWriter dw;
		private static final int REQUEST_CODE = 1;
		private static final int MAX_RESULT = 2;
	    private EditText mEdtResult;
	    NfcAdapter nfcAdapter;
		String message;
		public static boolean isService = false; 
		
		
		//METODO PRINCIPAL DO ANDROID 
		@TargetApi(Build.VERSION_CODES.DONUT)
		@SuppressLint("NewApi")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
				super.onCreate(savedInstanceState);
		   
				
			    //HABILITANDO O MULTICAST WIFI 
				WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				mcastLock = wifi.createMulticastLock("TocShapes");
				mcastLock.acquire();
		       
				
				//LENDO A LICENÇA 
				BufferedReader br = null;
				String license = new String("<");
				try {
					br = new BufferedReader(new InputStreamReader(this.getAssets().open("coredx_dds.lic")));
				} catch (IOException e) {
					Log.e("Shapes", e.getMessage());
				}
		
				if (br != null) {
					String ln;
					try {
						while ((ln = br.readLine()) != null) {
							license = new String(license + ln + "\n");
						}
					} catch (IOException e) {
						Log.e("Shapes", e.getMessage());
					}
				}
				license = new String(license + ">");
				
				//CRIANDO O DOMÍNIO DE PARTICIPANTE
				Log.i("", "STARTING -------------------------");
				dpf = DomainParticipantFactory.get_instance();												
				dpf.set_license(license);

		
				Log.i("", "CREATE PARTICIPANT ---------------");
				DomainParticipant dp = null;
				dp = dpf.create_participant(0, null, null, 0);
		
				if (dp == null) {
		
					new AlertDialog.Builder(this).setTitle("Erro").setMessage("Erro").setNeutralButton("Fechar",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dlg, int s) {}
					}).show();
				
				}else{
		            //DENTRO DESTE ELSE EU COLOQUEI O CODIGO DO PUBLISHER E DO SUBSCRIBER JUNTOS
					StringMsgTypeSupport ts = new StringMsgTypeSupport();
					retval = ts.register_type(dp, null); 
			 
					final Topic top = dp.create_topic("helloTopic", ts.get_type_name(), null, null, 0); 
					final Topic topSub = dp.create_topic("helloTopic", ts.get_type_name(), null, null, 0);
					
					PublisherQos pub_qos = null;
					PublisherListener pub_listener = null;
					Publisher pub = dp.create_publisher(pub_qos, pub_listener, 0);
					
					DataWriterQos dw_qos = new DataWriterQos();
					pub.get_default_datawriter_qos(dw_qos);
					dw_qos.entity_name.value = "JAVA_DW";
					DataWriterListener dw_listener = null;
					dw = (StringMsgDataWriter) pub.create_datawriter(top, dw_qos,dw_listener, 0);	
				    
					
					//SETANDO A VIEW PRINCIPAL DO APLICATIVO
					setContentView(R.layout.activity_liberar_carro);	
				
				}
		       			
		
				final EditText texto = (EditText) findViewById(R.id.edtResult);
				Button publicar = (Button) findViewById(R.id.btnPublicar);
			
		   	   //ESTE MÉTODO É RESPONSÁVEL POR ENVIAR A MENSAGEM SEMPRE QUE O BOTÃO "CLICAR" FOR PRESSIONADO
				publicar.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) { 
		
						StringMsg data = new StringMsg();
						data.msg = new String(texto.getText().toString());
						retval = dw.write(data, null);
						Log.i("", "Mensagem publicada: " + texto.getText().toString());
						String msg = "Mensagem publicada: "+ texto.getText().toString();
						toast(msg);
					}
				});
			    
		}
		//ESTE MÉTODO É RESPONSÁVEL POR TORNAR O MENU VISÍVEL
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.app_libera_veiculo, menu);
			return true;
		}
	
		
		//IMPRIME UMA MENSAGEM NA TELA
	    public void toast(String texto) {
				Toast toast = Toast.makeText(this, texto, 90000);
				toast.show();
		}
	
	   
	
	

}
