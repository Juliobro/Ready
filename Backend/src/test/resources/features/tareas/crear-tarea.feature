Feature: Crear nueva tarea
  Como usuario autenticado
  Quiero poder crear una tarea válida o manejar errores si los datos son inválidos
  Para que pueda gestionar mis pendientes fácilmente en el dashboard

  @test
  Scenario: El usuario ingresa a la aplicación e intenta crear una tarea ingresando datos válidos
    Given El usuario ha iniciado sesión correctamente
    When El usuario ingresa un título válido y una fecha futura en el formulario de creación
    And El usuario guarda la tarea
    Then La tarea "creada" aparece en la lista de tareas

  @test
  Scenario: El usuario ingresa a la aplicación e intenta crear una tarea ingresando datos inválidos
    Given El usuario ha iniciado sesión correctamente
    When El usuario deja el título en blanco y asigna una fecha pasada en el formulario de creación
    And El usuario guarda la tarea
    Then El usuario permanece en la página de creación de tareas
