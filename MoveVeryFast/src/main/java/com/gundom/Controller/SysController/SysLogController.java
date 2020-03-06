package com.gundom.Controller.SysController;

import com.gundom.Entity.SysLog;
import com.gundom.Service.SysLogService;
import com.gundom.VO.JsonResult;
import com.gundom.VO.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/log/")
public class SysLogController {
    @Autowired
    SysLogService sysLogService;
    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindePageObjects(String username,Integer pageCurrent){
        PageObject<SysLog> pageObject = sysLogService.findePageObjects(username, pageCurrent);
        JsonResult jsonResult4SysLogs = new JsonResult(pageObject);
        return jsonResult4SysLogs;
    }

//    @RequestMapping("test")
//    @ResponseBody
//    public PageObject testData(){
//        PageObject<SysLog> pageObject = sysLogService.findePageObjects("", 1);
//        List<SysLog> records = pageObject.getRecords();
//        for (SysLog record : records) {
//            System.out.println(record);
//
//        }
//        return pageObject;
//    }

    @RequestMapping("doDeleteObjects")
    @ResponseBody
    public JsonResult doDeleteObjects(Integer ...ids){
        sysLogService.deleteObjects(ids);
        JsonResult delete_ok = new JsonResult() ;
        delete_ok.setMessage("delete is OJ8K");
        return delete_ok;
    };


}
