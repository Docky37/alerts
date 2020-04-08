# SafetyNet - Alerts v0.1 beta release

### Infos
author: 		Thierry SCHREINER
release date:	07/04/2020

### Content
This second release contains : 

- the administrative Person endpoint that can be used for CRUD operations on person data ;

- the administrative firestation endpoint that can be used for CRUD operations on address - FireStation associations.

- It also contains actuators (health, info & metrics).

The data are saved in alerts_prod DB or alerts_tests DB (user 'root' / mdp 'rootroot') that contains persons and address tables. 

### The person endpoint
**GET - http://localhost/Person**   >>> returns the list of persons recorded in DataBase.
	
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
		
**GET - http://localhost/Person/{lastName}/{fistName}**   >>> returns the person named {firstName} {lastName} if exists in DB.

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
  
**POST - http://localhost/Person**   >>> add the person in DB persons table, if this person is not already recorded in DB.
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

**PUT - http://localhost/Person**   >>> add the person in DB persons table, if this person is not already recorded in DB.

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
		
**DELETE - http://localhost/Person/{lastName}/{fistName}**   >>> Delete the person named {firstName} {lastName} if exists in DB.

	Response 200 OK or 404 Not found
	
	
-------   Other URI: Persons (with a final s)   -------

**POST - http://localhost/Persons**   >>> add a list of persons in DB persons table. Used to copy all JSON list of person.
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
  
  
### The firestation endpoint
  
**GET - http://localhost/firestation**   >>> returns the list of address - FireStation associations recorded in DataBase.

	Response: 200 OK - An array of address - FireStation associations or 404 Not found if address table is empty
	[
	    {
	        "id": 1,
	        "address": "1509 Culver St",
	        "station": "3"
	    },
	    {
	        "id": 2,
	        "address": "29 15th St",
	        "station": "2"
	    },
			...
			
	    {
	        "id": 13,
	        "address": "951 LoneTree Rd",
	        "station": "2"
	    }
	]

**GET - http://localhost/firestation/{address}**   >>> returns the address - FiresStation association if exists in DB.

	Response: 200 An array of address - FiresStation association or 404 Not found
	for example /firestation/29 15th St returns :
	    {
	        "id": 2,
	        "address": "29 15th St",
	        "station": "2"
	    }  

  
**POST - http://localhost/firestation**   >>> add the address - FiresStation association, if this person is not already recorded in DB.
  
  
	Response 201 Created
	for example you can add '29 15th St' address with this JSON raw body (Do not add an id):
	    {
	        "address": "29 15th St",
	        "station": "2"
	    }
	      

**PUT - http://localhost/firestation**   >>> update the address - FiresStation association, if this address is recorded in DB.

	Response 201 Created or 404 Not found
	for example you can update '29 15th St' address  with this JSON raw body (Do not add an id):
		{
	        "address": "29 15th St",
	        "station": "3"
	    }
		
		
**DELETE - http://localhost/firestation/{address}**   >>> Delete the address - FiresStation association if exists in DB.

	Response 200 OK or 404 Not found
	
	
-------   Other URI: firestations (with a final s)   -------

**POST - http://localhost/firestations**   >>> add a list of address - FiresStation association in DB. Used to copy all JSON list of person. for example you can add the project 5 given data:  
	
	[
		{ "address":"1509 Culver St", "station":"3" },
	    { "address":"29 15th St", "station":"2" },
	    { "address":"834 Binoc Ave", "station":"3" },
	    { "address":"644 Gershwin Cir", "station":"1" },
	    { "address":"748 Townings Dr", "station":"3" },
	    { "address":"112 Steppes Pl", "station":"3" },
	    { "address":"489 Manchester St", "station":"4" },
	    { "address":"892 Downing Ct", "station":"2" },
	    { "address":"908 73rd St", "station":"1" },
	    { "address":"112 Steppes Pl", "station":"4" },
	    { "address":"947 E. Rose Dr", "station":"1" },
	    { "address":"748 Townings Dr", "station":"3" },
	    { "address":"951 LoneTree Rd", "station":"2" }
	]
  