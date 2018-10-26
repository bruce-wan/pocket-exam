package com.catalpa.pocket.enums;

/**
 * Created by wanchuan01 on 2018/10/26.
 */
public enum ExamLevelEnum {
    LEVEL_1(1, "{\"digit\":1, \"size\":5, \"amount\":10}"),
    LEVEL_2(2, "{\"digit\":2, \"size\":5, \"amount\":10}");

    private Integer level;
    private String content;

    ExamLevelEnum(Integer level, String content) {
        this.level = level;
        this.content = content;
    }

    public static ExamLevelEnum getByLevel(Integer level) {
        for (ExamLevelEnum examLevelEnum : values()) {
            if (level.equals(examLevelEnum.level)) {
                return examLevelEnum;
            }
        }
        return LEVEL_1;
    }

    public String getContent() {
        return content;
    }
}
