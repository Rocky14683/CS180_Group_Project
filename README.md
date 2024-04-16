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
- Comment.java
- CommentInterface.java
- DataWriter.java
= Database.java
- DoesNotExistException.java
- ExistingUsernameException.java
- ImNotSureWhyException.java
- InvalidInputObject.java
- InvalidOperationException.java
- Post.java
- PostInterface.java
- Test.java
  
(Network)
- Client.java
- ClientTests.java
- Client_temp_unuse.java
- Server.java

(UserFolder)
- IUserOperations.java
- User.java
- UserProfile.java


Main.java


## Client.java
The Client class in the Network package is responsible for initiating and maintaining a connection with a server using socket programming. It facilitates user interactions such as logging in, registering, and executing various commands related to social networking functionalities like posting, commenting, and managing friendships. The client handles input and output through streams connected to the server, interpreting user inputs and server responses. It operates primarily in an interactive mode, reading commands from the user via a console or GUI input, sending these commands to the server, and displaying responses. The design allows for real-time interaction with the server, making it a crucial component for user-facing operations in a networked environment.


## Server.java
The Server class acts as the central communication system in the Network package. It accepts connections from multiple clients and processing their requests. It runs continuously, listening on a specified port for new client connections and handling each connection in a separate thread to facilitate concurrent processing. The server reads commands sent by clients, such as login or content manipulation commands, and uses instances of the DataWriter class to interact with the backend data storage. It responds back to clients based on the outcomes of these operations, effectively managing session states and user interactions. 


## DataWriter.java
The DataWriter class, located in the DatabaseFolder package, manages all data persistence operations related to users and posts within the system. It provides methods for creating and updating user profiles, managing friendships and user blocks, and handling posts and comments. This class directly interacts with the file system to store and retrieve data, ensuring data integrity and consistency across operations. DataWriter serves as the backbone for data handling, offering a variety of methods that the Server class utilizes to fulfill client requests. Its functionality is essential for maintaining a robust data layer, supporting the social networking features that the Client and Server facilitate through their interactions.


## Post.java
The Post class in the DatabaseFolder package deals with the functionality of social media posts, including attributes like text, images, and ownership by a User. Each Post is uniquely identified with a postCode, which combines the owner’s user ID with a post count, ensuring unique identifiers for each post. The class manages interactions such as likes and dislikes through lists of User objects, reflecting user engagement. Comments, represented by a list of Comment objects, add a layer of interaction, making each post a central node of activity. Integrated closely with DataWriter, Post leverages methods for creating, updating, and deleting post data, tying in file management to persistently store post details and user interactions. This ensures that user activities like liking, disliking, and commenting are maintained across system sessions, supporting dynamic content management.


## Comment.java
This class has been designed to handle comments on posts within the DatabaseFolder, the Comment class links directly to the Post it belongs to, establishing a parent-child relationship essential for maintaining the integrity of discussions on the platform. Comments can be interacted with through likes and dislikes, similar to posts, with each interaction being tracked by lists of users. This class works in conjunction with DataWriter to ensure all interactions are recorded and maintained, including the creation and deletion of comments. Each comment is assigned a unique code, derived from the owner's user ID and a sequential number, allowing for precise tracking and management in the system’s file-based data structure.


## DataWriter.java
DataWriter acts as the backbone for data management in the DatabaseFolder package. It interfaces directly with the filesystem to perform CRUD operations for both the Post and Comment classes. It manages directories and files that store detailed information about users and their posts, ensuring data persistence. Through methods such as createUser, makePost, and likePost, it facilitates the interaction of Post and Comment with the system’s storage. This class is crucial for maintaining the continuity and integrity of user data, handling synchronization issues, and ensuring that user interactions with posts and comments are consistently updated and retrievable.


## User.java
