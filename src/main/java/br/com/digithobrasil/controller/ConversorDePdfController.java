package br.com.digithobrasil.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversorDePdfController {

    @RequestMapping("/")
    public String home() {
        return "Conversor de pdf da DígithoBrasil!";
    }
}
