package cc.labiras.web.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cc.labiras.web.util.JSONable;

@Entity
public class Inscrito implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@Column(nullable = false, length = 256)
	private String nome;
	
	@Column(nullable = false, length = 256)
	private String email;
	
	@Column(nullable = false, unique = true, length = 32)
	private String cpf;
	
	@Column(nullable = false)
	private int idade;
	
	@Column(nullable = false)
	private boolean estudante;
	
	@Column(nullable = false)
	private String nivelGraduacao;
	
	@Column(nullable = false)
	private String curso;
	
	@Column(nullable = false)
	private String ultimaInstituicao;
	
	@Column(nullable = false)
	private boolean profissional;
	
	@Column(nullable = false)
	private String empresa;
	
	@Column(nullable = false)
	private String cidade;
	
	@Column(nullable = false)
	private String uf;
	
	@Column(nullable = false)
	private String comoFicouSabendoDoEvento;
	
	@Column(nullable = false)
	private String jaConheceArduino;
	
	@Column(nullable = false)
	private boolean jaUsouArduino;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataInscricao;
	
	@Column(nullable = false)
	private boolean pago;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPagamento;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(final String cpf) {
		this.cpf = cpf;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(final int idade) {
		this.idade = idade;
	}

	public boolean isEstudante() {
		return estudante;
	}

	public void setEstudante(final boolean estudante) {
		this.estudante = estudante;
	}

	public String getNivelGraduacao() {
		return nivelGraduacao;
	}

	public void setNivelGraduacao(final String nivelGraduacao) {
		this.nivelGraduacao = nivelGraduacao;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(final String curso) {
		this.curso = curso;
	}

	public String getUltimaInstituicao() {
		return ultimaInstituicao;
	}

	public void setUltimaInstituicao(final String ultimaInstituicao) {
		this.ultimaInstituicao = ultimaInstituicao;
	}

	public boolean isProfissional() {
		return profissional;
	}

	public void setProfissional(final boolean profissional) {
		this.profissional = profissional;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(final String empresa) {
		this.empresa = empresa;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(final String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(final String uf) {
		this.uf = uf;
	}

	public String getComoFicouSabendoDoEvento() {
		return comoFicouSabendoDoEvento;
	}

	public void setComoFicouSabendoDoEvento(final String comoFicouSabendoDoEvento) {
		this.comoFicouSabendoDoEvento = comoFicouSabendoDoEvento;
	}

	public String getJaConheceArduino() {
		return jaConheceArduino;
	}

	public void setJaConheceArduino(final String jaConheceArduino) {
		this.jaConheceArduino = jaConheceArduino;
	}

	public boolean isJaUsouArduino() {
		return jaUsouArduino;
	}

	public void setJaUsouArduino(final boolean jaUsouArduino) {
		this.jaUsouArduino = jaUsouArduino;
	}

	public Map<String, Object> json() {
		return null;
	}

	public Date getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(final Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(final boolean pago) {
		this.pago = pago;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(final Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
}
