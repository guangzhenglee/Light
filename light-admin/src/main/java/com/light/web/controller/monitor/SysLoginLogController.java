package com.light.web.controller.monitor;

import com.light.system.service.ISysLoginLogService;
import com.light.web.controller.common.CommonController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统访问记录
 * author:ligz
 */
@Controller
@RequestMapping("/monitor/loginLog")
public class SysLoginLogController {
    @Autowired
    private ISysLoginLogService loginLogService;

    @RequiresPermissions("monitor:loginlog:view")
    @GetMapping
    public String loginLog() {
        String prefix = "monitor/loginLog";
        return prefix + "/loginLog";
    }

}
