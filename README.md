📚 Ecommerce Book Store
--
Un magazin online de boardgames și cărți dezvoltat în Spring Boot, care permite utilizatorilor să se înregistreze, să se autentifice, să adauge produse în coș, să finalizeze comenzi și să plătească online.
Proiectul implementează un backend REST API complet, cu JWT authentication, roluri (ADMIN / CUSTOMER), checkout, plăți și testare automată.

🧩 Descriere generală
--
EcommerceBookStore este o aplicație web backend care gestionează întregul flux al unui magazin online:

Utilizatorii pot naviga produsele, adăuga articole în coș și plasa comenzi

Administratorii pot gestiona cărți, autori, categorii și stocuri

Autentificarea se face prin JWT tokens

Aplicația este complet testată cu JUnit & Mockito

🚀 Funcționalități principale (MVP)
--
👤 Utilizatori & Autentificare
--
Înregistrare utilizatori

Login cu username + parolă

Autentificare prin JWT

Roluri: ADMIN, CUSTOMER

📦 Produse (Books / Boardgames)
--
CRUD complet pentru produse (admin)

Asociere cu autori și categorii

Stoc și preț

🛒 Coș de cumpărături
--
Adăugare produs în coș

Modificare cantitate

Eliminare produs

Vizualizare coș

🧾 Checkout & Comenzi
--
Crearea unei comenzi din coș

Verificare stoc

Asociere adresă de livrare

Generare Order + OrderItems

💳 Plăți
--
Creare obiect Payment

Status plată: SUCCESS, FAILED

Asociere cu comanda

🧪 Testare
--
Unit Tests pentru:

AdminBookService

AuthService

CartService

OrderService

Mockito pentru mock-uri

Verificare stoc, erori, cazuri limită

🧱 Arhitectură
--
Aplicația este construită folosind Spring Boot 3 și o arhitectură în straturi:

Controller → Service → Repository → Database

🔹 Layer-e:
Layer	Rol
Controller	Expune endpoint-uri REST
Service	Logica de business
Repository	JPA / Hibernate
Entity	Modelele din DB
Security	JWT, filtre, autentificare
Test	JUnit + Mockito

🗃️ Entități principale
--
Entitate	Descriere
User	Utilizator (admin sau client)
Address	Adresă de livrare
Books	Produsul din magazin
Author	Autorul unei cărți
Category	Categoria produsului
Cart	Coș de cumpărături
CartItem	Produse din coș
Order	Comandă
OrderItem	Produsele din comandă
Payment	Plata

🔐 Securitate
--
Autentificare cu JWT

Filtru JwtAuthenticationFilter

SecurityContext pentru utilizatorul logat

Rute protejate:
--
Endpoint	Acces
/api/v1/auth/**	Public
/api/v1/books	Public
/api/v1/cart/**	Autentificat
/api/v1/orders/**	Autentificat
/api/v1/admin/**	ADMIN only

🛠️ Tehnologii folosite
--
Java 17+

Spring Boot 3

Spring Security

JWT (jjwt)

Hibernate / JPA

MySQL / PostgreSQL

Lombok

JUnit 5

Mockito

Maven

🧑‍💻 Autor
--
Proiect realizat ca sistem complet de e-commerce backend cu Spring Boot, JWT și teste unitare.
