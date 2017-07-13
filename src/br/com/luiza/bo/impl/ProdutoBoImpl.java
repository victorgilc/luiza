package br.com.luiza.bo.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import br.com.luiza.bo.ProdutoBo;
import br.com.luiza.entity.Produto;
import br.com.luiza.entrada.ProdutoEntrada;
import br.com.luiza.enums.Order;
import br.com.luiza.util.Levenshtein;

/**
 * Classe que possui as regras de negócio da entidade {@link Produto}
 *
 */
public class ProdutoBoImpl implements ProdutoBo{
	
	
	/**
	 * Faz um cast para os tipos que possuímos no Produto, a fim de comparar um valor genérico no equals
	 * @param myClass
	 * @param valor
	 * @return O valor com o cast
	 */
	private <T> T castValue(Class<?> myClass, String valor)
	{
		if(myClass.equals(Long.class))
		{	
			return (T) Long.valueOf(valor);
		}	
		if(myClass.equals(Double.class))
		{	
			return (T) Double.valueOf(valor);
		}
		return (T) valor;	
		
	}
	
	
	/**
	 * Filtra os valores da lista de produtos
	 * @param produtoList
	 * @param filterBy
	 */
	private List<Produto> genericFilter(List<Produto> produtoList, LinkedHashMap<String, String> filterBy)
	{
		for(Entry<String, String> f: filterBy.entrySet()){
			String campo = f.getKey();
			String valor = f.getValue();
			
			produtoList = produtoList.stream().filter(s->{
					try {
						Field field = Produto.class.getDeclaredField(campo);
						field.setAccessible(true);
						return field.get(s).equals(castValue(field.getType(), valor));
						
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					return true;
				}).collect(Collectors.toList());
		}
		return produtoList;
	}
	
	/**
	 * Agrupa os valores da lista de produtos
	 * @param produtoList
	 * @param grouByList
	 * @return Um mapa de objetos agrupadores e produtos agrupados
	 */
	private Map<Map<String, Object>, List<Produto>> genericGroup(List<Produto> produtoList, Set<String> grouByList) {
		final int ACCEPTABLE_STRING_PERCENTAGE_DISTANCE = 20;
		final String ID = "id";
		
		Map<Map<String, Object>, List<Produto>> agrupadores = new HashMap<>(); 
		List<Map<String, Object>> listaAgrupadores = new ArrayList<>();
		Map<String, Object> currentAgrupador = null;
		try 
		{
			/**
			 * Definindo quais os agrupadores
			 */
			for(Produto p: produtoList)
			{
				currentAgrupador = new HashMap<>();
				
				for(String g: grouByList)
				{
					Field f = Produto.class.getDeclaredField(g);
					f.setAccessible(true);
					
					try {
						if(f.get(p)!=null)
						{
							currentAgrupador.put(f.getName(), f.get(p));
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				
				if(!agrupadores.containsKey(currentAgrupador))
				{
					listaAgrupadores.add(currentAgrupador);
					agrupadores.put(currentAgrupador, new ArrayList<>());
				}
				else
				{
					agrupadores.get(currentAgrupador).add(p);
				}
			
			} 
			
			/**
			 * Relacionando os registros ao respectivos agrupadores
			 */
			for(Map<String, Object> agrupa:listaAgrupadores)
			{
				for(Produto p: produtoList)
				{
					Boolean pertenceGrupo = true;
					for(Entry<String, Object> currentGroup: agrupa.entrySet())
					{
						
						Field f = Produto.class.getDeclaredField(currentGroup.getKey());
						f.setAccessible(true);
						try {
							Object value = f.get(p);
							
							if(currentGroup.getValue()!=null &&  f.get(p)!=null)
							{
								//aplicamos a definição de Levenshtein
								if(f.getName()!=ID && f.getType().equals(String.class))
								{
									
									String source = currentGroup.getValue().toString();
									String toCompare = "";
									
										toCompare = value.toString();
									
									//Não está no agrupamento
									if(!Levenshtein.isDistanceAcceptable(source, toCompare, ACCEPTABLE_STRING_PERCENTAGE_DISTANCE))
									{
										pertenceGrupo = false;
									}
								}
								else
								{
									pertenceGrupo = currentGroup.getValue().equals(f.get(p)); 
								}
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					
					if(pertenceGrupo)
					{
						agrupadores.get(agrupa).add(p);
					}
				}
			}
		}  
		catch (NoSuchFieldException | SecurityException e) 
		{
				e.printStackTrace();
		}
		

	    return agrupadores;
	}
	

	/**
	 * Ordenação da lista agrupada de produtos	
	 * @param groupedProducts
	 * @param orderBy
	 */
	private void genericSort(Map<Map<String, Object>, List<Produto>> groupedProducts,LinkedHashMap<String, Order> orderBy)
	{
		Comparator<Produto> toSort = (Produto p1, Produto p2) -> {
			
			int toReturn = 0;
			
			for(Entry<String, Order> m: orderBy.entrySet()){
				String valor = m.getKey();
				Order ordem = m.getValue();
				Field field;

				Comparable c1 = null;
				Comparable c2 = null;
				try {
					field = Produto.class.getDeclaredField(valor);
			
					field.setAccessible(true);
				
					c1 = (Comparable) field.get(p1);
				    c2 = (Comparable) field.get(p2);
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
				
				
				if(ordem.equals(Order.ASC)){
					toReturn = c1.compareTo(c2);
				}
				else if(ordem.equals(Order.DESC)){
					toReturn = c2.compareTo(c1);
				}
				
				if(toReturn!=0){
					break;
				}
			}
			return toReturn;
		};
		
		for(Entry<Map<String, Object>, List<Produto>> p: groupedProducts.entrySet()){
			p.getValue().sort(toSort);
		}
	}
	
	@Override
	public Map<Map<String, Object>, List<Produto>> trataItensSemelhantes(ProdutoEntrada produtoFilter) {
		
		//filter by, if exists
		if(!produtoFilter.getFilterBy().isEmpty())
		{
			produtoFilter.setProdutos(genericFilter(produtoFilter.getProdutos(), produtoFilter.getFilterBy()));
		}
		
		//default groupBy
		if(produtoFilter.getGroupBy().isEmpty())
		{
			final String EAN = "ean"; 
			final String TITLE = "title";
			final String BRAND = "brand";
			produtoFilter.getGroupBy().add(EAN);
			produtoFilter.getGroupBy().add(TITLE);
			produtoFilter.getGroupBy().add(BRAND);
		}
		
		Map<Map<String, Object>, List<Produto>> groupedProducts = genericGroup(produtoFilter.getProdutos(), produtoFilter.getGroupBy());
		
		//default sort
		if(produtoFilter.getOrderBy().isEmpty())
		{
			final String STOCK = "stock";
			final String PRICE = "price";
			produtoFilter.getOrderBy().put(STOCK, Order.DESC);
			produtoFilter.getOrderBy().put(PRICE, Order.ASC);
		}
		
		genericSort(groupedProducts, produtoFilter.getOrderBy());
		
		return groupedProducts;
	}

}
