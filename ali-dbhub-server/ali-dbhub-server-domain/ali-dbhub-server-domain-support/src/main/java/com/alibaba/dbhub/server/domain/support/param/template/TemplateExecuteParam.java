package com.alibaba.dbhub.server.domain.support.param.template;

import javax.validation.constraints.NotNull;

import com.alibaba.dbhub.server.tools.base.constant.EasyToolsConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 执行参数
 *
 * @author Jiaju Zhuang
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateExecuteParam {
    /**
     * 对应数据库存储的来源id
     */
    @NotNull
    private Long dataSourceId;

    /**
     * 控制台id
     */
    @NotNull
    private Long consoleId;

    /**
     * 对应的连接数据库名称
     * 支持多个database的数据库会调用use xx;来切换来数据库
     */
    @NotNull
    private String databaseName;

    /**
     * sql语句
     * 必须是单个语句
     */
    private String sql;

    /**
     * 是否返回总数 默认不返回
     */
    private Boolean total;

    /**
     * 分页编码
     * 只有查询语句分页才有效
     * 不传默认1
     */
    private Integer pageNo;

    /**
     * 分页大小
     * 只有查询语句分页才有效
     * 不传默认500
     *
     * @see EasyToolsConstant#MAX_PAGE_SIZE
     */
    private Integer pageSize;
}
