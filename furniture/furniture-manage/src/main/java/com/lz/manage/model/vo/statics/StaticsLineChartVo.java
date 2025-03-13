package com.lz.manage.model.vo.statics;

import lombok.Data;

import java.util.List;

/**
 * Project: furniture
 * Package: com.lz.manage.model.vo.statics
 * Author: YY
 * CreateTime: 2025-03-13  11:30
 * Description: StaticsLineChartVo
 * Version: 1.0
 */
@Data
public class StaticsLineChartVo<T> {
    private List<String> chartXData;
    private List<chartYDataVo> chartYData;

    @Data
    public class chartYDataVo {
        private String name;
        private List<T> value;
    }
}
