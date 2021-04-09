Wongnai Backend Assignment
===

Requirements

```
1. Java 8 (JDK 1.8) (either OracleJDK or OpenJDK)
2. Maven 3.5 or up
3. Recommended IDE IntelliJ 2018.3+ Community Edition or up
4. Internet speed at least 10 Mbps
5. Install Maven
6. mvn and java command should work on a command Prompt/Terminal
```

There are 2 parts for the assignment.
1. Warm up practice. It will be a short practice.
2. Movie Search System. It will contain 4 marks for you to complete. 

## PART 1 WARM UP
This assignment was designed to test basic java and algorithm and it only has 1 mark for you. Let's get started! 

### Practice 1

Open DigitalRoot.java then implement the method to find out the digital root of the positive input number.

Run class DigitalRootTest.java to test whether your code is working correctly or not. All unit tests in this file must be passed and if you think the tests are not covered enough you can add more.

Definition of Digital root number

```
Digital root of a non-negative integer is the single-digit value obtained by an iterative process of summing digits, 
on each iteration using the result from the previous iteration to compute the digit sum. 
The process continues until a single-digit number is reached.
// Example:
//   Input : 12345
//   Output : 6 (Because 1 + 2 + 3 + 4 + 5 equals 15 and then 1 + 5 equals 6)
```

## PART 2 Movie Search System

There are 4 practices for this part. We recommended you to do all the practices respectively to prevent any unknown error or misunderstanding while you are working. 

Your work is to complete the business logic and make all unit tests we prepared passed. You are allowed to modify any code as long as unit tests but make sure that unit tests are still working correctly. However, there were unit tests for the happy case only. So, you can add more unit tests to make it coverage. 

If you confront with any questions or any unknown case that we didn't mention in the practice, you can make your own choice. 

### Practice 1

This practice is about the movie search system which will be a web service that users can query the result via RESTful API and result as JSON.

The project has already implemented/configured all necessary dependencies, controller, model, database and integration test except the business logic that you have to complete.

First, make sure that your project can be started.
- Open a terminal
- Change directory to the project directory
- Run the following command.

```
mvn spring-boot:run
```
    
After the server started, go to URL http://localhost:8080/movies then you will see the "Hello World!"

Now, you are ready to start the practice. 

Situation: We want the system to search movies by specifying text and we found a movie database on the internet which the data is represented by JSON format already. Your work is finding the way to download or fetch that JSON data and correctly convert them into the java object. We do not need search function yet.

However, JSON data is updated occasionally. So, data may outdated if you download and store them into the local database.

Open `MovieDataServiceImpl.java` then complete fetchAll method which downloads data from `MOVIE_DATA_URL`, convert them into `MoviesResponse`.  

When you complete the logic, try to run `MovieDataServiceImplIntegrationTest` all test cases in this file must be passed. (There are multiple test files in the project. So, try to run just only this file first to make sure that your implementation is correct).


### Practice 2

Situation: We already have a method to fetch movie data from the previous practice. Next, we will create a simple web service that can search through the movie data by specifying text.

Go to `SimpleMovieSearchService` class then implement the logic to search movie data that come from `MovieDataService`.

#### Searching Example

Suppose a user specifies "Glorious" as a query text. The result must be

```
The Glorious Lady
The Glorious Fool
One Glorious Day
One Glorious Night
Glorious Betsy
His Glorious Night
Borat! Cultural Learnings of America for Make Benefit Glorious Nation of Kazakhstan
```

At this point, we don't want to support the partial search yet. If user specify somethings like `Glorios XXXYYY` as keyword search, just return result as empty.

Run `SimpleMovieSearchServiceIntegrationTest` and `MoviesControllerIntegrationTest` to test your method. Also, start the server and make sure that API http://localhost:8080/movies/search?q=Glorious is working.

### Practice 3
Situation: After launching the previous simple search feature, the team tells us that searching is too slow and needs some optimization to make it faster. 

We made some investigation and the cause is we always download data when we searched and the data is too big.

So, we need to occasionally download the data, maybe when a server is started, then store them into the database. We then search from a database instead. It must be faster as we don't have the overhead time to download the data anymore. The data may outdated is an acceptable trade-off for the fastest searcher.
 
Also the same as previous practice, we don't need to support the partial search. 

You may involve the following files.
1. DatabaseMovieSearchService
2. MovieDataSynchronizer
3. Movie
4. MovieRepository

All test in `DatabaseMovieSearchServiceIntegrationTest` must be passed.

### Practice 4
Situation: At this moment, we want to do a partial search instead.
Also, the time complexity for searching from the database (practice 3) is O(N*M) (which N is the total movies in the database and M is the total words of the movie title). We've tried load testing by sending many search requests and it seems to consume much CPU.

We want to make it faster than O(N*M) and reduce CPU consumption to support more requests.

One way we can achieve this is to implement an inverted index.
There is an explanation about an inverted index in `InvertedIndexMovieSearchService` already.

You may not need to make it complex. Just implement the in-memory inverted index by the data structure of java is acceptable. Anyway, if you come up with other approach, we also accept that too.

Run `InvertedIndexMovieSearchServiceIntegrationTest`. All test must be passed.

If the test passed, please go to `MoviesController` and modify bean from `simpleMovieSearchService` to `invertedIndexMovieSearchService`. We will then test the RESTful API with different search service this time. (also make sure that test in MoviesControllerIntegrationTest is still OK after changed bean).

Start the server and search using API. Everything should work.


### Before sending
Please make sure all of your unit test are passed by following command.

```
mvn clean install
```

Then start server and test all RESTful API again.
Send us your assignment if everything works fine.


## How to send
Please compress all of your source code to zip. file. Upload it to a link we provided in the email
