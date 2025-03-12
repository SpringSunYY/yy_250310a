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
 * Description: TaskStatusEnum
 * 任务状态枚举
 * Version: 1.0
 */
public enum TaskStatusEnum {

    TASK_STATUS_0("未开始", "0"),
    TASK_STATUS_1("进行中", "1"),
    TASK_STATUS_2("已完成", "2");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, TaskStatusEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (TaskStatusEnum enumValue : TaskStatusEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    TaskStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(TaskStatusEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<TaskStatusEnum> getEnumByValue(String value) {
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