package the.nss.boys.blog.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

public abstract class Controller {
    @RequestMapping("/**")
    public RedirectView redirect(){
        RedirectView toRet = new RedirectView("../");
        return toRet;
    }
}
