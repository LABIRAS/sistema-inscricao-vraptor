package cc.labiras.web.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Etapa {
	@Id @GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true, length = 256)
	private String nome;
	
	@Column(nullable = false)
	private int preco;
	
	@Column(nullable = false)
	private int inscritos;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataInicio;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataFim;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public int getPreco() {
		return preco;
	}

	public void setPreco(final int preco) {
		this.preco = preco;
	}

	public int getInscritos() {
		return inscritos;
	}

	public void setInscritos(final int inscritos) {
		this.inscritos = inscritos;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(final Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(final Calendar dataFim) {
		this.dataFim = dataFim;
	}
}
