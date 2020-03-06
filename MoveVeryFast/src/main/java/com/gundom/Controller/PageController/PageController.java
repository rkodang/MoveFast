package com.gundom.Controller.PageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
    /**
     * @return 返回启动
     */
    @RequestMapping("doIndexUI")
    public String doIndexUI(){
        return "starter";
    }

    /**
     * @return 返回日志管理页面
     */
//    @RequestMapping("log/log_list")
//    public String doLogUI(){
//
//        return "sys/log_list";
//    }

    /**
     * @return 加载pageUI
     */
    @RequestMapping("doPageUI")
    public String doPageUI(){
        return "common/page";
    }
    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }


    @RequestMapping("{module}/{moduleUI}")
    public String doPageUIs(@PathVariable String moduleUI){
        return "sys/"+moduleUI;
    }

}
