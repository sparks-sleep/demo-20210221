package com.example.demo20210221.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo20210221.entity.Database;
import com.example.demo20210221.service.TableService;
import com.example.demo20210221.utils.PropertiesUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping(value = "/tables")
public class TableController {
    @Autowired
    private TableService tablesService;

    /**
     * 数据服务器连接测试，并获取所有的数据库
     * @param jsonParam
     * @return
     */
    @ApiOperation(value = "数据服务器连接测试，并获取所有的数据库表")
    @PostMapping(value = "getTables",produces = "application/json;charset=UTF-8")
    public JSONObject getTables(@RequestBody JSONObject jsonParam){
        //Database database = JSONArray.toJavaObject(jsonParam,Database.class);
        String dbServer = jsonParam.getString("dbServer");
        String ip = jsonParam.getString("ip");
        String port = jsonParam.getString("port");
        String catalog = jsonParam.getString("catalog");//目录(数据库名)

        String userName = jsonParam.getString("userName");//用户名
        String password = jsonParam.getString("password");//密码

        File file = new File("src/main/resources/datasource.properties");
        //获取driver
        String driverKey = dbServer.toLowerCase().concat(".driver");
        String driver = PropertiesUtils.getProperty(file,driverKey);
        //获取url
        String urlKey = dbServer.toLowerCase().concat(".url");
        String urlValue = PropertiesUtils.getProperty(file,urlKey);

        Database db = new Database();
        db.setDriver(driver);
        db.setUrl(urlValue,ip,port,catalog);
        db.setCatalog(catalog);
        db.setUserName(userName);
        db.setPassWord(password);
        List<String> tables = this.tablesService.getTables(db);
        JSONObject object = new JSONObject();
        if(tables.size() <= 0){
           object.put("result","连接失败");
        }else{
            db.setTables(tables);
            object.put("db",db);
            object.put("result","连接成功");
        }
        return object;
    }

}
