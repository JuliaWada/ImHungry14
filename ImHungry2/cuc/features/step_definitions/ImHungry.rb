
/^Search page validation$/

Given(/^I am on the I'm Hungry Search page$/) do
 visit 'http://localhost:8080/ImHungry2/index.jsp'
end

Then(/^the search box should be prefilled with the phrase Enter food$/) do
 expect(find_field("searchBar")['placeholder']).to eq "Enter food"
end

Then(/^the number of results box should be prefilled with 5$/) do
 expect(find_field('numResults').value).to eq '5'
end

And(/^I enter nothing in the results box$/) do
 fill_in 'numResults', :with => ""
end

When("I enter {int} in the results box") do |int|
  fill_in 'numResults', :with => int
end

When(/^I enter nothing in the search box$/) do
 fill_in 'searchBar', :with => ""
end

When("I wait {int} seconds") do |int|
  sleep(int.to_i)
end

When(/^I enter "(.*?)" in the search box$/) do |f|
 fill_in 'searchBar', :with => f
end

And(/^I press the "Feed Me" button$/) do
 expect(page).to have_content("Feed Me")
 click_button('feedMeButton')
 sleep(20)
end

Then(/^I should remain on the search page$/) do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/index.jsp")
end

Then("I should be sent to the results page") do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/results.jsp")
end

Then("there should be {int} recipes and {int} restaurants") do |int, int2|
  i = 0
  loop do
    page.all('restaurant'+i.to_s)
    page.all('recipe'+i.to_s)
    i += 1
    if i == int
      break
    end
end
end

Then(/^there should be a title reading Results for (.*?)$/) do |str|
 expect(page).to have_content("Results for " + str)
end




/^Results$/

Given(/^I am on the I'm Hungry Results page$/) do
 visit 'http://localhost:8080/ImHungry2/index.jsp'
 fill_in 'searchBar', :with => "taco"
 click_button('feedMeButton')
end

When(/^I press the Return to Search Page button$/) do
 click_button('RTSbutton')
end

Then(/^I should navigate to the search page$/) do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/index.jsp")
end

When(/^nothing is selected in the menu$/) do
  select("", :from => 'listOptions')
end

When(/^Favorites is selected in the menu$/) do
 select("Favorites", :from => 'listOptions')
end

When(/^Do Not Show is selected in the menu$/) do
 select("Do Not Show", :from => 'listOptions')
end

When(/^To Explore is selected in the menu$/) do
 select("To Explore", :from => 'listOptions')
end

And(/^I press the List Management button$/) do
 click_button('Lbutton')
end

Then(/^I should remain on the results page$/) do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/results.jsp")
end

Then("I should navigate to the list management page with the items for the Favorites list displayed") do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/listMgmt.jsp?name=Favorites")
end

Then("I should navigate to the list management page with the items for the To Explore list displayed") do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/listMgmt.jsp?name=To%20Explore")
end

Then("I should navigate to the list management page with the items for the Do Not Show list displayed") do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/listMgmt.jsp?name=Do%20Not%20Show")
end




/^List Management page validation$/

Given("I am on the I'm Hungry Favorites Page") do
 visit 'http://localhost:8080/ImHungry2/index.jsp'
 fill_in 'searchBar', :with => "taco"
 click_button('feedMeButton')
 sleep(20)
 Capybara.ignore_hidden_elements = false
 find(".recipeCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
 select("Favorites", :from => 'listOptions')
 click_button('addLbutton')
 click_button('RPbutton')
 sleep(20)
 Capybara.ignore_hidden_elements = false
 find(".restaurantCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
 select("Favorites", :from => 'listOptions')
 click_button('addLbutton')
 click_button('RPbutton')
 sleep(20)
 select("Favorites", :from => 'listOptions')
 click_button('Lbutton')
end

Given("I am on the I'm Hungry Do Not Show Page") do
 visit 'http://localhost:8080/ImHungry2/index.jsp'
 fill_in 'searchBar', :with => "taco"
 click_button('feedMeButton')
 sleep(20)
 Capybara.ignore_hidden_elements = false
 find(".recipeCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
 select("Do Not Show", :from => 'listOptions')
 click_button('addLbutton')
 click_button('RPbutton')
 sleep(20)
 Capybara.ignore_hidden_elements = false
 find(".restaurantCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
 select("Do Not Show", :from => 'listOptions')
 click_button('addLbutton')
 click_button('RPbutton')
 sleep(20)
 select("Do Not Show", :from => 'listOptions')
 click_button('Lbutton')
end

Given("I am on the I'm Hungry To Explore Page") do
 visit 'http://localhost:8080/ImHungry2/index.jsp'
 fill_in 'searchBar', :with => "taco"
 click_button('feedMeButton')
 sleep(20)
 Capybara.ignore_hidden_elements = false
 find(".recipeCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
 select("To Explore", :from => 'listOptions')
 click_button('addLbutton')
 click_button('RPbutton')
 sleep(20)
 Capybara.ignore_hidden_elements = false
 find(".restaurantCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
 select("To Explore", :from => 'listOptions')
 click_button('addLbutton')
 click_button('RPbutton')
 sleep(20)
 select("To Explore", :from => 'listOptions')
 click_button('Lbutton')
end

When("I press the Results Page button") do
 click_button('RPbutton')
 sleep(20)
end

Then("I should navigate back to the results page, with the previous search term") do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/results.jsp")
end

Then("I should remain on the list management page with the items for the Favorites list displayed") do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/listMgmt.jsp?name=Favorites")
end

Then("I should remain on the list management page with the items for the To Explore list displayed") do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/listMgmt.jsp?name=To%20Explore")
end

Then("I should remain on the list management page with the items for the Do Not Show list displayed") do
 expect(page.current_url).to eq("http://localhost:8080/ImHungry2/listMgmt.jsp?name=Do%20Not%20Show")
end

When("I select a restaurant item") do
 Capybara.ignore_hidden_elements = false
 find(".restaurantCard", visible: false, match: :first).click
 @stra = find('.website')['href']
 Capybara.ignore_hidden_elements = true
end

Then("I should navigate to the restaurant page") do
 Capybara.exact = false
 expect(page.current_url[0...46]).to eq("http://localhost:8080/ImHungry2/restuarant.jsp")
 Capybara.exact = true
end

When("I select a recipe item") do
 Capybara.ignore_hidden_elements = false
 find(".recipeCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
end

Then("I should navigate to the recipe page") do
 expect(page.current_url[0...42]).to eq("http://localhost:8080/ImHungry2/recipe.jsp")
end




/^Recipe and Restaurant Pages$/

Given("I am on an I'm Hungry Recipe page") do
 visit 'http://localhost:8080/ImHungry2/index.jsp'
 fill_in 'searchBar', :with => "taco"
 click_button('feedMeButton')
 sleep(20)
 Capybara.ignore_hidden_elements = false
 find(".recipeCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
end

Given("I am on an I'm Hungry Restaurant page") do
 visit 'http://localhost:8080/ImHungry2/index.jsp'
 fill_in 'searchBar', :with => "taco"
 click_button('feedMeButton')
 sleep(20)
 Capybara.ignore_hidden_elements = false
 find(".restaurantCard", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
end


When("I press the add to list button") do
 click_button('addLbutton')
end

Then (/^the restaurant should be in the list$/) do
 Capybara.ignore_hidden_elements = false
 find(".website", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
 expect(page.current_url).to eq(@stra)
end

When("I click on a link to the restaurant's website") do
 Capybara.ignore_hidden_elements = false
 @str = find('.website')['href']
 find(".website", visible: false, match: :first).click
 Capybara.ignore_hidden_elements = true
end

Then(/^I should go to the restaurant's website$/) do
 expect(page.current_url).to eq(@str)
end

Then("I should remain on the recipe page") do
 expect(page.current_url[0...42]).to eq("http://localhost:8080/ImHungry2/recipe.jsp")
end

Then("I should remain on the restaurant page") do
 expect(page.current_url[0...46]).to eq("http://localhost:8080/ImHungry2/restuarant.jsp")
end

When("I press the Printable Version button") do
 click_button('PVbutton')
end

Then("I should navigate to a printable version of the recipe page, which has no buttons or menus") do
 expect(page.current_url[42]).to eq("?")
 expect(page).to have_no_content('Printable Version')
 expect(page).to have_no_content('Back to Results')
 expect(page).to have_no_content('Favorites')
 expect(page).to have_no_content('Do Not Show')
 expect(page).to have_no_content('To Explore')
 expect(page).to have_no_content('Add to List')
end

Then("I should navigate to a printable version of the restaurant page, which has no buttons or menus") do
 expect(page.current_url[46]).to eq("?")
 expect(page).to have_no_content('Printable Version')
 expect(page).to have_no_content('Back to Results')
 expect(page).to have_no_content('Favorites')
 expect(page).to have_no_content('Do Not Show')
 expect(page).to have_no_content('To Explore')
 expect(page).to have_no_content('Add to List')
end
