# CLAUDE.md

`AGENTS.md` is the main source of truth for repo instructions. Keep this file aligned with it and use this as the fast-start version for Claude-based agents.

## Repo Summary
Vendi is a monorepo with:

- `vendi_backend`: Spring Boot API with JWT auth, products, categories, cart, photos, and `me` endpoints
- `vendi_frontend`: Vue 3 + Vuetify SPA for auth, catalog browsing, product details, cart, checkout shell, and admin/product-management flows

Work in `vendi_backend` and `vendi_frontend`. Avoid editing generated output or the legacy top-level `vendi/` directory unless the user explicitly asks.

## Hard Rules

### TDD is required for backend changes
Every backend feature, fix, and refactor must include automated test coverage.

Prefer unit and integration tests.

Do not ship backend logic changes with only manual validation.

### Backend tests
Current backend tests live under:

- `vendi_backend/src/test/java/com/vendi/integration`
- `vendi_backend/src/test/java/com/vendi/dtoMocks`

When you add tests:

- keep integration tests focused on endpoint and cross-layer behavior
- add unit tests for mappers, validators, services, and utilities
- improve test organization when touching older tests instead of expanding messy patterns

### Frontend tests
Frontend automated tests are out of scope for this repository.

If you change frontend behavior, validate it with focused manual checks or build/lint commands when relevant, but do not add frontend tests unless explicitly requested.

## What The App Does

### Backend
- `POST /auth/register`, `POST /auth/login`
- `GET/POST/PUT/DELETE /products`
- `GET /products/{id}/details`
- `GET/POST /category`
- `GET /photo/{photoId}`
- `GET /cart`, `POST /cart/items`, `DELETE /cart/items/{productId}`
- `GET /me`, `PUT /me`, `PUT /me/password`, `GET /me/products`, `GET/POST /me/addresses`, `PUT /me/addresses/{addressId}`, `DELETE /me/addresses/{addressId}`, `PUT /me/addresses/{addressId}/active`

Security is JWT-based. Product/category writes are role-restricted. Prometheus metrics are exposed at `/actuator/prometheus`.

### Frontend
- auth screens in `src/authenticate`
- store catalog and filters in `src/store`
- product details in `src/product`
- cart in `src/cart`
- checkout shell in `src/checkout`
- account and product management in `src/user`
- shared API/router/services in `src/core`

The frontend defaults to `http://localhost:3333` and talks to `http://localhost:8080` unless `VITE_API_URL` is set.

## Change Checklist
Before finishing, confirm that:

- backend code changes are covered by tests
- relevant backend or frontend commands were run when possible
- `README.md` is updated if behavior, setup, or architecture changed
- this file and `AGENTS.md` stay consistent when workflow rules change

## Useful Commands

### Backend
```bash
cd vendi_backend
docker compose up -d
mvn spring-boot:run
mvn test
```

### Frontend
```bash
cd vendi_frontend
npm ci
npm run dev
npm run build
```
