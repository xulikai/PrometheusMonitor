package test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test.jmx.Infomation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/test")
public class JmxOut {


    @Resource
    private Infomation im;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public void login(HttpServletRequest request, HttpServletResponse response) {
        im.setLog("11");
        System.out.println("测试");
    }

}
