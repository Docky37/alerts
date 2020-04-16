# SafetyNet - Alerts v1.1 release

### Infos
author: 		Thierry SCHREINER

release date:	14/04/2020

### Content
The fourth release v1.1 adds an administrative medicalRecord endpoint to perform CRUD operations on MedicalRecord.
The post request to add one MedicalRecord automatically makes the one to one join between the new MedicalRecord and the Person concerned if exists (else the MedicalRecord add is rejected).

The Post request "add a list of MedicalRecord" performs a for each loop and uses add one MedicalRecord to add and join each.
 
A cascade delete has been created to remove the MedicalRecord of a person when this person is deleted.

Previous releases contains : 

- the administrative Person endpoint that can be used for CRUD operations on person data (since v0.1);

- the administrative firestation endpoint that can be used for CRUD operations on address - FireStation associations (since v0.2);

- the OPS endpoints comunityEmail/{city} and phoneAlert/{firestation} (since v1.0).

It also contains actuators (health, info & metrics).

The data are saved in alerts_prod DB or alerts_tests DB (user 'root' / mdp 'rootroot') that contains persons and address tables. 

### The person endpoint
**GET - http://localhost:8080/person**   >>> returns the list of persons recorded in DataBase.
	
	Response: 200 OK - An array of persons (or an empty array if no Person in DataBase)
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
		
**GET - http://localhost:8080/person/{lastName}/{fistName}**   >>> returns the person named {firstName} {lastName} if exists in DB.

	Response: 200 A person or 404 Not found
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
  
**POST - http://localhost:8080/person**   >>> add the person in DB persons table, if this person is not already recorded in DB.
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
		Response 201 Created

**PUT - http://localhost:8080/person**   >>> add the person in DB persons table, if this person is not already recorded in DB.

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
		
**DELETE - http://localhost:8080/person/{lastName}/{fistName}**   >>> Delete the person named {firstName} {lastName} if exists in DB.

	Response 200 OK or 404 Not found
	
	
-------   Other URI: Persons (with a final s)   -------

**POST - http://localhost:8080/persons**   >>> add a list of persons in DB persons table. Used to copy all JSON list of person.
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
  
**GET - http://localhost:8080/firestation**   >>> returns the list of address - FireStation associations recorded in DataBase.

	Response: 200 OK - An array of address - FireStation associations (or an empty array if table is empty)
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

**GET - http://localhost:8080/firestation/{address}**   >>> returns the address - FiresStation association if exists in DB.

	Response: 200 An array of address - FiresStation association or 404 Not found
	for example /firestation/29 15th St returns :
	    {
	        "id": 2,
	        "address": "29 15th St",
	        "station": "2"
	    }  

  
**POST - http://localhost:8080/firestation**   >>> add the address - FiresStation association, if this person is not already recorded in DB.
  
  
	Response 201 Created
	for example you can add '29 15th St' address with this JSON raw body (Do not add an id):
	    {
	        "address": "29 15th St",
	        "station": "2"
	    }
	      

**PUT - http://localhost:8080/firestation**   >>> update the address - FiresStation association, if this address is recorded in DB.

	Response 201 Created or 404 Not found
	for example you can update '29 15th St' address  with this JSON raw body (Do not add an id):
		{
	        "address": "29 15th St",
	        "station": "3"
	    }
		
		
**DELETE - http://localhost:8080/firestation/{address}**   >>> Delete the address - FiresStation association if exists in DB.

	Response 200 OK or 404 Not found
	
	
-------   Other URI: firestations (with a final s)   -------

**POST - http://localhost:8080/firestations**   >>> add a list of address - FiresStation association in DB. Used to copy all JSON list of person. for example you can add the project 5 given data:  
	
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

### The medicalRecord endpoint
  
