package com.shopper.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    @SneakyThrows
    public Boolean sendEmail(String to, String subject, String body) {
        new Thread(() -> {
            // send email to the actual customer
            sendEmailto(to, subject, body);
            if(!to.equals("allowski@gmail.com")) {
                // send email to me, for debug purposes
                try {
                    Thread.sleep(4000);
                    sendEmailto("allowski@gmail.com", "CC: " + subject, body);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }

    @SneakyThrows
    public Boolean sendEmailto(String to, String subject, String body) {

        new Thread(() -> {

            try {

                ObjectMapper mapper = new ObjectMapper();

                HashMap<String, String> requestBody = new HashMap<>();

                requestBody.put("to", to);

                requestBody.put("subject", subject);

                requestBody.put("html", body);

                requestBody.put("company", "Shopper Ecommerce");

                requestBody.put("sender", "Sales");

                OkHttpClient client = new OkHttpClient.Builder()
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .build();

                String payload = mapper.writeValueAsString(requestBody);

                System.out.println("payload: " + payload);

                Request request = new Request.Builder()
                        .url("https://ecommerce-66be.restdb.io/mail")
                        .header("Content-Type", "application/json")
                        .header("x-apikey", "e999bda5d16bc19c9578d8cf3897db4c3dee6")
                        .post(RequestBody.create(payload.getBytes()))
                        .build();

                Response response = client.newCall(request).execute();

                System.out.println("response: " + response.body().string());

            }catch (Exception err){
                err.printStackTrace();
            }

        }).start();

        return true;
    }

}
