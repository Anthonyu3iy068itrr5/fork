package com.alibaba.dbhub.server.domain.support.model;

import com.alibaba.dbhub.server.tools.base.enums.YesOrNoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 列信息
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TableColumn {
    /**
     * 列名
     */
    private String name;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列的类型
     * 比如 varchar(100) ,double(10,6)
     */
    private String columnType;

    /**
     * 列的数据类型
     * 比如 varchar ,double
     */
    private String dataType;

    /**
     * 是否可以为空
     *
     * @see YesOrNoEnum
     */
    private String nullable;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否自增
     *
     * @see YesOrNoEnum
     */
    private String autoIncrement;

    /**
     * 注释
     */
    private String comment;
}
