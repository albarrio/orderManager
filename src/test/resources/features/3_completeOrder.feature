#Complete Order.feature
  Feature: Complete an order and get order status
    As a Operator
    I want to be able to complete an order
    So that I can complete order on receive his new status
  Scenario: Complete an order in progress
    Given I am a Operator of the system
    When There are orders in the queue
    Then I can process the next order in queue
    And I can complete the order in progress
    And I should see the new order status as "COMPLETED"
