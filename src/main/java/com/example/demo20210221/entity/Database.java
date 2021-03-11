package com.example.demo20210221.entity;

import com.example.demo20210221.utils.PropertiesUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 数据库实体类
 */
@EntityScan
public class Database implements Serializable {

    private String dbServer; //数据库服务
    private String ip; //IP地址
    private String port; //端口

    private String driver; //数据库驱动
    private String url; //url地址=[ip]:[port]/[db]、[ip]:[port]:[db]
    private String userName; //用户名
    private String passWord; //密码
    private String catalog; //目录
    private List<String> tables; //表

    File file = new File("src/main/resources/datasource.properties");

    public Database() {

    }

    public Database(String driver, String url, String userName, String passWord, String catalog, List<String> tables) {
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
        this.catalog = catalog;
        this.tables = tables;
    }
    public Database(String driver, String url, String userName, String passWord, String catalog) {
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
        this.catalog = catalog;
    }

    public String getDbServer() {
        return dbServer;
    }

    public void setDbServer(String dbServer) {
        this.dbServer = dbServer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDriver() { return driver; }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setDriver(String driver,String dbServer) {
        driver = PropertiesUtils.getProperty(file,dbServer.toLowerCase().concat(".driver"));
        this.driver = driver;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrl(String dbServer, String ip, String port, String catalog) {
        String url = PropertiesUtils.getProperty(file,dbServer.toLowerCase().concat(".url"));
        this.url = url.replace("[ip]",ip).replace("[port]",port).replace("[dbName]",catalog);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}
