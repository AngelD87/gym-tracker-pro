import { iniciarSesion } from "../api/authApi.js";
import { guardarUsuario } from "../utils/storage.js";

const formLogin = document.getElementById("formLogin");
const mensajeLogin = document.getElementById("mensajeLogin");

formLogin.addEventListener("submit", async function (evento) {
    evento.preventDefault();

    const correo = document.getElementById("correo").value.trim();
    const contrasena = document.getElementById("contrasena").value.trim();

    mensajeLogin.textContent = "";

    try{
        const usuario = await iniciarSesion(correo, contrasena);

        //GUARDAMOS USUARIO USANDO STORAGE.JS
        guardarUsuario(usuario);

        mensajeLogin.textContent = "Login correcto";
        mensajeLogin.style.color = "green";

        setTimeout(() => {
            window.location.href = "dasboard.html";
            
        }, 1000);

    } catch (error) {
        mensajeLogin.textContent = error.message;
        mensajeLogin.style.color = "red";
    }

});
