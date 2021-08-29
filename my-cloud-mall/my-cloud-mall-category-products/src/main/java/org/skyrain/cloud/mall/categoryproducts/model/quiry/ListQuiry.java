package org.skyrain.cloud.mall.categoryproducts.model.quiry;

import java.util.ArrayList;
import java.util.List;

public class ListQuiry {
    private String keyword;

    private List<Integer> ids=new ArrayList<>();

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
