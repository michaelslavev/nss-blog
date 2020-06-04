package the.nss.boys.blog.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Rest controller for error redirection
 *
 */
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public RedirectView handleError() {
        RedirectView toRet = new RedirectView("../");
        return toRet;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
