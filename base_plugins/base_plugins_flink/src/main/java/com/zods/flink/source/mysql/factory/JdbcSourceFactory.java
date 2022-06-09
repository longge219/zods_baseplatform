package com.zods.flink.source.mysql.factory;
import com.zods.flink.utils.JdbcResultSetToDataUtil;
import com.zods.flink.utils.JdbcUtil;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.springframework.util.CollectionUtils;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description jdbc-source创建工厂
 * @createDate 2022-06-09
 */
public class JdbcSourceFactory<T> extends RichSourceFunction<T> {

    private PreparedStatement ps;

    private Connection connection;

    private String tableName;

    public JdbcSourceFactory build(String tableName) {
        return new JdbcSourceFactory(tableName);
    }

    public JdbcSourceFactory(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        connection = JdbcUtil.getConnection(JdbcUtil.getDbConfig().getDriverClassName(),
                JdbcUtil.getDbConfig().getUrl(),
                JdbcUtil.getDbConfig().getUserName(),
                JdbcUtil.getDbConfig().getPassWord());
        String sql = "select * from Student;";
        ps = this.connection.prepareStatement(sql);
    }

    /**
     * 程序执行完毕关闭连接和释放资源
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
        if (connection != null) {
            connection.close();
        }
        if (ps != null) {
            ps.close();
        }
    }

    /**
     * DataStream 调用一次 run() 方法用来获取数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void run(SourceContext<T> ctx) throws Exception {
        ResultSet resultSet = ps.executeQuery();
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        List<T> resultList = JdbcResultSetToDataUtil.getDataByClassFiled(resultSet, clazz);
        if (!CollectionUtils.isEmpty(resultList)) {
            resultList.parallelStream().forEach(data -> {
                ctx.collect(data);
            });
        }
    }

    @Override
    public void cancel() {

    }

}
