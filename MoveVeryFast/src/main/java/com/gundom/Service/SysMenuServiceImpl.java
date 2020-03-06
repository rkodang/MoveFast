package com.gundom.Service;

import com.gundom.Annoation.RequiredLog;
import com.gundom.DAO.SysMenuDao;
import com.gundom.DAO.SysRoleMenuDao;
import com.gundom.Entity.SysMenu;
import com.gundom.Exception.ServiceException;
import com.gundom.VO.Node;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @RequiredLog("菜单查询")
    @Override
    public List<Map<String, Object>> findObjects() {
        log.info("start----");
        List<Map<String, Object>> listObjects = sysMenuDao.findObjects();
        if (listObjects == null || listObjects.size()==0) {
            throw new ServiceException("没有相应的菜单信息");
        }

        for (Map<String, Object> listObject : listObjects) {
            System.out.println("-----------list->Map----------");
            System.out.println(listObject);
            System.out.println("-----------Map->Set-----------");
            System.out.println(listObject.keySet());
            System.out.println();
        }
        log.info("end--------");
        return listObjects;
    }

    @Transactional
    @Override
    public int deleteObject(Integer id) {
        if (id ==null || id<=0) {
            throw new IllegalArgumentException("请先选择");
        }

        int count=sysMenuDao.getChildCount(id);
        if (count>0) {
            throw new IllegalArgumentException("请先删除子菜单");
        }

        int rows=sysMenuDao.deleteObject(id);
        if (rows==0) {
            throw new ServiceException("此菜单可能已经被删除");
        }

        sysRoleMenuDao.deleteObjectsByMenuId(id);
        return rows;

    }

    @Override
    public List<Node> findZtreeMenuNodes() {
        List<Node> ztreeMenuNodes = sysMenuDao.findZtreeMenuNodes();
        for (Node ztreeMenuNode : ztreeMenuNodes) {
            System.out.println(ztreeMenuNode);
        }
        return ztreeMenuNodes;
    }

    @RequiresPermissions("sys:menu:add")
    @Override
    public int saveObject(SysMenu entity) {
        if (entity==null) {
            throw new IllegalArgumentException("保存对象不能为空");
        }
        if(StringUtils.isEmpty(entity.getName())){
            throw new IllegalArgumentException("菜单名不能为空");
        }
        int rows;
        try {
            rows = sysMenuDao.insertObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("保存失败");
        }
        return rows;
    }

    @RequiresPermissions("sys:menu:update")
    @Override
    public int updateObject(SysMenu entity) {
        if (entity==null) {
            throw new IllegalArgumentException("修改对象不能为空");
        }

        if(StringUtils.isEmpty(entity.getName())){
            throw new IllegalArgumentException("菜单名不能为空");
        }

        int rows;
        rows= sysMenuDao.updateObject(entity);
        if (rows==0) {
            throw new ServiceException("记录可能不存在");
        }
        return rows;
    }
}
