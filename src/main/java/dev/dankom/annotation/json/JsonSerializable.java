package dev.dankom.annotation.json;

import dev.dankom.type.Token;
import dev.dankom.util.general.StringUtil;
import org.json.simple.JSONAware;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface JsonSerializable extends JSONAware {
    @Override
    default String toJSONString() {
        String out = "{";
        try {
            for (int i = 0; i < fields().size(); i++) {
                Field f = fields().get(i);
                JsonExpose a = f.getAnnotation(JsonExpose.class);
                String name = (a.customName().equalsIgnoreCase("") ? f.getName() : a.customName());
                Object value = f.get(this);
                if (!(f.getType().equals(List.class))) {
                    out += StringUtil.wrap(name, Token.JSON_QUOTE.token()) + ": " + StringUtil.wrap(value, Token.JSON_QUOTE.token());
                } else {
                    out += StringUtil.wrap(name, Token.JSON_QUOTE.token()) + ": [";
                    try {
                        String stringArray = "";
                        List<String> objects = new ArrayList<>();
                        List<Object> l = (List<Object>) f.get(this);
                        for (Object o : l) {
                            if (o instanceof JSONAware) {
                                objects.add(((JSONAware) o).toJSONString());
                            } else {
                                objects.add(o.toString());
                            }
                        }
                        for (int j = 0; j < objects.size(); j++) {
                            stringArray += StringUtil.wrap(objects.get(j), Token.JSON_QUOTE.token());
                            if (j != (objects.size() - 1)) {
                                stringArray += ", ";
                            }
                        }
                        out += stringArray;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    out += "]";
                }
                if (i != (fields().size() - 1)) {
                    out += ", ";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out += "}";
        return out;
    }

    default List<Field> fields() {
        List<Field> fields = new ArrayList<>();
        for (Field f : getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(JsonExpose.class)) {
                fields.add(f);
            }
        }
        return fields;
    }
}
