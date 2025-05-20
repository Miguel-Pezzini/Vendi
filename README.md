# Vendi - E-commerce

![Vendi Logo](https://via.placeholder.com/150)

## 📌 Sobre o Projeto
O **Vendi** é um sistema de e-commerce completo, desenvolvido para oferecer uma experiência fluida e intuitiva aos usuários. A plataforma conta com um frontend moderno utilizando **Vue 3** e **Vuetify**, enquanto o backend robusto é construído com **Spring Boot**.

---

## 🚀 Tecnologias Utilizadas

### 🖥️ **Frontend**
- [Vue.js 3](https://vuejs.org/)
- [Vue Router](https://router.vuejs.org/)
- [Vuetify](https://vuetifyjs.com/) (Componentes UI)
- [Axios](https://axios-http.com/) (Requisições HTTP)
- [Vite](https://vitejs.dev/) (Ferramenta de build)

### 🛠️ **Backend**
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security) (Autenticação/Autorização)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) (Gerenciamento de banco de dados)
- [PostgreSQL](https://www.postgresql.org/) (Banco de dados relacional)
- [JWT](https://jwt.io/) (Token de autenticação)

---

## 🎯 Funcionalidades
- 📦 **Gerenciamento de Produtos** (CRUD completo)
- 🛒 **Carrinho de Compras** com cálculo de total
- 🔑 **Autenticação e Autorização** (JWT + Spring Security)
- 📜 **Cadastro e Login de Usuários**
- 💳 **Integração com Pagamentos** (Stripe/PayPal)
- 📊 **Painel Administrativo** para gestão de pedidos e clientes

---

## 🛠️ Como Rodar o Projeto

### 🔹 1. Clonar o repositório
```sh
  git clone https://github.com/seu-usuario/vendi.git
  cd vendi
```

### 🔹 2. Configurar o Backend (Spring Boot)
```sh
  cd backend
  mvn clean install
  mvn spring-boot:run
```
> **Obs:** Certifique-se de configurar o banco de dados no `application.properties` antes de rodar.

### 🔹 3. Configurar o Frontend (Vue 3 + Vuetify)
```sh
  cd frontend
  npm install
  npm run dev
```

Acesse no navegador: `http://localhost:3000`

---

## 🔗 API Endpoints

### 📌 **Autenticação**
- `POST /api/auth/register` → Registro de usuário
- `POST /api/auth/login` → Login e geração de token JWT

### 📌 **Produtos**
- `GET /api/products` → Listar todos os produtos
- `GET /api/products/{id}` → Detalhes de um produto específico
- `POST /api/products` → Criar um novo produto *(Admin)*
- `PUT /api/products/{id}` → Atualizar um produto *(Admin)*
- `DELETE /api/products/{id}` → Deletar um produto *(Admin)*

### 📌 **Carrinho de Compras**
- `POST /api/cart/add` → Adicionar item ao carrinho
- `GET /api/cart` → Visualizar carrinho do usuário
- `DELETE /api/cart/remove/{id}` → Remover item do carrinho
---

## 🛡️ Segurança e Autenticação
O **Vendi** usa **JWT (JSON Web Token)** para autenticação. Para acessar rotas protegidas, inclua o token JWT no cabeçalho da requisição:
```sh
Authorization: Bearer <seu-token-aqui>
```
