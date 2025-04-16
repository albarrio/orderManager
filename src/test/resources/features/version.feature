#version.feature
Feature: Version can be retrieved
  Scenario: Client call GET /version
    When client calls /version
    Then client receives status code 200
    And client receives server version "1.0"