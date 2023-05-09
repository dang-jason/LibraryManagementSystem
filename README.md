To download this repo run the following command or use the git features of your IDE.

git clone url_of_repo

    Commit as often as possible
    Write useful commit messages
    Every time you sit down to work run git pull to make sure you have your latest changes
    Every time you commit and whenever you are done working for the day run git push to make sure your changes are stored
    DO NOT remove or comment out parts of the gitignore. You may add to it.
    DO NOT use the GitHub website to make commits or modify files. If you are unable to use git locally please come to office hours
    DO NOT make branches, pull requests, or issues

# Login Page
<img width="690" alt="LoginPage" src="https://github.com/dang-jason/LibraryManagementSystem/assets/113158176/6b4443c5-3b56-4bc6-8536-81476feeee97">

# Dashboard
<img width="1100" alt="Dashboard" src="https://github.com/dang-jason/LibraryManagementSystem/assets/113158176/a0b12465-0ff7-4969-b8b6-a018307f0f7c">

# Programmer Documentation
### Program Structure
The Online Library is structured using Java. The code is divided into several modules, each with specific functionalities. The main modules include:
* finalServer: This module will handle all server-side activities. Some of these activities include creating the server socket for clients to connect to, updating the other online clients when an item is checked out, returned, or put on hold, and communicating the catalog of items with the client using MongoDB.
* finalClient: This module will contain mostly all of the client functionalities and features. There are multiple fxml files to enable the GUI for the login, dashboard, hold, and previous checkout screens. It also connected with MongoDB to log in to the account and grab images when needed for the item. There are also controllers that handle ActionEvents from the GUI when prompted to. 

Each module is designed to perform specific tasks, and their functionalities are integrated into a single system to provide seamless performance.

### Starting the Server
To start the server:
The server can be chosen to run on the local machine or on a cloud server, however, the IP Address for the client must be changed depending on the IP address at which the server is located. In the finalClient module Client class there is a field “private static int host = “IPADDRESS” and depending on where the server is run make sure to update the IP address here before starting the client.

To start the server simply double-click the JAR file or run using the terminal command "java -jar path/to/client_jar"

Once the server is started, it will be ready to accept client requests.

# User Documentation
### User Guide
The user guide for The Online Library is easy to follow. To use the client, simply follow these steps:
* Step 1: Ensure the library server is online

From Terminal:
* Step 2: Please launch the terminal and utilize the command "java -jar path/to/client_jar" to initiate the client application. Kindly ensure that Java version 1.8 is installed on your system prior to executing the command.

From jar:
* Step 2: Double-click the JAR file
* Step 3: After launching the application, new users are required to register an account, while existing users can simply log in to access the platform.
* Step 4: Browse the catalog and checkout, return, or hold an item that you desire.
To checkout, return, or hold an item, first click the item then click the desired action button.
You can switch between the tabs based on what you are looking for.
### Features
The Online Library comes with the following features:
* Library Members: Library members must register and create an account to use the library
* Item Catalog: Gui will display items that are contained in the online library.
* Borrow and Return Books: Library members can borrow books that are available and return items that they have borrowed.
* The library contains at least five catalog items: There are five books and five video games to choose from.
* Invalid Actions: Alerts will show up for invalid actions, such as wrong password/username, checking * out/returning invalid items, and holding items unnecessarily.
* Quit/Logout buttons: There are features to exit the client application swiftly.
### Extra Features
* The Online Library also includes the following optional features:
* Due Dates for items to automatically return items: When an item has been checked out, the library member will have 14 days until they are automatically returned.
* A hold system for when items are not available: A user will be able to reserve/be placed in a queue for an item if it is currently checked out. Only the user that is in front of the queue can check out the item once it has been returned. 
* Using the Observable class and Observer interface: The server utilizes the Observable and Observer to update the client when an action is performed that needs to be updated within the server.
* Use a cloud server to host the library server: Utilizing a cloud server, Microsoft Azure VM to host the library server, which clients can connect to.
* Cryptography techniques - password hashing/salting: Utilizing salting and SHA-256 hashing to save the login and password externally.
* Use MongoDB or other databases: Use MongoDB to store profiles, images, and library items.



