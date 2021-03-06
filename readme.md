# SafetyNet - Alerts v2.0.1 release

### Infos
author: 		Thierry 'Docky' SCHREINER   -   DA Java student - Open ClassRooms

mentored by:	Yann 'Monsieur Plus' IRRILO	

release date:	12/05/2020

###Install

- The application 'Safetynet Alerts' works with MySQL 8.0 DataBase that is called **alerts_prod**.

- A second DataBase, called **alerts_tests**, is used for integration tests.

- Both Databases use the same user ("root") and password ("rootroot").

- An SQL script file, named **'alerts.sql'** is available in **src/main/resources**.

- In src/main/resources you can found the **Alerts_postman_collection.json** file that you can import in Postman to use my request collection. Also available online at https://app.getpostman.com/run-collection/a63d09364c2b8fad69c1

- The **JSON data importation is made using administrative endpoints**. The Postman collection provide **3 batch POST requests**, that must be executed in this **order : firestation/batch, person/batch and finally medicalRecord/batch** (logical and mandatory to link data). 

### Content
This fifteenth release v2.0.1 fixes some problems:

- moving MedicalRecordDTO from model package to DTO package,

- replacing AlertsApplication.class by ClassName.class in the LoggerFactory.getLogger method of each Class  where it has been forgot. 


Previous releases contains : 

- the administrative Person endpoint that can be used for CRUD operations on person data (since v0.1);

- the administrative firestation endpoint that can be used for CRUD operations on address - FireStation associations (since v0.2);

- the endpoints OPS#7 'comunityEmail/{city}' and OPS#3 'phoneAlert/{firestation}' (since v1.0);

- the administrative medicalRecord endpoint to perform CRUD operations on MedicalRecord (since v1.1);

- the OPS#1 endpoint allows user to get the list of persons covered by a given fire station and the dissociated count of adults and children covered (since v1.2);

- the administrative endpoints integration tests (for firestation, person, medicalRecords) and increase JaCoCo covering rate by adding unit tests (PersonMappingTest and missed branches in existing tests), since v1.21;

- the OPS#2 ChildAlert that provides a list of children (first name, last name, age) and a list of adults living at a given address (since v1.3);

- a bugfix in update methods of person and medicalRecord administrative endpoints (v1.3.1);

- a rewrited OPS#1 'firestation' endpoint, and improvement of OPS#2 'chilAlert' and added OPS#4 'fire' and OPS#5 'flood' endpoints (v1.4);

- the OPS#6 'personInfo/{firstName}/{lastName} endpoints and refactors a great part of project classes.
OPS#6 **returns a list of PersonInfoDTO** instead of a single PersonInfoDTO, to be able to deal with with namesakes (since v1.5).

- the modification of the seven OPS requests using @RequestParam instead of @PathVariables, and the addition of a slf4j - log4j logger that performs info and debug logs to console & file for each OPS request (since 1.6).

- The twelfth one v1.6.1 modifies v1.6.2 setter, getter & constructor of PersonInfoDTO & MedicalRecord to avoid the exposition of internal representation by returning reference to mutable object and replaces bad practices (!= comparison in MedicalMapping) 

- Release v1.6.2 fix the inactive httptrace actuator bug and adds details in health one.

The fourteenth release v2.0 comes with a big refactoring of all the application: 

	- split AddressFirestation class in 2 classes, AddressDTO and AddressEntity;

	- add a new AddressMapping class;

	- rename all AddressFireStation* as Address*;

	- fix put requests troubles;

	- develop logging;

	- work on http responses;

	- consolidate tests;

	- fix CheckStyle issues... 

Application also contains actuators (health, info, metrics & httptrace).


### The person endpoint
	
**POST - http://localhost:8080/person/batch**   >>> add a list of persons in DB persons table. Used to copy all JSON list of person.
  for example you can add the project 5 given data:
  
		[
		    {
		        "firstName": "John",
		        "lastName": "Boyd",
		        "address": "1509 Culver St",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-6512",
		        "email": "jaboyd@email.com"
		    },
		    {
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
		        "firstName": "Eric",
		        "lastName": "Cadigan",
		        "address": "951 LoneTree Rd",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-7458",
		        "email": "gramps@email.com"
		    }
		]
  
