const API_URL = "http://localhost:8080/api/entrenamientos";

//CREAR ENTRENAMIENTO
export async function crearEntrenamiento(idUsuario, nombre) {
    const respuesta = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            idUsuario: idUsuario,
            nombre: nombre
        })
    });

    const datos = await respuesta.json();

    if (!respuesta.ok) {
        throw new Error(datos.message || "Error al crear el entrenamiento");
    }

    return datos;
}

//OBTENER ENTRENAMIENTO COMPLETO
export async function obtenerEntrenamientoCompleto(idEntrenamiento) {
    const respuesta = await fetch(`${API_URL}/${idEntrenamiento}/completo`);

    const datos = await respuesta.json();

    if (!respuesta.ok) {
        throw new Error(datos.message || "Error al obtener el entrenamiento");
    }

    return datos;
}