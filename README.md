# CS180 - Project05


## Compiling and Running the Project
Add text here


## Submission
XYZ Name - Submitted Report on Brightspace
XYZ Name - Submitted Vocareum workspace and Presentation


## Classes
(Chat Folder)
- Chat.java
- GroupChat.java
- PersonalChat.java

(Core)
- App_Core.java

(DatabaseFolder)
- Database.java

(UserFolder)
- User.java
- UserDataBase.java
- UserProfile.java
- UserRelationList.java

  
- Main.java


## Chat.java
The Chat interface, which is part of the ChatFolder, is the foundation for various chat functionalities in the project. It outlines essential communication methods including sendMessage, receiveMessage, sendImage, receiveImage, sendFile, and receiveFile. These methods are intended to be implemented by classes that facilitate different forms of chat, allowing for text, image, and file exchanges. This interface ensures consistency in chat functionality across various implementations. 
It plays a critical role in defining the structure and capabilities of chat-related classes in the project.




## GroupChat.java
The GroupChat class implements the Chat interface and is designed to handle chats with multiple members. It maintains a list of User members and a groupName. Key functionalities include adding or removing members, retrieving the member list, and managing the group's name. The class constructors allow for initializing a group chat with multiple members or just an owner. When a new member is added, the group name is updated to reflect the new member's name. As an implementer of Chat, GroupChat has a direct relationship with the User class, using instances of User to manage chat participants.


## PersonalChat.java
The PersonalChat class, also under ChatFolder, implements the Chat interface for one-on-one communication. It holds an array of two User objects and a chatName, initialized to reflect both users' names. 
This class is tightly coupled with the User class for representing chat participants and follows the Chat interface guidelines for chat functionalities. Its focus on one-on-one interactions distinguishes it from GroupChat while maintaining a consistent approach to chat functionalities within the project.




## App_Core.java
The App_Core class, located within the Core package of our Java project, is the primary entry point of the application. Central to this class is the main method, which signifies where execution begins for the Java Virtual Machine (JVM). Currently, this method is empty, since the initial setup and behavior of the application are yet to be defined. The App_Core class is designed to initialize and orchestrate components like GroupChat, PersonalChat, and the Chat interface , thereby setting the stage for the application's functionality which includes text, image, and file exchanges in both group and personal chat contexts.
In future development, this class will evolve to include instantiation of these chat components, setting up necessary environments, and handling initial user interactions.
Overall, App_Core.java is a foundational class that currently acts as a placeholder for the application's primary execution logic, poised to become the central hub from which the application's functionalities are accessed and controlled.


## Database.java
The Database class, which is part of the DatabaseFolder package, serves as the primary mechanism for storing, retrieving, and managing user data within the application. This class utilizes a text file (database.txt) to persist user information, adhering to a specific file format that includes username, unique ID, password, user 
Upon instantiation, Database checks for the existence of database.txt and creates it if not present. This ensures that user data is persistently stored and available across different sessions.
The read method parses the text file, reconstructing user objects and their associated data, including user profiles, friends, and blacklisted IDs. It ensures data consistency and integrity by following the predefined format.
The write method serializes the current state of user objects into the text file. This includes comprehensive user information, adhering to the structured format for easy retrieval.
Methods like saveUser, getUsers, searchUser, and getUser allow for operations like adding new users, retrieving user lists, searching, and accessing individual user details.
The class includes a test method, testStore, which validates the functionality of storing and retrieving user data, ensuring the reliability of the database operations.
As part of the chat application, Database interacts closely with the UserFolder package, particularly with classes like User, UserProfile, and UserRelationList. It is a central component for handling all data-related aspects of the application, including user credentials, profiles, and social connections. This class ensures that user information is consistently available and up-to-date for the various functionalities of the chat application, such as initiating personal or group chats.


## User.java
User.java, which is part of the UserFolder package, models an individual user within the chat application. It stores user-specific information like name, unique ID, password, profile, friends list, and blacklist.
Methods like setPassword, setProfile, and setProfilePic allow the user to update their password and profile details.
This class also includes methods like addFriend, removeFriend, block, and unblock for managing social interactions and provides utility methods like getName, getUniqueID, getFriends, getBlackList, and useProfile for accessing user details.
Methods like checkPassWord to verify user credentials.
The class also Integrates with Database through saveData for persisting user data and interacts with UserProfile for profile management, UserRelationList for managing friends and blocklist, and Database for data persistence.


## UserDataBase.java


## UserProfile.java


## UserRelationList.java


## Main.java




