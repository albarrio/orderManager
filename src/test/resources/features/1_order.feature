#order.feature
  Feature: Create new Order
    As a customer
    I want to be able to create an order
    So that I can receive an order ID for follow my order
  Scenario: Successful order creation
    Given I am a registered customer
    When I create a new order with valid details
    Then I should receive a valid order ID
    And I get the order status
    And I should see the order status as "PENDING"
