const API_URL = "http://localhost:8080/api/series";

//AÑADIR SERIE
export async function añadirSerie(idEntrenamientoEjercicio, numeroSerie, repeticiones, peso, rir) {
    const respuesta = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        
        body: JSON.stringify({
            idEntrenamientoEjercicio: idEntrenamientoEjercicio,
            numeroSerie: numeroSerie,
            repeticiones: repeticiones,
            peso: peso,
            rir: rir
        })
    });

    const datos = await respuesta.json();

    if(!respuesta.ok) {
        throw new Error(datos.message || "Error añ añadir serie");
    }

    return datos;
}

//ELIMINAR SERIE
export async function eliminarSerie(idSerie) {
    const respuesta = await fetch(`${API_URL}/${idSerie}`, {
        method: "DELETE",
    });

    if(!respuesta.ok) {
        let mensaje = "Error al eliminar la serie";

        try {
            const datos = await respuesta.json();
            mensaje = datos.message || mensaje;
        
        } catch (error) {
            mensaje = "Error al eliminarla serie";
        }

        throw new Error(mensaje);
    }
}

//EDITAR SERIE
export async function editarSerie(idSerie, repeticiones, peso, rir) {
    const respuesta = await fetch(`${API_URL}/${idSerie}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            repeticiones: repeticiones,
            peso: peso,
            rir: rir
        })
    });

    const datos = await respuesta.json();

    if (!respuesta.ok) {
        throw new Error(datos.message || "Error al editar la serie");
    }

    return datos;
}