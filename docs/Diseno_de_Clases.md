# Diseño de Clases – Gym Tracker Pro

Este apartado describe las clases principales del sistema y su correspondencia con el modelo de base de datos.

---

## Clase Usuario
Representa a un usuario del sistema.

**Atributos:**
- idUsuario
- nombre
- email
- passwordHash
- fechaRegistro
- pesoCorporal
- altura
- rol
- isActive

---

## Clase Musculo
Representa un grupo muscular.

**Atributos:**
- idMusculo
- nombre
- descripcion

---

## Clase Ejercicio
Representa un ejercicio del catálogo.

**Atributos:**
- idEjercicio
- nombre
- descripcion
- videoUrl
- dificultad
- activo
- musculo

---

## Clase Entrenamiento
Representa una sesión de entrenamiento.

**Atributos:**
- idEntrenamiento
- inicio
- fin
- comentario
- valoracion
- usuario

---

## Clase EntrenamientoEjercicio
Relaciona un entrenamiento con los ejercicios realizados.

**Atributos:**
- idEntrenamientoEjercicio
- entrenamiento
- ejercicio
- orden
- notas

---

## Clase Serie
Representa una serie realizada dentro de un ejercicio de un entrenamiento.

**Atributos:**
- idSerie
- numeroSerie
- repeticiones
- peso
- rir
- entrenamientoEjercicio
