package cn.zhangz.common.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
    @Value("${system.web.path:}")
    private String webAppName;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("appName", this.getPath());
    }

    public String getPath() {
        return StringUtils.isEmpty(webAppName) ? "" : "/" + this.webAppName;
    }
}
