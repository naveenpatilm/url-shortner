# url-shortner
HTTP service that serves to shorten URLs.

#1. Download the source code to your system by running below command:

	git clone git@github.com:naveenpatilm/url-shortner.git

#2. Switch into workspace:
	
	cd url-shortner
  
#3. To download required dependencies and run test cases perform:
	
	mvn clean install
  
#4. To start the application run(This starts the url shortner application and it is available at port 8080.
http://localhost:8080 is the url from which the application can be accessed):
	
	mvn spring-boot:run

API details:

POST http://localhost:8080/account

	Request:
		{
			"accountId":"second"
		}

	Response:
		{
			"success": true,
			"description": "Your account is opened",
			"password": "c93c8f0f"
		}
	
POST http://localhost:8080/register

	Header: Authorization ex:c93c8f0f

	Request:
		{
			"url": "http://www.stackoverflow.com",
			"redirectType":301
		}

	Response:
		{
			"shortUrl": "http://localhost:8080/bbf2c7b"
		}
	
GET http://localhost:8080//statistic/{accountId}

	Header: Authorization ex:c93c8f0f

	Response:
		{
			"http://www.stackoverflow.com": 9
		}
		
Help page:
	http://localhost:8080/help
	
If you use postman for API testing, than you can import the below collection url in postman to test APIs:
	https://www.getpostman.com/collections/50f64d4c304689a38040
