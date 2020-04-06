# SafetyNet - Alerts v0.1 beta release

### Infos
author: 		Thierry SCHREINER
release date:	07/04/2020

### Content
This first release contains the administrative Person endpoint that can be used for CRUD operations on person data.
It also contains actuators (health, info & metrics)

The persons are saved in persons table of alerts_prod DB or alerts_tests DB (user 'root' / mdp 'rootroot') 

### Help
GET - http://localhost/Person   >>> returns the list of persons recorded in DataBase.	
	Response: 200 OK - An array of persons or 404 Not found if persons table is empty
		[
		    {
		        "id": 1,
		        "firstName": "John",
		        "lastName": "Boyd",
		        "address": "1509 Culver St",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-6512",
		        "email": "jaboyd@email.com"
		    },
		    {
		        "id": 2,
		        "firstName": "Jacob",
		        "lastName": "Boyd",
		        "address": "1509 Culver St",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-6513",
		        "email": "drk@email.com"
		    },
		    ...
		    {
		        "id": 23,
		        "firstName": "Eric",
		        "lastName": "Cadigan",
		        "address": "951 LoneTree Rd",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-7458",
		        "email": "gramps@email.com"
		    }
		]
		
GET - http://localhost/Person/{lastName}/{fistName}   >>> returns the person named {firstName} {lastName} if exists in DB.
	Response: 200 An array of persons or 404 Not found
	for example /Person/Boyd/Tenley returns :
		{
		    "id": 3,
		    "firstName": "Tenley",
		    "lastName": "Boyd",
		    "address": "1509 Culver St",
		    "city": "Culver",
		    "zip": "97451",
		    "phone": "841-874-6512",
		    "email": "tenz@email.com"
		}
  
POST - http://localhost/Person   >>> add the person in DB persons table, if this person is not already recorded in DB.
  for example you can add 'Tenley Boyd' with this JSON raw body (Do not add an id):
		{
		    "firstName": "Tenley",
		    "lastName": "Boyd",
		    "address": "1509 Culver St",
		    "city": "Culver",
		    "zip": "97451",
		    "phone": "841-874-6512",
		    "email": "tenz@email.com"
		}

PUT - http://localhost/Person   >>> add the person in DB persons table, if this person is not already recorded in DB.
	Response 201 Created or 404 Not found
	for example you can update 'Tenley Boyd' data with this JSON raw body (Do not add an id):
		{
		    "firstName": "Tenley",
		    "lastName": "Boyd",
		    "address": "1509 Culver St",
		    "city": "Culver",
		    "zip": "97451",
		    "phone": "123-456-7890",
		    "email": "Tenley.Boyd@OpenClassrooms.com"
		}
		
DELETE - http://localhost/Person/{lastName}/{fistName}   >>> Delete the person named {firstName} {lastName} if exists in DB.
	Response 200 OK or 404 Not found

-------   Other URI: Persons (with a final s)   -------  
POST - http://localhost/Persons   >>> add a list of persons in DB persons table. Use to copy all JSON list of person.
  for example you can add the project 5 given data:
		[
		    {
		        "id": 1,
		        "firstName": "John",
		        "lastName": "Boyd",
		        "address": "1509 Culver St",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-6512",
		        "email": "jaboyd@email.com"
		    },
		    {
		        "id": 2,
		        "firstName": "Jacob",
		        "lastName": "Boyd",
		        "address": "1509 Culver St",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-6513",
		        "email": "drk@email.com"
		    },
		    ...
		    {
		        "id": 23,
		        "firstName": "Eric",
		        "lastName": "Cadigan",
		        "address": "951 LoneTree Rd",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-7458",
		        "email": "gramps@email.com"
		    }
		]
  
  