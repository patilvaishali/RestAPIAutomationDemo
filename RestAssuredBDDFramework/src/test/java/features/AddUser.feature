Feature: Validating User API's

Scenario: Verify id user is being successfully added using Create User API
Given Create User payload
When User calls Create user API with POST http request
Then API call is success with status code 200

