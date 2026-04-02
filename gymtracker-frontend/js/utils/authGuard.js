import { estaAutenticado, esAdmin} from "./storage.js";

//PROTEGER PAGINAS QUE NECESITAN LOGIN
export function protegerRuta() {
    if (!estaAutenticado()) {
        window.location.href = "/index.html";
    }
}

//PROTEGER PAGINAS SOLO ADMIN
export function protegerRutaAdmin() {
    if (!estaAutenticado()) {
        window.location.href = "/index.html";
        return;
    }

    if (!esAdmin()) {
        window.location.href = "/dashboard.html";
    }
}