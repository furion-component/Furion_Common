package com.huibur.furion.common.constant;

public enum DeleteFlagEnum {
    NORMAL(0, "正常"),DELETE(1, "已删除"), FROZEN(2, "冻结"), DISABLED(3, "禁用");

    private int value;
    private String label;

    private DeleteFlagEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }
}
