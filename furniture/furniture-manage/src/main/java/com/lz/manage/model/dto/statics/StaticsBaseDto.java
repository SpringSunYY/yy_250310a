package com.lz.manage.model.dto.statics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Project: furniture
 * Package: com.lz.manage.model.dto.statics
 * Author: YY
 * CreateTime: 2025-03-13  10:56
 * Description: StaticsBaseDto
 * Version: 1.0
 */
@Data
public class StaticsBaseDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
