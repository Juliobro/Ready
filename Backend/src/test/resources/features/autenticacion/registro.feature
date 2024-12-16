Feature: Registro de un nuevo usuario
  Como usuario
  Quiero registrarme en la aplicación Ready
  Para poder gestionar mis tareas de forma personalizada

  @test
  Scenario: El usuario ingresa a la página de registro e intenta registrarse utilizando datos válidos
    Given El usuario está en la página de registro
    When El usuario introduce un nombre de usuario válido, un correo electrónico válido y una contraseña válida
    And Hace clic en el botón "Registrar"
    Then El registro se completa exitosamente
    And El usuario es redirigido a su dashboard

  @test
  Scenario: El usuario ingresa a la página de registro e intenta registrarse utilizando datos inválidos
    Given El usuario está en la página de registro
    When El usuario introduce un nombre de usuario vacío, un correo electrónico vacío y una contraseña inválida
    And Hace clic en el botón "Registrar"
    Then El registro no se completa
    And El usuario permanece en la página de registro
