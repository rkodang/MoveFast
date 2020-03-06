package com.gundom.Service;

import com.gundom.Entity.SysMenu;
import com.gundom.VO.Node;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    List<Map<String,Object>> findObjects();

    int deleteObject(Integer id);

    public List<Node> findZtreeMenuNodes();

    int saveObject(SysMenu entity);

    int updateObject(SysMenu entity);
}
