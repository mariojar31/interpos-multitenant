//package com.interfija.masterposmultitenant.controller.tenant.main;
//
//import com.interfija.masterposmultitenant.services.main.MainService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Controlador principal de la aplicación.
// * Este controlador puede actuar como punto de acceso para operaciones globales, métricas del sistema,
// * configuración central o chequeos generales del estado de la aplicación.
// *
// * Autor: OpenAI para Steven Arzuza.
// */
//@RestController
//@RequestMapping("/api/tenant/main")
//public class MainController {
//
//    private final MainService mainService;
//
//    @Autowired
//    public MainController(MainService mainService) {
//        this.mainService = mainService;
//    }
//
//    /**
//     * Endpoint de prueba para verificar que el servicio principal está operativo.
//     *
//     * @return mensaje de estado.
//     */
//    @GetMapping("/ping")
//    public String ping() {
//        return "Main service activo";
//    }
//
//}