**GET - http://localhost:8080/person**   >>> returns the list of persons recorded in DataBase.
	
		[
		    {
		        "firstName": "John",
		        "lastName": "Boyd",
		        "address": "1509 Culver St",
		        "city": "Culver",
		        "zip": "97451",
		        "phone": "841-874-6512",
		        "email": "jaboyd@email.com"
		    },
		    {
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

	for example /Person/Boyd/Tenley returns :
		{
		    "firstName": "Tenley",
		    "lastName": "Boyd",
		    "address": "1509 Culver St",
		    "city": "Culver",
		    "zip": "97451",
		    "phone": "841-874-6512",
		    "email": "tenz@email.com"
		}
  
**POST - http://localhost:8080/person**   >>> add the person in DB persons table, if this person is not already recorded in DB.
  for example you can add 'Tenley Boyd' with this JSON raw body:
  
		{
		    "firstName": "Tenley",
		    "lastName": "Boyd",
		    "address": "1509 Culver St",
		    "city": "Culver",
		    "zip": "97451",
		    "phone": "841-874-6512",
		    "email": "tenz@email.com"
		}

**PUT - http://localhost:8080/person/{firstName}/{lastName}**   >>> update the person in DB persons table, if this person is not already recorded in DB.

	for example you can update 'Tenley Boyd' data with this JSON raw body:
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

  
### The firestation endpoint

**POST - http://localhost:8080/firestation/batch**   >>> add a list of address - FiresStation association in DB. Used to copy all JSON list of person. for example you can add the project 5 given data:  
	
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
	    { "address":"947 E. Rose Dr", "station":"1" },
	    { "address":"951 LoneTree Rd", "station":"2" }
	]
  
**GET - http://localhost:8080/firestation**   >>> returns the list of address - FireStation associations recorded in DataBase.

	[
	    {
	        "address": "1509 Culver St",
	        "city": "Culver",
	        "zip": "97451",
	        "station": "3"
	    },
	    {
	        "address": "29 15th St",
	        "city": "Culver",
	        "zip": "97451",
	        "station": "2"
	    },
			...
			
	    {
	        "address": "951 LoneTree Rd",
	        "city": "Culver",
	        "zip": "97451",
	        "station": "2"
	    }
	]

**GET - http://localhost:8080/firestation/{address}**   >>> returns the AddressDTO if exists in DB.

	for example /firestation/29 15th St returns :
	    {
	       "address": "29 15th St",
			"city" 
	        "city": "Culver",
	        "zip": "97451",
	       "station": "2"
	    }  

  
**POST - http://localhost:8080/firestation**   >>> add the address - FiresStation association, if this person is not already recorded in DB.
  
  
	for example you can add '29 15th St' address with this JSON raw body (Do not add an id):
	    {
	        "address": "29 15th St",
	        "station": "2"
	    }
	      

**PUT - http://localhost:8080/firestation/{address}**   >>> update the address - FiresStation association, if this address is recorded in DB.

	for example you can update '29 15th St' address  with this JSON raw body :
		{
	        "address": "29 15th St",
	        "station": "3"
	    }
		
		
**DELETE - http://localhost:8080/firestation/{address}**   >>> Delete the address - FiresStation association if exists in DB.

	

### The medicalRecord endpoint
  
**POST - http://localhost:8080/medicalRecord/batch**   >>> add a list of medical record in DB.  It uses a for each loop and the add one MedicalRecord to add and join each medical record.
  

 **GET - http://localhost:8080/medicalRecord**   >>> returns the list of MedicalRecord in DataBase.

	[
	    {
	        "firstName": "John",
	        "lastName": "Boyd",
	        "birthdate": "03/06/1984",
	        "medications": [
	            "aznol:350mg",
	            "hydrapermazol:100mg"
	        ],
	        "allergies": [
	            "nillacilan"
	        ]
	    },
	    {
	        "firstName": "Jacob",
	        "lastName": "Boyd",
	        "birthate": "03/06/1989",
	        "medications": [
	            "pharmacol:5000mg",
	            "terazine:10mg",
	            "noznazol:250mg"
	        ],
	        "allergies": []
	    },
		...
	    {
	        "firstName": "Eric",
	        "lastName": "Cadigan",
	        "birthdate": "08/06/1945",
	        "medications": [
	            "tradoxidine:400mg"
	        ],
	        "allergies": []
	    }
	]

**GET - http://localhost:8080/medicalRecord/{lastName}/{firstName}**   >>> returns the medical record if exists in DB.

	for example /firestation/29 15th St returns :
	    {
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

 
### OPS endpoints

**OPS#1: GET - http://localhost:8080/firestation/number?stationNumber=<stationNumber>**   >>> returns the list of persons covered by the given fire station number.  Use the same OpsPersonDTO class that is used in OPS#2.

	{
	    "adultCount": 8,
	    "childCount": 3,
	    "total": 11,
	    "coveredPersons": [
		
		    {
		        "id": 1,
		        "firstName": "John",
		        "lastName": "Boyd",
		        "age": "36 years old",
		        "address": "1509 Culver St",
		        "phone": "841-874-6512"
		    },
		    {
		        "id": 2,
		        "firstName": "Jacob",
		        "lastName": "Boyd",
		        "age": "31 years old",
		        "address": "1509 Culver St",
		        "phone": "841-874-6513"
		    },
				...
		    {
		        "id": 18,
		        "firstName": "Allison",
		        "lastName": "Boyd",
		        "age": "55 years old",
		        "address": "112 Steppes Pl",
		        "phone": "841-874-9888"
		    }
		]
	}
	
**OPS#2: GET - http://localhost:8080/childAlert?address=<address>**   >>> returns the list of children (first name, last name, age) and a list of adults living at a given address. Use the same OpsPersonDTO class that is used in OPS#1.

	{
	    "address": "1509 Culver St",
	    "childList": [
	        {
	            "firstName": "Tenley",
	            "lastName": "Boyd",
	            "age": "8 years old"
	            "address": "1509 Culver St",
	            "phone": "841-874-6512"
	        },
	        {
	            "firstName": "Roger",
	            "lastName": "Boyd",
	            "age": "19 months old"
	            "address": "1509 Culver St",
	            "phone": "841-874-6512"
	        }
	    ],
	    "adultList": [
	        "John Boyd",
	        "Jacob Boyd",
	        "Felicia Boyd"
	    ]
	}

**OPS#3: GET - http://localhost:8080/phoneAlert?station=<station>**   >>> returns the list of phone numbers of inhabitants covered by the given fire station number.

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

**OPS#4: GET - http://localhost:8080/fire?address=<address>**   >>> returns the household list of persons for a given address and the covering fire station number. Use the same OpsPersonInfoDTO class that is used in OPS#5 & #6.

	{
	    "personList": [
	        {
	            "firstName": "Foster",
	            "lastName": "Shepard",
	            "age": "40 years old",
	            "medications": [],
	            "allergies": [],
	            "phone": "841-874-6544"
	        },
	        {
	            "firstName": "Clive",
	            "lastName": "Ferguson",
	            "age": "26 years old",
	            "medications": [],
	            "allergies": [],
	            "phone": "841-874-6741"
	        }
	    ],
	    "addressDTO": {
	        "address": "748 Townings Dr",
	        "city": "Culver",
	        "zip": "97451",
	        "station": "3"
	    }
	}


**OPS#5: GET - http://localhost:8080/flood?stations=<stationList>**   >>> returns the list of household covered by the given list of fire station number. Use the same OpsPersonInfoDTO class that is used in OPS#4 & #6.

	[
    {
        "station": "1",
        "householdList": [
            {
                "personList": [
                    {
                        "firstName": "Peter",
                        "lastName": "Duncan",
                        "age": "19 years old",
                        "medications": [],
                        "allergies": [
                            "shellfish"
                        ],
                        "phone": "841-874-6512"
                    }
                ],
                "addressDTO": {
                    "address": "644 Gershwin Cir",
                    "city": "Culver",
                    "zip": "97451",
                    "station": "1"
                }
            },
            {
                "personList": [
                    {
                        "firstName": "Reginold",
                        "lastName": "Walker",
                        "age": "40 years old",
                        "medications": [
                            "thradox:700mg"
                        ],
                        "allergies": [
                            "illisoxian"
                        ],
                        "phone": "841-874-8547"
                    },
                    {
                        "firstName": "Jamie",
                        "lastName": "Peters",
                        "age": "38 years old",
                        "medications": [],
                        "allergies": [],
                        "phone": "841-874-7462"
                    }
                ],
                "addressDTO": {
                    "address": "644 Gershwin Cir",
                    "city": "Culver",
                    "zip": "97451",
                    "station": "1"
                }
            },
				...
            {
                "personList": [
                    {
                        "firstName": "Foster",
                        "lastName": "Shepard",
                        "age": "40 years old",
                        "medications": [],
                        "allergies": [],
                        "phone": "841-874-6544"
                    },
                    {
                        "firstName": "Clive",
                        "lastName": "Ferguson",
                        "age": "26 years old",
                        "medications": [],
                        "allergies": [],
                        "phone": "841-874-6741"
                    }
                ],
                "addressDTO": {
                    "address": "644 Gershwin Cir",
                    "city": "Culver",
                    "zip": "97451",
                    "station": "1"
                }
            },
            {
                "personList": [
                    {
                        "firstName": "Tessa",
                        "lastName": "Carman",
                        "age": "8 years old",
                        "medications": [],
                        "allergies": [],
                        "phone": "841-874-6512"
                    }
                ],
                "addressDTO": {
                    "address": "834 Binoc Ave",
                    "city": "Culver",
                    "zip": "97451",
                    "station": "3"
                }
            }
        ]
    }
]

**OPS#6: GET - http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>**   >>> returns the PersonInfoDTO list of person(s) that have this firstName and lastName (a list to be able to deal with with namesake, more than a single PersonInfoDTO). 

This example show the response if John Boyd'son is first named John like his father (instead of Roger).

	[
	    {
	        "firstName": "John",
	        "lastName": "Boyd",
	        "age": "36 years old",
	        "medications": [
	            "aznol:350mg",
	            "hydrapermazol:100mg"
	        ],
	        "allergies": [
	            "nillacilan"
	        ],
	        "phone": "841-874-6512"
	    }
	]
	        
	        
**OPS#7: GET - http://localhost:8080/communityEmail?city=<city>**   >>> returns the list of eMail address of inhabitants of the given city.

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
