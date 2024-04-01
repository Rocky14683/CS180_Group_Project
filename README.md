# CS180 - Project05


## Compiling and Running the Project
In the initial phase of this project, most classes are accompanied by their own test cases, ensuring the functionality and reliability of individual components. This helps in the early detection of issues and ensures a solid foundation for future development phases. 


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
User.java, a part of the UserFolder, is part of the UserFolder package, models an individual user within the chat application. It stores user-specific information like name, unique ID, password, profile, friends list, and blacklist.
Methods like setPassword, setProfile, and setProfilePic allow the user to update their password and profile details.
This class also includes methods like addFriend, removeFriend, block, and unblock for managing social interactions and provides utility methods like getName, getUniqueID, getFriends, getBlackList, and useProfile for accessing user details.
Methods like checkPassWord to verify user credentials.
The class also Integrates with Database through saveData for persisting user data and interacts with UserProfile for profile management, UserRelationList for managing friends and blocklist, and Database for data persistence.


## UserDataBase.java
UserDataBase.java, a part of the UserFolder, serves as the core repository for managing user information within the chat application. Its primary function is to facilitate the creation, retrieval, and management of User objects. This class includes createUser, which generates new users with unique identifiers, ensuring every user in the system is distinct. It also provides a search feature through searchUser, allowing for efficient location of users based on their names, and getUser, which retrieves users by their unique IDs. Furthermore, UserDataBase handles user management tasks with banUser and unbanUser methods, giving administrators control over user access within the application. The getUsers method is another key feature that offers access to the complete list of users. The functionality and reliability of UserDataBase have been tested and verified by the TestUserDataBase class, which ensures user creation, searching, banning, and unbanning processes work as intended. In terms of its relationship with other classes, UserDataBase directly interacts with and manages instances of the User class, playing a pivotal role in the overarching data management strategy of the chat application. It works in close conjunction with the Database class to ensure a comprehensive and integrated approach to data handling within the system.


## UserProfile.java
UserProfile.java, a part of the UserFolder, is a critical component for personalizing user accounts in the chat application, focusing primarily on profile pictures and biographies. It offers features for profile image management, including setProfilePic, which enables users to set their profile images, loadProfilePic for loading images, and getProfilePic to retrieve these images. In terms of biography management, setBio and getBio methods allow users to create and view their biographical information. Additionally, the class includes file handling mechanisms to save profile pictures to files and retrieve them, using methods such as getPFPFileName and getPfpStorageDir. The functionality of UserProfile has been tested through the UserProfileTest class, ensuring that both image and biography handling are reliable and effective. This class is closely associated with the User class, which integrates profile details into user accounts.


## UserRelationList.java
UserRelationList.java, a part of the UserFolder, manages lists of user IDs for maintaining friends and blocked users within the chat system. It provides efficient list management capabilities through methods like add, remove, and contains, which handle the addition, removal, and verification of user IDs in these lists. The class also offers utility functions such as getIDs, to access all IDs in the list, and toString, for a string representation of the user IDs. 


## Main.java
