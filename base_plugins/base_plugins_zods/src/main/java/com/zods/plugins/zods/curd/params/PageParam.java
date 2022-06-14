package com.zods.plugins.zods.curd.params;
/**
 * @description 分页查询公共类
 * @author jianglong
 * @date 2022-06-14
 **/
public class PageParam {

    /**页数*/
    private Integer pageNumber = 1;

    /**分页条数*/
    private Integer pageSize = 10;

    /**排序模式*/
    private String order;

    /**检索*/
    private String sort;


    private Integer offset;

    public PageParam() {
    }

    public Integer getOffset() {
        return (this.pageNumber - 1) * this.pageSize;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
