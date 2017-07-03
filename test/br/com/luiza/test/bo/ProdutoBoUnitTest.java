package br.com.luiza.test.bo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import br.com.luiza.bo.ProdutoBo;
import br.com.luiza.bo.impl.ProdutoBoImpl;
import br.com.luiza.entity.Produto;
import br.com.luiza.entrada.ProdutoEntrada;
import br.com.luiza.enums.Order;
import junit.framework.Assert;

/**
 * Classe responsável pelos testes unitários de {@link ProdutoBoImpl}
 *
 */
public class ProdutoBoUnitTest {
	
	private ProdutoBo produtoBo = new ProdutoBoImpl();
	
	private ProdutoEntrada entrada;
	
	private final static String STOCK = "stock";
	
	private final static String BRAND = "brand";
	
	private final static String BRAND_VALUE = "a123";
	
	private final static String PRICE = "price";
	
	private final static Double PRICE_VALUE = 15D;
	
	private final static Double ANOTHER_PRICE = 14D;
	
	/**
	 * Teste com as definições padrões de filtro, ordenação e agrupamento
	 */
	@Test
	public void trataItensSemelhantesDefaultsTest()
	{
		criarMassa();
		Map<Map<String, Object>, List<Produto>> trataItensSemelhantes = produtoBo.trataItensSemelhantes(entrada);
		
		Assert.assertTrue(trataItensSemelhantes.keySet().size()==6);
		
		
		for(Entry<Map<String, Object>, List<Produto>> entry:trataItensSemelhantes.entrySet())
		{
			for(Entry<String, Object> x:entry.getKey().entrySet())
			{
				if(x.getKey().equals(BRAND) && x.getValue().equals(BRAND_VALUE))
				{
					Assert.assertTrue(entry.getValue().size()==7);
				}
				else
				{
					Assert.assertTrue(entry.getValue().size()==1);
				}
			}
		}
	}
	
	/**
	 * Teste filtro, ordenação e agrupamento sendo informadas
	 */
	@Test
	public void trataItensSemelhantesTest()
	{
		criarMassa();
		
		LinkedHashMap<String, Order> orderByList = new LinkedHashMap<>();
		orderByList.put("brand", Order.ASC);
		orderByList.put("price", Order.DESC);
	
		
		entrada.setOrderBy(orderByList);
		entrada.getGroupBy().add(BRAND);
		entrada.getGroupBy().add(STOCK);
		entrada.getFilterBy().put(PRICE, PRICE_VALUE.toString());
		
		Map<Map<String, Object>, List<Produto>> trataItensSemelhantes = produtoBo.trataItensSemelhantes(entrada);
		
		Assert.assertTrue(trataItensSemelhantes.keySet().size()==6);
		
		for(Entry<Map<String, Object>, List<Produto>> entry:trataItensSemelhantes.entrySet())
		{
			if(entry.getKey().get("stock").equals(15) && entry.getKey().get("brand").equals("a123"))
			{
				Assert.assertTrue(entry.getValue().size()==3);
				
				//O menor preço no primeiro índice, obedecendo a ordenação!
				Assert.assertTrue(entry.getValue().get(0).getPrice().equals(ANOTHER_PRICE));
			}
			
			for(Produto p:entry.getValue())
			{
				//Todos os produtos tem o mesmo preço, obedecendo o filtro
				Assert.assertTrue(p.getPrice().equals(PRICE_VALUE));
			}
		}
	}
	
	/**
	 * Massa base do teste
	 */
	private void criarMassa()
	{
		entrada = new ProdutoEntrada();
		List<Produto> produtoList = new ArrayList<>();
		Produto p = new Produto();
		p.setId(1L);
		p.setBrand("a123");
		p.setStock(15L);
		p.setPrice(14D);
		produtoList.add(new Produto(p));
		
		p.setId(11L);
		p.setBrand("a123");
		p.setStock(15L);
		p.setPrice(15D);
		produtoList.add(new Produto(p));
		
		p.setId(2L);
		p.setBrand("z123");
		p.setStock(10L);
		p.setPrice(25D);
		produtoList.add(new Produto(p));
		
		p.setId(4L);
		p.setBrand("s123");
		p.setStock(16L);
		p.setPrice(15D);
		produtoList.add(new Produto(p));
		
		p.setId(5L);
		p.setBrand("g123");
		p.setStock(15L);
		p.setPrice(15D);
		produtoList.add(new Produto(p));
		
		p.setId(5L);
		p.setBrand("a123");
		p.setStock(15L);
		p.setPrice(15D);
		produtoList.add(new Produto(p));
		
		p.setId(6L);
		p.setBrand("u123");
		p.setStock(10L);
		p.setPrice(15D);
		produtoList.add(new Produto(p));
		
		p.setId(7L);
		p.setBrand("a123");
		p.setStock(130L);
		p.setPrice(15D);
		produtoList.add(new Produto(p));
		
		p.setId(8L);
		p.setBrand("t123");
		p.setStock(10L);
		p.setPrice(15D);
		produtoList.add(new Produto(p));
		entrada.setProdutos(produtoList);
	}
}
