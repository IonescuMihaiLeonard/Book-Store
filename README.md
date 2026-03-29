# Book-Store
## CERINȚE OBLIGATORII 

### Model de Date
  • Minimum 6-7 entități interconectate
  • Relații de toate tipurile:
    - @OneToOne (min. 1 exemplu)
    - @OneToMany / @ManyToOne (min. 2 exemple)
    - @ManyToMany (min. 1 exemplu)
  • Diagrama ER documentată în README
  
### Operații CRUD Complete
  • Create, Read, Update, Delete pentru toate entitățile
  • Repository pattern cu Spring Data JPA
  • Service layer cu logică de business
  • Exception handling specific pentru fiecare operație
  
### Configurare Multi-Environment
  • Minimum 2 profiluri Spring (dev, test)
  • Configurare pentru minimum 2 baze de date diferite:
    - Una pentru dezvoltare (PostgreSQL/MySQL)
    - Una pentru testare (H2 in-memory sau separată)
  • Fișiere de configurare separate (application-dev.yml, application-test.yml)
  
### Testing
  • Unit tests: minimum 70% coverage pentru service layer
  • Integration tests: minimum 3 scenarii end-to-end
  • Utilizare JUnit 5 + Mockito
  • Test database configuration
  
### Views și Validare
  • Frontend: Thymeleaf/JSP sau framework modern (React/Vue/Angular)
  • Formulare: pentru toate operațiile CRUD
  • Validare:
    - Server-side cu Bean Validation (@Valid, @NotNull, etc.)
    - Client-side validation
    - Mesaje de eroare user-friendly
  • Exception handling: pagini de eroare custom (404, 500, etc.)
    
### Logging
  • Framework: SLF4J + Logback/Log4j2
  • Nivele de logging configurate corect (INFO, DEBUG, ERROR)
  • Logging în fișiere separate pentru erori
  • [Opțional] Aspecte pentru logging automat
  
### Paginare și Sortare
  • Implementare Pageable pentru minimum 3 entități
  • Opțiuni de sortare după minim 2 criterii per entitate
  • UI pentru navigare între pagini
  • Configurare dimensiune pagină

### Spring Security
Cerințe minime:
  • Autentificare JDBC
  • Minimum 2 roluri (USER, ADMIN)
  • Protejarea endpoint-urilor bazată pe rol
  • Pagină de login custom
  • Logout funcțional
Cerințe recomandate pentru punctaj maxim:
  • Password encoding (BCrypt)
  • Remember me functionality
  • CSRF protection activă

##  CERINȚE OPȚIONALE - Microservicii
### Configurare Centralizată
  • Config Server pentru toate microserviciile
  • Externalizarea configurațiilor sensibile
  • Refresh dinamic fără restart+-+

### Service Discovery și Comunicare 
  • Service registry funcțional
  • Comunicare inter-servicii prin:
    - REST (Feign Client / RestTemplate)
    - SAU Message Broker (RabbitMQ/Kafka)
  • Demonstrare că serviciile se descoperă automat

### Load Balancing și Scalabilitate
  • Client-side load balancing (Spring Cloud LoadBalancer)
  • Demonstrare rulare multiplă instanță pentru un serviciu
  • Testing cu minim 2 instanțe per serviciu

### API Gateway
  • Routing centralizat
  • Rate limiting
  • Request/Response filtering
  
### Monitorizare și Metrici
  • Actuator endpoints expuse
  • Dashboard cu metrici (CPU, memory, requests)
  • Health checks pentru toate serviciile
  • Distributed tracing (Zipkin/Jaeger - bonus)
  
### Securitate Distribuită
  • JWT authentication între microservicii
  • SAU OAuth2 / Keycloak
  • Secure communication (HTTPS - bonus)
  
### Resilience și Fault Tolerance
  • Circuit Breaker pentru minimum 2 servicii
  • Retry mechanism
  • Fallback methods
  • Demonstrare comportament în caz de eroare
    
### Design Patterns
  • Implementare și documentare minimum 1 pattern
  
### NoSQL și Caching 
  • Integrare minimum 1 bază NoSQL (MongoDB/Redis/Cassandra)
  • Caching layer (Redis/Hazelcast) pentru date accesate frecvent
  • Demonstrare beneficii de performanță

### Micro-frontends
  • Separarea frontend-ului în module independente
  • Tehnologii: Module Federation / Single-SPA
    
### CI/CD Pipeline
  • Build automatizat
  • Rulare teste automate
  • Deployment automat (staging)
  • Docker containerization
  
### AI Agents - Dezvoltare 
  • GitHub Copilot pentru pair programming
  • Code review automatizat
  • Documentație generată automat
    
### AI Agents - Runtime
  • Recomandări personalizate
