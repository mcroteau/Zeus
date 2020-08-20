package xyz.ioc.service;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class StripeService {

    @Value("${stripe.api.key}")
    private String apiKey;

    public Charge charge(double amount, String stripeToken, String email){

        try {

            Stripe.apiKey = apiKey;

            int amountInCents = ((int) amount) * 100;
            System.out.println("amount " + amountInCents + " : " + stripeToken);

            Map<String, Object> chargeParams = new HashMap<String, Object>();
            chargeParams.put("amount", String.valueOf(amountInCents));
            chargeParams.put("currency", "usd");
            chargeParams.put("source", stripeToken);
            chargeParams.put("description", "Donation :" + amount + ":" + email);

            Charge charge = Charge.create(chargeParams);

            return charge;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
