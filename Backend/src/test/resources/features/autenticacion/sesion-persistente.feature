Feature: Gestión de sesión persistente
  Como usuario autenticado
  Quiero que mi sesión persista después de recargar la página
  Para poder seguir gestionando mis tareas sin tener que iniciar sesión nuevamente

  @test
  Scenario: El usuario sigue autenticado después de recargar la página
    Given El usuario ha iniciado sesión correctamente
    When El usuario recarga la página del dashboard
    Then El usuario sigue autenticado y ve el contenido del dashboard
