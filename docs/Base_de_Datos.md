# Diseño de la Base de Datos – Gym Tracker Pro (Modelo robusto)

El sistema utiliza una base de datos relacional MySQL para almacenar la información necesaria para registrar entrenamientos y analizar volumen/fatiga. El modelo se ha diseñado de forma robusta para permitir ampliaciones futuras.

## Tabla Usuarios
- id_usuario (PK)
- nombre
- email (UNIQUE)
- password_hash
- fecha_registro
- peso_corporal (NULL)
- altura (NULL)
- rol (ENUM: 'usuario', 'admin')
- is_active (BOOLEAN, default true) (opcional)

## Tabla Musculos
- id_musculo (PK)
- nombre (UNIQUE)
- descripcion (NULL)

## Tabla Ejercicios
- id_ejercicio (PK)
- nombre
- descripcion (NULL)
- video_url (NULL)
- dificultad (ENUM: 'principiante', 'intermedio', 'avanzado') (opcional)
- activo (BOOLEAN, default true)
- id_musculo (FK)

## Tabla Entrenamientos
- id_entrenamiento (PK)
- id_usuario (FK)
- inicio (DATETIME)
- fin (DATETIME, NULL)
- comentario (TEXT, NULL)
- valoracion (TINYINT 1-5, NULL)

## Tabla Entrenamiento_Ejercicios
Relaciona un entrenamiento con los ejercicios realizados y permite mantener el orden y notas por ejercicio.
- id_entrenamiento_ejercicio (PK)
- id_entrenamiento (FK)
- id_ejercicio (FK)
- orden (INT)
- notas (TEXT, NULL)

## Tabla Series
Almacena las series realizadas para cada ejercicio dentro de un entrenamiento.
- id_serie (PK)
- id_entrenamiento_ejercicio (FK)
- numero_serie (INT)
- repeticiones (INT)
- peso (DECIMAL)
- rir (TINYINT, NULL) (opcional) - Repeticiones en reserva
  
## Relaciones
- Un usuario puede realizar varios entrenamientos (Usuarios 1—N Entrenamientos).
- Un músculo puede tener varios ejercicios (Musculos 1—N Ejercicios).
- Un entrenamiento puede contener varios ejercicios (Entrenamientos 1—N Entrenamiento_Ejercicios).
- Un ejercicio puede aparecer en varios entrenamientos (Ejercicios 1—N Entrenamiento_Ejercicios).
- Un ejercicio de un entrenamiento puede tener varias series (Entrenamiento_Ejercicios 1—N Series).
