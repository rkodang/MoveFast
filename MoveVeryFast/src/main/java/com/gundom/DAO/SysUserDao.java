package com.gundom.DAO;

import com.gundom.Entity.SysUser;
import com.gundom.VO.SysUserDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserDao {
    @Select("select * from sys_users where username = #{username}")
    SysUser findUserByUserName(String username);

    List<SysUserDeptVo> findPageObjects(
            @Param("username") String username,
            @Param("startIndex")Integer startIndex,
            @Param("pageSize")Integer pageSize);

    int getRowCount(@Param("username") String username);

    int validById(
            @Param("id")Integer id,
            @Param("valid")Integer valid,
            @Param("modifiedUser")String modifiedUser);

    int insertObject(SysUser entity);

    SysUserDeptVo findObjectById(Integer id);

    int updateObject(SysUser entity);

    int updatePassword(@Param("password")String password,@Param("salt")String salt,@Param("id")Integer id);
}
