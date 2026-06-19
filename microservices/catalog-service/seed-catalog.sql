INSERT INTO author (id, name)
SELECT 1, 'George Orwell'
WHERE NOT EXISTS (SELECT 1 FROM author WHERE name = 'George Orwell');

INSERT INTO author (id, name)
SELECT 2, 'J.K. Rowling'
WHERE NOT EXISTS (SELECT 1 FROM author WHERE name = 'J.K. Rowling');

INSERT INTO author (id, name)
SELECT 3, 'J.R.R. Tolkien'
WHERE NOT EXISTS (SELECT 1 FROM author WHERE name = 'J.R.R. Tolkien');

INSERT INTO author (id, name)
SELECT 4, 'Agatha Christie'
WHERE NOT EXISTS (SELECT 1 FROM author WHERE name = 'Agatha Christie');

INSERT INTO author (id, name)
SELECT 5, 'Frank Herbert'
WHERE NOT EXISTS (SELECT 1 FROM author WHERE name = 'Frank Herbert');

INSERT INTO category (id, name)
SELECT 1, 'Fictiune'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Fictiune');

INSERT INTO category (id, name)
SELECT 2, 'Fantasy'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Fantasy');

INSERT INTO category (id, name)
SELECT 3, 'Mister'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Mister');

INSERT INTO category (id, name)
SELECT 4, 'Science Fiction'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Science Fiction');

INSERT INTO category (id, name)
SELECT 5, 'Clasici'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Clasici');

INSERT INTO book (id, title, isbn, price, stock, description, image_url)
SELECT 1, '1984', '9780451524935', 39.99, 18, 'Un roman distopic despre supraveghere, propaganda si libertate.', 'https://covers.openlibrary.org/b/isbn/9780451524935-L.jpg'
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780451524935');

INSERT INTO book (id, title, isbn, price, stock, description, image_url)
SELECT 2, 'Animal Farm', '9780451526342', 29.99, 22, 'O satira politica despre putere, revolutie si coruptie.', 'https://covers.openlibrary.org/b/isbn/9780451526342-L.jpg'
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780451526342');

INSERT INTO book (id, title, isbn, price, stock, description, image_url)
SELECT 3, 'Harry Potter and the Philosopher''s Stone', '9780747532699', 49.99, 30, 'Primul volum al seriei Harry Potter.', 'https://covers.openlibrary.org/b/isbn/9780747532699-L.jpg'
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780747532699');

INSERT INTO book (id, title, isbn, price, stock, description, image_url)
SELECT 4, 'The Hobbit', '9780547928227', 44.99, 16, 'O aventura fantasy clasica in Middle-earth.', 'https://covers.openlibrary.org/b/isbn/9780547928227-L.jpg'
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780547928227');

INSERT INTO book (id, title, isbn, price, stock, description, image_url)
SELECT 5, 'Murder on the Orient Express', '9780062693662', 37.50, 14, 'Hercule Poirot investigheaza o crima imposibila intr-un tren de lux.', 'https://covers.openlibrary.org/b/isbn/9780062693662-L.jpg'
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780062693662');

INSERT INTO book (id, title, isbn, price, stock, description, image_url)
SELECT 6, 'Dune', '9780441172719', 59.99, 20, 'Epopee science fiction despre politica, ecologie si destin.', 'https://covers.openlibrary.org/b/isbn/9780441172719-L.jpg'
WHERE NOT EXISTS (SELECT 1 FROM book WHERE isbn = '9780441172719');

INSERT INTO books_authors (book_id, author_id)
SELECT b.id, a.id
FROM (
    SELECT '9780451524935' isbn, 'George Orwell' author_name UNION ALL
    SELECT '9780451526342', 'George Orwell' UNION ALL
    SELECT '9780747532699', 'J.K. Rowling' UNION ALL
    SELECT '9780547928227', 'J.R.R. Tolkien' UNION ALL
    SELECT '9780062693662', 'Agatha Christie' UNION ALL
    SELECT '9780441172719', 'Frank Herbert'
) pairs
JOIN book b ON b.isbn = pairs.isbn
JOIN author a ON a.name = pairs.author_name
WHERE NOT EXISTS (
    SELECT 1 FROM books_authors ba
    WHERE ba.book_id = b.id AND ba.author_id = a.id
);

INSERT INTO books_categories (book_id, category_id)
SELECT b.id, c.id
FROM (
    SELECT '9780451524935' isbn, 'Fictiune' category_name UNION ALL
    SELECT '9780451524935', 'Clasici' UNION ALL
    SELECT '9780451526342', 'Fictiune' UNION ALL
    SELECT '9780451526342', 'Clasici' UNION ALL
    SELECT '9780747532699', 'Fantasy' UNION ALL
    SELECT '9780547928227', 'Fantasy' UNION ALL
    SELECT '9780062693662', 'Mister' UNION ALL
    SELECT '9780441172719', 'Science Fiction'
) pairs
JOIN book b ON b.isbn = pairs.isbn
JOIN category c ON c.name = pairs.category_name
WHERE NOT EXISTS (
    SELECT 1 FROM books_categories bc
    WHERE bc.book_id = b.id AND bc.category_id = c.id
);

UPDATE author_seq SET next_val = GREATEST(next_val, 101);
UPDATE category_seq SET next_val = GREATEST(next_val, 101);
UPDATE book_seq SET next_val = GREATEST(next_val, 101);
