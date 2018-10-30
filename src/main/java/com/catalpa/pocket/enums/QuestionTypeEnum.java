package com.catalpa.pocket.enums;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
public enum QuestionTypeEnum {
    SINGLE_CHOICE(1, "单选题"),
    MULTIPLE_CHOICE(2, "多选题"),
    JUDGMENT(3, "判断题"),
    BLANK_FILL(4, "填空题");

    private Integer type;
    private String desc;

    QuestionTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static QuestionTypeEnum getByType(Integer type) {
        for (QuestionTypeEnum examLevelEnum : values()) {
            if (type.equals(examLevelEnum.type)) {
                return examLevelEnum;
            }
        }
        return SINGLE_CHOICE;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
