package com.uciext.springfw.store.catalog.dao;

import java.util.List;

import com.uciext.springfw.store.catalog.model.Catalog;

public interface CatalogDao {
	List<Catalog> getCatalogs();

	Catalog addCatalog(Catalog catalog);

	Catalog getCatalog(int catalogId);
}
