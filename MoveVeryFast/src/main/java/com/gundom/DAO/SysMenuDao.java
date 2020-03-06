package com.gundom.DAO;

import com.gundom.Entity.SysMenu;
import com.gundom.VO.Node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface SysMenuDao {
    /**
     * 查询所有菜单以及上级菜单信息
     * @return
     */
    List<Map<String,Object>> findObjects();

    /**
     *统计子菜单
     * @param id
     * @return
     */
    int getChildCount(Integer id);

    int deleteObject(Integer id);

    //菜单表中的所有菜单信息
    List<Node> findZtreeMenuNodes();

    //菜单信息写入数据库
    int insertObject(SysMenu entity);

    //数据菜单更新
    int updateObject(SysMenu entity);

    List<String> findPermissions(@Param("menuIds") Integer[] menuIds);

}
