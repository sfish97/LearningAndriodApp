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

INSERT INTO places VALUES
   ('ASU-West');
INSERT INTO placeDescription VALUES
(
   'ASU-West',
   "Home of ASU's Applied Computing Program",
   'School',
   'ASU West Campus',
   '13591 N 47th Ave$Phoenix AZ 85051',
   1100.0,
   33.608979,
   -112.159469
);

INSERT INTO places VALUES
   ('UAK-Anchorage');
INSERT INTO placeDescription VALUES
(
   'UAK-Anchorage',
   "University of Alaska's largest campus",
   'School',
   'University of Alaska at Anchorage',
   '290 Spirit Dr$Anchorage AK 99508',
   0.0,
   61.189748,
   -149.826721
);

INSERT INTO places VALUES
   ('London-England');
INSERT INTO placeDescription VALUES
(
   'London-England',
   "Renaissance Hotel at the Heathrow Airport",
   'Travel',
   'Renaissance London Heathrow Airport',
   '5 Mondial Way$Harlington Hayes UB3 UK',
   82.0,
   51.481959,
   -0.445286
);

INSERT INTO places VALUES
   ('Moscow-Russia');
INSERT INTO placeDescription VALUES
(
   'Moscow-Russia',
   "The Marriott Courtyard in downtown Moscow",
   'Travel',
   'Courtyard Moscow City Center',
   'Voznesenskiy per 7 $ Moskva Russia 125009',
   512.0,
   55.758666,
   37.604058
);

INSERT INTO places VALUES
   ('New-York-NY');
INSERT INTO placeDescription VALUES
(
   'Name',
   "New York City Hall at West end of Brooklyn Bridge",
   'Travel',
   'New York City Hall',
   '1 Elk St$New York NY 10007',
   2.0,
   40.712991,
   -74.005948
);

INSERT INTO places VALUES
   ('Rogers-Trailhead');
INSERT INTO placeDescription VALUES
(
   'Rogers-Trailhead',
   "Trailhead for hiking to Rogers Canyon Ruins and Reavis Ranch",
   'Hike',
   '',
   '',
   4500.0,
   33.422212,
   -111.173393
);



