### API

[API documentation](https://github.com/SMaxxim/crypto-recommendation-service/blob/main/api-documentation.md)

### Implementation details

#### REST
Contract-first approach is used for REST api.
Module crypto-recommender-api contains 
specification in \contract\src\main\resources\crypto-recommender-api.yml 
and generates classes with controllers and models using openapi-generator-maven-plugin 
(see pom.xml)

#### Database
Module crypto-recommender-service implements api defined in crypto-recommender-api.
The database (postgresql) is used for the calculation of all statistics.
The reason for using a database is flexibility and extensibility, 
it will be easier to add new cryptocurrencies and modify endpoints to use different periods for analysis. 
Moreover, it will be possible to add new cryptocurrencies without modifying the service itself.
In fact, a database is used, because, for real use of service, information about other months seems to be 
strictly required (2022 year wasn't good for cryptocurrencies)

#### Database creation
For the initial creation of the database, liquibase is used.
(service\src\main\resources\db\changelog\changelog.xml)
Initial data is also loaded through liquibase (service\src\main\resources\db\data\)
(drawback: extra column price_unix_timestamp had to be added to implement loading from csv). 
In the future, probably it will be better to implement a special service (admin dashboard) for data loading.

#### Rate limiting
For API rate limiting bucket4j is used
(note: for simplicity, a local cache is used, 
 which is not suitable in case there will be many instances of service. 
 But in this case, probably will be better to use rate limiting in the load balancer.)

#### Containerization
Containerization is based on jib maven plugin.
In the root folder there is bat/sh scripts for rebuilding and image creating:
build_image.bat, build_local_image.bat
Also, there is docker-compose.yml for convenient development on a local machine
