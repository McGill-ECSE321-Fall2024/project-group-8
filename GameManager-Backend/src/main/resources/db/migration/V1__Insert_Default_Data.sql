INSERT INTO owner (email, name, password) VALUES
    ('manager@mail.com', 'manager', '1234567890');

INSERT INTO category (name, description) VALUES
('Action', 'An action game is a video game genre that emphasizes physical challenges, including hand–eye coordination and reaction time.'),
('Puzzle', 'Puzzle games focus on completion, which requires players to solve a logic puzzle or navigate a challenge to progress to the next, more difficult challenge'),
('Strategy', 'A strategic game is a model of interaction in which each player chooses an action not having been informed of the other players'' actions.'),
('Racing', 'Racing games are a video game genre where players race to the finish line in a vehicle, either against other drivers or against the clock.');

INSERT INTO game (description, game_status, genre, price, request_status, stock, title, category_id, average_rating, popularity) VALUES
('Call of Duty®: Black Ops 6 is signature Black Ops across a cinematic single-player Campaign, a best-in-class Multiplayer experience and with the epic return of Round-Based Zombies.', 1, 'action', 89.66, 0, 100, 'Call of Duty®: Black Ops 6', (SELECT category_id FROM category WHERE name = 'Action'), 0, 0),
('Civilization VI is the newest installment in the award winning Civilization Franchise. Expand your empire, advance your culture and go head-to-head against history’s greatest leaders. Will your civilization stand the test of time?', 1, 'strategy', 79.99, 0, 100, 'Sid Meier’s Civilization® VI', (SELECT category_id FROM category WHERE name = 'Strategy'), 0, 0),
('Play Russian roulette with a 12-gauge shotgun. Two enter. One leaves. Roll the dice with your life. Good luck!', 1, 'puzzle', 3.89, 0, 100, 'Buckshot Roulette', (SELECT category_id FROM category WHERE name = 'Puzzle'), 0, 0);
