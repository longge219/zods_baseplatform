package com.zods.plugins.db.annotation.resolver.mask;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.zods.plugins.db.annotation.Mask;
import com.zods.plugins.db.utils.MaskUtils;
import java.io.IOException;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class MaskJsonSerialize extends JsonSerializer<String> implements ContextualSerializer {

    private Mask mask;

    public MaskJsonSerialize() {
    }

    public MaskJsonSerialize(Mask mask) {
        this.mask = mask;
    }

    /**序列化*/
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String result = value;
        if (this.mask != null) {
            MaskEnum type = this.mask.type();
            if (type != MaskEnum.COMMON) {
                result = MaskUtils.mask(value, type.getPattern(), type.getReplacement());
            } else {
                String pattern = type.getPattern();
                int left = this.mask.left();
                int right = this.mask.right();
                String patternFormat;
                if (left + right >= value.length()) {
                    patternFormat = "(\\w{1})\\w+(\\w{1})";
                } else {
                    patternFormat = String.format(pattern, this.mask.left(), this.mask.right());
                }
                result = MaskUtils.mask(value, patternFormat, type.getReplacement());
            }
        }
        gen.writeString(result);
    }

    /**创建运行环境*/
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            this.mask = (Mask)property.getAnnotation(Mask.class);
            return (JsonSerializer)(this.mask != null ? new MaskJsonSerialize(this.mask) : prov.findValueSerializer(property.getType(), property));
        } else {
            return NullSerializer.instance;
        }
    }
}
