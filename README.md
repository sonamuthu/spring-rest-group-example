A simple example of using Spring for REST-ful web services. Just a place to hack around, used for some training and discussion on the Kuali Rice team.

# Spring documentation:

https://spring.io/guides/gs/rest-service/
https://spring.io/guides/gs/actuator-service/
http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html

# Running the Project

You can launch the application from the command line (if you have Maven installed) or from an IDE.

To launch using maven, run the following:

```
mvn spring-boot:run
```

To run from an IDE, load the project into your IDE and set up a run configuration for the ```com.westbrain.sandbox.spring.rest.Application``` main class.

# Using the API

We will use the Advanced Rest Client which is a chrome browser plugin to view REST calls

# Get group 1

```
http://localhost:8080/groups/1
Response Status Code:200 OK
```

# Get a group that doesn't exist

```
http://localhost:8080/groups/500
Response Status Code:404 Not Found
```

# Get all groups

```
http://localhost:8080/groups
Response Status Code:200 OK
```

# Create a new group

```
http://localhost:8080/groups
Payload: {"id":23,"name":"Twenty Three","description":"This is a new group twenty three"}
Response Status Code:201 Created
Response Headers: Location: http://localhost:8080/groups/23
```

# Update a group

```
http://localhost:8080/groups/23/update
Payload: {"id":23,"name":"Twenty Three","description":"This is an updated group twenty three"}
Response Status Code:200 OK
```

# Delete a group

```
http://localhost:8080/groups/1
Response Status Code:200 OK
Response Headers: Location: http://localhost:8080/groups
```

# Check the members on group 1

```
http://localhost:8080/groups/1/members
Response Status Code:200 OK
```

# Look at a specific member

```
http://localhost:8080/groups/1/members/1
Response Status Code:200 OK
```

# Add a member to group 1

```
http://localhost:8080/groups/1/members
Payload: {"name":"Trouble"}
Response Status Code:201 Created
Response Headers: Location: http://localhost:8080/groups/1/members/77
```

# Update a member on group 1

```
{"id":"22","name":"Jennie PUT"}
http://localhost:8080/groups/3/members/22
Payload: {"id":"22","name":"Jennie PUT"}
Response Status Code:200 OK
```

# Delete a member on group 1

```
http://localhost:8080/groups/1/members/22
Response Status Code:200 OK
Response Headers: Location: http://localhost:8080/groups/1/members
```



