Feature: Displaying the results page of the I'm Hungry website

Background:

	Given I am on the I'm Hungry Results page

Scenario: return to the search page

	When I press the Return to Search Page button
	Then I should navigate to the search page

Scenario: press the List Management button with no list selected

	When nothing is selected in the menu
	And I press the List Management button
	Then I should remain on the results page

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

Scenario: add to list
	
	When I select a restaurant item
	When Favorites is selected in the menu
	And I press the add to list button
	And I press the Results Page button
	And Favorites is selected in the menu
	And I press the List Management button
	And I select a restaurant item
	Then the restaurant should be in the list
