package com.nagarro.eBroker.model;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "t_trader")
public class Trader {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	@NotNull
	private String name;
	@NotNull
	private int funds;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "trader_equity", joinColumns = {
			@JoinColumn(name = "trader_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "equity_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private List<Equity> equityList = new ArrayList<Equity>();

	public Trader() {
		super();
	}

	public Trader(long id, String name, int funds) {
		super();
		this.id = id;
		this.name = name;
		this.funds = funds;
	}

	@Override
	public String toString() {
		return "Trader [id=" + id + ", name=" + name + ", funds=" + funds + ", equityList=" + equityList + "]";
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFunds() {
		return funds;
	}

	public void setFunds(int funds) {
		this.funds = funds;
	}

	public List<Equity> getEquityList() {
		return equityList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(equityList, funds, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trader other = (Trader) obj;
		return Objects.equals(equityList, other.equityList) && funds == other.funds && id == other.id
				&& Objects.equals(name, other.name);
	}

}
