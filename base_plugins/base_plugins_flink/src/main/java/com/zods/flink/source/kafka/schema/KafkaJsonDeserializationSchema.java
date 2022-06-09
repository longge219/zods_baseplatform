package com.zods.flink.source.kafka.schema;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author jianglong
 * @version 1.0
 * @Description kafka传输对象序列化反序列化
 * @createDate 2022-06-09
 */
@Slf4j
public class KafkaJsonDeserializationSchema<T> implements DeserializationSchema<T>, SerializationSchema<T> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final byte[] EMPTY_ARRAY = new byte[0];

    private static final long serialVersionUID = 9057827142312362244L;

    private Class<T> clazz;

    public KafkaJsonDeserializationSchema(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T deserialize(byte[] bytes) throws IOException {
        try {
            return JSON.parseObject(new String(bytes, DEFAULT_CHARSET), clazz);
        } catch (Throwable t) {
            log.error("json parse error", t.getCause());
        }
        return null;
    }

    @Override
    public boolean isEndOfStream(T t) {
        return false;
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeExtractor.getForClass(clazz);
    }


    @Override
    public byte[] serialize(T element) {
        if (null == element) {
            return EMPTY_ARRAY;
        }
        try {
            return JSON.toJSONString(element).getBytes(DEFAULT_CHARSET);
        } catch (Exception e) {
            return EMPTY_ARRAY;
        }
    }
}
