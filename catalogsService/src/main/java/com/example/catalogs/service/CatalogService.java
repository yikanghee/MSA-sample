package com.example.catalogs.service;

import com.example.catalogs.repository.entity.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
