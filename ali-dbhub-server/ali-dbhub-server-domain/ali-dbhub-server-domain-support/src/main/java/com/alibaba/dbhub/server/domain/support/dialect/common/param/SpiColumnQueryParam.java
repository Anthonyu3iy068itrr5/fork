package com.alibaba.dbhub.server.domain.support.dialect.common.param;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 列查询
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SpiColumnQueryParam {
    /**
     * jdbc执行器
     */
    @NotNull
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    /**
     * 对应的连接数据库名称
     */
    @NotNull
    private String databaseName;

    /**
     * 表名字列表
     * 不支持查询全部表的列信息，必须传入表的列表
     */
    @NotEmpty
    private List<String> tableNameList;
}
