const mentiras = document.getElementById('mentiras');
const ponerCorreo = document.getElementById('poner-correo');
const pswrd = document.getElementById('contraseña');
const seguridad = document.getElementById('seguridad');

//A ver, en general esto es más una manera entretenida de darte la contraseña. No busqué optimización ni eficiencia
//Solo fui fluyendo para entretenerme haciéndolo jaja

//Se empieza un timer una vez que el DOM se carga
setTimeout(() => {
    mentiras.innerHTML = "Mentiras jajaja"

    setTimeout(() => {
        ponerCorreo.innerHTML = "Ponga aquí su correo: "

        //Se crea un pequeño formulario con lo necesario para que pongas el correo
        //Y yo poder utilizar esa información para darte la contraseña
        let form = document.createElement("form");

        let casillaCorreo = document.createElement("input");
        casillaCorreo.setAttribute("type", "email");
        let botonEnviar = document.createElement("input");
        botonEnviar.setAttribute("type", "submit");

        form.appendChild(casillaCorreo);
        form.appendChild(botonEnviar);
        ponerCorreo.appendChild(form);

        //Por medio de este listener se compara lo que pusiste en casillaCorreo con los correos de los usuarios
        //Luego se ve si hay alguna coincidencia, en caso de que sea así le da la pswrd y en caso de que no ps no.
        document.addEventListener('submit', (e) => {
            e.preventDefault(); //Acuérdese que esto es pa que no se recargue la página, aquí sí molestaría bastante porque reiniciaría los timers

            let users = JSON.parse(localStorage.getItem('users'));
            let userFound = false;

            for (let id in users) {
                if (users[id].email.toLowerCase() === casillaCorreo.value.toLowerCase()) {
                    pswrd.innerHTML = `Tu contraseña es: ${users[id].password}`;
                    userFound = true;
                    setTimeout(() => {
                        seguridad.innerHTML = "Sé que no es la manera más segura de recuperar tu contraseña pero aceptaste esta inseguridad en la política de privacidad cuando te registraste, no? 😅"
                    }, 2000)
                    break;
                }
            }

            if (!userFound) {
                alert("El correo que has propocionado no coincide con el de ningúno de los usuarios")
            }
        })
    }, 1000)
}, 2500)