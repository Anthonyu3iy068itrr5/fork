package com.alibaba.dbhub.server.web.api.controller.rdb;

import java.util.List;

import com.alibaba.dbhub.server.domain.support.model.ExecuteResult;
import com.alibaba.dbhub.server.domain.support.model.Table;
import com.alibaba.dbhub.server.domain.support.param.table.TablePageQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableQueryParam;
import com.alibaba.dbhub.server.domain.support.param.table.TableSelector;
import com.alibaba.dbhub.server.domain.api.param.DataSourceExecuteParam;
import com.alibaba.dbhub.server.domain.api.service.DataSourceCoreService;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ActionResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.DataResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.ListResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.PageResult;
import com.alibaba.dbhub.server.tools.base.wrapper.result.web.WebPageResult;
import com.alibaba.dbhub.server.web.api.aspect.BusinessExceptionAspect;
import com.alibaba.dbhub.server.web.api.controller.rdb.converter.RdbDataConverter;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableBriefQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableDeleteRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableDetailQueryRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableManageRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.request.TableSqlExportRequest;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.ExecuteResultVO;
import com.alibaba.dbhub.server.web.api.controller.rdb.vo.TableVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mysql表运维类
 *
 * @author moji
 * @version MysqlTableManageController.java, v 0.1 2022年09月16日 17:41 moji Exp $
 * @date 2022/09/16
 */
@BusinessExceptionAspect
@RequestMapping("/api/rdb/table")
@RestController
public class RdbTableManageController {

    @Autowired
    private DataSourceCoreService dataSourceCoreService;

    @Autowired
    private RdbDataConverter rdbDataConverter;

    /**
     * 查询当前DB下的表列表
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public WebPageResult<TableVO> list(TableBriefQueryRequest request) {
        TablePageQueryParam queryParam = rdbDataConverter.tablePageRequest2param(request);
        TableSelector tableSelector = new TableSelector();
        tableSelector.setColumnList(true);
        tableSelector.setIndexList(true);
        PageResult<Table> tableDTOPageResult = dataSourceCoreService.pageQuery(queryParam, tableSelector);
        List<TableVO> tableVOS = rdbDataConverter.tableDto2vo(tableDTOPageResult.getData());
        return WebPageResult.of(tableVOS, tableDTOPageResult.getTotal(), request.getPageNo(), request.getPageSize());
    }

    /**
     * 导出建表语句
     *
     * @param request
     * @return
     */
    @GetMapping("/export")
    public DataResult<String> export(TableSqlExportRequest request) {
        return null;
    }

    /**
     * 获取表下列和索引等信息
     *
     * @param request
     * @return
     */
    @GetMapping("/query")
    public DataResult<TableVO> query(TableDetailQueryRequest request) {
        TableQueryParam queryParam = rdbDataConverter.tableRequest2param(request);
        TableSelector tableSelector = new TableSelector();
        tableSelector.setColumnList(true);
        tableSelector.setIndexList(true);
        DataResult<Table> tableDTODataResult = dataSourceCoreService.query(queryParam, tableSelector);
        TableVO tableVO = rdbDataConverter.tableDto2vo(tableDTODataResult.getData());
        return DataResult.of(tableVO);
    }

    /**
     * 增删改等表运维
     *
     * @param request
     * @return
     */
    @PutMapping("/manage")
    public ListResult<ExecuteResultVO> manage(@RequestBody TableManageRequest request) {
        DataSourceExecuteParam param = rdbDataConverter.tableManageRequest2param(request);
        ListResult<ExecuteResult> resultDTOListResult = dataSourceCoreService.execute(param);
        List<ExecuteResultVO> resultVOS = rdbDataConverter.dto2vo(resultDTOListResult.getData());
        return ListResult.of(resultVOS);
    }

    /**
     * 删除表
     *
     * @param request
     * @return
     */
    @DeleteMapping("/delete")
    public ActionResult delete(@RequestBody TableDeleteRequest request) {
        return null;
    }
}
