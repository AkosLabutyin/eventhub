-- Create database if not exists (safe for volume-based init)
CREATE DATABASE event_db
    OWNER event_manager
    ENCODING 'UTF8';

\connect event_db;

-- Tables
CREATE TABLE IF NOT EXISTS events (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL
    );

CREATE TABLE IF NOT EXISTS attendees (
                                         id SERIAL PRIMARY KEY,
                                         full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    preferred_type VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS registrations (
                                             id SERIAL PRIMARY KEY,
                                             event_id INT NOT NULL,
                                             attendee_id INT NOT NULL,
                                             CONSTRAINT fk_registration_event FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT fk_registration_attendee FOREIGN KEY (attendee_id) REFERENCES attendees(id),
    CONSTRAINT uq_registration UNIQUE (event_id, attendee_id)
    );

-- Demo data
INSERT INTO events (name, location, start_time, end_time)
VALUES
    ('Spring Workshop', 'Room 101', '2026-01-10 10:00:00', '2026-01-10 12:00:00'),
    ('Networking Night', 'Main Hall', '2026-01-12 18:00:00', '2026-01-12 20:00:00');

INSERT INTO attendees (full_name, email, preferred_type)
VALUES
    ('Teszt Teodor', 'test.teodor@mik.pte.demo.hu', 'workshop'),
    ('Teszt Teobald', 'test.teobald@mik.pte.demo.hu', 'talk'),
    ('Teszt Tamerlán', 'test.tamerlan@mik.pte.demo.hu', 'networking');

INSERT INTO registrations (event_id, attendee_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3);