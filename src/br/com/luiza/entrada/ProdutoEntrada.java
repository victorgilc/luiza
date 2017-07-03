package br.com.luiza.entrada;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import br.com.luiza.entity.Produto;
import br.com.luiza.enums.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoEntrada{
	private List<Produto> produtos = new ArrayList<>();
	private LinkedHashMap<String, String> filterBy = new LinkedHashMap<>();
	private LinkedHashMap<String, Order> orderBy = new LinkedHashMap<>();
	private Set<String> groupBy = new HashSet<>();
}
