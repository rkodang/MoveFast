package com.gundom.DAO;

import com.gundom.Entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLogDao {
    /** 分页查询档
     * @param username 查询的条件
     * @param StartIndex
     * @param pageSize
     * @return
     */
    List<SysLog> findPageObjects(@Param("username") String username,
                                 @Param("startIndex") Integer StartIndex,
                                 @Param("pageSize") Integer pageSize);

    /**
     * @param username 查询条件
     * @return 查询到的记录总数
     */
    int getRowCount(@Param("username") String username);

    int deleteObjects(@Param("ids") Integer ...ids);

    int insertObject(SysLog entity);

}
