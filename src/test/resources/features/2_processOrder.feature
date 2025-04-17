#Process Order.feature
  Feature: Process an order in queue orders
    As a Operator
    I want to be able to process an order
    So that I can process an order on top of queue and receive his orderID
  Scenario: Process an order in queue
    Given I am a Operator of the system
    When There are orders in the queue
    Then I can process the first order in queue
    And I get the orderId And I should see the order status as "IN_PROGRESS"
