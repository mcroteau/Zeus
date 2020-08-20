package xyz.ioc.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.ioc.model.Post;
import xyz.ioc.service.EmailService;
import xyz.ioc.service.StripeService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DonateController {

    private static final Logger log = Logger.getLogger(DonateController.class);

    @Autowired
    private StripeService stripeService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/donate", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String donate(HttpServletRequest request,
                                        final RedirectAttributes redirect,
                                        @RequestParam(value = "amount", required = true) double amount,
                                        @RequestParam(value = "email", required = false) String email,
                                        @RequestParam(value = "stripe-token", required = true) String stripeToken) {

        Map<String, Object> data = new HashMap<String, Object>();
        Gson gson = new Gson();

        Charge charge = stripeService.charge(amount, stripeToken, email);
        if(charge != null){
            String body = "<h1>Zeus</h1>" +
                    "<p>We at Zeus would like to take a second to thank you for your donation. " +
                    "We currently pride ourselves on being ad free! Please enjoy!</p>";

            emailService.send(email, "Thank you for your donation!", body);
            return gson.toJson(charge);
        }else{
            data.put("error", true);
            return gson.toJson(data);
        }
    }

}