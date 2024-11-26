package com.project.app.services.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class HttpRequestClient {

    private static Logger logger;

    public HttpRequestClient() {
    }

    public HttpResponse post(String destinationUrl, String requestBody, String authorization, Class<?> clazz) {

        logger = LoggerFactory.getLogger(clazz);

        HttpResponse httpResponse = new HttpResponse();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .hostnameVerifier((hostname, sslSession) -> true)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(destinationUrl)
                .post(body)
                .addHeader("Authorization", authorization )
                .addHeader("Content-Type", "application/json")
                .build();

        int response_code = 500;
        try {

            logger.info("Requesting from "+destinationUrl+":\n"+requestBody);

            httpResponse.setRequestTimestamp(new Date());
            Response response = okHttpClient.newCall(request).execute();
            httpResponse.setResponseTimestamp(new Date());

            response_code = response.code();
            httpResponse.setStatusCode(response_code);

            String response_body = response.body()!=null ? response.body().string() : null;
            logger.info(response.code() + ": Response from endpoint "+destinationUrl+":\n"+response_body);

            httpResponse.setResponseBody(response_body);

            return httpResponse;

        } catch (IOException e) {

            logger.error(response_code + ": Request Error: " + e.getMessage(), e);

            httpResponse.setStatusCode(response_code);
            httpResponse.setResponseTimestamp(new Date());


        }

        return httpResponse;

    }

    public HttpResponse get(String destinationUrl, String authorization, Class<?> clazz) {

        logger = LoggerFactory.getLogger(clazz);

        HttpResponse httpResponse = new HttpResponse();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(destinationUrl)
                .get()
                .addHeader("Authorization", authorization)
                .build();

        Date response_timestamp;
        int response_code = 500;
        try {

            logger.info("Requesting from "+destinationUrl);

            httpResponse.setRequestTimestamp(new Date());
            Response response = client.newCall(request).execute();
            httpResponse.setResponseTimestamp(new Date());

            response_code = response.code();
            httpResponse.setStatusCode(response_code);

            String response_body = response.body()!=null ? response.body().string() : null;
            logger.info(response_code + ": Response from "+destinationUrl+":\n"+response_body);
            httpResponse.setResponseBody(response_body);

            return httpResponse;

        } catch (IOException e) {

            logger.error(response_code + ": Request Error: " + e.getMessage());

            response_timestamp = new Date();

            httpResponse.setStatusCode(response_code);
            httpResponse.setResponseTimestamp(response_timestamp);

        }

        return httpResponse;

    }



    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HttpResponse {

        private Integer statusCode;
        private String responseBody;
        private Date requestTimestamp;
        private Date responseTimestamp;

    }


}
