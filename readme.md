# Catalyte Health Api and Frontend

This is the Catalyte Health website and the API. The instructions on how to download and use will be found below.


## Getting Started

These instructions will get you a copy of the project up and running on your local
machine for development and testing purposes.

## First ##

### Download the Catalyte Health website

Open the Terminal on your computer and copy the command below:

`git clone git@git.catalystitservices.com:MDW_2018_/Tyler-Bridges/Tyler_Final_Project/Final_Project_Frontend.git`

## Setting up the Database ##

Download the database

`$ git clone git@git.catalystitservices.com:CatalystTraining/final-health-project-db.git`

Make sure that you have [Docker](https://www.docker.com/products/overview) installed
on your local machine. You will need Docker in order to run the sample database image.

### Using the Docker Image

The following sections outline the use

#### Building The Docker Image

```
$ docker build -t healthdb .
```
#### Running The Docker Image
```
$ docker run -d -p 27017:27017 --name healthdb healthdb        
```

### Connecting to MongoDB ###

You can use MongoDB Compass, RoboMongo, or the Terminal to connect to the
running MongoDB instance. All collections are located in the healthdb database.
We would recommend using Kitematic to find the port on localhost that your
MongoDB is running on, as it may differ from one device to another.

### In a terminal window: ###

$ docker exec -it healthdb mongo - will connect to the running container and start the mongo shell.

### For MongoDB Compass: ###

Use Docker's Kitematic GUI to find the port your instance of this container
is runnning on. Enter this port into Compass under the field that asks for the
localhost port. You should be able to view the database through Compass.

## Third ##

Clone down the Final_Project_Backend.

`git@git.catalystitservices.com:MDW_2018_/Tyler-Bridges/Tyler_Final_Project/Final_Project_Backend.git`

After you have completed that Open the Final_Project_Backend inside your Java IDE. Navigate
 to the CHAPI_runner file and run the application as a java project to start the server.

## Fourth ##
Navigate to the Final_Project_Frontend. Once inside type the following command

npm install

npm start
This should open a tab inside of your chosen web browser.
If not, just type into the Navigation of your browser the following link

http://localhost:3000


### Completed ###

This should have the development enviroment up and running.



# Testing #

This guide is assuming you are using the Java IDE Eclipse.

## First ##
In order to test the functional classes inside of the API, you have to first open the Final_Project_Backend inside of Eclipse by importing it as an existing maven project.

## Second ##
If you open the file structure of Final_Project_Backend, follow it down until you see src/test/java.

If you right click on the src/test/folder you will see two options close to the middle of the popup.

### Run as Junit test ###
If you select run as Junit test, this will run all the tests in the application and show you what passes and what fails.

### Run coverage as Junit test ###
This will run all the tests as well showing you the percentage of the application that is covered. 


## End notes ##

Authored by: Tyler Bridges