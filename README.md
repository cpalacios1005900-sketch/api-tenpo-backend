# API Tenpo Backend

## 📌 Descripción

**API Tenpo Backend** es una aplicación desarrollada en **Java 17** con **Spring Boot 3** que permite registrar, administrar y consultar transacciones asociadas a clientes (*Tenpistas*).

El objetivo principal del proyecto es proveer una API REST robusta y escalable que permita:

- Registrar transacciones con información de monto, giro/comercio, fecha y cliente.
- Actualizar y eliminar transacciones existentes.
- Buscar y listar transacciones mediante distintos filtros.
- Gestionar la relación entre clientes y transacciones de forma eficiente.
- Proteger endpoints críticos mediante **rate limiting**.

---

## 🛠 Tecnologías utilizadas

- Java 17  
- Spring Boot 3  
- Spring Data JPA  
- PostgreSQL  
- Maven  
- Lombok  
- Springdoc OpenAPI (Swagger)  
- JUnit 5 + Mockito (pruebas unitarias)  
- Docker & Docker Compose  
- Rate Limiting  implementar un límite de 3 request por minuto por cliente para evitar abusos del sistema. Para Ejecutar en Postman incluye X-Client-Id en el header con el fin de simular un cliente  solo para (PUT, POST, DELETE) 
 

---

## 🗄 Configuración de la base de datos Manual

La aplicación utiliza **PostgreSQL** como motor de base de datos.

**Parámetros por defecto:**

- Base de datos: `tenpo_db`  
- Usuario: `postgres`  
- Contraseña: `postgres`

### Script de inicialización (`/api-tenpo-backend/src/main/resources/bd/init-db.sql`)

```sql
DROP DATABASE IF EXISTS tenpo_db;
CREATE DATABASE tenpo_db;

CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    nombre_tenpista VARCHAR(100) NOT NULL
);

CREATE TABLE transacciones (
    id_transaccion SERIAL PRIMARY KEY,
    numero_transaccion INT NOT NULL,
    monto_pesos INT NOT NULL CHECK (monto_pesos > 0),
    giro_comercio VARCHAR(100) NOT NULL,
    fecha_transaccion TIMESTAMP NOT NULL,
    id_cliente INT NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente)
        REFERENCES clientes(id_cliente)
        ON DELETE CASCADE
);
```

---

## 🔗 Endpoints principales

| Método | Endpoint | Descripción |
|------|---------|-------------|
| POST | `/api/transacciones/create` | Crear una nueva transacción |
| PUT | `/api/transacciones/update` | Actualizar una transacción |
| DELETE | `/api/transacciones/delete?idTransaccion={id}` | Eliminar una transacción |
| GET | `/api/transacciones/all` | Listar todas las transacciones |
| GET | `/api/transacciones/search?filtro={texto}` | Buscar transacciones |


---

### Swagger UI disponible en:
`http://localhost:8080/swagger-ui/index.html`
---

## ▶️ Ejecución del proyecto

### Opción 1: Ejecutar con Maven

```bash
mvn clean spring-boot:run
```

La aplicación se levantará en:  
`http://localhost:8080`

Una vez levantados
`http://localhost:8080/swagger-ui/index.html`

---

## Opción 2: 🐳 Docker

La aplicación cuenta con una imagen publicada en **Docker Hub**.

### 📥 Descargar imagen base de datos
```bash
docker pull cpalacios100590/postgres-tenpo:1.0
```

### ▶️ Ejecutar contenedor
```bash
docker run -d -p 5432:5432 --name postgres-tenpo cpalacios100590/postgres-tenpo:1.0
```

### 📥 Descargar imagen backend
```bash
docker pull cpalacios100590/spring-tenpo:1.0
```

### ▶️ Ejecutar contenedor
```bash
docker run -d -p 8080:8080 --name spring-tenpo cpalacios100590/spring-tenpo:1.0
```


Una vez levantados los contenedores, acceder a:

👉 `http://localhost:8080/swagger-ui/index.html`

---

## 🧪 Pruebas unitarias

El proyecto incluye pruebas unitarias utilizando:

- **JUnit 5**
- **Mockito**
- **Controller**
- **Repository**
- **Servicio**


## 👤 Autor

**Cristian Palacios**  
📧 Correo: [Cristian.palacios08@hotmail.com](mailto:Cristian.palacios08@hotmail.com)
