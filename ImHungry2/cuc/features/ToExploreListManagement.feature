Feature: Managing a list on the I'm Hungry website

Background:

	Given I am on the I'm Hungry To Explore Page

Scenario: return to the search page

	When I press the Return to Search Page button
	Then I should navigate to the search page

Scenario: return to the results page

	When I press the Results Page button
	Then I should navigate back to the results page, with the previous search term

Scenario: press the List Management button with no list selected

	When nothing is selected in the menu
	And I press the List Management button
	Then I should remain on the list management page with the items for the To Explore list displayed

Scenario: press the List Management button with favorites selected

	When Favorites is selected in the menu
	And I press the List Management button
	Then I should navigate to the list management page with the items for the Favorites list displayed

Scenario: press the List Management button with do not show selected

	When Do Not Show is selected in the menu
	And I press the List Management button
	Then I should navigate to the list management page with the items for the Do Not Show list displayed

Scenario: press the List Management button with to explore selected

	When To Explore is selected in the menu
	And I press the List Management button
	Then I should navigate to the list management page with the items for the To Explore list displayed

Scenario: click on a restaurant item

	When I select a restaurant item
	Then I should navigate to the restaurant page

Scenario: click on a recipe item

	When I select a recipe item
	Then I should navigate to the recipe page
