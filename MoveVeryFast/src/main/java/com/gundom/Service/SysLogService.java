package com.gundom.Service;

import com.gundom.Entity.SysLog;
import com.gundom.VO.PageObject;

public interface SysLogService {
    /**
     *
     * @param username 基于条件查询时的参数
     * @param pageCurrent 当前的页码值
     * @return 当前页记录+分页信息
     */
    PageObject<SysLog> findePageObjects(String username,Integer pageCurrent);

    int deleteObjects(Integer ...ids);

    void saveObject(SysLog entity);

}
