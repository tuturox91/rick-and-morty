# 🎞️ Rick and Morty 🎞️

A RESTful web application that downloads information from a third-party API or searches need data in database. 

## :star: Features  
* Search and returns list of all characters whose name contains the search string.
* Randomly generates a wiki about one character in the universe the animated series Rick & Morty.
* Automatic synchronization with Rick & Morty api once every certain time

## :computer: Technologies
* Spring Boot
* JDK 17
* Apache Maven
* PostgreSQL
* JPA, Hibernate
* Docker

## :clipboard: Project structure

The project is divided into several logical levels:
  * Controller: Separates the UI from the backend logic. The controller has a service component autowired which can help him return the request.
  * Service: Interacts with data through an autowired repository and contains the business logic that move and process data between the data and presentation layers.
  * Repository: Interacts with the chosen way to persist data. Another name for this layer is the data access layer.
  * Data tier: data store/retrieve layer.

## 🛠️ Setup guide

You can find setup guide in guthub [wiki](https://github.com/tuturox91/Cinema-Service/wiki/Start-work-with-project "wiki").
   
