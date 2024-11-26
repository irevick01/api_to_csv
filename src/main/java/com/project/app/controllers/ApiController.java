package com.project.app.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.app.services.api.ReportSvc;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ApiController
{
    private final ReportSvc reportSvc;

    @Autowired
    public ApiController(ReportSvc reportSvc)
    {
        this.reportSvc = reportSvc;
    }

    @PostMapping(path = "/reports", produces = "application/json")
    public ResponseEntity<JsonNode> read(@RequestBody JsonNode requestBody)
    {
        return reportSvc.read(requestBody);
    }


    @GetMapping(path = "/reports/download")
    public void downloadCsv(HttpServletRequest request, HttpServletResponse response)
    {
        reportSvc.download(request, response);
    }

}
