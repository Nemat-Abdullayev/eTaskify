# eTaskify application

***

## Installation
* Java jdk version 18
* data base (postgresql in the docker container should be up)
* test created with the Junit5
***
#  Run application
* first run tests and enjoy ☺
* second build application with gradle for create build file
* then go to the docker-compose.yml file direction
* run this command [ docker-compose -f docker-compose.yml up --build ]


### hint about application
* in the application swagger-ui used for documentation
* and the url is : http://localhost:8080/swagger-ui.html  
* in the application user have 3 role [ 'ADMIN' , 'MANAGER', 'USER' ]
* role inserted by default (while application up)
* ADMIN created by default in organization request
* MANAGER created by admin for manage tasks
* USER also created by admin
* Task assigned by manager


