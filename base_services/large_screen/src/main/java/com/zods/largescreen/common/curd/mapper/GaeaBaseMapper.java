package com.zods.largescreen.common.curd.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zods.largescreen.common.curd.entity.BaseEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * @description Dao基础接口
 * @author jianglong
 * @date 2022-06-14
 **/
public interface GaeaBaseMapper<T extends BaseEntity> extends BaseMapper<T> {

    /**批量添加*/
    int insertBatch(@Param("list") List<T> var1);

    /**根据ID更新属性*/
    int updateFieldsById(@Param("map") Map<String, Object> var1, @Param("id") Long var2);

    /**根据ID集合批量更新*/
    int updateFieldsBatchById(@Param("map") Map<String, Object> var1, @Param("ids") List<Long> var2);

    /**批量更新*/
    int updateFieldsBatch(@Param("map") Map<String, Object> var1, @Param("list") List<T> var2);
}
