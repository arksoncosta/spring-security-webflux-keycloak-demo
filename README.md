# spring-security-webflux-keycloak-demo

## How to get access token using username and password ?
Just run a command bellow
curl --location --request POST 'http://localhost:8082/realms/my-demo/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id={client_id}' \
--data-urlencode 'username={username}' \
--data-urlencode 'password={password}' \
--data-urlencode 'grant_type=password'
