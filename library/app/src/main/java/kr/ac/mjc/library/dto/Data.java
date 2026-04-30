package kr.ac.mjc.library.dto;

import java.util.ArrayList;

public class Data {
    private int totalCount;
    private ArrayList<Notice> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<Notice> getList() {
        return list;
    }

    public void setList(ArrayList<Notice> list) {
        this.list = list;
    }
}
