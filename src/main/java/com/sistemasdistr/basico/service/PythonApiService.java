package com.sistemasdistr.basico.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PythonApiService {

    private final RestTemplate restTemplate;

    // URL base de nuestro futuro contenedor de Python en Docker
    // (Cuando lo levantemos con Docker Compose, se llamará "python-api")
    private final String PYTHON_API_BASE_URL = "http://python-api:5000";

    public PythonApiService(){
        this.restTemplate = new RestTemplate();
    }

    // Llama al endpoint de Python que simula buscar un Pokemon
    public String obtenerPokemon(String nombre, String usuario){
        String url = PYTHON_API_BASE_URL + "/api/pokemon/" + nombre + "?usuario=" + usuario;
        return restTemplate.getForObject(url, String.class);
    }

    // Llama al endpoint de Python que simula un error de lectura de archivos
    public String forzarErrorArchivo() {
        String url = PYTHON_API_BASE_URL + "/api/error-archivo";
        return restTemplate.getForObject(url, String.class);
    }

    // Llama al endpoint de Python que simula un fallo en su propia Base de Datos
    public String forzarErrorBaseDatos() {
        String url = PYTHON_API_BASE_URL + "/api/error-bd";
        return restTemplate.getForObject(url, String.class);
    }
}
