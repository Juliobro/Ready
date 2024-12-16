Feature: Inicio de sesión de un usuario
  Como usuario
  Quiero iniciar sesión en la aplicación Ready
  Para poder gestionar mis tareas de forma personalizada

  @test
  Scenario: El usuario ingresa a la página de inicio de sesión e intenta iniciar sesión con credenciales válidas
    Given El usuario está en la página de inicio de sesión
    When El usuario introduce un correo electrónico válido y una contraseña válida
    And Hace clic en el botón "Iniciar sesión"
    Then El usuario es redirigido a su dashboard

  @test
  Scenario: El usuario ingresa  a la página de inicio de sesión e intenta iniciar sesión con credenciales inválidas
    Given El usuario está en la página de inicio de sesión
    When El usuario introduce un correo electrónico inválido y una contraseña inválida
    And Hace clic en el botón "Iniciar sesión"
    Then El usuario sigue en la página de inicio de sesión
    And El usuario ve el mensaje de error de "Credenciales incorrectas"
