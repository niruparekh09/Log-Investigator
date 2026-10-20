package com.nrv.log_investigator.controller;

import com.nrv.log_investigator.domain.LogAnalysisRequest;
import com.nrv.log_investigator.domain.LogAnalysisResponse;
import com.nrv.log_investigator.service.LogAnalysisService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logs")
public class LogAnalysisController {
    private final LogAnalysisService logAnalysisService;

    public LogAnalysisController(LogAnalysisService logAnalysisService) {
        this.logAnalysisService = logAnalysisService;
    }

    @PostMapping("/analyze")
    public LogAnalysisResponse analyze(
            @Valid @RequestBody LogAnalysisRequest request) {

        String analysis = logAnalysisService.analyze(request.logs());

        return new LogAnalysisResponse(analysis);
    }
}
