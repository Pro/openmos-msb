DROP TABLE IF EXISTS DeviceAdapter;
CREATE TABLE IF NOT EXISTS DeviceAdapter (
	id	              INTEGER       NOT NULL UNIQUE,
	name	            VARCHAR2(50)  NOT NULL,
	short_description	VARCHAR2(50)  NOT NULL,
	long_description	VARCHAR2(200) NOT NULL,
	protocol	        VARCHAR2(25)  NOT NULL,
	client_id  	      VARCHAR2(25)  NOT NULL UNIQUE,
	agent_id	        VARCHAR2(50)  NOT NULL UNIQUE,
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Skill;
CREATE TABLE IF NOT EXISTS Skill (
	id	         INTEGER       NOT NULL UNIQUE,
  aml_id       VARCHAR2(50)  NOT NULL UNIQUE,
	name	       VARCHAR2(50)  NOT NULL UNIQUE,
	description	 VARCHAR2(200) NOT NULL,
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS Recipe;
CREATE TABLE IF NOT EXISTS Recipe (
  id           INTEGER NOT NULL UNIQUE,
	aml_id       VARCHAR2(50) NOT NULL UNIQUE,
	da_id 	     INTEGER NOT NULL,
  sk_id 	     INTEGER NOT NULL,
	name         VARCHAR2(50) NOT NULL,
	endpoint     VARCHAR2(200) NOT NULL,
  PRIMARY KEY(id),
	FOREIGN KEY(da_id) REFERENCES DeviceAdapter(id)
);

DROP TABLE IF EXISTS SR;
CREATE TABLE IF NOT EXISTS SR (
  r_id INTEGER NOT NULL,
  sk_id INTEGER NOT NULL,
	PRIMARY KEY(r_id, sk_id),
	FOREIGN KEY(r_id) REFERENCES Recipe(id),
	FOREIGN KEY(sk_id) REFERENCES Skill(id)
);



DROP TABLE IF EXISTS DAS;
CREATE TABLE IF NOT EXISTS DAS (
	da_id	INTEGER NOT NULL,
	sk_id	INTEGER NOT NULL,
	PRIMARY KEY(da_id, sk_id),
	FOREIGN KEY(da_id) REFERENCES DeviceAdapter(id),
  FOREIGN KEY(sk_id) REFERENCES Skills(id)
);


