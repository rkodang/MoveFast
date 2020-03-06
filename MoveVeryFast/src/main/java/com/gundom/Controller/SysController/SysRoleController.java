package com.gundom.Controller.SysController;

import com.gundom.Entity.SysRole;
import com.gundom.Service.SysRoleService;
import com.gundom.VO.JsonResult;
import com.gundom.VO.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role/")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;

    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String name,Integer pageCurrent){
        PageObject pageObjects4Role = sysRoleService.findPageObjects(name, pageCurrent);
        return new JsonResult(pageObjects4Role);
    }

    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        sysRoleService.deleteObject(id);
        JsonResult jsonResult4Delete = new JsonResult();
        jsonResult4Delete.setMessage("Delete is Ok");
        return jsonResult4Delete;
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysRole Entity,Integer[] menuIds){
    sysRoleService.saveObject(Entity,menuIds);
        JsonResult jsonResult4Save = new JsonResult();
        jsonResult4Save.setMessage("Save is Ok");
        return jsonResult4Save;
    }

    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(sysRoleService.findObjectById(id));
    }
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysRole entity,
                                     Integer[] menuIds) {
        sysRoleService.updateObject(entity, menuIds);

        return new JsonResult();
    }

    @RequestMapping("doFindRoles")
    public JsonResult doFindObjects(){
        return new JsonResult(sysRoleService.findObjects());
    }

}
