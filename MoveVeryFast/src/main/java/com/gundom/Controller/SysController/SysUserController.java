package com.gundom.Controller.SysController;

import com.gundom.Entity.SysUser;
import com.gundom.Service.SysUserService;
import com.gundom.VO.JsonResult;
import com.gundom.VO.PageObject;
import com.gundom.VO.SysUserDeptVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("doLogin")
    public JsonResult doLogin(String username,String password,boolean isRememberMe){
        //1.封装用户信息
        UsernamePasswordToken token=new UsernamePasswordToken();
        token.setUsername(username);
        token.setPassword(password.toCharArray());
        JsonResult jsonResult=new JsonResult();
        if (isRememberMe) {
            token.setRememberMe(true);
        }
        //2.提交用户信息(借助Subject对象)
        //获取Subject对象信息
        Subject subject= SecurityUtils.getSubject();
        subject.login(token);//提交用户信息进行认证

        jsonResult.setMessage("登陆成功");
        return jsonResult;
    }

    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){

        PageObject<SysUserDeptVo> pageObject=
                sysUserService.findPageObjects(username,
                        pageCurrent);
        return new JsonResult(pageObject);
    }

    @RequestMapping("doValidById")
    public JsonResult doValidById(Integer id, Integer valid){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        sysUserService.validById(id, valid, sysUser.getUsername());//"admin"用户将来是登陆用户
        return new JsonResult();
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(
            SysUser entity,
            Integer[] roleIds){
        sysUserService.saveObject(entity,roleIds);
        return new JsonResult("save ok");
    }

    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(
            Integer id){
        Map<String,Object> map=
                sysUserService.findObjectById(id);
        return new JsonResult(map);
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(
            SysUser entity,Integer[] roleIds){
        sysUserService.updateObject(entity,
                roleIds);
        return new JsonResult();
    }

    public JsonResult doLogin(String username, String password, HttpServletRequest request, HttpServletResponse response){
        System.out.println(
                "账号:"+username+",密码:"+password
        );
        HttpSession session = request.getSession();
        session.setAttribute("username",username);

        return new JsonResult();
    }

    @RequestMapping("doUpdatePassword")
    public JsonResult doUpdatePassword(
            String pwd,
            String newPwd,
            String cfgPwd) {
        sysUserService.updatePassword(pwd, newPwd, cfgPwd);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setMessage("密码修改成功");
        return jsonResult;
    }
}
