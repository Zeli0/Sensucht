# Sensucht

## About

This is an Android application that recommends users recipes based on their meal budgets, leftover ingredients and kitchen appliances. The application is written entirely in Java, using the following libraries and APIs: Jackson, OkHTTPClient, Firebase, Google Maps API and Spoonacular API.

### Motivation

The motivation for creating this project mainly stemmed from our own lives as young adults in university. To try and cut our costs during our studies, we looked for ways to eat cheaper, which was difficult and also unhealthy if we chose to only eat outside. Thus we wanted to take up cooking for ourselves, yet encountered a whole new problem: we couldn't do half of the recipes that we had found online, because they all had ingredients we couldn't access or tools we couldn't afford. Most importantly, we were unsure about how much these recipes would cost, and whether or not it would be worth it to cook instead of eating out.

This lead us to conceive this application, a friendly recipe recommender that would help tackle these problems and give us recipes we actually could do, and also recommend recipes that would keep our costs low. This app also aims to tackle many other problems faced by people trying to get into cooking. These include issues like dealing with leftover ingredients, locating where to buy the cheapest ingredients, and finding recipes that conform to certain diets or allergies.

### Examples of Use

1. As a student studying abroad on a tight budget, I want to find simple recipes I can cook that can help me save money and also save time in finding ingredients.
2. As a person who wants to get into cooking, I want to get easy recipe recommendations that I can make with the limited tools I have.
3. As a housekeeper catering to a family, I want to find the right recipes so that I can keep my meal costs under budget, and reuse ingredients in the pantry without wasting them.
4. As a vegan, I want to get recipes without meat that I can be comfortable making and eating.
5. As a person who is constantly moving, I want to locate which supermarkets I can get ingredients easily every time I move to a new place

## Features

1. Login Page
	- Input username and password
	- Each account stores information about the customizable profile and user location
2. Customizable Profile
	- Users can record the following:
		- Leftover ingredients: Leftovers the users have
		- Kitchen appliances: What tools users have
		- Dietary Restrictions
		- Meal Budget
3. Supermarket Locator
	- Application will ask for the user’s location
	- App will then obtain locations of supermarkets around the user’s location
4. Recipe Cost Calculator
	- App will calculate the price of purchasing each ingredient in the recipe from nearby supermarkets
	- App tallies the smallest price of each to get the smallest total cost for the recipe
5. Recipe Recommender
	- Based on the customizable profile and recipe costs, the app selects and recommends recipes that meet all criteria

## Technologies Implemented

### OkHTTPClient

This is a library we used to make all of our API calls mentioned below. The library makes HTTP requests with a provided URL and receives the responses.

### Google MAps API

This API was used to display maps inside of our app. The maps are used to display the user's location and the location of nearby supermarkets. The latter is obtained with the Places API inside of Google Maps API, which with a NearbySearch API can help locate specific locations nearby based on a specific keyword and a central location. By giving it the keyword "supermarket" and the user's location Places API can find supermarkets near the user's current location.

### Jsoup API

This API was used for scraping. Similar to OkHTTPClient, Jsoup can make HTTP requests and download the results, but it also has the added function of being able to extract specific elements from HTML pages, making it great for scraping data. We used this to scrape prices for products off of supermarket websites in Singapore so that we could calculate the costs of each recipe.

### Firebase

This was a database API used to create the login page.


## Setup
To access the current version of the app, download the SensuchtV3.apk in the main folder of this repository onto an android device.
