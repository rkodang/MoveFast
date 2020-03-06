package com.gundom.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuDao {

    int deleteObjectsByMenuId(Integer menuId);

    int deleteObjectsByRoleId(Integer roleId);

    int insertObjects(
            @Param("roleId")Integer roleId,
            @Param("menuIds")Integer[] menuIds);

    int findMenuIdsByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleIds(@Param("roleIds") Integer[] roleIds);

}
