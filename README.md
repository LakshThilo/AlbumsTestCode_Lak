# Albums App 
As per requirements

-	Application displays albums list sorted by title
-	Albums persisted for offline viewing

![Alt text](app/AlbumScreen.png?raw=true "Album List Screen") 

Views are displayed as fragments using the Jetpack Navigation component.
The albums are fetched as json from the https://jsonplaceholder.typicode.com/ api using the retrofit library, and persisted using the Google Room Persistence library. 

# Architecture used- MVVM using lifecycle aware components 
The separate code layers of MVVM are:
Model: This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.
View: The purpose of this layer is to inform the ViewModel about the user’s action. This layer observes the ViewModel and does not contain any kind of application logic.
ViewModel: It exposes those data streams which are relevant to the View. Moreover, it servers as a link between the Model and the View. 

# Libraries: 
Dagger dependency injection 
Kotlin Coroutines
Retrofit, okhttp
Mockito TDD
Espresso Automated UI testing
View binding 
Moshi convertor 
Room Database
Other Jetpack components 

# Advantages:
ViewModel does not hold any kind of reference to the View.
Many to 1 relationship exist between View and ViewModel.
No triggering methods to update the View.
You can test the behavior of a ViewModel without involving its view. This also enabled test-driven development of view behavior, which is almost impossible using code-behind.

# Future Development:
Add an AlbumDetailFragment which displays additional information
Improve User Interface design
Add search functionality 
Paging functionality 
