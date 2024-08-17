const signupForm = document.getElementById('signup-form');

signupForm.addEventListener('submit', (e) => {
    e.preventDefault(); //Con esto se evita el comportamiento default del form (sobretodo que no se recargue la página)

    const email = document.getElementById('email').value;
    const fullname = document.getElementById('fullname').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    //Se crea un objeto con la info del usuario
    const user = {
        email: email,
        fullname: fullname,
        username: username,
        password: password
    };

    //Se obtiene el objeto de "users" del Local Storage en caso de que exista y, sino, se crea dicho objeto desde 0
    let users = JSON.parse(localStorage.getItem('users')) || {};

    //Se verifica que no hayan dos registros iguales para los campos que deberían ser únicos
    for (let id in users) {
        if (users[id].email.toLowerCase() === email.toLowerCase()) {
            alert('Este correo ya está registrado.');
            return; //En caso de que se acceda a alguno de estos condicionales, se cierra la función con el "return"
        }
        
        if (users[id].username.toLowerCase() === username.toLowerCase()) {
            alert('Este nombre de usuario ya está en uso.');
            return;
        }
    }

    //Genera un identificador para el nuevo usuario de manera incremental
    let userId = Object.keys(users).length + 1;
    //Esto evita que se repitan ids si te pones a borrar algun registro en específico y luego creas más
    while (users.hasOwnProperty(userId)) {
        userId++;
    }

    //Se agrega al nuevo usuario al objeto de usuarios
    users[userId] = user;

    //Se guarda el objeto actualizado y parseado a String en el Local Storage
    localStorage.setItem('users', JSON.stringify(users));
    
    //Se limpia el formulario y se envía un mensaje de confirmación
    signupForm.reset();
    alert('Has sido registrado satisfactoriamente. ¡Bienvenido a Ready!');
});