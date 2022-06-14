package com.zods.plugins.zods.curd.mapper.methods;


import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class UpdateFieldsBatchById extends AbstractMethod {
    public UpdateFieldsBatchById() {
    }

    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "<script>UPDATE %s SET %s WHERE id in %s</script>";
        String tableName = tableInfo.getTableName();
        String updateSql = this.prepareUpdateSql();
        String whereSql = this.prepareWhereSql();
        String formatSql = String.format("<script>UPDATE %s SET %s WHERE id in %s</script>", tableName, updateSql, whereSql);
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, formatSql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, "updateFieldsBatchById", sqlSource, new NoKeyGenerator(), (String)null, (String)null);
    }

    private String prepareUpdateSql() {
        StringBuilder valueSql = new StringBuilder();
        valueSql.append("<foreach collection=\"map\" item=\"value\" index=\"key\"  separator=\",\">");
        valueSql.append("${key} = #{value}");
        valueSql.append("</foreach>");
        return valueSql.toString();
    }

    private String prepareWhereSql() {
        StringBuilder valueSql = new StringBuilder();
        valueSql.append("<foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">");
        valueSql.append("#{id}");
        valueSql.append("</foreach>");
        return valueSql.toString();
    }
}