**GET - http://localhost:8080/medicalRecord**   >>> returns the list of MedicalRecord in DataBase.

	Response: 200 OK - An array of MedicalRecord (or an empty array if no MedicalRecords exists)
	[
	    {
	        "id": 1,
	        "firstName": "John",
	        "lastName": "Boyd",
	        "birthDate": "03/06/1984",
	        "medications": [
	            "aznol:350mg",
	            "hydrapermazol:100mg"
	        ],
	        "allergies": [
	            "nillacilan"
	        ]
	    },
	    {
	        "id": 2,
	        "firstName": "Jacob",
	        "lastName": "Boyd",
	        "birthDate": "03/06/1989",
	        "medications": [
	            "pharmacol:5000mg",
	            "terazine:10mg",
	            "noznazol:250mg"
	        ],
	        "allergies": []
	    },
		...
	    {
	        "id": 23,
	        "firstName": "Eric",
	        "lastName": "Cadigan",
	        "birthDate": "08/06/1945",
	        "medications": [
	            "tradoxidine:400mg"
	        ],
	        "allergies": []
	    }
	]

**GET - http://localhost:8080/medicalRecord/{lastName}/{firstName}**   >>> returns the medical record if exists in DB.

	Response: 200 An array of MedicalRecord or 404 Not found
	for example /firestation/29 15th St returns :
	    {
	        "id": 2,
	        "firstName": "Jacob",
	        "lastName": "Boyd",
	        "birthDate": "03/06/1989",
	        "medications": [
	            "pharmacol:5000mg",
	            "terazine:10mg",
	            "noznazol:250mg"
	        ],
	        "allergies": []
	    }

**POST - http://localhost:8080/medicalRecord**   >>> add a list of MedicalRecord. Used to copy all JSON list of medical records. for example you can add the project 5 given data.  

**PUT - http://localhost:8080/medicalRecord/{lastName}/{firstName}**   >>> update a medical record, if it exists in DB.

	Response 201 Created or 404 Not found
	for example you can update Boyd/Jacob medical record with this JSON raw body (Do not add an id):
		{
	        "firstName": "Jacob",
	        "lastName": "Boyd",
	        "birthDate": "03/06/1989",
	        "medications": [
	            "pharmacol:5000mg",
	        ],
	        "allergies": [
	        	"peanuts"
	        ]
	    }

**DELETE - http://localhost:8080/medicalRecord/{lastName}/{firstName}**  >>> Delete the medical record if exists in DB.

	Response 200 OK or 404 Not found
	
	
-------   Other URI: medicalRecords (with a final s)   -------

**POST - http://localhost:8080/medicalRecords**   >>> add a list of medical record in DB.  It uses a for each loop and the add one MedicalRecord to add and join each medical record.  
	
		
  
### OPS endpoints

  
**GET - http://localhost:8080/communityEmail/{city}**   >>> returns the list of eMail address of inhabitants of the given city.

	[
	    "jaboyd@email.com",
	    "drk@email.com",
	    "tenz@email.com",
	    "jaboyd@email.com",
	    "jaboyd@email.com",
	    "drk@email.com",
	    "tenz@email.com",
	    "jaboyd@email.com",
	    "jaboyd@email.com",
	    "clivfd@ymail.com",
	    "tcoop@ymail.com",
	    "jpeter@email.com",
	    "aly@imail.com",
	    "lily@email.com",
	    "soph@email.com",
	    "ward@email.com",
	    "zarc@email.com",
	    "reg@email.com",
	    "jpeter@email.com",
	    "bstel@email.com",
	    "ssanw@email.com",
	    "bstel@email.com",
	    "gramps@email.com"
	]
	
**GET - http://localhost:8080/phoneAlert/{station}**   >>> returns the list of phone numbers of inhabitants covered by the given fire station number.

	[
	    "841-874-6512",
	    "841-874-6513",
	    "841-874-6512",
	    "841-874-6512",
	    "841-874-6544",
	    "841-874-6512",
	    "841-874-6544",
	    "841-874-6741",
	    "841-874-6874",
	    "841-874-8888",
	    "841-874-9888"
	]