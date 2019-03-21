package com.sandu.common.model;

import java.util.HashMap;
import java.util.Map;

public class JsonDataModel<T> extends JsonModel {
    /**
     *
     */
    private static final long serialVersionUID = -2050592525058467139L;

    private T data; // the data returned

    private Map<String, Object> extra; // // the extra data returned


    private JsonDataModel(String message) {
        super(message);
    }

    private JsonDataModel(T data) {
        super(true);
        this.data = data;
    }


    public T getData() {
        return data;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public void putExtra(String key, Object value) {
        if (extra == null) {
            extra = new HashMap<String, Object>();
        }
        extra.put(key, value);
    }

    /*****************************************************************************/
  /* Helper methods to construct JsonModel */

    /*****************************************************************************/

    public static <T> JsonDataModel<T> createByData(T data) {
        return new JsonDataModel<T>(data);
    }

    public static JsonDataModel<?> createFailedJsonModel(String message) {
        return new JsonDataModel<Object>(message);
    }
}
