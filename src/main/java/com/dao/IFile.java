package com.dao;

import com.vo.MFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IFile {
    @Select("select * from files")
    public List<MFile> findAll();

    @Select("select * from files where rpath=#{rpath}")
    public MFile findByRpath(@Param("rpath") String rpath);

    @Insert("insert into files(oriname,rpath,size,time) values (#{oriname},#{rpath},#{size},#{time})")
    public void insert(MFile mFile);

    @Delete("delete from files where rpath=#{rpath}")
    public void delete(@Param("rpath") String rpath);
}
