package com.cqf.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 78544 on 2016/12/5.
 */
public class Model {

    private Map attribute = new HashMap<String, Object>();
    public Map getAttribute() {
        return attribute;
    }

    public void setAttribute(String key, Object value) {
        this.attribute.put(key, value);
    }

    @Override
    public String toString() {
        return "Model{" +
                "attribute=" + attribute +
                '}';
    }
}
