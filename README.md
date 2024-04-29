# CS180 - Project05


## Compiling and Running the Project
In the initial phase of this project, most classes are accompanied by their own test cases, ensuring the functionality and reliability of individual components. This helps in the early detection of issues and ensures a solid foundation for future development phases. 


## Submission
XYZ Name - Submitted Report on Brightspace
XYZ Name - Submitted Vocareum workspace and Presentation


Classes
(Chat Folder)
- Publisher.java
- Subscriber.java
- Topic.java

(Core)
- App_Core.java

(DatabaseFolder)
- AlreadyThereException.java
- BlockedException.java
- Functional.Comment.java
- CommentInterface.java
- DatabaseFolder.DataWriter.java
  = DatabaseFolder.Database.java
- DoesNotExistException.java
- ExistingUsernameException.java
- ImNotSureWhyException.java
- InvalidInputObject.java
- InvalidOperationException.java
- Functional.Post.java
- PostInterface.java
- DatabaseFolder.Test.java
  
(Network)

- Network.Client.java
- ClientTests.java
- Client_temp_unuse.java
- Network.Server.java

(UserFolder)

- UserFolder.IUserOperations.java
- UserFolder.User.java
- UserFolder.UserProfile.java

Main.java

## Network.Client.java

The Network.Client class in the Network package is responsible for initiating and maintaining a connection with a server
using socket programming. It facilitates user interactions such as logging in, registering, and executing various
commands related to social networking functionalities like posting, commenting, and managing friendships. The client
handles input and output through streams connected to the server, interpreting user inputs and server responses. It
operates primarily in an interactive mode, reading commands from the user via a console or GUI input, sending these
commands to the server, and displaying responses. The design allows for real-time interaction with the server, making it
a crucial component for user-facing operations in a networked environment.

## Network.Server.java

The Network.Server class acts as the central communication system in the Network package. It accepts connections from
multiple clients and processing their requests. It runs continuously, listening on a specified port for new client
connections and handling each connection in a separate thread to facilitate concurrent processing. The server reads
commands sent by clients, such as login or content manipulation commands, and uses instances of the
DatabaseFolder.DataWriter class to interact with the backend data storage. It responds back to clients based on the
outcomes of these operations, effectively managing session states and user interactions.

## DatabaseFolder.DataWriter.java

The DatabaseFolder.DataWriter class, located in the DatabaseFolder package, manages all data persistence operations
related to users and posts within the system. It provides methods for creating and updating user profiles, managing
friendships and user blocks, and handling posts and comments. This class directly interacts with the file system to
store and retrieve data, ensuring data integrity and consistency across operations. DatabaseFolder.DataWriter serves as
the backbone for data handling, offering a variety of methods that the Network.Server class utilizes to fulfill client
requests. Its functionality is essential for maintaining a robust data layer, supporting the social networking features
that the Network.Client and Network.Server facilitate through their interactions.

## Functional.Post.java

The Functional.Post class in the DatabaseFolder package deals with the functionality of social media posts, including
attributes like text, images, and ownership by a UserFolder.User. Each Functional.Post is uniquely identified with a
postCode, which combines the owner’s user ID with a post count, ensuring unique identifiers for each post. The class
manages interactions such as likes and dislikes through lists of UserFolder.User objects, reflecting user engagement.
Comments, represented by a list of Functional.Comment objects, add a layer of interaction, making each post a central
node of activity. Integrated closely with DatabaseFolder.DataWriter, Functional.Post leverages methods for creating,
updating, and deleting post data, tying in file management to persistently store post details and user interactions.
This ensures that user activities like liking, disliking, and commenting are maintained across system sessions,
supporting dynamic content management.

## Functional.Comment.java

This class has been designed to handle comments on posts within the DatabaseFolder, the Functional.Comment class links
directly to the Functional.Post it belongs to, establishing a parent-child relationship essential for maintaining the
integrity of discussions on the platform. Comments can be interacted with through likes and dislikes, similar to posts,
with each interaction being tracked by lists of users. This class works in conjunction with DatabaseFolder.DataWriter to
ensure all interactions are recorded and maintained, including the creation and deletion of comments. Each comment is
assigned a unique code, derived from the owner's user ID and a sequential number, allowing for precise tracking and
management in the system’s file-based data structure.

## DatabaseFolder.DataWriter.java

DatabaseFolder.DataWriter acts as the backbone for data management in the DatabaseFolder package. It interfaces directly
with the filesystem to perform CRUD operations for both the Functional.Post and Functional.Comment classes. It manages
directories and files that store detailed information about users and their posts, ensuring data persistence. Through
methods such as createUser, makePost, and likePost, it facilitates the interaction of Functional.Post and
Functional.Comment with the system’s storage. This class is crucial for maintaining the continuity and integrity of user
data, handling synchronization issues, and ensuring that user interactions with posts and comments are consistently
updated and retrievable.

## UserFolder.User.java

The UserFolder.User class in the UserFolder package is a core model representing a user in the system. It manages
user-specific information such as username, password, friends list, and blocked users list. Users are identified by a
unique userId generated by the system. The class provides methods to manage user relationships (e.g., add or remove
friends, block or unblock users) and user profile details through an associated UserFolder.UserProfile object.

## UserFolder.UserProfile.java

The UserFolder.UserProfile class serves as a supplementary model to UserFolder.User, handling additional details
specific to a user's public profile. This includes the user's biography and profile picture, which can be stored as a
BufferedImage and accessed via a file name. The class provides methods to set and retrieve the bio and profile picture,
including functionality to load and store images from disk, facilitating user interaction with profile data.

## UserFolder.IUserOperations.java

The UserFolder.IUserOperations interface defines essential user-related operations that must be implemented to manage
user data effectively. It outlines methods for getting and setting user attributes, managing friends and blocked users,
and other necessary user interactions such as adding and removing friends or blocking and unblocking other users. This
interface ensures that any class implementing it will provide a consistent set of functionalities for user operations,
crucial for maintaining a solid and predictable framework for handling user data across different parts of the
application, especially when interacting with the DatabaseFolder.DataWriter for data persistence.

## How to Run
Run Server.java then GUI.java and the GUI should open up. Since there are no users created when you first run it 
type in your desired username and password and hit register. If there is a user created type in the username and password
for that user and press login. Once logged in you will see the main feed, the option to make a post is at the top. 
When a post is made, you can like, dislike, hide or delete it. You can make comments and like dislike or delete comments.
The search page lets you search for users and add or block them. The friends page is a feed for only friends. THe profile
edit page lets you edit your username and password
