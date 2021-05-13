DROP TABLE IF EXISTS boards;

CREATE TABLE boards
(
    bid  INT AUTO_INCREMENT PRIMARY KEY,
    json CLOB NOT NULL
);

DROP TABLE IF EXISTS scores;

CREATE TABLE scores
(
    uid   VARCHAR(50) not null,
    score int         not null
);