Feature: Searching for a type of food on the I'm Hungry website

Background:
	Given I am on the I'm Hungry Search page

Scenario: check input fields
	Then the search box should be prefilled with the phrase Enter food
	Then the number of results box should be prefilled with 5

Scenario: search with no search term and no number of results

	When I enter nothing in the search box
	And I enter nothing in the results box
	And I press the "Feed Me" button
	Then I should remain on the search page

Scenario: search with a search term and no number of results

	When I enter "pasta" in the search box
	And I enter nothing in the results box
	And I press the "Feed Me" button
	Then I should remain on the search page

Scenario: search with no search term and a negative number of results

	When I enter nothing in the search box
	And I enter -5 in the results box
	And I press the "Feed Me" button
	Then I should remain on the search page

Scenario: search with a search term and a negative of results

	When I enter "pasta" in the search box
	And I enter -5 in the results box
	And I press the "Feed Me" button
	Then I should remain on the search page

Scenario: search with no search term and 0 results

	When I enter nothing in the search box
	And I enter 0 in the results box
	And I press the "Feed Me" button
	Then I should remain on the search page

Scenario: search with a search term and 0 results

	When I enter "pasta" in the search box
	And I enter 0 in the results box
	And I press the "Feed Me" button
	Then I should remain on the search page

Scenario: search with no search term and some number of results

	When I enter nothing in the search box
	And I enter 5 in the results box
	And I press the "Feed Me" button
	Then I should remain on the search page

Scenario: search with a search term and some number of results

	When I enter "pasta" in the search box
	And I enter 5 in the results box
	And I press the "Feed Me" button
	Then I should be sent to the results page
	Then there should be 5 recipes and 5 restaurants
	Then there should be a title reading Results for pasta

