document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login-form");

    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const login = document.getElementById("login").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ login, password }),
            });

            if (response.status === 403) {
                throw new Error("Credenciales incorrectas");
            }

            if (!response.ok) {
                throw new Error("Error en el servidor. Inténtalo más tarde.");
            }

            const data = await response.json();
            const token = data.jwt;

            if (!token) {
                throw new Error("Token no recibido. Contacta con soporte.");
            }

            localStorage.setItem("authToken", token);

            showNotification("Inicio de sesión exitoso. Redirigiendo...", "success");

            setTimeout(() => {
                window.location.href = "dashboard.html";
            }, 2000);
        } catch (error) {
            console.error("Error al iniciar sesión:", error.message);
            showNotification("Error: " + error.message, "error");
        }
    });
});