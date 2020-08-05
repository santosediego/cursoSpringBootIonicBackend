package com.santosediego.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE (1, "Pendente"),
	QUITADO (2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int id;
	private String descricao;
	
	private EstadoPagamento(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	/*Lembrar de mudar o tipo na classe para inteiro*/
	public static EstadoPagamento toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for(EstadoPagamento x : EstadoPagamento.values()) {
			if(cod.equals(x.getId())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inváldo: " + cod);
	}
}
