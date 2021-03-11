package com.example.demo20210221.dao;

import com.alibaba.fastjson.JSONArray;
import com.example.demo20210221.entity.Columns;
import com.example.demo20210221.entity.Database;
import com.example.demo20210221.entity.Table;
import com.example.demo20210221.utils.ChangValueUtils;
import com.example.demo20210221.utils.PropertiesUtils;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TableDao {

    /**
     * 获取当前数据库的所有表
     * @param db
     * @return
     */
    public List<String> getTables(Database db) {
        List<String> listTable = new ArrayList<>();
        String catalog = db.getCatalog();
        //1、与数据库的连接
        Connection conn = DatabaseDao.getConnection(db);
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

    /**
     * 获取表主键
     * @param rs_priKeys
     * @return
     */
    public String getPkFields(ResultSet rs_priKeys){
        String pkField = "";
        Set<String> set = new HashSet<String>();
        try {
            while (rs_priKeys.next()) {
                String pkColumn = rs_priKeys.getString("COLUMN_NAME");
                //String pkName = rs_priKeys.getString("PK_NAME"); //主键名称
                //String keySeq = rs_primaryKeys.getString("KEY_SEQ");
                set.add(ChangValueUtils.changeToJavaField(pkColumn));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(set.size() == 1 ){
            pkField = set.iterator().next();
        }else{
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                System.out.println("it.next()="+it.next());
            }
        }
        return pkField;
    }
    /**
     * 获取所有的表字段
     * @param rs_columns
     * @return
     */
    public List<Columns> getColumns(ResultSet rs_columns){
        List<Columns> columns = new ArrayList<>();
        try {
            while (rs_columns.next()) {
                String columnName = rs_columns.getString("COLUMN_NAME");//列名
                String fieldName = ChangValueUtils.changeToJavaField(columnName);//字段名
                int dataType = rs_columns.getInt("DATA_TYPE");     //对应的java.sql.Types的SQL类型(列类型ID)
                String dbTypeName = rs_columns.getString("TYPE_NAME");  //java.sql.Types类型名称(列类型名称)

                File file = new File("src/main/resources/typeConverter.properties");
                //String javaColumnType = String.valueOf(PropertiesUtils.customMap.get(dbTypeName));//Java变量
                String javaColumnType = PropertiesUtils.getProperty(file,dbTypeName);

                Integer columnSize = rs_columns.getInt("COLUMN_SIZE");//字段大小
                int decimalDigits = rs_columns.getInt("DECIMAL_DIGITS");  //小数位数
                String nullableValue = "true";
                /**
                 *  0 (columnNoNulls) - 该列不允许为空 false
                 *  1 (columnNullable) - 该列允许为空 true
                 *  2 (columnNullableUnknown) - 不确定该列是否为空
                 */
                int nullableKey = rs_columns.getInt("NULLABLE");  //是否允许为null

                if (nullableKey == 0) {
                    nullableValue = "false";
                }
                String remarks = rs_columns.getString("REMARKS");  //列描述
                String columnDef = rs_columns.getString("COLUMN_DEF");  //默认值
                int charOctetLength = rs_columns.getInt("CHAR_OCTET_LENGTH");    // 对于 char 类型，该长度是列中的最大字节数
                int ordinalPosition = rs_columns.getInt("ORDINAL_POSITION");   //表中列的索引（从1开始）
                /**
                 * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
                 * YES -- 该列可以有空值;
                 * NO -- 该列不能为空;
                 * 空字符串--- 不知道该列是否可为空
                 */
                //String isNullAble = rs_columns.getString("IS_NULLABLE");
//                String culKey = "";
//                if(pkFields.contains(columnName)){
//                    culKey = "PRI";
//                }
                Columns cu = new Columns();
                cu.setColumnName(columnName.toUpperCase());//字段全部大写
                cu.setFieldName(fieldName);
                cu.setDbColumnType(dbTypeName);
                cu.setJavaColumnType(javaColumnType);
                cu.setColumnSize(columnSize);
                cu.setDecimalDigits(decimalDigits);
                cu.setNullableValue(nullableValue);
                cu.setRemarks(remarks);
                columns.add(cu);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return columns;
    }

    /**
     *
     * @param db
     * @param dbTable
     * @return
     */
    public List<Table> findAllColumnsAndColKeys(Database db, JSONArray dbTable){
        List<Table> listTable = new ArrayList<>();
        String catalog = db.getCatalog();
        //1、与数据库的连接
        Connection conn = DatabaseDao.getConnection(db);
        ResultSet rs_priKeys = null;
        ResultSet rs_columns = null;
        try {
            //2、获取元数据
            DatabaseMetaData dbMetaData = conn.getMetaData();
            //for (String tableName : listTable) {
            for (int i = 0; i < dbTable.size(); i++) {
                String tableName = dbTable.getString(i).trim();//表名
                //3、获取表主键
                rs_priKeys = dbMetaData.getPrimaryKeys(catalog,null,tableName);
                rs_columns = dbMetaData.getColumns(catalog, null, tableName, "%");
                String pkField = this.getPkFields(rs_priKeys);
                List<Columns> columns = this.getColumns(rs_columns);

                Table table = new Table();
                table.setCatalog(catalog.toUpperCase());
                table.setTableName(tableName.toUpperCase());
                //去掉前缀,清除下划线,下划线后的首字母大写
                table.setPojoName(ChangValueUtils.firstStrUpperCase(tableName));
                table.setPkField(pkField);
                table.setColumns(columns);
                listTable.add(table);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("连接数据库错误：dbMetaData值");
        } finally {
            DatabaseDao.closeResultSet(rs_columns);
            DatabaseDao.closeResultSet(rs_priKeys);
            DatabaseDao.closeConnection(conn);
        }
        return listTable;
    }

}
