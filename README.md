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
- **Story:** It is an app that allows people to discover new recipes, explore different cultures, and connect with others through food. 
- **Market:** Any individual who is interested in cooking and may not have all of the ingredients available in their kitchen. 
- **Habit:** The app will be visited frequently based on how often the user cooks. User can go on the app even when they aren't cooking, just to get inspiration and new cooking ideas.
- **Scope:** App could first start as a way for people to share and get recipes with available ingredients. In the future, users can like and favorite recipes and follow or chat with others. (extend the social networking aspect of the app)

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [ ] User can create a new account or log in and log out of his or her account.
- [x] User can directly type in the ingredients or take a picture and the app will scan for listed ingredients in picture
      
  <img src="https://github.com/CodePath-Spring-2021/Notecook/blob/main/findbyingredients_walkthrough.gif" width=150>
      
- [x] Search for recipes with user's available ingredients
- [x] Display relevant recipes involving those ingredients

  <img src="https://github.com/CodePath-Spring-2021/Notecook/blob/main/RecipeListFragWalkthrough.gif" width=150>

- [x] User can tap a post to view recipe details (DetailActivity will be implemented in Milestone 3)
- [ ] User can also post new recipes along with the ingredients and pictures
- [x] User can see other users' recipe posts
      
  <img src="https://github.com/CodePath-Spring-2021/Notecook/blob/main/homescreen_walkthrough.gif" width=150>
      
- [x] User can refresh the list of recipes by pulling down to refresh (see above for GIF)

**Optional Nice-to-have Stories**

- [ ] User can load more recipes once he or she reaches the bottom of the feed using infinite scrolling
- [ ] Provide a list of possible ingredient substitutions
- [ ] User can favorite recipes and view them later

### 2. Screen Archetypes

* Log-in/Sign-up Screen
   * User will stayed logged in even after exiting the app if they didn't log out prior
* Home Screen
    * Main screen, first screen users see upon logging into app
    * Shows all recipe posts by other users
* Recipe List Screen
   * List of recipes with the inputted ingredient list
* Detailed Recipe View
   * Detail recipe details (including images, ingredient list, and link to website)
* Type Ingredients Screen
   * User can type a list of ingredients into a textbox (separate each item using commas)
* Take Picture Screen
   * User can take a picture of their ingredients list
   * App will read in the texts displayed in the picture
* Post Recipe Screen
   * Prompt user for a new recipe and picture 
* Profile Screen
   * Displays user's information
   * Log out button

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Find Recipes
    * Type Ingredients
    * Take Picture
* Post Recipe
* Profile

**Flow Navigation** (Screen to Screen)

* Log-in/Sign-up Screen => Home Screen after signing in user (or creating a new account for user)
* Take Picture Screen => Recipe List Screen
* Type Ingredients Screen => Recipe List Screen
* Post Recipe Screen => Home Screen
* Recipe List => Detailed Recipe View
* Home Screen => Detailed Recipe View
* Profile (after tapping Log Out) => Log-in/Sign-up Screen
   
## Wireframes

<img src="https://github.com/CodePath-Spring-2021/Notecook/blob/main/wireframes.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 

### Models
**RecipePost**

| Property | Type     | Description |
| -------- | -------- | --------    |
| objectId | String   | unique ID for the recipe post (default field)        |
| author | String     | recipe author        |
| image | File     | image that author posts        |
| title | String     | name of recipe        |
| description | String     | recipe description by author        |
| readyInMinutes | DateTime     |  time of how long recipe takes in total       |
| ingredientsList | String     | comma-separated list of ingredients        |
| recipeSteps | String     | array list of steps        |

**User**


| Property | Type     | Description |
| -------- | -------- | --------    |
| Username    | String     |user-created name used for log in|
| Password    | String     |user's choice of a string of characters used to differentiate users|

### Networking
- Log-in/Sign-up Screen
    - (Create/POST) Create new user
    - (Create/POST) Log in user
        ```
        Log.i(TAG, "Attempting to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Invalid username/password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                    Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        ```
- Home Screen
    - (Read/GET) Load in users' recipes
- Post Recipe Screen
    - (Create/POST) Create new recipe
- Profile Screen
    - (Read/GET) Query logged in user object

**OPTIONAL: Existing API**

**Spoonacular API**
* Base URL - https://api.spoonacular.com/recipes

| HTTP Verb | Endpoint     | Description |
| -------- | -------- | --------    |
| `GET` | /{id}/analyzedInstructions   | get recipe instructions for given recipeId       |
| `GET` | /findByIngredients?ingredients={ingredientsList}   | return recipes with the given ingredientsList        |
| `GET` | /{id}/information   | get recipe information with the given recipeId        |
