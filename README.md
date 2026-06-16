status# 📚 Book-Store

## ✅ CERINȚE OBLIGATORII — Tracking Progres

---

# 🗄️ Model de Date

* [x] Minimum **6-7 entități interconectate**

### Relații de toate tipurile

* [x] `@OneToOne` (min. 1 exemplu)

* [x] `@OneToMany` / `@ManyToOne` (min. 2 exemple)

* [x] `@ManyToMany` (min. 1 exemplu)

* [ ] Diagrama **ER** documentată în `README`

---

# 🔧 Operații CRUD Complete

* [X] Create pentru toate entitățile

* [X] Read pentru toate entitățile

* [X] Update pentru toate entitățile

* [X] Delete pentru toate entitățile

* [ ] Repository pattern cu **Spring Data JPA**

* [X] Service layer cu logică de business

* [ ] Exception handling specific pentru fiecare operație

---

# ⚙️ Configurare Multi-Environment

* [ ] Minimum **2 profiluri Spring**

  * [X] `dev`
  * [ ] `test`

* [ ] Configurare pentru minimum **2 baze de date diferite**

  * [x] Bază pentru dezvoltare (`PostgreSQL` / `MySQL`)
  * [ ] Bază pentru testare (`H2` in-memory sau separată)

* [ ] Fișiere de configurare separate

```yaml
application-dev.yml
application-test.yml
```

---

# 🧪 Testing

## Unit Tests

* [X] Minimum **70% coverage** pentru service layer

## Integration Tests

* [ ] Minimum **3 scenarii end-to-end**

## Tehnologii

* [x] `JUnit 5`

* [x] `Mockito`

* [ ] Test database configuration

---

# 🖥️ Views și Validare

## Frontend

* [X] Framework modern (`React` / `Vue` / `Angular`)

## Formulare

* [X] Formulare pentru toate operațiile **CRUD**

## Validare

### Server-side

* [x] Bean Validation

```java
@Valid
@NotNull
```

### Client-side

* [X] Client-side validation
* [X] Mesaje de eroare user-friendly

## Exception Handling

* [X] Pagină de eroare `404`
* [X] Pagină de eroare `500`
* [X] Alte pagini de eroare custom

---

# 📝 Logging

* [ ] Framework configurat (`SLF4J`)
* [ ] `Logback` sau `Log4j2`

## Nivele de logging

* [ ] `INFO`

* [ ] `DEBUG`

* [ ] `ERROR`

* [ ] Logging în fișiere separate pentru erori

* [ ] Aspecte pentru logging automat (opțional)

---

# 📄 Paginare și Sortare

* [ ] Implementare `Pageable` pentru minimum **3 entități**
* [ ] Sortare după minimum **2 criterii per entitate**
* [X] UI pentru navigare între pagini
* [X] Configurare dimensiune pagină (Responsive Design)

---

# 🔐 Spring Security

## Cerințe minime

* [x] Autentificare `JDBC`

* [x] Minimum **2 roluri**

  * [x] `CUSTOMER`
  * [x] `ADMIN`

* [x] Protejarea endpoint-urilor bazată pe rol

* [X] Pagină de login custom

* [X] Logout funcțional

## Cerințe pentru punctaj maxim

* [x] Password encoding (`BCrypt`)
* [ ] Remember me functionality
* [ ] CSRF protection activă

---

# 🧩 CERINȚE OPȚIONALE — Microservicii

---

# 🛠️ Configurare Centralizată

* [ ] Config Server pentru toate microserviciile
* [ ] Externalizarea configurațiilor sensibile
* [ ] Refresh dinamic fără restart+-+

---

# 🔎 Service Discovery și Comunicare

* [ ] Service registry funcțional

## Comunicare inter-servicii

* [x] REST (`Feign Client` / `RestTemplate`)

* [ ] Message Broker (`RabbitMQ` / `Kafka`)

* [ ] Demonstrare descoperire automată a serviciilor

---

# ⚖️ Load Balancing și Scalabilitate

* [ ] Client-side load balancing (`Spring Cloud LoadBalancer`)
* [ ] Rulare multiplă instanță pentru un serviciu
* [ ] Testing cu minim **2 instanțe per serviciu**

---

# 🚪 API Gateway

* [X] Routing centralizat
* [ ] Rate limiting
* [ ] Request/Response filtering

---

# 📊 Monitorizare și Metrici

* [ ] `Actuator` endpoints expuse

## Dashboard metrici

* [ ] CPU

* [ ] Memory

* [ ] Requests

* [ ] Health checks pentru toate serviciile

* [ ] Distributed tracing (`Zipkin` / `Jaeger` — bonus)

---

# 🔒 Securitate Distribuită

* [X] JWT authentication între microservicii
* [ ] `OAuth2` / `Keycloak`
* [ ] Secure communication (`HTTPS` — bonus)

---

# 🧯 Resilience și Fault Tolerance

* [ ] Circuit Breaker pentru minimum **2 servicii**
* [ ] Retry mechanism
* [ ] Fallback methods
* [ ] Demonstrare comportament în caz de eroare

---

# 🧠 Design Patterns

* [ ] Implementare și documentare minimum **1 pattern**

---

# 🗃️ NoSQL și Caching

## NoSQL

* [ ] Integrare bază NoSQL

  * [ ] `MongoDB`
  * [ ] `Redis`
  * [ ] `Cassandra`

## Caching

* [ ] Caching layer (`Redis` / `Hazelcast`)
* [ ] Demonstrare beneficii de performanță

---

# 🧩 Micro-frontends

* [ ] Separarea frontend-ului în module independente

## Tehnologii

* [ ] Module Federation
* [ ] Single-SPA

---

# 🚀 CI/CD Pipeline

* [ ] Build automatizat
* [ ] Rulare teste automate
* [ ] Deployment automat (`staging`)
* [ ] Docker containerization

---

# 🤖 AI Agents — Dezvoltare

* [ ] GitHub Copilot pentru pair programming
* [ ] Code review automatizat
* [ ] Documentație generată automat

---

# 🤖 AI Agents — Runtime

* [ ] Recomandări personalizate

---


