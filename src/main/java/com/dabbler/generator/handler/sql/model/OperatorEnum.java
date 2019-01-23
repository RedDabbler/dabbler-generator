package com.dabbler.generator.handler.sql.model;

public enum OperatorEnum {

    EQUAL(1, " = ", "是"),
    IN(2," in ", "包含"),
    BETWEEN_AND(3, " BETWEEN ", "介于"),
    IS_NULL(4, " IS NULL ", "为空"),
    CONTAIN(5, " LIKE ", "包含"),
    NOT_CONTAIN(6, " NOT LIKE ", "不包含"),
    NOT_EQUAL(7, " != ", "不是"),
    IS_NOT_NULL(8, " IS NOT NULL ", "不为空"),
    GREATER_THAN(9, " > ", "大于"),
    LOWER_THAN(10, " < ", "小于"),
    GREATER_EQUAL_THAN(11, " >= ", "大于等于"),
    LOWER_EQUALTHAN(12, " <= ", "小于等于"),

    NOT_IN(13," not in ","不包含");


    private int id;
    private String operator;
    private String label;

    OperatorEnum(int id, String operator, String label) {
        this.id = id;
        this.operator = operator;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getOperator() {
        return operator;
    }

    public String getLabel() {
        return label;
    }

}
