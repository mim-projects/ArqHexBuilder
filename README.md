# ArqHexBuilder

ArqHexBuilder lee tablas en formato SQL y genera los archivos necesarios para seguir la convención de arquitectura hexagonal.

### Características
- Generación automática basada en definición de tablas SQL. 
- Opción para crear la estructura del proyecto. 
- Inclusión automática de la capa web y de API.
  - La capa de API incluye un CRUD completo con autorización en cada petición.
- Organización por capas según los principios de la arquitectura hexagonal. 
- Evita duplicidad en carpetas existentes. 
- Cada clase incluye estructura básica y métodos iniciales.

#### Ejemplo

```sql
CREATE TABLE roles (
    ...
);

CREATE TABLE user (
    ...
);
```

Por cada tabla se genera la siguiente estructura (si la carpeta ya existe, se omite su creación). Cada clase contiene sus respectivos métodos para funcionar desde el momento de su creación:
- application
  - dto
    - `RolesDto.java`
    - `UserDto.java`
  - mapper 
    - `RolesMapper.java`
    - `UserMapper.java`
  - service
    - `RolesService.java`
    - `UserService.java`
  - usecases
- domain
  - entities
    - `Roles.java`
    - `User.java`
  -repository
    - `RolesRepository.java`
    - `UserRepository.java`
- infrastructure
  - adapter
    - repository
      - `RolesRepositoryImpl.java`
      - `UserRepositoryImpl.java`
    - service
      - `RolesServiceImpl.java`
      - `UserServiceImpl.java`
    - usecases
- entrypoint
  - api
    - annotations
    - controllers
      - base
        - `RolesController.java`
        - `UserController.java`
      - custom
    - factory
    - filters
    - utils
  - web
- configuration