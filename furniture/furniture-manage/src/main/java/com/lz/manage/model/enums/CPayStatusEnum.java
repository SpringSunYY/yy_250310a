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
 * Description: CPayStatusEnum
 * 支付状态枚举
 * Version: 1.0
 */
public enum CPayStatusEnum {

    PAY_STATUS_0("未付", "0"),
    PAY_STATUS_1("已付", "1");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, CPayStatusEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (CPayStatusEnum enumValue : CPayStatusEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    CPayStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(CPayStatusEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<CPayStatusEnum> getEnumByValue(String value) {
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