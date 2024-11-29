document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("authToken");

    if (!token) {
        alert("Acceso denegado. Debes iniciar sesión.");
        window.location.href = "login.html";
        return;
    }
});
