package br.com.luiza.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import br.com.luiza.bo.ProdutoBo;
import br.com.luiza.entity.Produto;
import br.com.luiza.entrada.ProdutoEntrada;
import br.com.luiza.saida.ProdutoSaida;

@Path("/produtoEndpoint")
public class ProdutoEndpoint {
	@Inject
	private ProdutoBo produtoBo;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/trataItensSemelhantes")
	public Response trataItensSemelhantes(ProdutoEntrada filter){
		Map<Map<String, Object>, List<Produto>> saidaMap = produtoBo.trataItensSemelhantes(filter);
		
		List<ProdutoSaida> saidaList = new ArrayList<>();
		for(Entry<Map<String, Object>, List<Produto>> entry: saidaMap.entrySet())
		{
			ProdutoSaida saida = new ProdutoSaida();
			saida.setDescription(entry.getKey());
			saida.setItems(entry.getValue());
			saidaList.add(saida);
		}
		
		return Response.status(200).entity(saidaList).build(); 
	}
}
