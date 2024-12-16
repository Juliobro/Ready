Feature: Editar tarea existente
  Como usuario autenticado
  Quiero editar una tarea válida o manejar errores si los datos son inválidos
  Para actualizar correctamente mi lista de tareas en el dashboard

  @test
  Scenario: El usuario ingresa a la aplicación e intenta editar una tarea con datos válidos
    Given El usuario ha iniciado sesión correctamente
    And Existe una tarea en el dashboard
    When El usuario modifica el título, agrega una descripción, actualiza la fecha a futura y cambia el estado a "Completada"
    And El usuario guarda la tarea
    Then La tarea "actualizada" aparece en la lista de tareas

  @test
  Scenario: El usuario ingresa a la aplicación e intenta editar una tarea con datos inválidos
    Given El usuario ha iniciado sesión correctamente
    And Existe una tarea en el dashboard
    When El usuario borra el título y asigna una fecha pasada
    And El usuario guarda la tarea
    Then El usuario permanece en la página de creación de tareas
