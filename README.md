# Book Store

Am realizat o aplicatie web pentru administrarea unei librarii online. Proiectul este impartit in microservicii pentru autentificare, catalog, comenzi si rutare prin API Gateway, iar interfata este facuta separat in React.

## Repository-uri

- Backend / microservicii: `IonescuMihaiLeonard/Book-Store`
- Frontend React: `IonescuMihaiLeonard/Book-Store-FrontEnd`

## Tehnologii

Backend:

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Spring Boot Actuator
- Maven
- Docker

Frontend:

- React
- Vite
- React Router
- JWT decode
- CSS modular pe pagini/componente

## Arhitectura

Am impartit aplicatia in trei microservicii de business si un API Gateway:

| Componenta | Port local | Responsabilitate |
| --- | ---: | --- |
| `auth-service` | `8081` | autentificare, inregistrare, utilizatori, roluri, JWT |
| `catalog-service` | `8082` | carti, autori, categorii, review-uri |
| `order-service` | `8083` | cos, adrese, comenzi, plati |
| `api-gateway` | `8085` | rutare centralizata, filtrare auth/admin, injectare `userId` |
| `frontend` | `5173` | interfata React pentru utilizatori si administratori |
| `mysql` | `13306` | baze de date pentru microservicii |

```mermaid
flowchart LR
    Browser[Browser / React Frontend] -->|/api/v1| Gateway[API Gateway :8085]
    Gateway --> Auth[auth-service :8081]
    Gateway --> Catalog[catalog-service :8082]
    Gateway --> Orders[order-service :8083]
    Auth --> AuthDB[(auth_service)]
    Catalog --> CatalogDB[(catalog_service)]
    Orders --> OrderDB[(order_service)]
    AuthDB --> MySQL[(MySQL :13306)]
    CatalogDB --> MySQL
    OrderDB --> MySQL
```

In frontend folosesc proxy-ul Vite pentru `/api/v1`, iar cererile ajung in `api-gateway`. Gateway-ul trimite mai departe cererile catre serviciul potrivit si verifica accesul la rutele de admin.

## Baze de Date

Fiecare microserviciu are baza lui:

- `auth_service`: utilizatori si roluri
- `catalog_service`: carti, autori, categorii si review-uri
- `order_service`: cosuri, adrese, comenzi, item-uri de comanda si plati

Am definit relatiile JPA directe in interiorul fiecarui microserviciu. Relatiile dintre servicii sunt pastrate prin identificatori logici, de exemplu `userId` si `bookId`, pentru a evita foreign key-uri intre baze de date diferite.

## Diagrama ERD

```mermaid
erDiagram
    USERS {
        bigint id PK
        string username UK
        string email UK
        string password
        string role
    }

    BOOK {
        bigint id PK
        string title
        string isbn UK
        double price
        int stock
        string description
        string imageUrl
    }

    AUTHOR {
        bigint id PK
        string name
    }

    CATEGORY {
        bigint id PK
        string name
    }

    REVIEW {
        bigint id PK
        bigint userId
        int rating
        string comment
        bigint book_id FK
    }

    BOOKS_AUTHORS {
        bigint book_id FK
        bigint author_id FK
    }

    BOOKS_CATEGORIES {
        bigint book_id FK
        bigint category_id FK
    }

    CART {
        bigint id PK
        bigint userId UK
    }

    CART_ITEM {
        bigint id PK
        bigint cart_id FK
        bigint bookId
        string bookTitle
        double unitPrice
        int quantity
    }

    ADDRESS {
        bigint id PK
        bigint userId
        string street
        string city
        string country
        string zipCode
    }

    ORDERS {
        bigint id PK
        bigint userId
        string status
        datetime createdAt
        bigint shipping_address_id FK
    }

    ORDER_ITEM {
        bigint id PK
        bigint order_id FK
        bigint bookId
        string bookTitle
        double price
        int quantity
    }

    PAYMENT {
        bigint id PK
        bigint order_id FK
        string status
        double amount
        datetime paidAt
    }

    BOOK ||--o{ REVIEW : has
    BOOK ||--o{ BOOKS_AUTHORS : links
    AUTHOR ||--o{ BOOKS_AUTHORS : writes
    BOOK ||--o{ BOOKS_CATEGORIES : links
    CATEGORY ||--o{ BOOKS_CATEGORIES : groups

    CART ||--o{ CART_ITEM : contains
    ADDRESS ||--o{ ORDERS : ships_to
    ORDERS ||--o{ ORDER_ITEM : contains
    ORDERS ||--|| PAYMENT : paid_by

    USERS ||--o{ REVIEW : writes_logical
    USERS ||--|| CART : owns_logical
    USERS ||--o{ ADDRESS : owns_logical
    USERS ||--o{ ORDERS : places_logical
    BOOK ||--o{ CART_ITEM : selected_logical
    BOOK ||--o{ ORDER_ITEM : purchased_logical
```

## Relatii JPA Acoperite

- `@OneToOne`: `Order` - `Payment`
- `@OneToMany` / `@ManyToOne`: `Cart` - `CartItem`, `Order` - `OrderItem`, `Address` - `Order`, `Book` - `Review`
- `@ManyToMany`: `Book` - `Author`, `Book` - `Category`

Entitati principale:

- `User`
- `Book`
- `Author`
- `Category`
- `Review`
- `Cart`
- `CartItem`
- `Address`
- `Order`
- `OrderItem`
- `Payment`

## Functionalitati

Utilizator:

- inregistrare cont
- autentificare cu JWT
- listare carti
- detalii carte
- adaugare produse in cos
- modificare cantitate in cos
- stergere produse din cos
- creare adresa
- checkout comanda
- vizualizare comenzi

Administrator:

