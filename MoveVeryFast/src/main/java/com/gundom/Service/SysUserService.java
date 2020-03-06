package com.gundom.Service;

import com.gundom.Entity.SysUser;
import com.gundom.VO.PageObject;
import com.gundom.VO.SysUserDeptVo;

import java.util.Map;

public interface SysUserService  {

    PageObject<SysUserDeptVo> findPageObjects(
            String username,
            Integer pageCurrent);

    int validById(Integer id,Integer valid,String modifiedUser);

    int saveObject(SysUser entity, Integer[] roleIds);

    Map<String, Object> findObjectById(
            Integer userId);

    int updateObject(SysUser entity,Integer[] roleIds);

    int updatePassword(String password,String newPassword,String cfgPassword);
}
