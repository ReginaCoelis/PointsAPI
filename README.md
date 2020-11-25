# PointsAPI

Please follow the steps below to test the API

Step 1: Cloning the repository on your local machine. 
Create a new folder locally on your machine. 
Open the repository https://github.com/ReginaCoelis/PointsAPI and on the right hand side, select the CODE drop down option and copy the link : https://github.com/ReginaCoelis/PointsAPI.git .
Right click on the folder and select git bash here. A terminal will open, and you run the command to clone the project:  git clone https://github.com/ReginaCoelis/PointsAPI.git
The above command will clone the project.

Step 2: Packaging the project with maven
Go to the root of the cloned project and run :  ./mvnw package 

Step 3: Running the API
Run the API using the following command:  java -jar ./target/demo-0.0.1-SNAPSHOT.jar

Step 4: Testing the end points
After starting the project, you can test the end points on 
http://localhost:8080/swagger-ui.html#/points45controller

