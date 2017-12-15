package com.example.autentica_entrada;

public class Veiculo {
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int permissao;
	private String placa;
	
	
	public Veiculo(){}
	
	public Veiculo(int id, int permissao, String placa){
		this.id = id;
		this.permissao = permissao;
		this.placa = placa;
		
	}

	public int getPermissao() {
		return permissao;
	}

	public void setPermissao(int permissao) {
		this.permissao = permissao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "permissao = " + permissao + ", placa = " + placa ;
	}
	
	
	

	
	
}
