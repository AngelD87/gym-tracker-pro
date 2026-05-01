import { protegerRuta } from "../utils/authGuard.js";
import { obtenerEntrenamientoCompleto } from "../api/entrenamientoApi.js";
import { obtenerEjerciciosPorMusculo } from "../api/ejercicioApi.js";
import { obtenerMusculos } from "../api/musculoApi.js";

protegerRuta();

//ELEMENTOS PRINCIPALES
const tituloEntrenamiento = document.getElementById("tituloEntrenamiento");
const infoEntrenamiento = document.getElementById("infoEntrenamiento");
const listaEjerciciosEntrenamiento = document.getElementById("listaEjerciciosEntrenamiento");
const btnVolverDashboard = document.getElementById("btnVolverDashboard");
const btnAñadirEjercicio = document.getElementById("btnAñadirEjercicio");

//ELEMENTOS DEL MODAL
const modalEjercicio = document.getElementById("modalEjercicio");
const selectMusculo = document.getElementById("selectMusculo");
const selectEjercicio = document.getElementById("selectEjercicio");
const ordenEjercicio = document.getElementById("ordenEjercicio");
const notasEjercicio = document.getElementById("notasEjercicio");
const btnCancelarEjercicio = document.getElementById("btnCancelarEjercicio");
const btnGuardarEjercicio = document.getElementById("btnGuardarEjercicio");
const mensajeModalEjercicio = document.getElementById("mensajeModalEjercicio");

//INFO DEL EJERCICIO
const infoEjercicio = document.getElementById("infoEjercicio");
const nombreEjercicioInfo = document.getElementById("nombreEjercicioInfo");
const descripcionEjercicioInfo = document.getElementById("descripcionEjercicioInfo");
const btnVerVideo = document.getElementById("btnVerVideo");

//DATOS AUXILIARES
let ejerciciosCargados = [];

//LEER ID DEL ENTRENAMIENTO DESDE LA URL
const parametroUrl = new URLSearchParams(window.location.search);
const idEntrenamiento = parametroUrl.get("id");

//VALIDAR SI HAY ID
if (!idEntrenamiento) {
    tituloEntrenamiento.textContent = "Error";
    infoEntrenamiento.textContent = "No se ha encontrado el entrenamiento";
} else {
    cargarEntrenamiento();
}

//BOTON VOLVER
btnVolverDashboard.addEventListener("click", function () {
    window.location.href = "../dashboard.html";
});

//ABRIR MODAL
btnAñadirEjercicio.addEventListener("click", async function () {
    modalEjercicio.classList.remove("oculto");
    mensajeModalEjercicio.textContent = "";
    ordenEjercicio.value = "";
    notasEjercicio.value = "";
    infoEjercicio.classList.add("oculto");

    selectMusculo.innerHTML = `<option value="">Selecciona un músculo</option>`;
    selectEjercicio.innerHTML = `<option value="">Selecciona un ejercicio</option>`;

    try {
        const musculos = await obtenerMusculos();
        cargarSelectMusculos(musculos);
    } catch (error) {
        mensajeModalEjercicio.textContent = error.message;
        mensajeModalEjercicio.style.color = "red";
    }
});

//CERRAR MODAL
btnCancelarEjercicio.addEventListener("click", function () {
    modalEjercicio.classList.add("oculto");
});

//CAMBIAR MUSCULO
selectMusculo.addEventListener("change", async function () {
    const idMusculo = this.value;

    selectEjercicio.innerHTML = `<option value="">Selecciona un ejercicio</option>`;
    infoEjercicio.classList.add("oculto");
    mensajeModalEjercicio.textContent = "";

    if (!idMusculo) {
        return;
    }

    try {
        const ejercicios = await obtenerEjerciciosPorMusculo(idMusculo);
        ejerciciosCargados = ejercicios;
        cargarSelectEjercicios(ejercicios);
    } catch (error) {
        mensajeModalEjercicio.textContent = error.message;
        mensajeModalEjercicio.style.color = "red";
    }
});

//CAMBIAR EJERCICIO
selectEjercicio.addEventListener("change", function () {
    const idEjercicio = this.value;

    if (!idEjercicio) {
        infoEjercicio.classList.add("oculto");
        return;
    }

    const ejercicioSeleccionado = ejerciciosCargados.find(function (ejercicio) {
        return ejercicio.idEjercicio == idEjercicio;
    });

    if (!ejercicioSeleccionado) {
        infoEjercicio.classList.add("oculto");
        return;
    }

    nombreEjercicioInfo.textContent = ejercicioSeleccionado.nombre;
    descripcionEjercicioInfo.textContent = ejercicioSeleccionado.descripcion || "Sin descripción disponible";

    btnVerVideo.onclick = function () {
        if (ejercicioSeleccionado.videoUrl) {
            window.open(ejercicioSeleccionado.videoUrl, "_blank");
        } else {
            alert("Este ejercicio no tiene vídeo asociado");
        }
    };

    infoEjercicio.classList.remove("oculto");
});

//FUNCION PARA CARGAR ENTRENAMIENTO
async function cargarEntrenamiento() {
    try {
        const entrenamiento = await obtenerEntrenamientoCompleto(idEntrenamiento);

        tituloEntrenamiento.textContent = entrenamiento.nombre;
        infoEntrenamiento.textContent = `Inicio: ${formatearFecha(entrenamiento.inicio)}`;

        mostrarEjercicios(entrenamiento.ejercicios);
    } catch (error) {
        tituloEntrenamiento.textContent = "Error al cargar entrenamiento";
        infoEntrenamiento.textContent = error.message;
    }
}

//MOSTRAR LISTA DE EJERCICIOS
function mostrarEjercicios(ejercicios) {
    if (!ejercicios || ejercicios.length === 0) {
        listaEjerciciosEntrenamiento.innerHTML = `
            <p>No hay ejercicios añadidos todavía.</p>
        `;
        return;
    }

    listaEjerciciosEntrenamiento.innerHTML = "";

    ejercicios.forEach(function (ejercicio) {
        const tarjetaEjercicio = document.createElement("div");
        tarjetaEjercicio.classList.add("tarjeta-ejercicio");

        tarjetaEjercicio.innerHTML = `
            <h3>${ejercicio.nombreEjercicio}</h3>
            <p><strong>Orden:</strong> ${ejercicio.orden}</p>
            <p><strong>Notas:</strong> ${ejercicio.notas ? ejercicio.notas : "Sin notas"}</p>
            <p><strong>Series:</strong> ${ejercicio.series.length}</p>
        `;

        listaEjerciciosEntrenamiento.appendChild(tarjetaEjercicio);
    });
}

//CARGAR SELECT DE MUSCULOS
function cargarSelectMusculos(musculos) {
    selectMusculo.innerHTML = `<option value="">Selecciona un músculo</option>`;

    musculos.forEach(function (musculo) {
        const opcion = document.createElement("option");
        opcion.value = musculo.idMusculo;
        opcion.textContent = musculo.nombre;
        selectMusculo.appendChild(opcion);
    });
}

//CARGAR SELECT DE EJERCICIOS
function cargarSelectEjercicios(ejercicios) {
    selectEjercicio.innerHTML = `<option value="">Selecciona un ejercicio</option>`;

    ejercicios.forEach(function (ejercicio) {
        const opcion = document.createElement("option");
        opcion.value = ejercicio.idEjercicio;
        opcion.textContent = ejercicio.nombre;
        selectEjercicio.appendChild(opcion);
    });
}

//FORMATEAR FECHA
function formatearFecha(fechaTexto) {
    const fecha = new Date(fechaTexto);
    return fecha.toLocaleString("es-ES");
}