import { protegerRuta } from "../utils/authGuard.js";
import { obtenerUsuario, cerrarSesion, esAdmin, obtenerIdUsuario } from "../utils/storage.js";
import { crearEntrenamiento } from "../api/entrenamientoApi.js";

protegerRuta();

//ELEMENTOS DEL HTML
const saludoUsuario = document.getElementById("saludoUsuario");
const btnCerrarSesion = document.getElementById("btnCerrarSesion");
const btnNuevoEntrenamiento = document.getElementById("btnNuevoEntrenamiento");
const zonaAdmin = document.getElementById("zonaAdmin");

//ELEMENTOS DEL MODAL
const modalEntrenamiento = document.getElementById("modalEntrenamiento");
const inputNombreEntrenamiento = document.getElementById("inputNombreEntrenamiento");
const btnCancelarModal = document.getElementById("btnCancelarModal");
const btnCrearEntrenamiento = document.getElementById("btnCrearEntrenamiento");
const mensajeModal = document.getElementById("mensajeModal");

//OBTENEMOS USUARIO
const usuario = obtenerUsuario();

if (usuario) {
    saludoUsuario.textContent = `Hola, ${usuario.nombre} 👋`;
}

//CERRAR SESIÓN
btnCerrarSesion.addEventListener("click", function () {
    cerrarSesion();
});

//MOSTRAR PANEL ADMIN
if (esAdmin()) {
    zonaAdmin.classList.remove("oculto");
}

//ABRIR MODAL NUEVO ENTRENAMIENTO
btnNuevoEntrenamiento.addEventListener("click", function () {
    modalEntrenamiento.classList.remove("oculto");
    inputNombreEntrenamiento.value = "";
    mensajeModal.textContent = "";
    btnCrearEntrenamiento.disabled = false;
    btnCrearEntrenamiento.textContent = "Crear";
});

//CERRAR MODAL
btnCancelarModal.addEventListener("click", function () {
    modalEntrenamiento.classList.add("oculto");
});

//CREAR NUEVO ENTRENAMIENTO DESDE EL MODAL
btnCrearEntrenamiento.addEventListener("click", async function () {
    const idUsuario = obtenerIdUsuario();

    if (!idUsuario) {
        mensajeModal.textContent = "No se ha encontrado el usuario actual";
        mensajeModal.style.color = "red";
        return;
    }

    const nombreEntrenamiento = inputNombreEntrenamiento.value.trim();

    if (!nombreEntrenamiento) {
        mensajeModal.textContent = "Debes introducir un nombre para el entrenamiento";
        mensajeModal.style.color = "red";
        return;
    }

    try {
        btnCrearEntrenamiento.disabled = true;
        btnCrearEntrenamiento.textContent = "Creando...";

        const entrenamiento = await crearEntrenamiento(idUsuario, nombreEntrenamiento);

        window.location.href = `pages/entrenamiento.html?id=${entrenamiento.idEntrenamiento}`;

    } catch (error) {
        mensajeModal.textContent = error.message;
        mensajeModal.style.color = "red";
        btnCrearEntrenamiento.disabled = false;
        btnCrearEntrenamiento.textContent = "Crear";
    }
});