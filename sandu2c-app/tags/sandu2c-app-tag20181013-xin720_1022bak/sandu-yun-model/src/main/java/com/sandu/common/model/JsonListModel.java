package com.sandu.common.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonListModel<T> extends JsonModel {

    /**
     *
     */
    private static final long serialVersionUID = 3093934032181772436L;
    private List<T> data;
    private Map<String, Object> extra; // the extra data returned
    private PageModel pageModel;


    private JsonListModel(String message) {
        super(message);
    }

    private JsonListModel(List<T> data, PageModel model) {
        super(true);
        this.pageModel = model;
        this.data = data;
    }

    public List<T> getData() {
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

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static <T> JsonListModel<T> createByListData(List<T> data, PageModel model) {
        return new JsonListModel<T>(data, model);
    }

    public static <T> JsonListModel<T> createFailedJsonListData(String message) {
        return new JsonListModel<T>(message);
    }

}
