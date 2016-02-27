package com.uciext.springfw.store.catalog.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "quantityIncrement")
public class QuantityIncrement {
	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
