package com.example.demo20210221.dao;

import com.example.demo20210221.entity.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class DatabaseDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseDao.class);
    /**
     * 获取数据库连接
     * @param database
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(Database database){
        //获取连接
        try {
            System.out.println("driver="+database.getDriver());
            Class.forName(database.getDriver());//注册驱动

        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("驱动错误");
            //LOGGER.error("can not load jdbc driver", e);
        }
        Properties props = new Properties();
        props.put("remarksReporting","true");//获取数据库的备注信息
        props.put("user",database.getUserName());
        props.put("password",database.getPassWord());
        Connection conn = null;
        try {
            System.out.println("url="+database.getUrl());
            conn = DriverManager.getConnection(database.getUrl(),props);
        } catch (SQLException throwables) {
            System.out.println("连接失败");
//            throwables.printStackTrace();
            //LOGGER.error("get connection failure", throwables);
        }
        return conn;
    }
    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }
    public static void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("close ResultSet failure", e);
            }
        }
    }
    public static void closeStatement(Statement stmt) {
        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                LOGGER.error("close Statement failure", e);
            }
        }
    }
    public static void closePreparedStatement(PreparedStatement pStmt) {
        if(pStmt != null) {
            try {
                pStmt.close();
            } catch (SQLException e) {
                LOGGER.error("close PreparedStatement failure", e);
            }
        }
    }

    /**
     * 获取数据库列表
     * @param db
     * @return
     */
    public List<String> getCatalogs(Database db){
        List<String> listCatalog = new ArrayList<>();
        ResultSet rs_catalogs = null;
        ResultSet rs_tableTypes = null;
        //获取元数据
        Connection conn = getConnection(db);
        if(conn == null){
            return listCatalog;
        }
        //获取数据库的元数据
        DatabaseMetaData dbmd;
        try {
            dbmd = conn.getMetaData();
            System.out.println("数据库已知的用户: "+ dbmd.getUserName());
            System.out.println("数据库的系统函数的逗号分隔列表: "+ dbmd.getSystemFunctions());
            System.out.println("数据库的时间和日期函数的逗号分隔列表: "+ dbmd.getTimeDateFunctions());
            System.out.println("数据库的字符串函数的逗号分隔列表: "+ dbmd.getStringFunctions());
            System.out.println("数据库供应商用于 'schema' 的首选术语: "+ dbmd.getSchemaTerm());
            System.out.println("数据库URL: " + dbmd.getURL());
            System.out.println("是否允许只读:" + dbmd.isReadOnly());
            System.out.println("数据库的产品名称:" + dbmd.getDatabaseProductName());
            System.out.println("数据库的版本:" + dbmd.getDatabaseProductVersion());
            System.out.println("驱动程序的名称:" + dbmd.getDriverName());
            System.out.println("驱动程序的版本:" + dbmd.getDriverVersion());
            System.out.println("数据库中使用的表类型");
            rs_tableTypes = dbmd.getTableTypes();
            while (rs_tableTypes.next()) {
                System.out.println(rs_tableTypes.getString("TABLE_TYPE"));
            }
            //从元数据中获取到所有数据库
            rs_catalogs = dbmd.getCatalogs();
            while (rs_catalogs.next()) {
                listCatalog.add(rs_catalogs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        } finally {
            closeResultSet(rs_catalogs);
            closeResultSet(rs_tableTypes);
            closeConnection(conn);
        }
        return listCatalog;
    }

    /**
     * 获取当前库所有表
     * @param database
     * @return
     */
    public List<String> getTables(Database database) {
        List<String> listTable = new ArrayList<>();
        String catalog = database.getCatalog();
        //1、与数据库的连接
        Connection conn = DatabaseDao.getConnection(database);
        if(conn == null){
            return listTable;
        }
        ResultSet rs_tables = null;
        try {
            //2.获取元数据
            DatabaseMetaData dbMetaData = conn.getMetaData();
            //3.获取表
            rs_tables = dbMetaData.getTables(catalog,null,null,new String[] { "TABLE" });
            while (rs_tables.next()) {
                //从元数据中获取到所有的表名
                //String table = rs_tables.getString("TABLE_NAME");
                String table = rs_tables.getString(3);
                listTable.add(table);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            DatabaseDao.closeResultSet(rs_tables);
            DatabaseDao.closeConnection(conn);
        }
        return listTable;
    }
}
