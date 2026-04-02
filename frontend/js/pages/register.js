import { registrarUsuario } from "../api/usuarioApi.js";

const formRegistro = document.getElementById("formRegistro");
const mensajeRegistro = document.getElementById("mensajeRegistro");

formRegistro.addEventListener("submit", async function (evento) {
    evento.preventDefault();

    const nombre = document.getElementById("nombre").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const contrasena = document.getElementById("contrasena").value.trim();
    const pesoCorporal = document.getElementById("pesoCorporal").value.trim();
    const altura = document.getElementById("altura").value.trim();

    mensajeRegistro.textContent = "";

    const datosUsuario = {
        nombre: nombre,
        email: correo,
        password: contrasena,
        pesoCorporal: pesoCorporal ? parseFloat(pesoCorporal) : null,
        altura: altura ? parseFloat(altura) : null
    };

    try {
        await registrarUsuario(datosUsuario);

        mensajeRegistro.textContent = "Usuario registrado correctamente";
        mensajeRegistro.style.color = "green";

        setTimeout(() => {
            window.location.href = "index.html";
            
        }, 1200);

    } catch (error) {
        mensajeRegistro.textContent = error.message;
        mensajeRegistro.style.color = "red";
    }
    
});