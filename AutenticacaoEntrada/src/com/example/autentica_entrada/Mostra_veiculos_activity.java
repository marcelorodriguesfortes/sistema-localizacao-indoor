package com.example.autentica_entrada;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Mostra_veiculos_activity extends Activity{
	
private ListView listViewMostraTodosVeiculos;
	
	protected void onCreate (Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_entrada);
		
		listViewMostraTodosVeiculos = (ListView) findViewById(R.id.mostraTodosVeiculos);
	
		BD bd = new BD(this);
		List<Veiculo> listaVeiculo = bd.buscar(); 
	
	    ArrayAdapter<Veiculo> adp =  new ArrayAdapter<Veiculo>(this, android.R.layout.simple_list_item_1,listaVeiculo);
	    listViewMostraTodosVeiculos.setAdapter(adp);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.app_bd, menu);
		return true;
	}
}
