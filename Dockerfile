# ==========================================
# Etapa 1: Compilación (Build)
# ==========================================
# Usamos una imagen oficial de Maven con Amazon Corretto para JDK 25
FROM maven:3-amazoncorretto-25

# Crear usuario nonroot por seguridad
RUN groupadd -r nonroot && useradd -r -g nonroot -m nonroot \
    && mkdir -p /webapp \
    && chown -R nonroot:nonroot /webapp

# Establecemos el directorio de trabajo
WORKDIR /webapp

# Copiamos todo el codigo del proyecto
COPY . .

# Al usar la imagen oficial de Maven, ya tenemos el comando "mvn" nativo
RUN mvn clean install -DskipTests

RUN chown -R nonroot:nonroot /webapp

USER nonroot

# Arrancamos Spring Boot con el comando oficial
CMD ["mvn", "spring-boot:run"]