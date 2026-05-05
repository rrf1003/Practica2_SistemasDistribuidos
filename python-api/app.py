from flask import Flask, jsonify
import requests
import mysql.connector
import os # Añadimos esto para leer las variables de Docker

app = Flask(__name__)

# Leemos las credenciales que nos manda Docker Compose
DB_CONFIG = {
    "host": os.environ.get("DB_HOST", "localhost"),
    "user": os.environ.get("DB_USER", "root"),
    "password": os.environ.get("DB_PASSWORD", "root"),
    "database": os.environ.get("DB_NAME", "practica_db")
}

def guardar_historial(nombre_pokemon, usuario):
    """Guarda la búsqueda en la BD incluyendo el nombre del usuario."""
    try:
        conexion = mysql.connector.connect(**DB_CONFIG)
        cursor = conexion.cursor()

        # Creamos una tabla nueva que incluye la columna 'usuario'
        cursor.execute("""
                       CREATE TABLE IF NOT EXISTS registro_busquedas (
                                                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                                                         usuario VARCHAR(50),
                           pokemon VARCHAR(100),
                           fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                           )
                       """)

        # Insertamos el Pokémon y el usuario
        sql = "INSERT INTO registro_busquedas (usuario, pokemon) VALUES (%s, %s)"
        cursor.execute(sql, (usuario, nombre_pokemon))

        conexion.commit()
        cursor.close()
        conexion.close()
        print(f"Búsqueda de {nombre_pokemon} por {usuario} guardada.")
    except mysql.connector.Error as e:
        print(f"Error al guardar: {e}")

@app.route('/api/pokemon/<nombre>')
def obtener_pokemon(nombre):
    # Recibimos el usuario que nos manda Java en la URL
    usuario = request.args.get('usuario', 'Desconocido')

    respuesta = requests.get(f"https://pokeapi.co/api/v2/pokemon/{nombre}")

    if respuesta.status_code != 200:
        return jsonify({"error": "El Pokémon especificado no fue encontrado en la API externa."}), respuesta.status_code

    # Guardamos los dos datos
    guardar_historial(nombre, usuario)

    return jsonify(respuesta.json())

@app.route('/api/error-archivo')
def forzar_error_archivo():
    try:

        with open("un_archivo_super_secreto_que_no_existe.txt", "r") as archivo:
            contenido = archivo.read()
        return jsonify({"mensaje": "Archivo leído con éxito"}), 200
    except FileNotFoundError as e:
        return jsonify({"error": f"Fallo al leer el archivo. Detalles: {str(e)}"}), 500

@app.route('/api/error-bd')
def forzar_error_bd():
    try:
        conexion = mysql.connector.connect(
            host="localhost",
            user="usuario_inventado",
            password="clave_incorrecta",
            database="db_inexistente",
            connection_timeout=2
        )
        return jsonify({"mensaje": "Conectado a la base de datos"}), 200
    except mysql.connector.Error as e:
        return jsonify({"error": f"Fallo catastrófico en la base de datos. Detalles: {str(e)}"}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)