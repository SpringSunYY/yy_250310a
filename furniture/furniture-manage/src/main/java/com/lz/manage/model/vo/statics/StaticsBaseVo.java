package com.lz.manage.model.vo.statics;

import lombok.Data;

/**
 * Project: furniture
 * Package: com.lz.manage.model.vo.statics
 * Author: YY
 * CreateTime: 2025-03-13  10:34
 * Description: StaticsBaseVo
 * Version: 1.0
 */
@Data
public class StaticsBaseVo<T> {
    private String name;
    private T value;
}
