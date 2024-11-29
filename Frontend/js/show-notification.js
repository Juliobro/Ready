function showNotification(message, type = 'info') {
    const notificationContainer = document.getElementById('notification-container');

    //Se crea el elemento de la notificación dependiendo del tipo de mensaje que se desea que sea
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;

    //Se agrega la notificación al contenedor
    notificationContainer.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 4000);
}