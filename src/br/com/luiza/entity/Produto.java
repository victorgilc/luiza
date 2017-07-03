package br.com.luiza.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Entidade para Produto
 */
@Getter
@Setter
@NoArgsConstructor
public class Produto {
	
	public Produto(Produto p)
	{
		this.id = p.getId();
		this.ean = p.getEan();
		this.title = p.getTitle();
		this.brand = p.getBrand();
		this.price = p.getPrice();
		this.stock = p.getStock();
	}
	private String id;
	private Long ean;
	private String title;
	private String brand;
	private Double price;
	private Long stock;
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("id:");
		builder.append(id);
		builder.append("\n");
		builder.append("ean:");
		builder.append(ean);
		builder.append("\n");
		builder.append("title:");
		builder.append(title);
		builder.append("\n");
		builder.append("brand:");
		builder.append(brand);
		builder.append("\n");
		builder.append("price:");
		builder.append(price);
		builder.append("\n");
		builder.append("stock:");
		builder.append(stock);
		builder.append("\n");
		return builder.toString();
		
	}
}
