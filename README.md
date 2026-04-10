# Vendi

Vendi is an e-commerce monorepo with a Spring Boot backend and a Vue 3 + Vuetify frontend.

The project already supports the main shopping flow foundations: authentication, catalog browsing, product details with images, category filtering, cart management, and admin-managed product creation/editing. Some screens and domain models exist beyond that, but the most complete flows today are products, categories, cart, auth, photos, and authenticated user data.

## Monorepo structure

```text
.
├── vendi_backend
│   ├── src/main/java/com/vendi
│   ├── src/main/resources
│   ├── src/test/java
│   └── compose.yaml
├── vendi_frontend
│   ├── src
│   └── package.json
├── AGENTS.md
├── CLAUDE.md
└── README.md
```

## What the system does

### Backend
The backend is a JWT-secured Spring Boot API that currently handles:

- user registration and login
- public product listing and product details
- admin product creation, update, and deletion
- category listing and admin category creation
- product photo retrieval by photo id
- authenticated cart retrieval, item add, and item removal
- authenticated Stripe checkout session creation and checkout status lookup
- public Stripe webhook processing for payment confirmation
- authenticated order listing and order detail lookup under `/orders`
- authenticated `me` endpoints for profile, products, and addresses
- Prometheus metrics for observability

The backend also contains domain models for wishlist, ratings, and addresses. Orders are now created through the checkout flow and finalized from Stripe webhook events.

### Frontend
The frontend is a Vue single-page application that currently includes:

- login and registration screens
- home page with banners, featured categories, and recent products
- store page with search and category filters
- product details page with gallery images loaded from the backend photo API
- cart page with hydrated product data and subtotal calculation
- checkout form that creates a Stripe Checkout session and confirms the order after payment
- account, addresses, order tracking, and "my products" pages
- admin-only product creation/editing routes and admin dashboard shell

## Tech stack

### Frontend
- Vue 3
- Vue Router
- Vuex
- Vuetify 3
- Axios
- Vite

### Backend
- Java 17+
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- JUnit 5
- Testcontainers
- Prometheus
- Grafana

## Local development

### Prerequisites
- Java 17 or newer
- Maven 3.8+
- Node.js LTS
- Docker and Docker Compose support

### 1. Clone the repository

```bash
git clone https://github.com/Miguel-Pezzini/vendi.git
cd vendi
```

### 2. Start infrastructure

The backend includes a Docker Compose file with PostgreSQL, Prometheus, and Grafana:

```bash
cd vendi_backend
docker compose up -d
```

Services started by `compose.yaml`:

- PostgreSQL on `localhost:5432`
- Prometheus on `localhost:9090`
- Grafana on `localhost:3000` with `admin/admin`

### 3. Run the backend

From `vendi_backend`:

```bash
mvn spring-boot:run
```

Default local backend settings live in `vendi_backend/src/main/resources/application.properties`.

The backend expects:

- database URL: `jdbc:postgresql://localhost:5432/vendi`
- database user: `user`
- database password: `1234`
- API base URL: `http://localhost:8080`

To enable the checkout flow locally, also configure Stripe in `vendi_backend/src/main/resources/application.properties` or your runtime environment:

- `app.frontend-base-url=http://localhost:3333`
- `stripe.secret-key=<your-stripe-secret-key>`
- `stripe.webhook-secret=<your-stripe-webhook-secret>`

Prometheus metrics are exposed at:

```text
http://localhost:8080/actuator/prometheus
```

### 4. Run the frontend

From `vendi_frontend`:

```bash
npm ci
npm run dev
```

The frontend runs on:

```text
http://localhost:3333
```

The frontend API client defaults to `http://localhost:8080`. If needed, override it with:

```bash
VITE_API_URL=http://localhost:8080
```

For local Stripe webhook forwarding, use the frontend and backend defaults above and point Stripe CLI at the backend:

```bash
stripe listen --forward-to localhost:8080/checkout/webhook
```

Note: the backend CORS configuration currently allows `http://localhost:3333`.

## Testing

### Backend tests
Backend tests live under `vendi_backend/src/test/java`.

Current automated coverage is centered on integration tests using Spring Boot + Testcontainers, especially for:

- auth
- categories
- products

Run them with:

```bash
cd vendi_backend
mvn test
```

### Frontend tests
Frontend automated tests are not maintained in this repository.

If you change frontend behavior, use focused manual verification or build/lint commands as needed instead of adding frontend tests unless that work is explicitly requested.

### Run backend tests from the repo root
Despite the script name, it currently runs backend tests only:

```bash
./scripts/test_all.sh
```

If you want the backend test run to use an isolated Maven cache, set `MAVEN_REPO_LOCAL`:

```bash
MAVEN_REPO_LOCAL=/tmp/vendi-m2 ./scripts/test_all.sh
```

## API surface

### Authentication
- `POST /auth/register`
- `POST /auth/login`

### Products
- `GET /products`
- `GET /products/{productId}`
- `GET /products/{productId}/details`
- `POST /products`
- `PUT /products/{productId}`
- `DELETE /products/{productId}`

### Categories
- `GET /category`
- `POST /category`

### Photos
- `GET /photo/{photoId}`

### Cart
- `GET /cart`
- `POST /cart/items`
- `DELETE /cart/items/{productId}`

### Checkout
- `POST /checkout/session`
- `GET /checkout/session/{sessionId}`
- `POST /checkout/webhook`

### Orders
- `GET /orders`
- `GET /orders/{orderId}`

### Authenticated user data
- `GET /me`
- `GET /me/products`
- `GET /me/addresses`

## Security

Vendi uses JWT authentication.

To call protected endpoints, send:

```text
Authorization: Bearer <jwt-token>
```

Role behavior currently includes:

- public read access for products, categories, and photos
- admin-only write access for product and category management
- authenticated access for cart, checkout session endpoints, `orders`, and `me` endpoints
- public access for the Stripe webhook endpoint only

## Frontend architecture notes

- shared routing lives in `vendi_frontend/src/core/router`
- shared HTTP client lives in `vendi_frontend/src/core/plugins/api.js`
- auth session is stored in `localStorage`
- product images are hydrated on demand through the `/photo/{id}` backend endpoint
- the store page supports `search` and `category` query params
- the account area includes `/account/orders`, which expands order details and status history from `/orders`

## Backend architecture notes

- security config lives in `vendi_backend/src/main/java/com/vendi/infra/security`
- CORS config lives in `vendi_backend/src/main/java/com/vendi/infra/web/CorsConfig.java`
- product query filtering supports `limit`, `search`, and `categoryId`
- cart totals are computed server-side in `CartResponseDTO`
- checkout creates pending orders, redirects the user to hosted Stripe Checkout, and confirms payment through `/checkout/webhook`

## Development rule

For this repository, every backend feature, bug fix, or refactor is expected to ship with automated tests. The preferred focus is on unit tests and integration tests. Frontend changes currently do not require automated tests.
