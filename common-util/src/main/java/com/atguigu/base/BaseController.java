package com.atguigu.base;

import org.springframework.ui.Model;

/**
 * Date: 2022/5/17
 * Author:George
 * Description:
 */
public class BaseController {
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_ERROR = "common/errorPage";

    public String successPage(Model model,String successMessage) {
        model.addAttribute("messagePage",successMessage);
        return PAGE_SUCCESS;
    }

    public String errorPage(Model model,String errorMessage) {
        model.addAttribute("messagePage",errorMessage);
        return PAGE_ERROR;
    }


}
