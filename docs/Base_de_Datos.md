# Diseño de la Base de Datos - Gym Tracker Pro
El sistema utiliza una base de datos relacional para almacenar la información necesaria para el seguimiento de entrenamientos.

## Tabla Usuarios
Almacena los datos de los usuarios registrados.
- id_usuario (PK)
- nombre
- email
- contraseña
- fecha_registro


## Tabla Entrenamientos
Almacena cada sesión de entrenamiento realizada por un usuario.
- id_entrenamientos (PK)
- fecha
- comentario
- id_usuario (FK)

## Tabla Musculos
Catálogo de grupos musculares.
- id_musculo (PK)
- nombre

## Tabla Ejercicios
Catálogo de ejercicios disponibles en el sistema.
- id_ejercicio (PK)
- nombre
- id_musculo (FK)

## Tabla Series
Almacena las series realizadas en cada entrenamiento
- id_serie (PK)
- numero_serie (1, 2, 3...)
- repeticiones
- peso
- id_entrenamiento (FK)
- id_ejercicio (FK)

## Relaciones
- Un usuario puede realizar varios entrenamientos.
- Un entrenamiento puede tener varias series.
- Un ejercicio puede aparecer en varias series.
- Un músculo puede estar asociado a varios ejercicios.
