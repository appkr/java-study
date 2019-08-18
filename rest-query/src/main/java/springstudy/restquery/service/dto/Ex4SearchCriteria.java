package springstudy.restquery.service.dto;

import springstudy.restquery.infra.SearchOperation;

public class Ex4SearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;

    public Ex4SearchCriteria(String key, SearchOperation operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }
}
