package com.vo;

import java.sql.Date;

public class MFile {

    private String oriname;
    private String rpath;
    private long size;
    private Date time;

    public MFile() {
    }

    @Override
    public String toString() {
        return "MFile{" +
                "oriname='" + oriname + '\'' +
                ", rpath='" + rpath + '\'' +
                ", size=" + size +
                ", time=" + time +
                '}';
    }

    public String getOriname() {
        return oriname;
    }

    public void setOriname(String oriname) {
        this.oriname = oriname;
    }

    public String getRpath() {
        return rpath;
    }

    public void setRpath(String rpath) {
        this.rpath = rpath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public MFile(String oriname, String rpath, long size, Date time) {
        this.oriname = oriname;
        this.rpath = rpath;
        this.size = size;
        this.time = time;
    }
}
