package com.example.demo20210221.service;

import com.example.demo20210221.dao.TableDao;
import com.example.demo20210221.entity.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    @Autowired
    private TableDao tablesDao;

    public List<String> getTables(Database db) {
        return  this.tablesDao.getTables(db);
    }
}
