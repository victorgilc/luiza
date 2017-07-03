package br.com.luiza.saida;

import java.util.List;
import java.util.Map;

import br.com.luiza.entity.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoSaida {

	private Map<String, Object> description;
	private List<Produto> items;
}
