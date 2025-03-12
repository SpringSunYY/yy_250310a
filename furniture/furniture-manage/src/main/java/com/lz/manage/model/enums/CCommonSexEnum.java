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
 * Description: CCommonSexEnum
 * 通用性别枚举
 * Version: 1.0
 */
public enum CCommonSexEnum {

    COMMON_SEX_0("男", "0"),
    COMMON_SEX_1("女", "1");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, CCommonSexEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (CCommonSexEnum enumValue : CCommonSexEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    CCommonSexEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(CCommonSexEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<CCommonSexEnum> getEnumByValue(String value) {
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