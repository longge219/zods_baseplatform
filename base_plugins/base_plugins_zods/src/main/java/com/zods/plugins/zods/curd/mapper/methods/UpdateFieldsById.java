package com.zods.plugins.zods.curd.mapper.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class UpdateFieldsById extends AbstractMethod {
    public UpdateFieldsById() {
    }

    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "<script>UPDATE %s SET %s WHERE id = #{id}</script>";
        String tableName = tableInfo.getTableName();
        String updateSql = this.prepareUpdateSql();
        String formatSql = String.format("<script>UPDATE %s SET %s WHERE id = #{id}</script>", tableName, updateSql);
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, formatSql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, "updateFieldsById", sqlSource, new NoKeyGenerator(), (String)null, (String)null);
    }

    private String prepareUpdateSql() {
        StringBuilder valueSql = new StringBuilder();
        valueSql.append("<foreach collection=\"map\" item=\"value\" index=\"key\"  separator=\",\">");
        valueSql.append("${key} = #{value}");
        valueSql.append("</foreach>");
        return valueSql.toString();
    }
}
