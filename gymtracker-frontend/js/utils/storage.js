//GUARDAR DATOS DEL USUARIO
export function guardarUsuario(usuario) {
    localStorage.setItem("usuario", JSON.stringify(usuario));
}

//OBTENER DATOS DEL USUARIO
export function obtenerUsuario() {
    const usuario = localStorage.getItem('usuario');
    return usuario ? JSON.parse(usuario) : null;
}

//VERIFICAR SI HAY SESION ACTIVA
export function estaAutenticado() {
    return obtenerUsuario() !== null;
}

//CERRAR SESION
export function cerrarSesion() {
    localStorage.removeItem('usuario');
    window.location.href = '/index.html'
}

//OBTENER ID DEL USUARIO ACTUAL
export function obtenerIdUsuario() {
    const usuario = obtenerUsuario();
    return usuario ? usuario.idUsuario : null;
}

//OBTENER ROL  DEL USUARIO ACTUAL
export function obtenerRolUsuario() {
    const usuario = obtenerUsuario(); 
    return usuario ? usuario.rol : null;
}

//VERIFICAR EL EL USUARIO ES ADMIN
export function esAdmin() {

    return obtenerRolUsuario() === 'ADMIN';
}