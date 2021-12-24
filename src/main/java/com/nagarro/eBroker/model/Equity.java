package com.nagarro.eBroker.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_equity")
public class Equity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private int price;
	@ManyToMany(mappedBy = "equityList", fetch = FetchType.LAZY)
	private List<Trader> trader = new ArrayList<Trader>();;
	@NotNull
	private int quantity;

	@Override
	public String toString() {
		return "Equity [id=" + id + ", price=" + price + ", trader=" + ", quantity=" + quantity + "]";
	}

	public Equity(int id, int price, int quantity) {
		super();
		this.id = id;
		this.price = price;
		this.quantity = quantity;
	}

	public Equity() {
		super();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Trader> getTrader() {
		return trader;
	}

}
