Feature: Eliminar tarea existente
  Como usuario autenticado
  Quiero eliminar una tarea desde el dashboard
  Para no tener tareas que ya no necesito

  @test
  Scenario: El usuario ingresa a la aplicación e intenta eliminar una tarea existente
    Given El usuario ha iniciado sesión correctamente
    And Existe una tarea en el dashboard
    When El usuario elimina una tarea
    Then La tarea eliminada ya no aparece en la lista de tareas
