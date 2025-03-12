package com.lz.manage.model.enums;

import com.lz.common.utils.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Project: config
 * Description: CTaskStatusEnum
 * 任务状态枚举
 * Version: 1.0
 */
public enum CTaskStatusEnum {

    TASK_STATUS_0("未开始", "0"),
    TASK_STATUS_1("进行中", "1"),
    TASK_STATUS_2("已完成", "2");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, CTaskStatusEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (CTaskStatusEnum enumValue : CTaskStatusEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    CTaskStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(CTaskStatusEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<CTaskStatusEnum> getEnumByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}