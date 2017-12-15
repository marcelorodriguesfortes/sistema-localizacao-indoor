package com.example.autentica_entrada;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BD {
	private SQLiteDatabase bd;
	
	public BD(Context context){
		BDCore auxBd = new BDCore(context);
		bd = auxBd.getWritableDatabase();
	}
	
	public void inserir(Veiculo veiculo){
		ContentValues valores = new ContentValues();
		valores.put("permissao", veiculo.getPermissao());
		valores.put("placa", veiculo.getPlaca());
		
		bd.insert("veiculo", null, valores);
	}
	
	
	public void atualizar(Veiculo veiculo){
		ContentValues valores = new ContentValues();
		valores.put("permissao", veiculo.getPermissao());
		valores.put("placa", veiculo.getPlaca());
		
		bd.update("veiculo", valores, "placa = ?", new String[]{""+veiculo.getPlaca()});
	}
	
	
	public void deletar(Veiculo veiculo){
		bd.delete("veiculo", "placa = ?", new String[]{""+veiculo.getPlaca()});
	}
	
	
	public List<Veiculo> buscar(){
		List<Veiculo> list = new ArrayList<Veiculo>();
		String[] colunas = new String[]{"id", "permissao", "placa"};
		
		Cursor cursor = bd.query("veiculo", colunas, null, null, null, null, "id ASC");
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			do{
				
				Veiculo veiculo = new Veiculo();
				veiculo.setId(cursor.getInt(0));
				veiculo.setPermissao(cursor.getInt(1));
				veiculo.setPlaca(cursor.getString(2));
			
				list.add(veiculo);
				
			}while(cursor.moveToNext());
		}
		
		return(list);
	}
	
	public int buscarQtdVagas(){
		
		String[] colunas = new String[]{"id", "permissao", "placa"};
		int cont = 0;
		
		Cursor cursor = bd.query("veiculo", colunas, null, null, null, null, "id ASC");
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
				cont++;
			while(cursor.moveToNext()){
				cont++;
			}
		}
		
		return  (cont);
	}
}
