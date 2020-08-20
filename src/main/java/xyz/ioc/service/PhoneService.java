package xyz.ioc.service;

import com.plivo.api.Plivo;
import com.plivo.api.models.message.MessageCreateResponse;

import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;

public class PhoneService {

    @Value("${plivo.api.key}")
    private String apiKey;

    @Value("${plivo.secret.key}")
    private String secretKey;

    private static final String PLIVO_PHONE = "+18302026537";
    private static final String NOTIFY_PHONE = "+19076879557";

    public boolean validate(String phone){
        try{

            Plivo.init(apiKey, secretKey);
            MessageCreateResponse message = com.plivo.api.models.message.Message.creator(
                    PLIVO_PHONE, Collections.singletonList("+1" + phone), "Zeus : Setup complete ${account.name}")
                    .create();

            System.out.println(message.toString());

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean send(String phones, String notification){
        try{

            Plivo.init(apiKey, secretKey);
            MessageCreateResponse message = com.plivo.api.models.message.Message.creator(
                    PLIVO_PHONE, Collections.singletonList(phones), notification)
                    .create();

            System.out.println("message sent.");

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean support(String notification){
        try{

            System.out.println("keys >> " + apiKey + " : " + secretKey);

            Plivo.init(apiKey, secretKey);
            MessageCreateResponse message = com.plivo.api.models.message.Message.creator(
                    PLIVO_PHONE, Collections.singletonList(NOTIFY_PHONE), notification)
                    .create();

            System.out.println("message sent.");

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}