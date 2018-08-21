package com.java.crawler.javaCrawler.domain;

import java.util.List;

public class ExportDto {
    private String name;
    private List<MoveInfoDto> valueList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MoveInfoDto> getValueList() {
        return valueList;
    }

    public void setValueList(List<MoveInfoDto> valueList) {
        this.valueList = valueList;
    }
}
