package com.uciext.springfw.store.catalog.model;

public class Catalog {
	private int catalogId;
	private String catalogName;

	public Catalog(int catalogId, String catalogName) {
		this.catalogId = catalogId;
		this.catalogName = catalogName;
	}

	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	@Override
	public String toString() {
		return "Catalog [catalogId=" + catalogId + ", catalogName=" + catalogName + "]";
	}

}
