A continuación, se mostrarán las respuestas de las peticiones
*CRUD* para la entidad `Tarea`, así como las peticiones de 
registro y autenticación de un `Usuario` utilizando *JWT*.

## Registro
![image](https://github.com/user-attachments/assets/6ae04464-8b05-455c-80f7-7b626125f2f7)

## Autenticación
![image](https://github.com/user-attachments/assets/4b2e6c04-1325-4c4f-ad26-8f55089c4961)

## Crear tarea
![image](https://github.com/user-attachments/assets/c28ad0f9-e0e6-49fc-8d73-3d0e97fd74ae)

## Mostrar tarea
![image](https://github.com/user-attachments/assets/171445c8-f056-4769-8cb1-cc016091b444)

## Listar tareas
![image](https://github.com/user-attachments/assets/17da3155-e0cb-4184-aaa9-3e5ddc86155f)

## Actualizar tarea
![image](https://github.com/user-attachments/assets/27e149c1-af14-40ba-95ac-e188a57f0589)

## Eliminar tarea
![image](https://github.com/user-attachments/assets/bd57a784-6227-428f-8869-b5f9c91e714f)

---

### Notas adicionales:
Se ha establecido una relación entre las entidades `Usuario` y `Tarea`, para que dependiendo del usuario que esté autenticado en la aplicación se pueda otorgar acceso a sus tareas correspondientes en la base de datos.

Siendo así, por ejemplo, a la hora de querer *Mostrar*, *Actualizar* o *Eliminar* una tarea, la aplicación verificará que el `id` del usuario relacionado a la tarea concuerde con el del usuario autenticado. En caso afirmativo se dará acceso, y en caso de que no devolverá un estado `403 - Forbidden`. A la hora de *Crear* una tarea simplemente se relacionará la misma con el usuario que esté autenticado y creándola por medio de su `id`, y para *Listar* tareas únicamente se mostrarán las que estén relacionadas con el mismo. Si una tarea no existe devuelve un estado `404 - Not Found`.
