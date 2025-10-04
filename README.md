RSS Feed Reader
Description

This is a desktop Java application built with Apache NetBeans that serves as an RSS feed reader. It is designed to fetch, parse, and manage articles from various news sources, the default case is Politico. 
The application features a user-friendly graphical interface with a login system, allowing for different user roles and functionalities.

Key features include:

    RSS Feed Parsing: Automatically reads and processes RSS feeds from configured news sites.

    Database Integration: Stores articles, their creators, and contributors in an SQL database for persistent storage and easy retrieval.

    User Authentication: A simple login system with two roles:

        Admin: Has the ability to upload new articles and delete all existing articles from the database.

        Standard User: Can create and edit articles through a dedicated tab and write general text in another.

    Pop-up Interface: All extra features are managed through pop-up windows.

Getting Started
Prerequisites

You will need the following software installed on your machine to run and develop this application:

    Java Development Kit (JDK) 8 or higher

    Apache NetBeans IDE

    An SQL database system (e.g., MySQL, PostgreSQL, or SQLite)

    JDBC Driver for your chosen database (e.g., mysql-connector-java-8.0.28.jar)

Installation

    Clone the repository:

    git clone [repository-url]
    cd [project-directory]

    Open in NetBeans:

        Open Apache NetBeans.

        Click File > Open Project and select the cloned project folder.

    Configure the database:

        Create a new database in your SQL system.
        The SQL script has been provided in the project file!

    Add the JDBC driver:

        In NetBeans, right-click the Libraries folder in the Projects pane.

        Select Add JAR/Folder...

        Navigate to the location of your downloaded JDBC driver file and add it.

    Make sure JavaProjectZero/Dao/src/main/resources/config/db.properties and .../repository.properties are correctly configured to your SQL database!!!

    Build and run the application:

        Right-click the project in NetBeans.

        Select Run.

Usage
Login

Upon launching the application, you will be presented with a login screen.

    Admin Login: Use the default admin credentials (username: admin, password: admin) to access the admin features.

    Standard User Login: You will need to first register to access the standard user features.

Admin Features

Once logged in as an admin, you will have access to all functionalities.

    Upload Articles: The application will fetch and parse the articles, storing them in the database.

    Delete All Articles: This action will permanently remove all articles from the database.

Standard User Features

Standard users have a simpler interface.

    Edit Article Tab: This tab provides a form where users can manually create and submit a new article.

    Save Text Tab: A simple text editor tab for writing and saving notes or drafts.

Structure

    The project has three main parts. The first part are all the utilities, they are modular and scalable. Only use them when necessary, feel free to add anything to them.
    
    The second part is the "Dao". Everything that has to do with the database is located here (Repository, SQL methods ...).

    The third part is the actual project, all of the user controls and tabs are located here.
    Feel free to play around and add new features!
