----------------------
-- Créer les tables
----------------------

CREATE TABLE User (
	id			INTEGER 	PRIMARY KEY AUTOINCREMENT,
	name 	 	TEXT		NOT NULL,
		);	
	
CREATE TABLE Game (
 id    INTEGER PRIMARY KEY AUTOINCREMENT,
    user TEXT NOT NULL,
    score    INTEGER NOT NULL,
    state    TEXT NOT NULL,
    word TEXT NOT NULL	
);	
	
CREATE TABLE SEQUENCES (
        id      VARCHAR(50)             NOT NULL,
        sValue  numeric(10)             NOT NULL,
        constraint IDSEQUENCE primary key (id)
        );
