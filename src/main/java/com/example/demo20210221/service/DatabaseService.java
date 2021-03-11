package com.example.demo20210221.service;

import com.example.demo20210221.dao.DatabaseDao;

import com.example.demo20210221.entity.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private final DatabaseDao databaseDao;

    public DatabaseService(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public List<String> getCatalogs(Database db){
        return this.databaseDao.getCatalogs(db);
    }

    public List<String> getTables(Database database) {
        return this.databaseDao.getTables(database);
    }
}
