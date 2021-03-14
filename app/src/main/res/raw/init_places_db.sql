DROP TABLE places;
DROP TABLE placeDescription;

CREATE TABLE places(
	name TEXT PRIMARY KEY
);

CREATE TABLE placeDescription(
	name TEXT,
    description TEXT,
    category TEXT,
    addressTitle TEXT,
    addressStreet TEXT,
    elevation FLOAT,
    latitude DOUBLE,
	longitude DOUBLE,
    FOREIGN KEY(name) REFERENCES places(name) ON DELETE CASCADE
);


INSERT INTO places VALUES
   ('ASU-Poly');
   
INSERT INTO placeDescription VALUES
   (
   'ASU-Poly',
   "Home of ASU's Software Engineering Programs",
   'School',
   'ASU Software Engineering',
   '7171 E Sonoran Arroyo Mall\nPeralta Hall 230\nMesa AZ 85212',
   1384.0,
   33.306388,
   -111.679121
   );

