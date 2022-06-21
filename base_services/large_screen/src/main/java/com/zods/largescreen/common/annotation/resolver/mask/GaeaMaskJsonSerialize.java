package com.zods.largescreen.common.annotation.resolver.mask;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.zods.largescreen.common.annotation.GaeaMask;
import com.zods.largescreen.common.utils.GaeaMaskUtils;
import java.io.IOException;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class GaeaMaskJsonSerialize extends JsonSerializer<String> implements ContextualSerializer {
    private GaeaMask gaeaMask;

    public GaeaMaskJsonSerialize() {
    }

    public GaeaMaskJsonSerialize(GaeaMask gaeaMask) {
        this.gaeaMask = gaeaMask;
    }

    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String result = value;
        if (this.gaeaMask != null) {
            MaskEnum type = this.gaeaMask.type();
            if (type != MaskEnum.COMMON) {
                result = GaeaMaskUtils.mask(value, type.getPattern(), type.getReplacement());
            } else {
                String pattern = type.getPattern();
                int left = this.gaeaMask.left();
                int right = this.gaeaMask.right();
                String patternFormat;
                if (left + right >= value.length()) {
                    patternFormat = "(\\w{1})\\w+(\\w{1})";
                } else {
                    patternFormat = String.format(pattern, this.gaeaMask.left(), this.gaeaMask.right());
                }

                result = GaeaMaskUtils.mask(value, patternFormat, type.getReplacement());
            }
        }

        gen.writeString(result);
    }

    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            this.gaeaMask = (GaeaMask)property.getAnnotation(GaeaMask.class);
            return (JsonSerializer)(this.gaeaMask != null ? new GaeaMaskJsonSerialize(this.gaeaMask) : prov.findValueSerializer(property.getType(), property));
        } else {
            return NullSerializer.instance;
        }
    }
}
