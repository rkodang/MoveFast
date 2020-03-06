package com.gundom.Controller.SysController;

import com.gundom.Entity.SysMenu;
import com.gundom.Service.SysMenuService;
import com.gundom.VO.JsonResult;
import com.gundom.VO.Node;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        JsonResult jsonResult4Menu = new JsonResult();
        List<Map<String, Object>> listobjects = sysMenuService.findObjects();
        jsonResult4Menu.setData(listobjects);
        return jsonResult4Menu;
    }

    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id){
        int rowsChange = sysMenuService.deleteObject(id);
        System.out.println("执行了删除操作,并删除了:"+rowsChange);
        JsonResult deleteObjects=new JsonResult();
        deleteObjects.setMessage("删除已执行");
        return deleteObjects;
    }

    @RequestMapping("doFindZtreeMenuNodes")
    @ResponseBody
    public JsonResult doFindeZtreeMenuNodes(){
        List<Node> ztreeMenuNodes = sysMenuService.findZtreeMenuNodes();
        JsonResult findZtreeNodes = new JsonResult();
        findZtreeNodes.setData(ztreeMenuNodes);
        return findZtreeNodes;
    }

    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysMenu entity){
         sysMenuService.saveObject(entity);
        JsonResult jsonResult4Save = new JsonResult();
        jsonResult4Save.setMessage("保存成功");
        return jsonResult4Save;
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysMenu entity){
        sysMenuService.updateObject(entity);
        JsonResult jsonResult4Update = new JsonResult();
        jsonResult4Update.setMessage("修改成功");
        return jsonResult4Update;
    }

}
