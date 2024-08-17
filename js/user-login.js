const loginForm = document.getElementById('login-form');

loginForm.addEventListener('submit', (e) => {
    e.preventDefault(); //Con esto se evita el comportamiento default del form

    const email = document.getElementById('email').value.toLowerCase(); //Se convierte a minúsculas para comparación
    const password = document.getElementById('password').value;

    //Se obtienen los usuarios guardados en el Local Storage
    let users = JSON.parse(localStorage.getItem('users')) || {};

    //Aquí se verificará si el correo y la contraseña coinciden
    let userFound = false;
    let userName = '';

    for (let id in users) {
        if (users[id].email.toLowerCase() === email && users[id].password === password) {
            userFound = true;
            userName = users[id].username;
            break;
        }
    }

    if (userFound) {
        alert(`Has iniciado sesión correctamente. ¡Bienvenido de nuevo ${userName}!`);
        window.location.href = 'dashboard.html'; //(Esto por ahora va a estar comentado, lo implementaré en el futuro)
    } else {
        alert('Correo o contraseña incorrectos. Por favor, intenta de nuevo.');
    }
});
