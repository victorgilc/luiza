package br.com.luiza.bo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.luiza.entity.Produto;
import br.com.luiza.entrada.ProdutoEntrada;

/**
 * Interface para regras de negócio de {@link Produto}
 *
 */
public interface ProdutoBo {
	/**
	 * 
	 * Método responsável por ordenar, filtra e agrupar itens semelhantes
	 * 
	 * @param entrada
	 * @return
	 */
	Map<Map<String, Object>, List<Produto>> trataItensSemelhantes(ProdutoEntrada entrada);
}
