package com.gundom.Service;
import com.gundom.Entity.SysDept;
import com.gundom.VO.Node;

import java.util.List;
import java.util.Map;



public interface SysDeptService {
	int saveObject(SysDept entity);
	int updateObject(SysDept entity);
	
	List<Node> findZTreeNodes();
	List<Map<String,Object>> findObjects();
	
	int deleteObject(Integer id);
}
