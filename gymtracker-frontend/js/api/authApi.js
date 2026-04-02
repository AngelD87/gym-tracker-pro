const API_URL = "http://localhost:8080/api/auth";

export async function iniciarSesion(correo, contrasena) {
    
    const respuesta = await fetch(`${API_URL}/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: correo,
            password: contrasena
        })
    });

    const datos = await respuesta.json();

    if(!respuesta.ok) {
        throw new Error(datos.message || "Error al iniciar sesión");
    }
    
    return datos;
}
