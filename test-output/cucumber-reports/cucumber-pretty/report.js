$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("UI-Test.feature");
formatter.feature({
  "line": 1,
  "name": "Checkout items in the basket",
  "description": "Please use home page of https://www.saucedemo.com/",
  "id": "checkout-items-in-the-basket",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 4,
  "name": "Check item total cost and tax",
  "description": "",
  "id": "checkout-items-in-the-basket;check-item-total-cost-and-tax",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "I am on the home page",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I login in with the following details",
  "rows": [
    {
      "cells": [
        "userName",
        "Password"
      ],
      "line": 7
    },
    {
      "cells": [
        "standard_user",
        "secret_sauce"
      ],
      "line": 8
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 10,
  "name": "I add the following items to the basket",
  "rows": [
    {
      "cells": [
        "Sauce Labs Backpack"
      ],
      "line": 11
    },
    {
      "cells": [
        "Sauce Labs Fleece Jacket"
      ],
      "line": 12
    },
    {
      "cells": [
        "Sauce Labs Bolt T-Shirt"
      ],
      "line": 13
    },
    {
      "cells": [
        "Sauce Labs Onesie"
      ],
      "line": 14
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 16,
  "name": "I  should see 4 items added to the shopping cart",
  "keyword": "And "
});
formatter.step({
  "line": 17,
  "name": "I click on the shopping cart",
  "keyword": "And "
});
formatter.step({
  "line": 18,
  "name": "I verify that the QTY count for each item should be 1",
  "keyword": "And "
});
formatter.step({
  "line": 19,
  "name": "I remove the following item:",
  "rows": [
    {
      "cells": [
        "Sauce Labs Fleece Jacket"
      ],
      "line": 20
    }
  ],
  "keyword": "And "
});
formatter.step({
  "line": 21,
  "name": "I  should see 3 items added to the shopping cart",
  "keyword": "And "
});
formatter.step({
  "line": 22,
  "name": "I click on the CHECKOUT button",
  "keyword": "And "
});
formatter.step({
  "line": 23,
  "name": "I type \"FirstName\" for First Name",
  "keyword": "And "
});
formatter.step({
  "line": 24,
  "name": "I type \"LastName\" for Last Name",
  "keyword": "And "
});
formatter.step({
  "line": 25,
  "name": "I type \"EC1A 9JU\" for ZIP/Postal Code",
  "keyword": "And "
});
formatter.step({
  "line": 27,
  "name": "I click on the CONTINUE button",
  "keyword": "When "
});
formatter.step({
  "line": 28,
  "name": "Item total will be equal to the total of items on the list",
  "keyword": "Then "
});
formatter.step({
  "line": 29,
  "name": "a Tax rate of 8 % is applied to the total",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});