# 📚 Book-Store

## ✅ CERINȚE OBLIGATORII — Tracking Progres

---

# 🗄️ Model de Date

* [ ] Minimum **6-7 entități interconectate**

### Relații de toate tipurile

* [ ] `@OneToOne` (min. 1 exemplu)

* [ ] `@OneToMany` / `@ManyToOne` (min. 2 exemple)

* [ ] `@ManyToMany` (min. 1 exemplu)

* [ ] Diagrama **ER** documentată în `README`

---

# 🔧 Operații CRUD Complete

* [ ] Create pentru toate entitățile

* [ ] Read pentru toate entitățile

* [ ] Update pentru toate entitățile

* [ ] Delete pentru toate entitățile

* [ ] Repository pattern cu **Spring Data JPA**

* [ ] Service layer cu logică de business

* [ ] Exception handling specific pentru fiecare operație

---

# ⚙️ Configurare Multi-Environment

* [ ] Minimum **2 profiluri Spring**

  * [ ] `dev`
  * [ ] `test`

* [ ] Configurare pentru minimum **2 baze de date diferite**

  * [ ] Bază pentru dezvoltare (`PostgreSQL` / `MySQL`)
  * [ ] Bază pentru testare (`H2` in-memory sau separată)

* [ ] Fișiere de configurare separate

```yaml
application-dev.yml
application-test.yml
```

---

# 🧪 Testing

## Unit Tests

* [ ] Minimum **70% coverage** pentru service layer

## Integration Tests

* [ ] Minimum **3 scenarii end-to-end**

## Tehnologii

* [ ] `JUnit 5`

* [ ] `Mockito`

* [ ] Test database configuration

---

# 🖥️ Views și Validare

## Frontend

* [ ] `Thymeleaf`
* [ ] `JSP`
* [ ] Framework modern (`React` / `Vue` / `Angular`)

## Formulare

* [ ] Formulare pentru toate operațiile **CRUD**

## Validare

### Server-side

* [ ] Bean Validation

```java
@Valid
@NotNull
```

### Client-side

* [ ] Client-side validation
* [ ] Mesaje de eroare user-friendly

## Exception Handling

* [ ] Pagină de eroare `404`
* [ ] Pagină de eroare `500`
* [ ] Alte pagini de eroare custom

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
* [ ] UI pentru navigare între pagini
* [ ] Configurare dimensiune pagină

---

# 🔐 Spring Security

## Cerințe minime

* [ ] Autentificare `JDBC`

* [ ] Minimum **2 roluri**

  * [ ] `USER`
  * [ ] `ADMIN`

* [ ] Protejarea endpoint-urilor bazată pe rol

* [ ] Pagină de login custom

* [ ] Logout funcțional

## Cerințe pentru punctaj maxim

* [ ] Password encoding (`BCrypt`)
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

* [ ] REST (`Feign Client` / `RestTemplate`)

* [ ] Message Broker (`RabbitMQ` / `Kafka`)

* [ ] Demonstrare descoperire automată a serviciilor

---

# ⚖️ Load Balancing și Scalabilitate

* [ ] Client-side load balancing (`Spring Cloud LoadBalancer`)
* [ ] Rulare multiplă instanță pentru un serviciu
* [ ] Testing cu minim **2 instanțe per serviciu**

---

# 🚪 API Gateway

* [ ] Routing centralizat
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

* [ ] JWT authentication între microservicii
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


