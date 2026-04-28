# Usamos una imagen oficial de Python ligera
FROM python:3.9-slim

# Creamos una carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo de dependencias y las instalamos
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copiamos nuestro código de la API
COPY app.py .

# Exponemos el puerto 5000 (el que usa Flask)
EXPOSE 5000

# Comando para arrancar el servidor cuando el contenedor se inicie
CMD ["python", "app.py"]