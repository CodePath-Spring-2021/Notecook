Original App Design Project - README Template
===

# Notecook

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Notecook allows you to scan or type in a list of ingredients so you can find simple, easy recipes involving only what you have available. Dive into new experiences as you explore different recipes, or share your own delicious recipes with others around the world.

### App Evaluation

- **Category:** Social Networking / Food
- **Mobile:** Connects to user's camera to take a picture of a list of ingredients and implements the pull-to-refresh feature to load new, real-time recipes. The app would work primarily on mobile devices, but can be extended to work on computers in the future.
- **Story:** Notecook allows people to discover new recipes, explore different cultures, and connect with others through food. 
- **Market:** Any individual who is interested in cooking and may not have all of the ingredients available in their kitchen. 
- **Habit:** The app will be visited frequently based on how often the user cooks. User can go on the app even when they aren't cooking, just to get inspiration and new cooking ideas.
- **Scope:** App could first start as a way for people to share and get recipes with available ingredients. In the future, users can like and favorite recipes and follow or chat with others. (extend the social networking aspect of the app)

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can create a new account or log in and log out of his or her account.
* User can take a picture and the app will scan for listed ingredients in picture or directly type in ingredients
* Search for recipes with users' available ingredients and displays relevant recipes involving those ingredients
* User can favorite recipes and view them later
* User can tap a post to view recipe details
* Users can also post new recipes along with the ingredients and pictures
* User can refresh the list of recipes by pulling down to refresh
* Settings (Notifications, General, Accessibility, Display)

**Optional Nice-to-have Stories**

* User can load more recipes once he or she reaches the bottom of the feed using infinite scrolling
* Translate a list of ingredients or a recipe in another language 
* Provide a list of possible ingredient substitutions

### 2. Screen Archetypes

* Log in/Sign up Screen
   * User will stayed logged in even after exiting the app if they didn't log out prior
* Recipe List Screen
   * Main screen, first screen users see upon logging into app
* Detailed Recipe View
   * Detail recipe details (including images, ingredient list, and link to website)
* Type Ingredients Screen
   * User can type in their ingredients into textboxes
* Take Picture Screen
   * User can take a picture of their ingredients list
   * App will read in the texts displayed in the picture
* Post Recipe Screen
   * Prompt user for a new recipe and picture 
* Profile/Favorites Screen
   * Displays user's information
   * Show a list of favorited recipes
* Settings Screen
   * Log out

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Recipe List
* Find Recipes
    * Type Ingredients
    * Take Picture
* Post Recipe
* Profile/Favorites

**Flow Navigation** (Screen to Screen)

* Log-in/Sign-up Screen => Recipe List Screen after signing in user (or creating a new account for user)
* Take Picture Screen => Recipe List 
* Type Ingredients => Recipe List
* Post Recipe Screen => Recipe List
* Recipe List => Detailed Recipe View
* Profile/Favorites List => Detailed Recipe View
* Settings (after tapping Log Out) => Log-in/Sign-up Screen
   
## Wireframes
<img src="https://github.com/CodePath-Spring-2021/Notecook/blob/main/wireframes.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
