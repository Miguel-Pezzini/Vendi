# Vendi Agent Guide

## Purpose
This repository is a monorepo for Vendi, an e-commerce application with:

- a Spring Boot backend in `vendi_backend`
- a Vue 3 + Vuetify frontend in `vendi_frontend`

The goal of this file is to give AI coding agents enough context to make safe, useful changes without guessing how the project works.

## Active Workspace
Treat these as the active parts of the repo:

- `vendi_backend`
- `vendi_frontend`
- `scripts`
- `README.md`

Do not spend time editing generated or legacy output unless the user explicitly asks:

- `vendi_frontend/dist`
- `vendi_backend/target`
- `vendi/out`
- `vendi/target`
- the top-level `vendi/` directory, which currently looks like legacy IDE/build output rather than a source module

## What The System Does

### Backend
The backend is a JWT-secured Spring Boot API for the Vendi store. The main implemented flows today are:

- authentication with `POST /auth/register` and `POST /auth/login`
- product management under `/products`
- product search/filtering with `limit`, `search`, and `categoryId`
- product photo retrieval under `/photo/{photoId}`
- category creation/listing under `/category`
- authenticated cart management under `/cart` and `/cart/items`
- authenticated Stripe checkout session creation/status under `/checkout`
- public Stripe webhook handling under `/checkout/webhook`
- authenticated self-service endpoints under `/me`, `/me/products`, and `/me/addresses`
- Prometheus metrics exposure through `/actuator/prometheus`

The backend also contains domain models for addresses, ratings, and wishlist data. Orders are now created through checkout and finalized from Stripe webhook events.

### Frontend
The frontend is a Vue SPA that consumes the backend API. The main user-facing flows currently present are:

- login and registration
- home page with banners, featured categories, and recent products
- store/catalog browsing with search and category filters
- product details with photo gallery hydration from the `/photo` endpoint
- cart page with subtotal calculation
- checkout form that starts Stripe Checkout and shows purchase confirmation after return
- account, addresses, and "my products" pages
- admin-only product creation/editing routes and admin dashboard shell

There is also a wishlist route and some admin/account UI scaffolding, but some of those flows are still partial.

## Repository Map

### Backend
- `vendi_backend/src/main/java/com/vendi/auth`: auth controller/service
- `vendi_backend/src/main/java/com/vendi/product`: product controllers, DTOs, mapper, repository, service
- `vendi_backend/src/main/java/com/vendi/category`: category CRUD
- `vendi_backend/src/main/java/com/vendi/cart`: cart endpoints and cart aggregation
- `vendi_backend/src/main/java/com/vendi/photo`: photo lookup and DTO mapping
- `vendi_backend/src/main/java/com/vendi/user`: user and `me` endpoints
- `vendi_backend/src/main/java/com/vendi/infra`: security and web config
- `vendi_backend/src/main/resources/application.properties`: local backend config
- `vendi_backend/compose.yaml`: PostgreSQL, Prometheus, and Grafana

### Frontend
- `vendi_frontend/src/core`: shared router, API plugin, services, store, global UI building blocks
- `vendi_frontend/src/authenticate`: login/register flows
- `vendi_frontend/src/home`: landing page
- `vendi_frontend/src/store`: catalog view and filters
- `vendi_frontend/src/product`: product details page
- `vendi_frontend/src/cart`: cart UI
- `vendi_frontend/src/checkout`: checkout form + Stripe redirect/return handling
- `vendi_frontend/src/user`: account, addresses, and product management views
- `vendi_frontend/src/admin`: admin dashboard shell

## Non-Negotiable Engineering Rules

### 1. TDD is mandatory for backend changes
Every backend feature, bug fix, and refactor must leave the repo with one or more relevant automated tests.

Do not treat backend tests as optional cleanup.

Do not merge backend logic changes without coverage.

If a backend change reveals missing test infrastructure, add the minimum viable backend test support in the same change instead of skipping tests.

Frontend changes do not require automated tests in this repository.

Do not add or maintain frontend tests unless the user explicitly asks for them.

### 2. Prefer unit and integration tests
Backend test coverage should focus primarily on:

