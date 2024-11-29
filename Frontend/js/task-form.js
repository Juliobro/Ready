const API_URL = 'http://localhost:8080/tareas';

function obtenerToken() {
    return localStorage.getItem('authToken');
}

const params = new URLSearchParams(window.location.search);
const id = params.get('id');

const formTitle = document.getElementById('formTitle');
const formTarea = document.getElementById('formTarea');
const tituloInput = document.getElementById('titulo');
const descripcionInput = document.getElementById('descripcion');
const fechaTerminalInput = document.getElementById('fechaTerminal');
const estadoSelect = document.getElementById('estado');
const tareaIdInput = document.getElementById('tareaId');

async function cargarTarea(id) {
    const token = obtenerToken();
    if (!token) {
        showNotification('No se encontró un token de autenticación. Por favor, inicie sesión nuevamente.', 'error');
        window.location.href = 'login.html';
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) throw new Error('Tarea no encontrada');

        const tarea = await response.json();
        formTitle.textContent = 'Editar Tarea';
        tituloInput.value = tarea.titulo;
        descripcionInput.value = tarea.descripcion || '';
        fechaTerminalInput.value = tarea.fechaLimite ? tarea.fechaLimite.slice(0, 16) : '';
        estadoSelect.value = tarea.estado;
        tareaIdInput.value = tarea.id;
    } catch (error) {
        console.error('Error cargando tarea:', error);
        showNotification('No se pudo cargar la tarea. Redirigiendo al listado.', 'error');
        setTimeout(() => window.location.href = 'tasks.html', 2000);
    }
}

async function guardarTarea(event) {
    event.preventDefault();

    const token = obtenerToken();
    if (!token) {
        showNotification('No se encontró un token de autenticación. Por favor, inicie sesión nuevamente.', 'error');
        window.location.href = 'login.html';
        return;
    }

    const tarea = {
        titulo: tituloInput.value.trim(),
        descripcion: descripcionInput.value.trim(),
        fechaLimite: fechaTerminalInput.value ? new Date(fechaTerminalInput.value).toISOString() : null,
        estado: estadoSelect.value, //Solo se enviará si se está editando
    };

    const tareaId = tareaIdInput.value;
    if (tareaId) {
        tarea.id = parseInt(tareaId, 10);
    }

    try {
        const method = tarea.id ? 'PUT' : 'POST';
        const url = tarea.id ? `${API_URL}/${tarea.id}` : API_URL;

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(tarea),
        });

        if (response.ok) {
            showNotification('Tarea guardada exitosamente.', 'success');
            setTimeout(() => window.location.href = 'dashboard.html', 1000);
        } else {
            const contentType = response.headers.get("Content-Type");
            if (contentType && contentType.includes("application/json")) {
                const errors = await response.json();
                errors.forEach(({ error }) => {
                    showNotification(`${error}`, 'info');
                });
            } else {
                const errorText = await response.text();
                showNotification(errorText || 'Hubo un problema al guardar la tarea.', 'error');
            }
        }
    } catch (error) {
        console.error('Error guardando tarea:', error);
        showNotification('No se pudo completar la operación. Intenta de nuevo más tarde.', 'error');
    }
}

function cerrarSesion() {
    const confirmar = confirm('¿Estás seguro de que deseas cerrar sesión?');
    if (confirmar) {
        localStorage.removeItem('authToken');
        window.location.href = 'login.html';
    }
}

//Si hay un id presente es que se está editando una tarea, en caso contrario se estará creando una nueva
if (id) {
    cargarTarea(id);
    estadoSelect.style.display = 'block';
} else {
    estadoSelect.value = 'pendiente';
    estadoSelect.disabled = true;
}

formTarea.addEventListener('submit', guardarTarea);
