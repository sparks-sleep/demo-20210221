package com.example.demo20210221.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;

/**
 * 表字段
 */
@EntityScan
public class Columns implements Serializable {

    private String columnName;//列名
    private String fieldName;//字段名
    private String jsColumnType; //JS变量类型
    private String javaColumnType;//Java变量类型
    private String dbColumnType;//数据库字段类型
    private String remarks;//列描述
    private Integer columnSize;//字段大小
    private Integer decimalDigits;//小数位数
    private String nullableValue;//是否空

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getJsColumnType() {
        return jsColumnType;
    }

    public void setJsColumnType(String jsColumnType) {
        this.jsColumnType = jsColumnType;
    }

    public String getJavaColumnType() {
        return javaColumnType;
    }

    public void setJavaColumnType(String javaColumnType) {
        this.javaColumnType = javaColumnType;
    }

    public String getDbColumnType() {
        return dbColumnType;
    }

    public void setDbColumnType(String dbColumnType) {
        this.dbColumnType = dbColumnType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getNullableValue() {
        return nullableValue;
    }

    public void setNullableValue(String nullableValue) {
        this.nullableValue = nullableValue;
    }
}
