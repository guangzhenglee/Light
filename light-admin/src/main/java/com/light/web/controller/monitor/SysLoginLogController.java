package com.light.web.controller.monitor;

import com.light.system.service.ISysLoginLogService;
import com.light.web.controller.common.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统访问记录
 * author:ligz
 */
@Controller
@RequestMapping("/monitor/loginLog")
public class SysLoginLogController extends CommonController {
    @Autowired
    private ISysLoginLogService loginLogService;


}
