package de.schramm.royalbash.infrastructure.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {

    @RequestMapping("/api")
    public String swagger() {
        return "redirect:swagger-ui.html";
    }
}
