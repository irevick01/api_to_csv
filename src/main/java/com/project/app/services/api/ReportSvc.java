package com.project.app.services.api;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface ReportSvc
{
    ResponseEntity<JsonNode> read(JsonNode requestBody);
    void download(HttpServletRequest request, HttpServletResponse response);
}
