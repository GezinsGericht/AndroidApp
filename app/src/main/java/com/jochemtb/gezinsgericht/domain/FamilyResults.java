package com.jochemtb.gezinsgericht.domain;

import java.io.Serializable;
import java.util.Map;

public class FamilyResults implements Serializable {

    private Map<User, Score> resultMap;

    public FamilyResults(Map<User, Score> resultMap) {
        this.resultMap = resultMap;
    }

    public Map<User, Score> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<User, Score> resultMap) {
        this.resultMap = resultMap;
    }
}
