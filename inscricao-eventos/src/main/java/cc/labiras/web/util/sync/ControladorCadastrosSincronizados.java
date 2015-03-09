package cc.labiras.web.util.sync;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ControladorCadastrosSincronizados {
	private final Map<String, Etapa> ETAPAS = Collections.synchronizedMap(new HashMap<String, Etapa>(5));
	
	public Etapa etapa(final cc.labiras.web.model.Etapa etapa) {
		return etapa(etapa.getNome()).setMaximo(etapa.getInscritos());
	}
	
	public Etapa etapa(final String etapa) {
		if (!ETAPAS.containsKey(etapa)) {
			ETAPAS.put(etapa, new Etapa());
		}
		
		return ETAPAS.get(etapa);
	}
	
	/**
	 * Representa uma etapa da inscrição.
	 *
	 */
	public static class Etapa {
		private int maximo = -1;
		private int inscritos = 0;
		
		/**
		 * Seta o máximo de inscritos para esta etapa.
		 * 
		 * @param maximo Máximo de inscritos (se {@code <= 0} então não há número máximo)
		 * 
		 * @return {@code this} (interface fluida)
		 */
		public synchronized Etapa setMaximo(final int maximo) {
			this.maximo = maximo;
			return this;
		}
		
		/**
		 * Adiciona um inscrito.
		 * @return {@code true} se o inscrito foi adicionado com sucesso e pode ser cadastrado no banco.
		 */
		public synchronized boolean adicionarInscrito() {
			if (podeInscrever()) {
				inscritos++;
				return true;
			}
			
			return false;
		}
		
		/**
		 * Remove um inscrito.
		 */
		public synchronized void removerInscrito() {
			if (inscritos > 0) {
				inscritos--;
			}
		}
		
		/**
		 * @return Retorna a contagem de candidatos inscritos neste momento
		 */
		public synchronized int contagemInscritos() {
			return inscritos;
		}
		
		/**
		 * Retorna {@code true} se o candidato puder ser inscrito
		 * @return
		 */
		public synchronized boolean podeInscrever() {
			return maximo <= 0 ? true : inscritos < maximo;
		}
	}
}
