package com.gundom.Service;

import com.gundom.DAO.SysLogDao;
import com.gundom.Entity.SysLog;
import com.gundom.Exception.ServiceException;
import com.gundom.VO.PageObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysLogServiceImpl implements  SysLogService {

    @Autowired
    private SysLogDao sysLogDao;


    @Override
    public PageObject<SysLog> findePageObjects(String username, Integer pageCurrent) {
        //1.验证参数是否合法
        //.1.验证pageCurrent的合法性
        //不合法抛出异常 提示
        if(pageCurrent==null||pageCurrent<1){
            throw new IllegalArgumentException("当前页码不正确");
        }

        //2.基于条件查询总记录数
        int rowCount=sysLogDao.getRowCount(username);
        if (rowCount==0) {
            //2.1验证查询结果如果没有结果就抛出自定义的异常
            throw new ServiceException("没有查到对应的记录");
        }

        //3.基于条件查询当前页记录(pageSize)和起始数目(startIndex)
        int pageSize=2;
        int startIndex=(pageCurrent-1)*pageSize;
        List<SysLog> sysLogRecords = sysLogDao.findPageObjects(username, startIndex, pageSize);

        //4.创建分页信息以及对应记录的封装类对象
        PageObject<SysLog> pageObject=new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        pageObject.setPageCount((rowCount-1)/pageSize+1);
        pageObject.setRecords(sysLogRecords);


        //5.返回封装的pageObject
        return pageObject;
    }

    @RequiresPermissions("sys:log:delete")
    @Override
    public int deleteObjects(Integer... ids) {
        //1.判断参数的合法性
        if(ids==null||ids.length==0){
            throw new IllegalArgumentException("至少选择一个来删除");
        }

        int rows;
        try {
            rows = sysLogDao.deleteObjects(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除出问题了");
        }

        if(rows==0){
            throw new ServiceException("没有此记录");
        }
        return rows;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async//异步方式-->ThreadPoolTaskExecutor
    @Override
    public void saveObject(SysLog entity) {
        sysLogDao.insertObject(entity);
    }
}
