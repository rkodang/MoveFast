package com.gundom.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleDao {
    int deleteObjectsByRoleId(Integer roleId);

    /**
     * 基于用户获取角色ID
     * @param id
     * @return
     */
    List<Integer> findRoleIdsByUserId(Integer id);
    int insertObjects(
            @Param("userId")Integer userId,
            @Param("roleIds")Integer[]roleIds);

    int deleteObjectsByUserId(Integer userId);
}
