package com.project.app.services.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.configurations.APIProperties;
import com.project.app.services.utils.CsvUtil;
import com.project.app.services.utils.HttpRequestClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReportSvcImpl implements ReportSvc
{
    private final ObjectMapper mapper;
    private final APIProperties apiProperties;
    private final HttpRequestClient httpRequestClient;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    public ReportSvcImpl(ObjectMapper mapper, APIProperties apiProperties, HttpRequestClient httpRequestClient)
    {
        this.mapper = mapper;
        this.apiProperties = apiProperties;
        this.httpRequestClient = httpRequestClient;
    }

    @SneakyThrows
    @Override
    public ResponseEntity<JsonNode> read(JsonNode requestBody)
    {
        HttpRequestClient.HttpResponse httpResponse = getData(requestBody);

        JsonNode responsePayload = mapper.readValue(httpResponse.getResponseBody(), JsonNode.class);
        return ResponseEntity.status(HttpStatus.OK).body(responsePayload);
    }
    @SneakyThrows
    @Override
    public void download(HttpServletRequest request, HttpServletResponse response)
    {
        HttpRequestClient.HttpResponse httpResponse = getData(mapper.createObjectNode());
        JsonNode responsePayload = mapper.readValue(httpResponse.getResponseBody(), JsonNode.class);
        CsvUtil.downloadCsv(responsePayload.get("banks").deepCopy(), response);
    }

    @SneakyThrows
    public HttpRequestClient.HttpResponse getData(JsonNode requestBody)
    {
        String endpoint = apiProperties.getBaseUrl().concat("/reports");
        String payload = mapper.writeValueAsString(requestBody);

        return httpRequestClient.post(endpoint,payload, "Bearer ".concat(apiProperties.getToken()), ReportSvcImpl.class);
    }

}
