# Arquitectura del Sistema – Gym Tracker Pro

Gym Tracker Pro es una aplicación web desarrollada siguiendo una arquitectura cliente-servidor en tres capas, que permite separar la interfaz de usuario, la lógica de negocio y la persistencia de datos.

Esta arquitectura facilita el mantenimiento del sistema, su escalabilidad y la reutilización del backend por diferentes clientes en el futuro.

---

## 1. Capa de Presentación (Frontend)

La capa de presentación está formada por una aplicación web desarrollada con HTML, CSS y JavaScript, que se ejecuta en el navegador del usuario.

Características principales:
- Interfaz sencilla e intuitiva.
- Diseño responsive y mobile-first, pensado para su uso desde dispositivos móviles durante el entrenamiento.
- Formularios y vistas para el registro de entrenamientos, ejercicios y series.
- Panel de administración accesible solo para usuarios con rol administrador.

El frontend no accede directamente a la base de datos, sino que se comunica exclusivamente con el backend mediante peticiones HTTP.

---

## 2. Capa de Lógica de Negocio (Backend)

La lógica de negocio se implementa mediante una API REST desarrollada en Java.

Funciones principales del backend:
- Gestión de usuarios y autenticación.
- Control de roles (usuario y administrador).
- Gestión de entrenamientos, ejercicios y series.
- Aplicación de reglas de negocio (orden de ejercicios, numeración de series, activación/desactivación de usuarios y ejercicios).
- Cálculo de estadísticas básicas de entrenamiento.

La API recibe y envía datos en formato JSON y actúa como intermediaria entre el frontend y la base de datos.

---

## 3. Capa de Persistencia de Datos (Base de Datos)

La capa de datos está formada por una base de datos relacional MySQL.

En ella se almacenan:
- Usuarios y sus datos personales.
- Entrenamientos realizados.
- Catálogo de músculos y ejercicios.
- Series registradas en cada entrenamiento.

El acceso a la base de datos se realiza únicamente desde el backend, garantizando la seguridad y la integridad de los datos.

---

## 4. Comunicación entre capas

La comunicación entre el frontend y el backend se realiza mediante peticiones HTTP utilizando la API Fetch de JavaScript.

El intercambio de información se realiza en formato JSON, siguiendo el modelo REST.

La comunicación entre el backend y la base de datos se realiza mediante consultas SQL gestionadas desde Java.

---

## 5. Control de versiones

El proyecto utiliza GitHub como sistema de control de versiones.  
En el repositorio se almacenan tanto la documentación como el código del proyecto, permitiendo llevar un seguimiento de la evolución del desarrollo mediante commits periódicos.

---
