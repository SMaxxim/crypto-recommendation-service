cd contract 
./mvnw clean install
cd ../service
./mvnw clean install jib:dockerBuild
