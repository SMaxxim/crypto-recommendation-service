cd contract 
call mvnw clean install
cd ..\service
mvnw clean install jib:dockerBuild