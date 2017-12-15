package com.example.autentica_entrada;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {
	private static final String NOME_BD = "teste";
	private static final int VERSAO_BD = 15;
	
	
	public BDCore(Context ctx){
		super(ctx, NOME_BD, null, VERSAO_BD);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase bd) {
		
		String sqlCreateTableCarro = "CREATE TABLE veiculo(" 
				+"id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+"permissao INTEGER,"
				+"placa TEXT"
				+")";
		bd.execSQL(sqlCreateTableCarro);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
		bd.execSQL("drop table veiculo;");
		onCreate(bd);
	}

}
