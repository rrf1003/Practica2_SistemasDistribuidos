package com.sistemasdistr.basico.exception;

import com.sistemasdistr.basico.model.ErrorLog;
import com.sistemasdistr.basico.repository.ErrorLogRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Inyectamos nuestro nuevo repositorio para poder guardar el error
    private final ErrorLogRepository errorLogRepository;

    public GlobalExceptionHandler(ErrorLogRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    @ExceptionHandler(SQLException.class)
    public String handleDatabaseExceptions(SQLException ex, Model model) {
        // 1. Guardar en Base de Datos (CRUD)
        ErrorLog log = new ErrorLog();
        log.setTitulo("Fallo de Base de Datos");
        log.setDetalle(ex.getMessage());
        log.setTipoExcepcion("SQLException");
        errorLogRepository.save(log); // ¡Guardado!

        // 2. Mostrar al usuario
        model.addAttribute("tituloError", "Error de Acceso a Datos");
        model.addAttribute("mensajeError", "No se pudo conectar con la base de datos del sistema.");
        model.addAttribute("detalleTecnico", ex.getMessage());
        return "error-amigable";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public String handlePythonApiExceptions(HttpClientErrorException ex, Model model) {
        // 1. Guardar en Base de Datos (CRUD)
        ErrorLog log = new ErrorLog();
        log.setTitulo("Fallo de API Python");
        log.setDetalle(ex.getMessage());
        log.setTipoExcepcion("HttpClientErrorException");
        errorLogRepository.save(log); // ¡Guardado!

        // 2. Mostrar al usuario
        model.addAttribute("tituloError", "Error en Servicio Externo");
        model.addAttribute("mensajeError", "La API de Python ha respondido con un error.");
        model.addAttribute("detalleTecnico", "Status: " + ex.getStatusCode());
        return "error-amigable";
    }
}