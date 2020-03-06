package com.gundom.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;


@Controller
@RequestMapping("/goods/")
public class GoodsController {
    @RequestMapping("doGoodsUI")
    public String doGoodsUI(){

        return "Goods";
    }
    @ResponseBody
    @RequestMapping("dgUI")
    public String doGoodUI(Integer...ids){
        System.out.println("init"+ Arrays.toString(ids));
        return "hihi";
    }


}
