const API_URL = "http://localhost:8080/api/musculos";

//OBTENER TODOS LOS MUSCULOS
export async function obtenerMusculos() {
    const respuesta = await fetch(API_URL);

    const datos = await respuesta.json();

    if (!respuesta.ok) {
        throw new Error(datos.message || "Error al obtener músculos")
    }

    return datos;
}

