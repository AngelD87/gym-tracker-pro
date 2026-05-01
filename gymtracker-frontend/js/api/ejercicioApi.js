const API_URL = "http://localhost:8080/api/ejercicios";

//LISTAR TODOS LOS EJERCICIOS
export async function obtenerEjercicios() {
    const respuesta = await fetch(API_URL);

    const datos = await respuesta.json();

    if(!respuesta.ok) {
        throw new Error(datos.message || "Error al obtener ejercicios");
    }

    return datos;
    
}

//OBTENER EJERCICIO POR MUSCULO
export async function obtenerEjerciciosPorMusculo(idMusculo) {
    const respuesta = await fetch(`${API_URL}/musculo/${idMusculo}`);

    const datos = await respuesta.json();

    if(!respuesta.ok) {
        throw new Error(datos.message || "Error al obtener ejercicios por músculo");
    }
    
    return datos;
}