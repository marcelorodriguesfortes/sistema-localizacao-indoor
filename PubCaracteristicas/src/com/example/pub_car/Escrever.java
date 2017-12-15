package com.example.pub_car;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Escrever  extends Activity{
	public static boolean isService = false; 
	NfcAdapter nfcAdapter; 
    String message;
    private Handler handler = new  Handler();
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_escrever);
		

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
        	Toast.makeText(this, "NFC HABILITADO!", Toast.LENGTH_LONG).show();
        }else
        	Toast.makeText(this, "NFC DESABILITADO!", Toast.LENGTH_LONG).show();
        
        Button button = (Button) findViewById(R.id.botton);
        
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				new Thread(){
					public void run(){
						EditText textview = (EditText) findViewById(R.id.editText1);
						Log.i("", "botao clicado");	
						 message = new String(textview.getText().toString());
						 
						 handler.post(new Runnable() {
							
							@Override
							public void run() {
						   		// TODO Auto-generated method stub
								toast("Aproxime a Tag");	
							}
						});
						 
					}
				}.start();
			}
		});
	}
	

	public void toast(String texto) {
		Toast toast = Toast.makeText(this, texto, 90000);
		toast.show();
    }

	  @Override
	    protected void onResume() {
	        super.onResume();
	        enableForegroundDispatchSystem();
	    }
	    
	 
	    
	    @Override
	    protected void onPause() {
	        super.onPause();
	        disableForegroundDispatchSystem();
	    }


	    @Override
	    protected void onNewIntent(Intent intent) {
	        super.onNewIntent(intent);

	        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
	            Toast.makeText(this, "TAG detectada!", Toast.LENGTH_SHORT).show();

	            final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	            if(message == null)
	            	message = "hello";
	           
		        NdefMessage ndefMessage = createNdefMessage(message);
		        writeNdefMessage(tag, ndefMessage);
	          
	        }
	    }

	   


	    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
		@SuppressLint("NewApi")
		private void enableForegroundDispatchSystem() {

	        Intent intent = new Intent(this, Escrever.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
	        IntentFilter[] intentFilters = new IntentFilter[]{};

	        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
	    }

	    @SuppressLint("NewApi")
		private void disableForegroundDispatchSystem() {
	        nfcAdapter.disableForegroundDispatch(this);
	    }

	    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
		@SuppressLint("NewApi")
		private void formatTag(Tag tag, NdefMessage ndefMessage) {
	        try {

	            NdefFormatable ndefFormatable = NdefFormatable.get(tag);

	            if (ndefFormatable == null) {
	                Toast.makeText(this, "Tag não está no formato ndfe!", Toast.LENGTH_SHORT).show();
	                return;
	            }

	            ndefFormatable.connect();
	            ndefFormatable.format(ndefMessage);
	            ndefFormatable.close();

	            Toast.makeText(this, "Tag escrita!", Toast.LENGTH_SHORT).show();

	        } catch (Exception e) {
	            Log.e("formatTag", e.getMessage());
	        }

	    }

	    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
		@SuppressLint("NewApi")
		private void writeNdefMessage(Tag tag, NdefMessage ndefMessage) {

	        try {
	            if (tag == null) {
	                Toast.makeText(this, "Tag não pode ser um objeto nulo", Toast.LENGTH_SHORT).show();
	                return;
	            }
	            Ndef ndef = Ndef.get(tag);

	            if (ndef == null) {
	                // formata a tag para ndfe para que a mensagem possa ser escrita
	                formatTag(tag, ndefMessage);
	            } else {
	                ndef.connect();

	                if (!ndef.isWritable()) {
	                    Toast.makeText(this, "Tag não pode ser escrita!", Toast.LENGTH_SHORT).show();
	                    ndef.close();
	                    return;
	                }
	                ndef.writeNdefMessage(ndefMessage);
	                ndef.close();

	                Toast.makeText(this, "A mensagem:  "+ message +" foi escrita na tag!", Toast.LENGTH_SHORT).show();
	                Toast.makeText(getApplicationContext(), "Não foi possível ler a tag", Toast.LENGTH_LONG).show();
				    
	            }

	        } catch (Exception e) {
	            Log.e("writeNdefMessage", e.getMessage());
	            Toast.makeText(getApplicationContext(), "Não foi possível ler a tag", Toast.LENGTH_LONG).show();
			  
	        }

	    }


	    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
		private NdefRecord createTextRecord(String content) {
	        try {
	            byte[] language;
	            language = Locale.getDefault().getLanguage().getBytes("UTF-8");

	            final byte[] text = content.getBytes("UTF-8");
	            final int languageSize = language.length;
	            final int textLength = text.length;
	            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);

	            payload.write((byte) (languageSize & 0x1F));
	            payload.write(language, 0, languageSize);
	            payload.write(text, 0, textLength);

	            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());

	        } catch (UnsupportedEncodingException e) {
	            Log.e("createTextRecord", e.getMessage());
	        }
	        return null;
	    }


	    @SuppressLint("NewApi")
		private NdefMessage createNdefMessage(String content) {

	        NdefRecord ndefRecord = createTextRecord(content);

	        NdefMessage ndefMessage = new NdefMessage(ndefRecord, new NdefRecord[]{NdefRecord.createApplicationRecord("com.example.pub_car")});

	        return ndefMessage;
	    }
	
		
}
