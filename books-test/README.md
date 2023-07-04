# ReadMe

## Introduction

The Java application provided in this repository performs operations on a list of books. The main
objective is to use functional programming oriented coding taking advantage of the features
introduced in Java 8, such as the new Stream API, to work with lists in an efficient and concise
way.

## Feature

When the application is run, the user will be prompted by console to enter a text, which will be used to filter on the list of books.


## Build

This project is built using the Maven tool. If you want to run all phases,
just run from the root of the project

````shell script
mvn clean package
````

Once compiled you can run the application with the following command:

````shell script
java -jar target/demo-books-1.0-SNAPSHOT.jar
````