package com.example.demo20210221.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo20210221.entity.Database;
import com.example.demo20210221.service.DatabaseService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController注解，相当于@Controller+@ResponseBody两个注解的结合，
 * 返回json数据不需要在方法前面加@ResponseBody注解了，
 * 但使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面
 */
@RestController
@RequestMapping(value = "/database")
public class DatabaseController {

    @Autowired
    private final DatabaseService databaseService;

    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @ApiOperation(value = "测试连接MySQL数据库")
    @PostMapping(value = "isConnSuccess",produces = "application/json;charset=UTF-8")
    public JSONObject isConnSuccess(@RequestBody JSONObject jsonParam){
        Database database = JSONArray.toJavaObject(jsonParam,Database.class);
        String dbServer = database.getDbServer();
        String ip = database.getIp();
        String port = database.getPort();
        String catalog = database.getCatalog();
        database.setDriver(null,dbServer);
        database.setUrl(dbServer,ip,port,catalog);
        String result = "fail";
        JSONObject object = new JSONObject();
        if(StringUtils.isNotBlank(catalog)){
            List<String> tables = this.databaseService.getTables(database);
            if(tables.size() > 0) {
                //database.setTables(tables);
                result = "success";
                object.put("database",database);
                object.put("tables",tables);
            }
        }else{
            List<String> catalogs = this.databaseService.getCatalogs(database);
            if(catalogs.size() > 0) {
                result = "success";
                object.put("database",database);
                object.put("catalogs",catalogs);
            }
        }
        object.put("result",result);
        return object;
    }
    @ApiOperation(value = "数据库连接成功，下一步")
    @PostMapping(value = "nextStep",produces = "application/json;charset=UTF-8")
    public JSONObject nextStep(@RequestBody JSONObject jsonParam){
        Database database = JSONArray.toJavaObject(jsonParam,Database.class);
        String catalog = database.getCatalog();
        String result = "fail";
        JSONObject object = new JSONObject();
        if(StringUtils.isNotBlank(catalog)){
            List<String> tables = this.databaseService.getTables(database);
            if(tables.size() > 0) {
                //database.setTables(tables);
                result = "success";
                object.put("database",database);
                object.put("tables",tables);
            }
        }else{
            List<String> catalogs = this.databaseService.getCatalogs(database);
            if(catalogs.size() > 0) {
                result = "success";
                object.put("database",database);
                object.put("catalogs",catalogs);
            }
        }
        object.put("result",result);
        return object;
    }
}
