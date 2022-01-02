# Lighthouse-App
Lighthouse App
Functionality:
Lighthouse app is a simple application that allows users to add their favourite Lighthouses of Ireland, including their name, description, image and location on the map. All details are stored in the local database. The project all has interchangeable storage inside a JSON file. 
App also features Location access permission implementation, adding, saving and editing lighthouse details like name, description, image and location as well as deleting the lighthouse. The app also has the capability to view saved lighthouses on the map.
The app has 5 main views: 
Login/Signup, this takes the user to the List View and displays Lighthouses that have been added if there are any. This view has a menu that allows the user to choose either to add a new Lighthouse, View existing Lighthouses on a map or Logout.
If the user chooses to add another Lighthouse, another activity is launched with options to add Lighthouse name/description/image and location on the map. The menu here allows to Save changes, Delete or Cancel.
When clicking on the marker location Card View below will display name, description and image of the Lighthouse. 
On map update the Toolbar displays live coordinates.
If the app is launched for the first time Permission to access location will be requested.
There is also a basic authentication through Firebase enabled with some simple validation and messaging. It is missing a more robust authentication and user interaction features.

Git Approach/UX/DX/Model
The project uses Model/View Presenter approach to lighten the load with added features.
I have adopted and linear git commit strategy pushing to git hub as new features are implemented.
The link to Git Hub is as follows: https://github.com/MaryiaBalbachan/Lighthouse-App.

Personal Statement
I have not been able to implement more complex features and my project is heavily based on the material covered in class and labs. I am aiming in all honesty and with sincere hope to have achieved enough for a baseline level.
