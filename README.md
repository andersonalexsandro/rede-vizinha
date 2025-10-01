# 🚀 Rede Vizinha API

API da Rede Vizinha — uma rede social focada em comunidades geográficas (bolhas).  
Projeto desenvolvido em **Spring Boot**, utilizando **PostgreSQL** como banco de dados e **Swagger/OpenAPI** para documentação interativa.

---

## 📦 Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado na sua máquina:

- [Docker](https://docs.docker.com/get-docker/)  
- [Docker Compose](https://docs.docker.com/compose/install/)  
- [Java 17+](https://adoptium.net/)  
- [Maven](https://maven.apache.org/install.html)  

---

## 🐘 Banco de Dados (Postgres com Docker)

O projeto já inclui um arquivo `docker-compose.yml`.  
Para subir o banco, execute:

```bash
docker-compose up -d
```

Isso vai criar um container com:

- **Banco:** `rede-vizinha`
- **Usuário:** `postgres`
- **Senha:** `123456`
- **Porta:** `5432`

📂 Os dados ficam persistidos na pasta local `postgres-data/`.

Para verificar se o banco subiu corretamente:

```bash
docker ps
```

---

## ⚙️ Configuração do Spring Boot

No arquivo `application.yml` já está configurado:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/rede-vizinha
    username: postgres
    password: 123456
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: validate
  flyway:
    enabled: true
```

Ou seja, ao subir a aplicação o **Flyway** aplica as migrações automaticamente.

---

## ▶️ Rodando a Aplicação

Após subir o banco, rode a aplicação com Maven:

```bash
./mvnw spring-boot:run
```

Ou build + execução:

```bash
./mvnw clean package
java -jar target/rede-vizinha-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em:

👉 `http://localhost:8080`

---

## 📖 Documentação da API (Swagger)

A documentação interativa está habilitada com **JWT Auth**.

Acesse:

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

No canto superior direito, use o botão **Authorize** para informar o **token JWT** obtido via login (`/auth/login`).  
Exemplo:

```
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 🔑 Fluxo de Autenticação

1. Registre-se:  
   `POST /auth/register`  
2. Faça login:  
   `POST /auth/login` → retorna o **JWT Token**  
3. Clique em **Authorize** no Swagger e cole o token.  
4. Agora você pode testar todas as rotas protegidas.

---

## 🧹 Parar e limpar containers

Para parar o banco:

```bash
docker-compose down
```

Para parar e remover volumes (apagar dados):

```bash
docker-compose down -v
```

---

✅ Agora qualquer pessoa consegue subir seu projeto **do zero** com Docker e acessar o **Swagger** em minutos.
