package com.interfija.masterposmultitenant.controller.tenant.login;

import com.interfija.masterposmultitenant.dto.session.SessionDTO;
import com.interfija.masterposmultitenant.services.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar la autenticación e inicio de sesión en terminales.
 * Provee la sesión completa del sistema con contexto organizacional.
 *
 * Autor: OpenAI para Steven Arzuza.
 */
@RestController
@RequestMapping("/api/tenant/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Retorna los datos de sesión (terminal, caja, piso, sucursal, empresa) dado un nombre de terminal.
     *
     * @param terminal nombre de la terminal (identificador lógico).
     * @return Sesión completa si existe, o código 404 si no se encontró.
     */
    @GetMapping("/session")
    public ResponseEntity<SessionDTO> getSessionByTerminal(@RequestParam String terminal) {
        return loginService.getSession(terminal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

