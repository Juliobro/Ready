const API_URL = 'http://localhost:8080/tareas';

function obtenerToken() {
    return localStorage.getItem('authToken');
}

function formatearFecha(fecha) {
    if (!fecha) return 'Sin definir';
    const date = new Date(fecha);
    return date.toLocaleString('es-ES', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

async function cargarTareas(pagina = 0) {
    const token = obtenerToken();
    if (!token) {
        showNotification('No se encontró un token de autenticación. Por favor, inicie sesión nuevamente.', 'error');
        window.location.href = 'login.html';
        return;
    }

    try {
        const headers = new Headers({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });

        const response = await fetch(`${API_URL}?page=${pagina}`, { headers });
        if (!response.ok) throw new Error('Error al obtener tareas');

        const data = await response.json();
        const tareas = data.content;
        const tbody = document.getElementById('tareasBody');
        const noTareas = document.getElementById('noTareas');

        //Se limpia la tabla antes de rellenarla en caso tal
        tbody.innerHTML = '';

        //Se muestra un mensaje de que no hay tareas cuando sea el caso
        if (tareas.length === 0) {
            noTareas.style.display = 'block';
            return;
        } else {
            noTareas.style.display = 'none';
        }

        //Se rellena la tabla con las tareas del usuario
        tareas.forEach((tarea) => {
            const tr = document.createElement('tr');

            tr.innerHTML = `
                <td>${tarea.titulo}</td>
                <td>${tarea.descripcion || 'N/A'}</td>
                <td>${formatearFecha(tarea.fechaLimite)}</td>
                <td>${tarea.estado.charAt(0).toUpperCase() + tarea.estado.slice(1)}</td>
                <td style="padding: 12px 10px;">
                    <button id="editarTareaBtn" class="btn btn-info btn-sm" onclick="editarTarea('${tarea.id}')">
                        <i class="fa fa-edit"></i>
                    </button>
                    <button id="eliminarTareaBtn" class="btn btn-danger btn-sm" onclick="eliminarTarea('${tarea.id}')">
                        <i class="fa fa-trash"></i>
                    </button>
                </td>
            `;

            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error cargando tareas:', error);
        showNotification('Hubo un problema al cargar las tareas. Por favor, intenta nuevamente.', 'error');
    }
}

async function eliminarTarea(id) {
    const token = obtenerToken();
    if (!token) {
        showNotification('No se encontró un token de autenticación. Por favor, inicie sesión nuevamente.', 'error');
        window.location.href = 'login.html';
        return;
    }

    const confirmacion = confirm(`¿Está seguro de eliminar esta tarea?`);
    if (!confirmacion) return;

    try {
        const headers = new Headers({
            'Authorization': `Bearer ${token}`
        });

        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
            headers
        });

        if (response.ok) {
            showNotification('Tarea eliminada exitosamente.', 'success');
            cargarTareas();
        } else {
            showNotification('Error al eliminar la tarea. Por favor, intenta nuevamente.', 'error');
        }
    } catch (error) {
        console.error('Error eliminando tarea:', error);
        showNotification('Hubo un problema al eliminar la tarea. Por favor, intenta nuevamente.', 'error');
    }
}

function editarTarea(id = null) {
    const url = id ? `task-form.html?id=${encodeURIComponent(id)}` : 'task-form.html';
    window.location.href = url;
}

function cerrarSesion() {
    const confirmar = confirm('¿Estás seguro de que deseas cerrar sesión?');
    if (confirmar) {
        localStorage.removeItem('authToken');
        window.location.href = 'login.html';
    }
}

document.getElementById('nuevaTareaBtn').addEventListener('click', () => editarTarea());
document.addEventListener('DOMContentLoaded', () => cargarTareas());
