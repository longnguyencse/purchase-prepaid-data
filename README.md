# A brief explanation for the software development principles, patterns & practices
being applied
- SOLID principles
- DRY priciples
- MVC Pattern
- OOP Pattern
- DTO Pattern
# A brief explanation for the code folder structure and the key Java libraries and
frameworks being used
## folder structure
	- controller
	- service
	- repository
	- model
	- dto
	- config
	- utils
	
- Java 8, Spring Boot, Hibernate/Spring JPA
- OKHTTP2
# All the required steps in order to get the application run on a local computer
- Install docker
- run command on infrastructure folder
 - Start mysql and create schema core_app : 
 docker run --name mysql-latest  \
-p 3306:3306 -p 33060:33060  \
-e MYSQL_ROOT_HOST='%' -e MYSQL_ROOT_PASSWORD='1234567a'   \
-d mysql/mysql-server:latest

- install rabitmq: in infrastructure/reabitmq: docker-compose up
- 


# Full CURL commands to verify the APIs (include full request endpoint, HTTP
Headers and request payload if any)
- get voucher
- purchase data 
- send sms