- unit tests for pure business rules, mappers, validators, query builders, utilities, and service-level branching logic
- integration tests for Spring Boot wiring, persistence, security boundaries, and multi-layer API flows

Avoid adding end-to-end-only coverage when a unit or integration test would catch the behavior faster and more reliably.

### 3. Organize tests by behavior, not by convenience
Current backend tests already exist under:

- `vendi_backend/src/test/java/com/vendi/integration`
- `vendi_backend/src/test/java/com/vendi/dtoMocks`

Those tests are a starting point, not the finished structure.

When adding or improving tests:

- keep integration tests grouped by feature or endpoint behavior
- add unit tests in a clearly named package instead of mixing them into integration suites
- keep reusable test builders/helpers focused and readable
- avoid growing the `dtoMocks` package into a dumping ground

If you touch older tests, improve their naming and structure as part of the same change when it is low-risk to do so.

## Testing Strategy

### Backend testing
Use the existing Spring Boot and Testcontainers setup as the default integration-testing path.

Good candidates for backend unit tests:

- mappers such as `ProductMapper`, `PhotoMapper`, `CategoryMapper`
- validators such as `PositiveFloatValidator`
- service methods with important branching or error handling
- repository query parameter behavior that can be isolated

Good candidates for backend integration tests:

- auth registration/login flows
- secured vs public endpoint behavior
- product creation/update flows with categories and photos
- cart totals and cart item mutation flows
- category parent/child persistence behavior
- `me` endpoint behavior for authenticated users

### Frontend testing
Frontend automated tests are currently out of scope for this repository.

If you make a meaningful frontend change, validate it with focused manual checks or build/lint commands when relevant, but do not add frontend tests unless the user explicitly asks for them.

## Definition Of Done
A change is not done until all of the following are true:

- the relevant code works
- the relevant backend automated tests exist when backend code changed
- the affected backend tests pass locally, or any blocker is clearly documented
- docs are updated when behavior, commands, or architecture changed
- no generated output was hand-edited

## Backend Implementation Notes

- Security is stateless JWT auth via Spring Security.
- Public endpoints currently include auth, product reads, category reads, photo reads, Prometheus metrics, and the Stripe webhook endpoint.
- Product create/update/delete and category creation are admin-only.
- The frontend expects the backend on `http://localhost:8080`.
- CORS currently allows `http://localhost:3333`.
- Product photo data is loaded separately by photo id and returned as base64 data.
- Product creation requires a main photo and at least one category.
- Checkout creates a pending order from the authenticated user's cart, then redirects to hosted Stripe Checkout.
- Successful Stripe webhook events mark the order as paid and clear the user's cart.

## Frontend Implementation Notes

- Routing and auth guards live in `vendi_frontend/src/core/router/index.js`.
- The shared API client lives in `vendi_frontend/src/core/plugins/api.js`.
- The API plugin stores `token` and `roles` in `localStorage`.
- Product cards/details are hydrated through `productService`, which fetches image payloads from `/photo/{id}`.
- Cart UI depends on `cartService`, which rehydrates product summaries after cart API calls.
- Checkout UI depends on `checkoutService`, which creates Stripe sessions and polls order status after redirect.
- Keep using the current feature-based folder structure unless the user asks for a broader reorganization.

## Local Commands

### Backend
From `vendi_backend`:

```bash
docker compose up -d
mvn spring-boot:run
mvn test
```

### Frontend
From `vendi_frontend`:

```bash
npm ci
npm run dev
npm run build
```

### Run backend tests from the repo root
Despite the name, this script currently runs backend tests only:

```bash
./scripts/test_all.sh
```

Optional isolated Maven cache:

```bash
MAVEN_REPO_LOCAL=/tmp/vendi-m2 ./scripts/test_all.sh
```

Frontend dev server runs on `http://localhost:3333`.

The frontend API base URL defaults to `http://localhost:8080` and can be overridden with `VITE_API_URL`.

Local Stripe support also requires backend values for:

- `app.frontend-base-url`
- `stripe.secret-key`
- `stripe.webhook-secret`

## Documentation Rule
If you change the system in a way that affects how it is run, tested, or understood, update:

- `README.md`
- this `AGENTS.md`
- `CLAUDE.md` when the agent workflow guidance also changed
