Feature: Getting animals from the get Api

  Scenario: Validation of positive response code for Get Animals from the application
    Given i am an authenticated user
    When i hit the get animals api url
    Then i get 200 as the response code

  Scenario: Validation of positive response body for Get Animals from the application
    Given i am an authenticated user
    When i hit the get animals api url
    Then i get animals in the response body of the api

  Scenario: Validation of negative response code for Get Animals from the application
    Given i am an unauthenticated user
    Then i get 400 as the response code

  Scenario: Validation of negative response body for Get Animals from the application
    Given i hit the get animals api url without access token
    Then i get 401 as the response code