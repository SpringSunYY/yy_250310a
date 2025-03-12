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
 * Description: DemandStatusEnum
 * 需求状态枚举
 * Version: 1.0
 */
public enum DemandStatusEnum {

    DEMAND_STATUS_0("已发布", "0"),
    DEMAND_STATUS_1("进行中", "1"),
    DEMAND_STATUS_2("已完成", "2");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, DemandStatusEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (DemandStatusEnum enumValue : DemandStatusEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    DemandStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(DemandStatusEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<DemandStatusEnum> getEnumByValue(String value) {
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