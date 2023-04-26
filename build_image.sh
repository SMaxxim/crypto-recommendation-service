cd contract 
./mvnw clean install
cd ../service
./mvnw clean install jib:build -Djib.to.image=${docker_hub_username}/crypto-recommender -Djib.from.auth.username=${docker_hub_username} -Djib.from.auth.password=${docker_hub_password}