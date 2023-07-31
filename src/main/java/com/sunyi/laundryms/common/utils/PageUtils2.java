package com.sunyi.laundryms.common.result.utils;

public class PageUtils2 {
    //默认一页数据的大小
    private int pageSize=1;
    //页码为1
    private int pageNo2=1;
    //总条数
    private int countSum=0;
    //总页数
    private int pageNum=0;
    //是否有下一页
    private boolean hasNext=false;
    //是否有上一页
    private boolean hasPre=false;
    //当前页 查询数据库数据条目
    private Object data;
    private Object data2;

    //当用户设置总条数时重新计算页数是否有前一页 后一页
    private void initParam() {
        this.pageNum = this.countSum % this.pageSize == 0 ? (this.countSum / this.pageSize == 0 ? 1 : this.countSum / this.pageSize) : (this.countSum / this.pageSize) + 1;
        this.hasNext = this.pageNo2 != this.pageNum && this.pageNum > 0;
        this.hasPre = this.pageNo2 != 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo2() {
        return pageNo2;
    }

    public void setPageNo2(int pageNo) {
        this.pageNo2 = pageNo;
    }

    public int getCountSum() {
        return countSum;
    }

    public void setCountSum(int countSum) {
        this.countSum = countSum;
        //重新计算分页信息
        this.initParam();
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData2() {
        return data2;
    }

    public void setData2(Object data2) {
        this.data2 = data2;
    }

}
