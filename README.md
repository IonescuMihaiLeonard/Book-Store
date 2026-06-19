# Book Store - Microservices

Aplicatia Book Store a fost migrata de la monolit la arhitectura de microservicii.

## Structura

- `microservices/auth-service`: autentificare, utilizatori, roluri si JWT
- `microservices/catalog-service`: carti, autori, categorii si review-uri
- `microservices/order-service`: cos, adrese, comenzi si plati
- `microservices/api-gateway`: rutare centralizata catre servicii
- `start-dev.cmd`: pornire rapida pentru mediul local
- `stop-dev.cmd`: oprire containere locale

Frontend-ul este mentinut in repository-ul separat `Book-Store-FrontEnd` si comunica local cu backend-ul prin gateway, la `/api/v1`.

## Documentatie

Documentatia detaliata pentru servicii, endpoint-uri si diagrama ER este in:

- `microservices/README.md`

## Pornire Locala

```powershell
.\start-dev.cmd
```

Servicii:

- frontend: `http://localhost:5173`
- api-gateway: `http://localhost:8085`
- auth-service: `http://localhost:8081`
- catalog-service: `http://localhost:8082`
- order-service: `http://localhost:8083`
- MySQL: `localhost:13306`

Cont admin initial:

- username: `admin`
- password: `admin123`
