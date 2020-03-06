package com.gundom.DAO;

import com.gundom.Entity.SysRole;
import com.gundom.VO.CheckBox;
import com.gundom.VO.SysRoleMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleDao {

    /**
     * 按用户名进行分页查询
     * @param name
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<SysRole> findPageObjects(@Param("name") String name,
                                  @Param("startIndex") Integer startIndex,
                                  @Param("pageSize")Integer pageSize);


    int getRowCount(@Param("name")String name);

    int deleteObject(Integer id);

    int insertObject(SysRole entity);

    SysRoleMenuVo findObjectById(Integer id);

    int updateObject(SysRole entity);

    List<CheckBox> findObjects();


}
