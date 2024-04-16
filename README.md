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


##Post.java
The Post class in the DatabaseFolder package deals with the functionality of social media posts, including attributes like text, images, and ownership by a User. Each Post is uniquely identified with a postCode, which combines the owner’s user ID with a post count, ensuring unique identifiers for each post. The class manages interactions such as likes and dislikes through lists of User objects, reflecting user engagement. Comments, represented by a list of Comment objects, add a layer of interaction, making each post a central node of activity. Integrated closely with DataWriter, Post leverages methods for creating, updating, and deleting post data, tying in file management to persistently store post details and user interactions. This ensures that user activities like liking, disliking, and commenting are maintained across system sessions, supporting dynamic content management.

## Comment.java
This class has been designed to handle comments on posts within the DatabaseFolder, the Comment class links directly to the Post it belongs to, establishing a parent-child relationship essential for maintaining the integrity of discussions on the platform. Comments can be interacted with through likes and dislikes, similar to posts, with each interaction being tracked by lists of users. This class works in conjunction with DataWriter to ensure all interactions are recorded and maintained, including the creation and deletion of comments. Each comment is assigned a unique code, derived from the owner's user ID and a sequential number, allowing for precise tracking and management in the system’s file-based data structure.


## DataWriter.java
DataWriter acts as the backbone for data management in the DatabaseFolder package. It interfaces directly with the filesystem to perform CRUD operations for both the Post and Comment classes. It manages directories and files that store detailed information about users and their posts, ensuring data persistence. Through methods such as createUser, makePost, and likePost, it facilitates the interaction of Post and Comment with the system’s storage. This class is crucial for maintaining the continuity and integrity of user data, handling synchronization issues, and ensuring that user interactions with posts and comments are consistently updated and retrievable.
