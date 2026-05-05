# 🚀 Práctica 2 - Sistema de Gestión de Excepciones, Integración de APIs y Persistencia

Este proyecto implementa una arquitectura basada en microservicios contenerizados para demostrar la correcta gestión de excepciones globales, la integración entre diferentes lenguajes (Java y Python) y la persistencia de datos compartida.

## 🏗️ 1. Arquitectura del Sistema

El ecosistema se orquesta al completo mediante **Docker** y `docker-compose`, y se compone de tres piezas fundamentales:
* **☕ Backend Principal (Spring Boot):** Actúa como el núcleo de la aplicación. Gestiona la seguridad (Spring Security), el renderizado de vistas (Thymeleaf) y la comunicación HTTP con otros servicios.
* **🐬 Base de Datos (MySQL):** Almacena de forma persistente los usuarios y sus roles para la autenticación, además de centralizar el **historial de búsquedas** generadas por la API externa.
* **🐍 Microservicio Externo (Python/Flask):** Una API secundaria que actúa como puente para consumir APIs de terceros, persistir datos en la base de datos compartida y simular fallos controlados.

## 💥 2. Microservicio de Python (Integración y Simulador de Errores)

Se ha desarrollado una API en Python utilizando el *framework* Flask. Este microservicio expone tres *endpoints* para probar tanto el flujo de éxito como la resiliencia del sistema principal:

1. **Consumo de API de Terceros y Base de Datos (`/api/pokemon/<nombre>?usuario=<user>`):** Consume la PokéAPI real. 
   * **Flujo de éxito:** Si el Pokémon existe, el microservicio se conecta a MySQL y persiste un registro con el nombre del usuario logueado en Spring Boot, el Pokémon buscado y la fecha/hora. Finalmente, devuelve el JSON con los datos.
   * **Flujo de error:** Al pasarle un nombre falso, atrapa el fallo y devuelve un error **HTTP 404 (Not Found)**.
2. **Error de Lectura de Archivos (`/api/error-archivo`):** Fuerza una excepción `FileNotFoundError` al intentar abrir un documento inexistente, devolviendo un error **HTTP 500 (Internal Server Error)**.
3. **Error de Base de Datos (`/api/error-bd`):** Fuerza un fallo de conexión `mysql.connector.Error` al intentar conectar con credenciales y bases de datos falsas, devolviendo también un **HTTP 500**.

## 🛡️ 3. Consumo y Manejo Global de Excepciones (Spring Boot)

Para consumir la API de Python, se ha implementado el servicio `PythonApiService` utilizando `RestTemplate`. Cuando Python devuelve errores (404 o 500), `RestTemplate` lanza automáticamente una excepción `HttpClientErrorException` o `HttpServerErrorException`.

Para evitar que la aplicación colapse, se ha implementado un patrón de intercepción global:
* Se ha creado la clase `GlobalExceptionHandler` anotada con `@ControllerAdvice`.
* Esta clase "escucha" y captura excepciones específicas (`SQLException`, `HttpClientErrorException`, `ResourceAccessException`).
* Inyecta un título, un mensaje amigable y el detalle técnico real dentro del `Model`, y redirige el flujo hacia una vista de error personalizada.

## 🖥️ 4. Interfaz de Usuario (Frontend)

El frontend se ha construido utilizando **Thymeleaf** y **Bootstrap 5** para garantizar un diseño *responsive*. Destacan cuatro pantallas clave:
* **Login (`login.html`):** Autentica al usuario contra la base de datos MySQL usando contraseñas encriptadas con BCrypt.
* **Panel de Pruebas (`api-test.html`):** Interfaz interactiva que permite buscar datos reales (que formatea los JSON de respuesta de manera elegante) y lanzar los botones de acción para disparar excepciones en el contenedor de Python.
* **Historial de Búsquedas (`historial.html`):** Panel de administración que lee directamente desde la base de datos MySQL para mostrar en tiempo real todas las consultas exitosas realizadas a través del contenedor de Python.
* **Pantalla de Error Amigable (`error-amigable.html`):** Interfaz que muestra un mensaje tranquilizador al usuario y un botón colapsable para que los desarrolladores puedan visualizar el *log* técnico real devuelto por la red de contenedores.

## ⚙️ 5. Instrucciones de Ejecución

Para levantar todo el ecosistema de microservicios, sitúate en la raíz del proyecto y ejecuta:
```bash
docker compose up -d --build
```

Una vez que los contenedores estén en ejecución:
```bash
Accede a la aplicación en: http://localhost:7001
```

Inicia sesión con las credenciales del administrador (para la gestión de usuarios):
```bash
Usuario: admin
Contraseña: admin123
```
