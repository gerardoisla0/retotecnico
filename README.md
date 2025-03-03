# Reto Técnico Seek Implementado con Arquitectura Limpia

## Descripción

Este proyecto fue desarrollado siguiendo el modelo de **Arquitectura Limpia** y programación reactiva, utilizando **Spring WebFlux** con **Java 17**. La estructura está organizada en capas bien definidas: dominio, infraestructura y aplicación, aplicando el patrón de **puertos y adaptadores**. Esta arquitectura modular mejora la mantenibilidad, permite una fácil extensión del sistema y facilita la implementación de pruebas unitarias.

Además, se implementó el patrón **CircuitBreaker** para manejar fallos repetidos y garantizar la estabilidad del sistema en escenarios de alta carga.

La cobertura del código se mantiene en un **27%**, asegurando que las pruebas unitarias se ejecutan correctamente en todos los subproyectos.

## Arquitectura

La arquitectura sigue los principios de **Clean Architecture** y está basada en el patrón **Hexagonal**, con las siguientes capas:

1. **Capa de Dominio**: Contiene las entidades de negocio y las interfaces de los casos de uso.
2. **Capa de Aplicación**: Implementa los casos de uso definidos en la capa de dominio, es donde se gestionan las reglas de negocio.
3. **Capa de Infraestructura**: Implementa las interacciones con el mundo exterior (bases de datos, APIs externas, etc.).
4. **Adaptadores y Puertos**: La comunicación entre las capas se maneja a través de puertos y adaptadores para desacoplar la lógica de negocio del resto del sistema.

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Instalación

### Requisitos

- **Java 17** (LTS)
- **Base de datos MySQL 8.0** (con Flyway para migraciones automáticas)
- **Gradle** para la gestión de tareas y ejecución del proyecto.
- **Docker** (para despliegue en AWS ECR y ECS)

### Pasos para iniciar

1. **Instalar Java 17**: Asegúrate de tener instalada la versión 17 de Java.
2. **Base de datos**: Utiliza **MySQL 8.0** y configura **Flyway** para que ejecute automáticamente los scripts de creación e inserción de la base de datos desde la carpeta `resources/db/migrations/`.
3. **IDE**: Abre el proyecto con **IntelliJ IDEA** (preferiblemente).
4. **Compilación y ejecución**:
    - Ejecuta el proyecto con **Gradle** utilizando el comando `bootRun`.
    - El puerto por defecto será **8083**.
5. **Generación de Token**: Utiliza el endpoint `/api/v1/user/token` para obtener un token JWT que autoriza las siguientes operaciones:

### Endpoints

1. **Generar Token**:
    ```bash
    curl --location 'http://localhost:8083/api/v1/user/token' \
    --header 'Content-Type: application/json' \
    --data '{
        "username": "gerardo"
    }'
    ```
2. **Crear Cliente**:
    ```bash
    curl --location 'http://localhost:8083/api/v1/user/create' \
    --header 'Authorization: Bearer <your_jwt_token>' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "name": "Julio",
        "lastName": "Isla",
        "documentId": "47154490",
        "age" : 33,
        "birthDay" : "22-08-1991"
    }'
    ```

    Puede responder 200 Ok, cuando se registró ó  un 422 en caso del usuario existir.

3. **Listar Clientes**:
    ```bash
    curl --location 'http://localhost:8083/api/v1/users/list' \
    --header 'Authorization: Bearer <your_jwt_token>' \
    --data ''
    ```

    Reponde una lista de todos los usuarios.

4. **Obtener Métricas del Cliente**:
    ```bash
    curl --location 'http://localhost:8083/api/v1/users/metrics' \
    --header 'Authorization: Bearer <your_jwt_token>' \
    --data ''
    ```
    Response las métricas de total de usuarios, desviación estándar y promedio de edad.

### Actuadores

Se utilizan varios endpoints de **Spring Actuator** para monitorear y gestionar el estado de la aplicación:

- **Health**: `actuator/health`
- **Beans**: `actuator/beans`
- **Metrics**: `actuator/metrics`
- **Mappings**: `actuator/mappings`
- **Loggers**: `actuator/loggers`

Estos endpoints proporcionan información crítica sobre el estado de la aplicación, incluyendo métricas de rendimiento y configuración de los beans.

### Docker y Despliegue en AWS

El proyecto cuenta con un **Dockerfile** para la creación de la imagen Docker. Esta imagen fue subida al **ECR de AWS** y posteriormente se definieron las tareas en **ECS (Elastic Container Service)** para el despliegue automático.

La **base de datos MySQL** se despliega en **RDS** (Amazon Relational Database Service), y la comunicación entre el servicio de la aplicación y la base de datos está protegida mediante **Security Groups** para asegurar el acceso controlado.

#### Dirección del Servicio:

- **Host Actual**: `100.26.242.0:8083` (Despliegue actual en AWS adjunto, puede variar).

### Variables de Entorno

Se deben configurar las siguientes variables de entorno para el correcto funcionamiento de la aplicación:

- `FLYWAY_URL`: URL de conexión a la base de datos.
    ```bash
    ${FLYWAY_URL:jdbc:mysql://127.0.0.1:3306/seek}
    ```

- `FLYWAY_USER`: Usuario para la conexión a la base de datos.
    ```bash
    ${FLYWAY_USER:root}
    ```

