## Requirements

1. Java - 1.8.x

2. gradle - 7.x.x

3. Mysql - 5.x.x

4. Spring - 2.2.x above

5. Junit5

## Steps to Setup

**1. Clone the application**

```bash
git clone 
```

**2. Database configuration**
```bash
Query to execute : src\main\resources\data.sql
```
Configure database and data before running application.


**3. Change mysql username and password as per your installation**

+ open `src\main\resources\application.properties`

+ open `src\test\resources\application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using gradle**

For build and run application ,navigate to build.gradle file path and execute following command in cmd:
```bash
gradlew
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following APIs.

| Sr.NO  | API Function | Description |API URL | Input Parameter | Output |
| ------------- | ------------- | ------------- | ------------- |------------- |------------- |
| 1  | Create Score   | Add scrore for player | POST http://localhost:8080/api/oyo/player/  | {"player":"PlayerO","score": 100,"time":"2020-01-01 10:10:00"} | Record details in Json format|
| 2  | Get Score with Id   | Get player score with id| GET http://localhost:8080/api/oyo/player/{id}  | {id}= 1 | Record details in Json format|
| 3 | Delete Score with Id | Delete record with id from database|DELETE http://localhost:8080/api/oyo/player/delete/{id}  | {id}=1  | Record delete information message  |
| 4 | List of Score by playerX | Get all score list with player name|GET http://localhost:8080/api/oyo/playerList?name={player}  | {Player}=PlayerA OR Playera  | Detailed score list in JSON format |
| 5 | Get all score after 1st November 2020| Get all score list after given date|GET http://localhost:8080/api/oyo/playerList?fromDate={date}  | {date} =2021-01-01  | Detailed score list in JSON format |
| 6 | Get all scores by player1, player2 and player3 before 1st december 2020| Get all score list for player list before given date|GET http://localhost:8080/api/oyo/playerList?name={player}&toDate={date}  |{player}=PlayerA,{date}=2020-12-01(YYYY-MM-DD) | Detailed score list in JSON format |
| 7 | Get all scores after 1 Jan 2020 and before 1 Jan 2021| Get top score (time and score),low score (time and score),average score,list of all the scores (time and score) |GET http://localhost:8080/api/oyo/playerList?fromDate={fromdate}&toDate={todate}   | {fromdate} =2020-01-01,{todate}=2021-01-01 | Detailed in JSON format |


## Testing

To  application's tests, navigate to build.gradle file path and execute following command in cmd:

   gradlew test clean --info   
    
    
