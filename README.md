# Football manager application
# _Description_

## Models:

### Model Player
Contains next fields:
- id;
- name;
- secondName;
- age;
- experienceInMonths;
- team;

### Model Team
Contains next fields:
- id;
- title;
- balance;
- commissionPercent;
- players;

## Controllers:

##1) _Player controller_

**POST** request(`/players`):
- Allows to create a new player.

Examples of request body:
1) `{"name":"Toni",
   "secondName":"Kroos",
   "age":"32",
   "experienceInMonths":180}`
   (will be created a new player with no team)

2){"name":"Karim",
"secondName":"Benzema",
"age":"34",
"experienceInMonths":216,
"teamId":1}
(will be created a new player with team)

**GET** request(`/players/{id}`)
- Returns a player by id or throws an exception if player with requested id doesn't exist in DB.

**GET** request(`/players`)
- returns a list of all players in DB;

**PATCH** request (`/players/{id}`)
- Updates player's fields or throws an exception if player with requested id doesn't exist in DB.
  Note: only `age` and `experienceInMonths` fields will be updated
  Example of request body:
  `{"name":"Lionel",
  "secondName":"Messi",
  "age":"35",
  "experienceInMonths":192}`

**DELETE** request(`/players/{id}`)
- If player with requested id exists, deletes him from DB, otherwise throws an exception.

##2) _Team controller_

**POST** request(`/teams`):
- Allows to create a new team with no players.

Examples of request body:
`{"title":"Bayern Munich",
"balance":50000000,
"commissionPercent":7}`

**GET** request(`/teams/{id}`)
- Returns a team by id or throws an exception if team with requested id doesn't exist in DB.

**GET** request(`/teams`)
- returns a list of all teams in DB;

**PATCH** request (`/teams/{id}`)
- Updates team's fields or throws an exception if team with requested id doesn't exist in DB.
  Note: `players` field can't be updated by this request
  Example of request body:
  {"title":"PSG",
  "balance":"100000000000",
  "commissionPercent":"10"}

**DELETE** request(`/teams/{id}`)
- If team with requested id exists, deletes it from DB, otherwise throws an exception. If there were players in team, they all become free agents.

##1) _Transfer controller_
**PATCH** request (`transfers/transfer-a-player?buyer-team-id={id}&player-id={id}`)
- Transfers a player from one team to another.
- If player has no team/player is already in buyer team/buyer team has not enough money to make a transfer - throws an exception.

**PATCH** request (`transfers/sign-free-agent?team-id={id}&player-id={id}`)
- If player is a free agent - adds him to the requested team, otherwise throws an exception.

**PATCH** request (`transfers/fire-player?team-id={id}&player-id={id}`)
- If requested player has a contract with requested team - dismisses him from the team, otherwise throws an exception.

# _How to start_:

    1) Clone this repository;
    2) Run `mvn package` and `java -jar target/football-manager-0.0.1-SNAPSHOT.jar`;
    3) Tables `players` and `teams` are creating automatically after running the program;
    4) Postman collection with all required requests is on the following path: `src/main/resources/Football-manager collection.postman_collection.json`
    5) App is based on H2 in memory DB. To enter into H2 console follow the next address `http://localhost:8080/h2-console` and use login and password from `src/main/resources/application.properties` file (by default - `sa` and `password`).

_P.S.: the most popular exceptions are handled by CustomGlobalExceptionHandler._