package com.lz.manage.model.vo.statics;

import lombok.Data;

import java.util.List;

/**
 * Project: furniture
 * Package: com.lz.manage.model.vo.statics
 * Author: YY
 * CreateTime: 2025-03-13  10:34
 * Description: StaticsBaseVo
 * Version: 1.0
 */
@Data
public class StaticsPieVo<T> {
    private List<String> names;
    private List<T> values;
}
