package com.zods.largescreen.common.curd.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zods.largescreen.common.annotation.AccessKey;
import com.zods.largescreen.common.annotation.log.GaeaAuditLog;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.dto.BaseDTO;
import com.zods.largescreen.common.curd.entity.BaseEntity;
import com.zods.largescreen.common.curd.params.PageParam;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.common.holder.UserContentHolder;
import com.zods.largescreen.common.utils.GaeaBeanUtils;
import com.zods.largescreen.common.utils.GaeaUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description 基础controller
 * @createDate 2022-06-20
 */
public abstract class GaeaBaseController<P extends PageParam, T extends BaseEntity, D extends BaseDTO> extends BaseController {
    public GaeaBaseController() {
    }

    public abstract GaeaBaseService<P, T> getService();

    public abstract T getEntity();

    public abstract D getDTO();

    public List<D> pageResultHandler(List<D> list) {
        return list;
    }

    public D detailResultHandler(D detail) {
        return detail;
    }

    protected D resultDtoHandle(D d) {
        return d;
    }

    @GetMapping({"/pageList"})
    @GaeaAuditLog(pageTitle = "分页查询")
    public ResponseBean pageList(P param) {
        IPage<T> iPage = this.getService().page(param);
        List<T> records = iPage.getRecords();
        List<D> list = GaeaBeanUtils.copyList(records, this.getDTO().getClass());
        this.pageResultHandler(list);
        Page<D> pageDto = new Page();
        pageDto.setCurrent(iPage.getCurrent()).setRecords(list).setPages(iPage.getPages()).setTotal(iPage.getTotal()).setSize(iPage.getSize());
        return this.responseSuccessWithData(pageDto);
    }


    @GetMapping({"/{id}"})
    @AccessKey
    public ResponseBean detail(@PathVariable("id") Long id) {
        this.logger.info("{}根据ID查询服务开始，id为：{}", this.getClass().getSimpleName(), id);
        T result = this.getService().selectOne(id);
        D dto = this.getDTO();
        GaeaBeanUtils.copyAndFormatter(result, dto);
        this.detailResultHandler(dto);
        ResponseBean responseBean = this.responseSuccessWithData(this.resultDtoHandle(dto));
        this.logger.info("{}根据ID查询结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(responseBean));
        return responseBean;
    }



    @PostMapping
    @GaeaAuditLog(pageTitle = "新增")
    public ResponseBean insert(@Validated @RequestBody D dto) {
        this.logger.info("{}新增服务开始，参数：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(dto));
        ResponseBean responseBean = this.responseSuccess();
        T entity = this.getEntity();
        BeanUtils.copyProperties(dto, entity);
        this.getService().insert(entity);
        this.logger.info("{}新增服务结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(responseBean));
        return responseBean;
    }

    @PutMapping
    @GaeaAuditLog(pageTitle = "修改")
    public ResponseBean update(@Validated @RequestBody D dto) {
        String username = UserContentHolder.getContext().getUsername();
        this.logger.info("{}更新服务开始,更新人：{}，参数：{}", new Object[]{this.getClass().getSimpleName(), username, GaeaUtils.toJSONString(dto)});
        T entity = this.getEntity();
        BeanUtils.copyProperties(dto, entity);
        this.getService().update(entity);
        this.logger.info("{}更新服务结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(entity));
        return this.responseSuccess();
    }

    @DeleteMapping({"/{id}"})
    @GaeaAuditLog(pageTitle = "删除")
    public ResponseBean deleteById(@PathVariable("id") Long id) {
        this.logger.info("{}删除服务开始，参数ID：{}", this.getClass().getSimpleName(), id);
        this.getService().deleteById(id);
        this.logger.info("{}删除服务结束", this.getClass().getSimpleName());
        return this.responseSuccess();
    }

    @PostMapping({"/delete/batch"})
    @GaeaAuditLog(pageTitle = "批量删除")
    public ResponseBean deleteBatchIds(@RequestBody List<Serializable> ids) {
        this.logger.info("{}批量删除服务开始，批量参数Ids：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(ids));
        boolean deleteCount = this.getService().deleteByIds(ids);
        ResponseBean responseBean = this.responseSuccessWithData(deleteCount);
        this.logger.info("{}批量删除服务结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(responseBean));
        return responseBean;
    }
}
