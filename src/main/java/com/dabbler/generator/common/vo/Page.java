package com.dabbler.generator.common.vo;
import com.google.common.base.Preconditions;
import java.util.List;
/**
 * @author RedDabbler
 * @create 2019-01-31 10:59
 **/
public class Page<T> {
    // 默认从1开始表示第一页
    private int pageNo = 1;
    private int pageSize =20;
    private int totalSize;
    private int totalPages;
    private List<T> data;


    public Page() {

    }

    public Page(int pageNo, int pageSize) {
        Preconditions.checkArgument(pageSize>0,"pageSize必须大于0");
        Preconditions.checkArgument(pageNo>=1,"pageNo不合法");
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Page(int pageNo, int pageSize,int totalSize) {
        this(pageNo,pageSize);
        Preconditions.checkArgument(totalSize>=0,"totalSize必须大于或等于0");
        this.totalSize = totalSize;
        setTotalPages();
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalSize(final int totalSize) {
        Preconditions.checkArgument(totalSize >= 0,"totalSize不能小于0");
        this.totalSize = totalSize;
    }

    private void setTotalPages() {
        if (totalSize % pageSize > 0) {
            this.totalPages = totalSize / pageSize + 1;
        } else {
            this.totalPages =  totalSize / pageSize;
        }
    }

    public void setData(List<T> data){
        int size = data.size();
        setTotalSize(size);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }
}
