package com.dabbler.generator.common.vo;

public class XMLEntity {
    private String root;
    String test;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("XMLEntity{");
        sb.append("root='").append(root).append('\'');
        sb.append(", test='").append(test).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
