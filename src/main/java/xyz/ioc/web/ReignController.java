package xyz.ioc.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ReignController {


    @Value("${twitter.api.key}")
    private String twitterKey;

    @Value("${twitter.api.secret.key}")
    private String twitterSecretKey;

    @Value("${twitter.auth.token}")
    private String twitterToken;

    @Value("${twitter.auth.secret.token}")
    private String twitterSecretToken;

    @Value("${twitter.bearer.token}")
    private String twitterBearerToken;

    @RequestMapping(value = "/twitter/auth", method = RequestMethod.GET)
    public void auth(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {

        resp.setStatus(HttpServletResponse.SC_FOUND);
        resp.setHeader("authorization", "Bearer " + twitterBearerToken);
    }
}