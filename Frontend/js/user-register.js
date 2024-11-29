const signupForm = document.getElementById("signup-form");

signupForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    const userData = {
        email: email,
        username: username,
        password: password,
    };

    try {
        const response = await fetch("http://localhost:8080/signup", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userData),
        });

        if (response.ok) {
            const result = await response.text();
            showNotification(result || "Registro exitoso. Redirigiendo...", "success");
            signupForm.reset();
            setTimeout(() => (window.location.href = "dashboard.html"), 2000);

        } else {
            const contentType = response.headers.get("Content-Type");
            if (contentType && contentType.includes("application/json")) {
                const errors = await response.json();
                errors.forEach(({ error }) => {
                    showNotification(`${error}`, 'info');
                });
            } else {
                const errorText = await response.text();
                showNotification(errorText || "Ocurrió un error inesperado.", "error");
            }
        }
    } catch (error) {
        console.error("Error:", error);
        showNotification("No se pudo completar el registro. Intenta de nuevo más tarde.", "error");
    }
});
