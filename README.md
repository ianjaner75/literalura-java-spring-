LiterAlura (Java + Spring Boot + PostgreSQL)
Catálogo de libros en consola. Permite buscar libros en la API de Gutendex y guardarlos en PostgreSQL. Luego puedes listar libros, autores, autores vivos en un año y libros por idioma.

🧰 Stack
Java 21

Spring Boot 3.5 (Spring Data JPA, HikariCP)

PostgreSQL 16+

Jackson (JSON)

Maven

✨ Funcionalidades
 1) Buscar libro por título en Gutendex y guardarlo (evita duplicados)

 2) Listar libros registrados (con autores)

 3) Listar autores registrados

 4) Listar autores vivos en un año (ej.: 1600)

 5) Listar libros por idioma (ES/EN/FR/PT)

    Variables de entorno
Define estas variables (en tu SO o en Run → Edit Configurations → Environment variables):

DB_HOST=localhost

DB_PORT=5432

DB_NAMELITERATURA=literatura (nombre de tu base)

DB_USER=postgres

DB_PASSWORD=******

El proyecto lee estas variables en application.properties:

properties
Copiar
Editar
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAMELITERATURA:literatura}
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

Requisitos previos
Java 21 instalado (java -version)

PostgreSQL 16+ corriendo (puerto 5432 por defecto)

Base de datos creada (ej. literatura)

▶️ Ejecutar
Con Maven

bash
Copiar
Editar
./mvnw spring-boot:run
Con IntelliJ IDEA

Abrir el proyecto → botón Run en LiteraturaApplication.
