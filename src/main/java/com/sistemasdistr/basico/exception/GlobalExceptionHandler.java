package com.sistemasdistr.basico.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    //1. Atrapa errores de Base de Datos (Ej: MySQL apagado o error de consulta)
    @ExceptionHandler(SQLException.class)
    public String handleDatabaseExceptions(SQLException ex, Model model){
        model.addAttribute("tituloError", "Error de Base de Datos");
        model.addAttribute("mensajeError", "Lo sentimos, hay un problema temporal conectando con la base de datos");
        model.addAttribute("detalleTecnico", ex.getMessage());
        return "error-amigable";
    }

    //2. Atrapa errores HTTP del API de Python (Ej: devuelve un 404 No Encontrado o 500 Error Interno)
    @ExceptionHandler(HttpClientErrorException.class)
    public String handlePythonApiExceptions(HttpClientErrorException ex, Model model){
        model.addAttribute("tituloError", "Error en el Servicio Externo");
        model.addAttribute("mensajeError", "El servicio de Python ha devuelto un error al procesar la petición");
        model.addAttribute("detalleTecnico", "Código HTTP: " + ex.getStatusCode() + " - " + ex.getStatusText());
        return "error-amigable";
    }

    //3. Atrapa errores de conexión (Ej: El servidor de Python está apagado)
    @ExceptionHandler(ResourceAccessException.class)
    public String handleConnectionExceptions(ResourceAccessException ex, Model model){
        model.addAttribute("tituloError", "Servicio no Disponible");
        model.addAttribute("mensajeError", "No hemos podido contactar con el API de Python. Verifica que esté en ejecución");
        model.addAttribute("detalleTecnico", ex.getMessage());
        return "error-amigable";
    }

    //4. Atrapa cualquier error genérico (Fallback)
    @ExceptionHandler(Exception.class)
    public String handleAllOtherExceptions(Exception ex, Model model){
        model.addAttribute("tituloError", "Error Inesperado");
        model.addAttribute("mensajeError", "Ha ocurrido un error inesperado en el sistema.");
        model.addAttribute("detalleTecnico",  ex.getMessage());
        return "error-amigable";
    }
}