- `FLYWAY_PWD`: Contraseña para la conexión a la base de datos.
    ```bash
    ${FLYWAY_PWD:4kll2ni19h}
    ```

- `JWT_TOKEN`: El token JWT utilizado para autenticar las peticiones.
    ```bash
    ${JWT_TOKEN}
    ```

- `DB_URL`: URL de la base de datos MySQL (especificar la conexión adecuada a la instancia RDS de AWS).
    ```bash
    ${DB_URL:jdbc:mysql://your-database-host:3306/your-database-name}
    ```

### Patrones Implementados

- **CircuitBreaker**: Este patrón se utiliza para manejar fallos de servicios externos y garantizar la resiliencia del sistema. Si un servicio externo falla repetidamente, el **CircuitBreaker** interrumpe temporalmente las llamadas a ese servicio para evitar caídas en cascada y proteger el sistema de sobrecargas.

- **Builder**: El patrón **Builder** se utiliza para crear objetos complejos de manera flexible y reutilizable. Es especialmente útil en el proyecto para la creación de clientes, configuraciones y objetos de datos que requieren una construcción paso a paso, manteniendo el código limpio y comprensible.

- **Arquitectura Limpia**: La **Arquitectura Limpia** organiza el código en capas que se comunican a través de interfaces bien definidas. Separa la lógica de negocio de la infraestructura externa, facilitando la mantenibilidad y extensibilidad del sistema sin comprometer la lógica de dominio.

- **Repository**: El patrón **Repository** se utiliza para abstraer la interacción con la base de datos, permitiendo a los casos de uso acceder a las entidades sin conocer los detalles de implementación de la base de datos. Este patrón se implementa dentro de la capa de **Infraestructura**, y es comúnmente utilizado en combinación con **Spring Data** y **WebFlux** para manejar operaciones asincrónicas.

- **Factory**: El patrón **Factory** se utiliza para crear objetos de manera flexible y desacoplada. En lugar de instanciar objetos directamente, se utiliza una fábrica para crear instancias. Esto es útil para la creación de objetos con configuraciones específicas o dependencias complejas, y en **WebFlux** es común para la creación de servicios reactivos.

- **Adapter**: El patrón **Adapter** permite que el sistema interactúe con APIs o servicios externos mediante una interfaz común. En el contexto de **WebFlux**, se usa para adaptar diferentes tipos de comunicación reactiva (como el manejo de solicitudes HTTP, WebSockets, o interacciones con bases de datos reactivas) a un formato que sea fácilmente manejable por las capas de negocio.

- **Strategy**: El patrón **Strategy** se utiliza para definir una familia de algoritmos y permitir que sean intercambiables en tiempo de ejecución. Este patrón es útil en escenarios donde se requiere cambiar el comportamiento del sistema según el contexto (por ejemplo, en la ejecución de diferentes algoritmos de validación o procesamiento de datos en función de la entrada).

- **Decorator**: El patrón **Decorator** permite añadir funcionalidades adicionales a un objeto sin modificar su estructura original. En el caso de **Spring WebFlux**, se puede utilizar para añadir funcionalidades como el manejo de autenticación o control de acceso a servicios reactivos sin alterar la lógica del negocio.

---

### Modificación de IPs en los Puertos del Grupo de Seguridad

En caso de que sea necesario **agregar o quitar una IP** en los puertos específicos 3306 (MySQL) o 8083 (aplicación) en el grupo de seguridad de AWS, puedes hacerlo siguiendo estos pasos en la consola de administración de VPC:

1. Accede a la consola de **VPC** en AWS: [Consola de VPC](https://us-east-1.console.aws.amazon.com/vpcconsole/home?region=us-east-1#SecurityGroup:group-id=sg-0fb40b8016e5ad49b).
2. Selecciona el **grupo de seguridad** correspondiente con el **ID** `sg-0fb40b8016e5ad49b`.
3. En el panel de navegación izquierdo, haz clic en **Inbound rules** (Reglas de entrada) para modificar las reglas de acceso entrante.
4. Para **agregar una IP**:
    - Haz clic en **Edit inbound rules** (Editar reglas de entrada).
    - Haz clic en **Add rule** (Agregar regla).
    - Selecciona el **puerto** correspondiente: `3306` para MySQL o `8083` para la aplicación.
    - En el campo **Source** (Origen), especifica la **IP** o el rango de IPs que deseas permitir. Si deseas restringir el acceso solo a ciertas IPs, ingresa su rango (por ejemplo, `192.168.1.0/24`).
5. Para **quitar una IP**:
    - Encuentra la regla correspondiente al puerto `3306` o `8083` en las reglas de entrada.
    - Haz clic en el icono de **eliminar** junto a la regla que deseas modificar o quitar.
6. **Guarda los cambios** para que las modificaciones surtan efecto.

Es importante que solo se permita el acceso a las IPs de confianza, especialmente para puertos sensibles como el `3306` (MySQL), para evitar accesos no autorizados. Asegúrate de revisar las configuraciones de acceso y restringir el tráfico según las necesidades de seguridad de tu aplicación.


## Conclusión

Este proyecto está diseñado para ser fácilmente extensible y mantenible, aplicando los principios de la **Arquitectura Limpia** y utilizando herramientas modernas como **Spring WebFlux**, **Docker**, **AWS ECS**, y **RDS**. Con los patrones de diseño adecuados y un enfoque modular, se logra un sistema resiliente y escalable.

