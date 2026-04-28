from flask import Flask, jsonify
import requests
import mysql.connector

app = Flask(__name__)

@app.route('/api/pokemon/<nombre>')
def obtener_pokemon(nombre):
    respuesta = respuesta.get(f"https://pokeapi.co/api/v2/pokemon/{nombre}")

    if respuesta.status_code != 200:
        return jsonify({"error": "El Pokémon especificado no fue encontrado en la API externa."}), respuesta.status_code

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