- login cu rol `ADMIN`
- administrare carti
- administrare autori
- administrare categorii
- vizualizare utilizatori
- vizualizare comenzi
- modificare status comanda

## Securitate

- parole criptate cu BCrypt
- autentificare JWT
- roluri: `CUSTOMER`, `ADMIN`
- rute admin protejate in API Gateway
- logout in frontend prin stergerea tokenului
- gateway-ul blocheaza accesul la `/api/v1/admin/**` pentru utilizatorii fara rol `ADMIN`

Cont admin initial:

- username: `admin`
- password: `admin123`

## Structura Backend

```text
microservices/
  auth-service/
  catalog-service/
  order-service/
  api-gateway/
  pom.xml
```

## Structura Frontend

Am pus frontend-ul intr-un repository separat, dar el face parte din aceeasi aplicatie.

```text
Book-Store-FrontEnd/
  src/
    components/
    config/api.js
    pages/
    routes/
  vite.config.js
  package.json
```

Configurarea API in frontend:

- `VITE_API_BASE_URL=/api/v1`
- Vite proxy trimite `/api` catre `http://host.docker.internal:8085`

## Endpoint-uri Principale

Auth:

- `POST /api/v1/auth/register`
- `POST /api/v1/auth/login`
- `GET /api/v1/auth/validate?token=...`
- `GET /api/v1/auth/me`
- `GET /api/v1/admin/users`
- `GET /api/v1/admin/users/{id}`
- `POST /api/v1/admin/users`
- `PUT /api/v1/admin/users/{id}`
- `DELETE /api/v1/admin/users/{id}`

Catalog:

- `GET /api/v1/books`
- `GET /api/v1/books/{id}`
- `GET /api/v1/admin/books`
- `POST /api/v1/admin/books`
- `PUT /api/v1/admin/books/{id}`
- `DELETE /api/v1/admin/books/{id}`
- `GET /api/v1/admin/authors`
- `POST /api/v1/admin/authors`
- `PUT /api/v1/admin/authors/{id}`
- `DELETE /api/v1/admin/authors/{id}`
- `GET /api/v1/admin/categories`
- `POST /api/v1/admin/categories`
- `PUT /api/v1/admin/categories/{id}`
- `DELETE /api/v1/admin/categories/{id}`
- `GET /api/v1/books/{bookId}/reviews`
- `POST /api/v1/books/{bookId}/reviews`
- `GET /api/v1/admin/reviews`
- `GET /api/v1/admin/reviews/{id}`
- `POST /api/v1/admin/reviews`
- `PUT /api/v1/admin/reviews/{id}`
- `DELETE /api/v1/admin/reviews/{id}`

Orders:

- `GET /api/v1/cart`
- `POST /api/v1/cart/items`
- `PUT /api/v1/cart/items/{id}`
- `DELETE /api/v1/cart/items/{id}`
- `DELETE /api/v1/cart`
- `GET /api/v1/address`
- `POST /api/v1/address`
- `PUT /api/v1/address/{id}`
- `DELETE /api/v1/address/{id}`
- `POST /api/v1/orders/checkout`
- `GET /api/v1/orders`
- `GET /api/v1/admin/orders`
- `GET /api/v1/admin/orders/{id}`
- `POST /api/v1/admin/orders`
- `PUT /api/v1/admin/orders/{id}`
- `PUT /api/v1/admin/orders/{id}/status`
- `DELETE /api/v1/admin/orders/{id}`
- `GET /api/v1/admin/addresses`
- `GET /api/v1/admin/addresses/{id}`
- `POST /api/v1/admin/addresses`
- `PUT /api/v1/admin/addresses/{id}`
- `DELETE /api/v1/admin/addresses/{id}`
- `GET /api/v1/admin/carts`
- `GET /api/v1/admin/carts/{id}`
- `POST /api/v1/admin/carts`
- `PUT /api/v1/admin/carts/{id}`
- `DELETE /api/v1/admin/carts/{id}`
- `GET /api/v1/admin/cart-items`
- `GET /api/v1/admin/cart-items/{id}`
- `POST /api/v1/admin/cart-items`
- `PUT /api/v1/admin/cart-items/{id}`
- `DELETE /api/v1/admin/cart-items/{id}`
- `GET /api/v1/admin/order-items`
- `GET /api/v1/admin/order-items/{id}`
- `POST /api/v1/admin/order-items`
- `PUT /api/v1/admin/order-items/{id}`
- `DELETE /api/v1/admin/order-items/{id}`
- `GET /api/v1/admin/payments`
- `GET /api/v1/admin/payments/{id}`
- `POST /api/v1/admin/payments`
- `PUT /api/v1/admin/payments/{id}`
- `DELETE /api/v1/admin/payments/{id}`

## Rutare in API Gateway

- `/api/v1/auth/**` -> `auth-service`
- `/api/v1/books/**` -> `catalog-service`
- `/api/v1/admin/users` -> `auth-service`
- `/api/v1/admin/orders/**` -> `order-service`
- `/api/v1/admin/addresses/**` -> `order-service`
- `/api/v1/admin/carts/**` -> `order-service`
- `/api/v1/admin/cart-items/**` -> `order-service`
- `/api/v1/admin/order-items/**` -> `order-service`
- `/api/v1/admin/payments/**` -> `order-service`
- `/api/v1/admin/books/**` -> `catalog-service`
- `/api/v1/admin/authors/**` -> `catalog-service`
- `/api/v1/admin/categories/**` -> `catalog-service`
- `/api/v1/admin/reviews/**` -> `catalog-service`
- `/api/v1/address/**` -> `order-service`
- `/api/v1/cart/**` -> `order-service`
- `/api/v1/orders/**` -> `order-service`
