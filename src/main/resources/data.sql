-- Insert sample users
INSERT INTO users (first_name, last_name, username, email, password, created_at) VALUES
('John', 'Doe', 'john_doe', 'john@example.com', '$2a$12$SDWURpRW.bY9okZy.hrmpOim9TOpDO9.y5ETvxGci9o9AZTitictK', CURRENT_TIMESTAMP),
('Jane', 'Smith', 'jane_smith', 'jane@example.com', '$2a$12$SDWURpRW.bY9okZy.hrmpOim9TOpDO9.y5ETvxGci9o9AZTitictK', CURRENT_TIMESTAMP);

-- Insert sample decks
INSERT INTO decks (name, description, created_at, owner_id) VALUES
('Spanish Vocabulary', 'Basic Spanish words and phrases', CURRENT_TIMESTAMP, 1),
('Java Programming', 'Core Java concepts and syntax', CURRENT_TIMESTAMP, 1),
('History Facts', 'Important historical dates and events', CURRENT_TIMESTAMP, 2);

-- Insert sample cards
INSERT INTO cards (user_id, deck_id, front, back, ease_factor, times_reviewed, next_review_date, created_at, interval_days) VALUES
(1, 1, 'Hola', 'Hello', 2.5, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
(1, 1, 'Gracias', 'Thank you', 2.5, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
(1, 2, 'What is a class in Java?', 'A blueprint for creating objects', 2.5, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
(1, 2, 'What is inheritance?', 'Mechanism where one class acquires properties of another', 2.5, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
(2, 3, 'When did World War II end?', '1945', 2.5, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
