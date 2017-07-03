package br.com.luiza.bo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.luiza.entity.Produto;
import br.com.luiza.entrada.ProdutoEntrada;

/**
 * Interface para regras de neg�cio de {@link Produto}
 *
 */
public interface ProdutoBo {
	/**
	 * 
	 * M�todo respons�vel por ordenar, filtra e agrupar itens semelhantes
	 * 
	 * @param entrada
	 * @return
	 */
	Map<Map<String, Object>, List<Produto>> trataItensSemelhantes(ProdutoEntrada entrada);
}
