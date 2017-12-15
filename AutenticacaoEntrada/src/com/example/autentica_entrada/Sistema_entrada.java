package com.example.autentica_entrada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
import android.widget.ListView;
import android.widget.Toast;

	@SuppressLint("NewApi")
	public class Sistema_entrada extends ActionBarActivity {
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
		EditText etPlaca;
		private ListView listViewMostraTodosVeiculos;
		Button buttonBD;
		
		//METODO PRINCIPAL DO ANDROID 
		@TargetApi(Build.VERSION_CODES.DONUT)
		@SuppressLint("NewApi")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				
				//INSTANCIANDO O OBJETO RESPONSÁVEL POR GERENCIAL O NFC DO DISPOSITIVO
				nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		 		
				if(nfcAdapter != null && nfcAdapter.isEnabled())
		        	Toast.makeText(this, "NFC HABILITADO!", Toast.LENGTH_LONG).show();
		        else
		        	Toast.makeText(this, "NFC DESABILITADO!", Toast.LENGTH_LONG).show();
		
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.app_entrada, menu);
			return true;
		}
		
		//IMPRIME UMA MENSAGEM NA TELA
	    public void toast(String texto) {
				Toast toast = Toast.makeText(this, texto, 90000);
				toast.show();
		}
	
	    //MÉTODO CHAMADO SEMPRE QUE A INTENT PRINCIPAL FICA VISÍVEL
	    @Override
	    protected void onResume() {
	        super.onResume();
	        enableForegroundDispatchSystem();
	    }
	    
	   
	    
	    //MÉTODO CHAMADO SEMPRE QUE A INTENT PRINCIPAL NÃO ESTÁ VISÍVEL 
	    @Override
	    protected void onPause() {
	        super.onPause();
	       disableForegroundDispatchSystem();
	    }
	
	   
	    
	   //TODOS OS MÉTODOS ABAIXO SÃO USADOS PARA A LEITURA DA TAG NFC
	   @SuppressLint("InlinedApi")
	   @Override
	    protected void onNewIntent(Intent intent) {
	        super.onNewIntent(intent);
	        
	        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
	            Toast.makeText(this, "TAG detectada!", Toast.LENGTH_SHORT).show();

	            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	            readFromTag(intent, tag);  //chamando a função para leitura da Tag                                        
	        }
	       
	    }
	 
	   	@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
		@SuppressLint("NewApi")
		private void enableForegroundDispatchSystem() {
	   	 
	        Intent intent = new Intent(this, Sistema_entrada.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);//Ao dar um PendingIntent para outro aplicativo, você está concedendo-lhe o direito de executar a operação que você tenha especificado
	        IntentFilter[] intentFilters = new IntentFilter[]{};

	        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);//Habilitar envio de primeiro plano para determinada activity
	       
	   	}

	    @SuppressLint("NewApi")
		private void disableForegroundDispatchSystem() {
	        nfcAdapter.disableForegroundDispatch(this);
	    }
	
	    //Lendo a tag NFC e publicando!
	    
	    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	    @SuppressLint("NewApi")
	    public void readFromTag(Intent intent , Tag detectedTag){

				Ndef ndef = Ndef.get(detectedTag);	
		
				try{
				    ndef.connect();
				    Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		
				    if (messages != null) {
				        NdefMessage[] ndefMessages = new NdefMessage[messages.length];
				        
				        for (int i = 0; i < messages.length; i++) {
				            ndefMessages[i] = (NdefMessage) messages[i];
				        }
					    
				        NdefRecord record = ndefMessages[0].getRecords()[0];
					    byte[] payload = record.getPayload();
					 
					    String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
 
				        //Get the Language Code
				        int languageCodeLength = payload[0] & 0077;
				        String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

				        Runtime runTime = Runtime.getRuntime();
				        
				        //Get the Text
				        String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
		
				        //Testando se existe vaga no estacionamento
				        BD bd = new BD(this);
				        int qtd = bd.buscarQtdVagas();
				        
				        if (qtd > 20)
				        	toast(" Não há vagas no estacionamento");
				        else{
					        Veiculo veiculo = new Veiculo();
							veiculo.setPermissao(0);
							veiculo.setPlaca(text);
					        
							bd.inserir(veiculo);
							qtd = bd.buscarQtdVagas();
							qtd = 20 - qtd;
				        	toast(" Quantidade de vagas disponíveis: " + qtd);
					        toast("A placa: " + text + " foi lida e armazenada no banco de dados");
				        }
					    ndef.close();
				        
				    }
				}
				catch (Exception e) {
				    Toast.makeText(getApplicationContext(), "Não foi possível ler a tag", Toast.LENGTH_LONG).show();
				}
        }

}
