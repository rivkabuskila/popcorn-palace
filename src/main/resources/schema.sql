CREATE TABLE IF NOT EXISTS task (
                                    description VARCHAR(64) NOT NULL,
    completed   VARCHAR(30) NOT NULL);
-- DROP TABLE IF EXISTS movies CASCADE;

DROP TABLE IF EXISTS movies;
CREATE TABLE movies (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255),
                        genre VARCHAR(100),
                        duration INT,
                        rating DOUBLE PRECISION,
                        release_year INT
);
--
-- DROP TABLE IF EXISTS Showtime CASCADE;

--
CREATE TABLE IF NOT EXISTS Showtime (
                          id SERIAL PRIMARY KEY,
                          movie_id BIGINT,
                          theater VARCHAR(255),
                          start_time TIMESTAMP WITH TIME ZONE,
                          end_time TIMESTAMP WITH TIME ZONE,
                          price DOUBLE PRECISION
);


ALTER TABLE showtime
    ALTER COLUMN start_time SET DATA TYPE timestamp with time zone
    USING start_time AT TIME ZONE 'UTC';

ALTER TABLE showtime
    ALTER COLUMN end_time SET DATA TYPE timestamp with time zone
    USING end_time AT TIME ZONE 'UTC';
-- ALTER TABLE showtime
--     ALTER COLUMN start_time SET DATA TYPE timestamp(6) without time zone
-- USING ('1970-01-01 ' || start_time)::timestamp(6);
--
-- ALTER TABLE showtime
--     ALTER COLUMN start_time SET DATA TYPE timestamp(6) with time zone
--     USING start_time AT TIME ZONE 'UTC';
--
-- ALTER TABLE showtime
--     ALTER COLUMN end_time SET DATA TYPE timestamp(6) without time zone
-- USING ('1970-01-01 ' || end_time)::timestamp(6);
--
-- ALTER TABLE showtime
--     ALTER COLUMN end_time SET DATA TYPE timestamp(6) with time zone
--     USING end_time AT TIME ZONE 'UTC';

-- DROP TABLE bookings;
--
-- CREATE TABLE bookings (
--                         bookingId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--                         showtimeId BIGINT,
--                         seatNumber INT,
--                         userId UUID
--
-- );

DROP TABLE IF EXISTS theater;

CREATE TABLE theater (
                         title VARCHAR(255) PRIMARY KEY,
                         total_seats INT NOT NULL
);




