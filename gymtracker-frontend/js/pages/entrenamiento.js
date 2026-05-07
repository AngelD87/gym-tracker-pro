import { protegerRuta } from "../utils/authGuard.js";
import { obtenerEntrenamientoCompleto } from "../api/entrenamientoApi.js";
import { obtenerEjerciciosPorMusculo } from "../api/ejercicioApi.js";
import { obtenerMusculos } from "../api/musculoApi.js";
import { añadirEjercicioAEntrenamiento } from "../api/entrenamientoEjercicioApi.js";
import { añadirSerie, eliminarSerie, editarSerie } from "../api/serieApi.js";

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

//SERIE
const modalSerie = document.getElementById("modalSerie");
const numeroSerie = document.getElementById("numeroSerie");
const repeticionesSerie = document.getElementById("repeticionesSerie");
const pesoSerie = document.getElementById("pesoSerie");
const rirSerie = document.getElementById("rirSerie");
const btnCancelarSerie = document.getElementById("btnCancelarSerie");
const btnGuardarSerie = document.getElementById("btnGuardarSerie");
const mensajeModalSerie = document.getElementById("mensajeModalSerie");

let idEntrenamientoEjercicioSeleccionado = null;

let idSerieEditando = null;
let modoEdicionSerie = false;


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

//GUARDAR EJERCICIO EN EL ENTRENAMIENTO
btnGuardarEjercicio.addEventListener("click", async function () {
    const idEjercicio = selectEjercicio.value;
    const orden = parseInt(ordenEjercicio.value);
    const notas = notasEjercicio.value.trim();

    if(!idEjercicio) {
        mensajeModalEjercicio.textContent = "Debes seleccionar un ejercicio";
        mensajeModalEjercicio.style.color = "red";
        return;
    }

    if(!orden || orden <= 0) {
        mensajeModalEjercicio.textContent = "El orden debe ser mayor que 0";
        mensajeModalEjercicio.style.color = "red";
        return;
    }

    try {
        btnGuardarEjercicio.disabled = true;
        btnGuardarEjercicio.textContent = "Guardando...";

        await añadirEjercicioAEntrenamiento(
            idEntrenamiento,
            idEjercicio,
            orden,
            notas
        );

        modalEjercicio.classList.add("oculto");

        btnGuardarEjercicio.disabled = false;
        btnGuardarEjercicio.textContent = "Guardar";

        await cargarEntrenamiento();
    
    } catch(error) {
        mensajeModalEjercicio.textContent = error.message;
        mensajeModalEjercicio.style.color = "red";

        btnGuardarEjercicio.disabled = false;
        btnGuardarEjercicio.textContent = "Guardar";
    }
    
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

    if(ejercicioSeleccionado.videoUrl) {
        btnVerVideo.disabled = false;
        btnVerVideo.textContent = "Ver vídeo";

        btnVerVideo.onclick = function () {
            window.open(ejercicioSeleccionado.videoUrl, "_blank");

        };

    } else {
        btnVerVideo.disabled = true;
        btnVerVideo.textContent = "Sin vídeo disponible";
        btnVerVideo.onclick = null;
    }

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
            
            <div class="lista-series">
                ${generarHtmlSeries(ejercicio.series)}
                </div>

    <button 
        class="boton-principal btnAñadirSerie"
        data-id-entrenamiento-ejercicio="${ejercicio.idEntrenamientoEjercicio}">
        Añadir serie
    </button>
        `;

        listaEjerciciosEntrenamiento.appendChild(tarjetaEjercicio);
    });

    activarBotonesAñadirSerie();
    activarBotonesEliminarSerie();
    activarBotonesEditarSerie();

}

//MOSTRAR SERIES
function generarHtmlSeries(series) {
    if (!series || series.length === 0) {
        return `<p><strong>Series:</strong> No hay series añadidas</p>`;
    }

    let html = `<p><strong>Series:</strong></p>`;

    series.forEach(function (serie) {
        html += `
            <div class="serie-item">
                <div class="serie-info">
                    <p>
                        Serie ${serie.numeroSerie}: 
                        ${serie.repeticiones} reps - 
                        ${serie.peso} kg 
                        ${serie.rir !== null ? ` - RIR ${serie.rir}` : ""}
                    </p>
                </div>

                <div class="acciones-serie">
                    <button 
                        class="boton-icono btnEditarSerie" 
                        data-id-serie="${serie.idSerie}"
                        data-numero-serie="${serie.numeroSerie}"
                        data-repeticiones="${serie.repeticiones}"
                        data-peso="${serie.peso}"
                        data-rir="${serie.rir !== null ? serie.rir : ""}">
                        <img src="../images/icons/IconoEditar.png" alt="Editar">
                    </button>

                    <button class="boton-icono btnEliminarSerie" data-id-serie="${serie.idSerie}">
                        <img src="../images/icons/IconoBorrar.png" alt="Eliminar">
                    </button>
                </div>
            </div>
        `;
    });

    return html;
}


//AÑADIR SERIE
function activarBotonesAñadirSerie() {
    const botones = document.querySelectorAll(".btnAñadirSerie");

    botones.forEach(function (boton) {
        boton.addEventListener("click", function () {

            modoEdicionSerie = false;
            idSerieEditando = null;
            numeroSerie.disabled = false;

            idEntrenamientoEjercicioSeleccionado = boton.dataset.idEntrenamientoEjercicio;

            numeroSerie.value = "";
            repeticionesSerie.value = "";
            pesoSerie.value = "";
            rirSerie.value = "";
            mensajeModalSerie.textContent = "";

            btnGuardarSerie.disabled = false;
            btnGuardarSerie.textContent = "Guardar";

            modalSerie.classList.remove("oculto");

        });
    });
}

//BORRAR SERIES
function activarBotonesEliminarSerie() {
    const botones = document.querySelectorAll(".btnEliminarSerie");

    botones.forEach(function (boton) {
        boton.addEventListener("click", async function () {
            const idSerie = boton.dataset.idSerie;

            const confirmar = confirm("¿Seguro que quieres eliminar esta serie?");

            if (!confirmar) {
                return;
            }

            try {
                await eliminarSerie(idSerie);
                await cargarEntrenamiento();
            } catch (error) {
                alert(error.message);
            }
        });
    });
}

//EDITAR SERIES
function activarBotonesEditarSerie() {
    const botones = document.querySelectorAll(".btnEditarSerie");

    botones.forEach(function (boton) {
        boton.addEventListener("click", function () {
            modoEdicionSerie = true;
            idSerieEditando = boton.dataset.idSerie;

            numeroSerie.value = boton.dataset.numeroSerie;
            repeticionesSerie.value = boton.dataset.repeticiones;
            pesoSerie.value = boton.dataset.peso;
            rirSerie.value = boton.dataset.rir;

            numeroSerie.disabled = true;

            mensajeModalSerie.textContent = "";
            btnGuardarSerie.disabled = false;
            btnGuardarSerie.textContent = "Guardar cambios";

            modalSerie.classList.remove("oculto");
        });
    });
}


//CERRAR MODAL SERIE
btnCancelarSerie.addEventListener("click", function() {
    modalSerie.classList.add("oculto");
});



//GUARDAR SERIE
btnGuardarSerie.addEventListener("click", async function () {
    const numero = parseInt(numeroSerie.value);
    const repeticiones = parseInt(repeticionesSerie.value);
    const peso = parseFloat(pesoSerie.value);
    const rir = rirSerie.value === "" ? null : parseInt(rirSerie.value);

    if(!numero || numero <= 0) {
        mensajeModalSerie.textContent = "El número de serie debe ser mayor que 0";
        mensajeModalSerie.style.color = "red";
        return;
    }

    if(!repeticiones || repeticiones <= 0) {
        mensajeModalSerie.textContent = "Las repeticiones deben ser mayores que 0";
        mensajeModalSerie.style.color = "red";
        return;
    }

    if(isNaN(peso) || peso < 0) {
        mensajeModalSerie.textContent = "El peso no puede ser negativo";
        mensajeModalSerie.style.color = "red";
        return;
    }

    if(rir !== null && (rir < 0 || rir > 10)) {
        mensajeModalSerie.textContent = "El RIR debe estar entre 0 y 10";
        mensajeModalSerie.style.color = "red";
        return;
    }

    try {
        btnGuardarSerie.disabled = true;
        btnGuardarSerie.textContent = "Guardando...";

    
    if (modoEdicionSerie) {
        await editarSerie(
        idSerieEditando,
        repeticiones,
        peso,
        rir
        );
    } else {
        await añadirSerie(
            idEntrenamientoEjercicioSeleccionado,
            numero,
            repeticiones,
            peso,
            rir
        );

    }

        modalSerie.classList.add("oculto");

        btnGuardarSerie.disabled = false;
        btnGuardarSerie.textContent = "Guardar";

        await cargarEntrenamiento();
    
    } catch (error) {
        mensajeModalSerie.textContent = error.message;
        mensajeModalSerie.style.color = "red";

        btnGuardarSerie.disabled = false;
        btnGuardarSerie.textContent = "Guardar";
    }

    
});

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