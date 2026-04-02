const API_URL = "http://localhost:8080/api/usuarios";

export async function registrarUsuario(datosUsuario) {
    const respuesta = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(datosUsuario)
    });

    const datos = await respuesta.json();

    if (!respuesta.ok) {
        throw new Error(datos.message || "Error al registrar usuario");
    }

    return datos;
    
}