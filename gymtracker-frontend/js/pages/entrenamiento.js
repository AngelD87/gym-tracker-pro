import { protegerRuta } from "../utils/authGuard.js";
import { obtenerEntrenamientoCompleto } from "../api/entrenamientoApi.js";

protegerRuta();

const tituloEntrenamiento = document.getElementById("tituloEntrenamiento");
const infoEntrenamiento = document.getElementById("infoEntrenamiento");
const listaEjerciciosEntrenamiento = document.getElementById("listaEjerciciosEntrenamiento");
const btnVolverDashboard = document.getElementById("btnVolverDashboard");

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

//FUNCION PARA CARGAR ENTRENAMIENTO
async function cargarEntrenamiento() {
    try{
        const entrenamiento = await obtenerEntrenamientoCompleto(idEntrenamiento);

        //TITULO
        tituloEntrenamiento.textContent = entrenamiento.nombre;

        //INFO GENERAL
        infoEntrenamiento.textContent = `Inicio: ${formatearFecha(entrenamiento.inicio)}`;

        //MOSTRAR EJERCICIOS
        mostrarEjercicios(entrenamiento.ejercicios)
    } catch (error) {
        tituloEntrenamiento.textContent = "Error al cargar entrenamiento";
        infoEntrenamiento.textContent = error.message;
    }
    
}

//MOSTRAR LISTA DE EJERCICIOS
function mostrarEjercicios(ejercicios) {
    if(!ejercicios || ejercicios.length === 0) {
        listaEjerciciosEntrenamiento.innerHTML = `
        <p>No hay ejercicios añadidos todavia.</p>
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

//FORMATWAR FECHA
function formatearFecha(fechaTexto) {
    const fecha = new Date(fechaTexto);
    return fecha.toLocaleString("es-ES");
}