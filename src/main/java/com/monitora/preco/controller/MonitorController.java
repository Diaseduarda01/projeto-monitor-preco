package com.monitora.preco.controller;

import com.monitora.preco.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoramento")
@RequiredArgsConstructor
public class MonitorController {

    private final MonitorService service;

    @PostMapping("/executar")
    public ResponseEntity<String> monitorarAgora() {
        service.executarMonitoramento();
        return ResponseEntity.ok("Monitoramento executado manualmente.");
    }
}
