package com.uciext.springfw.store.catalog.dao.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.uciext.springfw.store.catalog.dao.CatalogDao;
import com.uciext.springfw.store.catalog.model.Catalog;
import com.uciext.springfw.store.common.Util;

public class JdbcCatalogDao implements CatalogDao {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	// SQL statements
	private static final String SQL_INSERT_CATALOG = "INSERT INTO catalogs (catalog_id, catalog_name) VALUES (?, ?)";
	private static final String SQL_GET_CATALOGS = "SELECT catalog_id, catalog_name FROM catalogs";
	private static final String SQL_GET_CATALOG_BY_ID = "select * from catalogs where catalog_id = ?";
	// Datastore Template
	private SimpleJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// =================================================
	// DB methods

	@Override
	public List<Catalog> getCatalogs() {
		logger.info("Geting catalogs");

		List<Catalog> catalogs = new ArrayList<Catalog>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_CATALOGS);
		for (Map<String, Object> row : rows) {
			Catalog catalog = new Catalog(Integer.parseInt(String.valueOf(row.get("catalog_id"))),
					(String) row.get("catalog_name"));
			logger.info("Fetched catalog: " + catalog);
			catalogs.add(catalog);
		}

		return catalogs;
	}

	@Override
	public Catalog addCatalog(Catalog catalog) {
		catalog.setCatalogId(Util.getRandomInt());
		logger.info("Inserting catalog: " + catalog);
		jdbcTemplate.update(SQL_INSERT_CATALOG, catalog.getCatalogId(), catalog.getCatalogName());
		return catalog;
	}

	@Override
	public Catalog getCatalog(int catalogId) {
		logger.info("Getting catalog for ID: " + catalogId);

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_CATALOG_BY_ID, catalogId);
		if (rows.size() == 0) {
			return null;
		}
		Map<String, Object> row = rows.get(0);
		Catalog catalog = new Catalog(Integer.parseInt(String.valueOf(row.get("catalog_id"))),
				(String) row.get("catalog_name"));
		logger.info("Fetched catalog: " + catalog);

		return catalog;
	}

}
