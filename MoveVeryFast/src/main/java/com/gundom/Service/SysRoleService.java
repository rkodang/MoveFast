package com.gundom.Service;

import com.gundom.Entity.SysRole;
import com.gundom.VO.CheckBox;
import com.gundom.VO.PageObject;
import com.gundom.VO.SysRoleMenuVo;

import java.util.List;

public interface SysRoleService {
    PageObject findPageObjects(String name,Integer pageCurrent);

    int deleteObject(Integer id);

    int saveObject(SysRole Entity,Integer[] menuIds);

    SysRoleMenuVo findObjectById(Integer id) ;

    int updateObject(SysRole entity,Integer[] menuIds);

    List<CheckBox> findObjects();

}
