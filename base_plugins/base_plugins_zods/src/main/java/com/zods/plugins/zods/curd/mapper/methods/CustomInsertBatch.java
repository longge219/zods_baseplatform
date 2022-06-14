package com.zods.plugins.zods.curd.mapper.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class CustomInsertBatch extends AbstractMethod {
    public CustomInsertBatch() {
    }

    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "<script>insert into %s %s values %s</script>";
        String fieldSql = this.prepareFieldSql(tableInfo);
        String valueSql = this.prepareValuesSqlForMysqlBatch(tableInfo);
        String sqlResult = String.format("<script>insert into %s %s values %s</script>", tableInfo.getTableName(), fieldSql, valueSql);
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sqlResult, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, "insertBatch", sqlSource, new Jdbc3KeyGenerator(), "id", "id");
    }

    private String prepareFieldSql(TableInfo tableInfo) {
        StringBuilder fieldSql = new StringBuilder();
        fieldSql.append(tableInfo.getKeyColumn()).append(",");
        tableInfo.getFieldList().forEach((x) -> {
            fieldSql.append(x.getColumn()).append(",");
        });
        fieldSql.delete(fieldSql.length() - 1, fieldSql.length());
        fieldSql.insert(0, "(");
        fieldSql.append(")");
        return fieldSql.toString();
    }

    private String prepareValuesSqlForMysqlBatch(TableInfo tableInfo) {
        StringBuilder valueSql = new StringBuilder();
        valueSql.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">");
        valueSql.append("#{item.").append(tableInfo.getKeyProperty()).append("},");
        tableInfo.getFieldList().forEach((x) -> {
            valueSql.append("#{item.").append(x.getProperty()).append("},");
        });
        valueSql.delete(valueSql.length() - 1, valueSql.length());
        valueSql.append("</foreach>");
        return valueSql.toString();
    }
}
