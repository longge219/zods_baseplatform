package com.zods.mysql.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageResult<T> implements Serializable, Cloneable {

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private int totalRecords;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 数据集
     */
    private List<T> items = new ArrayList<>();

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 是否有上一页
     */
    private boolean hasPrevious;

    public PageResult() {
    }

    /**
     * 构造分页结果
     *
     * @param pageSize    每页条数
     * @param totalRecord 总记录数
     * @param currentPage 当前第几页
     * @param items       数据集合
     */
    public PageResult(int pageSize, int totalRecord, int currentPage, List<T> items) {
        setPageSize(pageSize);
        this.totalRecords = totalRecord;
        this.totalPages = getTotalPages();
        setCurrentPage(currentPage);
        this.items = items;
    }

    /**
     * 构造分页结果
     *
     * @param pageSize    每页条数
     * @param totalRecord 总记录数
     * @param currentPage 当前第几页
     * @param items       数据集合
     */
    public PageResult(int pageSize, Long totalRecord, int currentPage, List<T> items) {
        setPageSize(pageSize);
        this.totalRecords = totalRecord.intValue();
        this.totalPages = getTotalPages();
        setCurrentPage(currentPage);
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPageSize() {
        return this.pageSize > 0 ? this.pageSize : 10;
    }

    public void setPageSize(int pageSize) {
        // 默认10条
        this.pageSize = pageSize > 0 ? pageSize : 10;
    }

    public int getTotalRecords() {
        return this.totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords >= 0 ? totalRecords : 0;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int page) {
        if (page == 0 || page < 0) {
            this.currentPage = 1;
            return;
        }
        if (page >= totalPages) {
            this.currentPage = totalPages;
            return;
        }
        this.currentPage = page;

    }

    public int getTotalPages() {
        int total = this.totalRecords % this.pageSize;
        if (total > 0) {
            this.totalPages = this.totalRecords / this.pageSize + 1;
            return totalPages;
        }
        this.totalPages = this.totalRecords / this.pageSize;
        return totalPages;
    }

    public boolean hasNext() {
        this.hasNext = currentPage != totalPages;
        return this.hasNext;
    }

    public boolean hasPrevious() {
        this.hasPrevious = currentPage != 1;
        return this.hasPrevious;
    }
}
