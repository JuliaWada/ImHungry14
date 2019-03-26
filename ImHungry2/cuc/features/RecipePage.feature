Feature: Displaying a recipe page on the I'm Hungry website

Background:

	Given I am on an I'm Hungry Recipe page

Scenario: return to the results page

	When I press the Results Page button
	Then I should navigate back to the results page, with the previous search term

Scenario: navigate to the printable version page

	When I press the Printable Version button
	Then I should navigate to a printable version of the recipe page, which has no buttons or menus

Scenario: add to list when no list is selected

	When nothing is selected in the menu
	And I press the add to list button
	Then I should remain on the recipe page
