package DatabaseFolder;
import UserFolder.User;

/**
 * DataBase
 * <p>
 * Interface for the DataWrtier class
 *
 * @author Lucas Abreu, L12
 * @version 4/1/24
 */

public interface Database {

    boolean createUser(User user) throws AlreadyThereException, ExistingUsernameException, ImNotSureWhyException;
    User redefineUser(String userId) throws DoesNotExistException, ImNotSureWhyException;
    boolean addFriends(User newFreiend, User user) throws AlreadyThereException, BlockedException, InvalidOperationException, ImNotSureWhyException, DoesNotExistException;
    boolean removeFriend(User oldFreiendId, User userId) throws DoesNotExistException, ImNotSureWhyException;
    boolean blockUser(User newBlockId, User userId) throws AlreadyThereException, DoesNotExistException, ImNotSureWhyException;
    boolean unblockUser(User oldBlock, User user) throws DoesNotExistException, ImNotSureWhyException;
    boolean setProfile(User user, String profile);
    boolean updateUser(User user, String username, String password);
    boolean logIn(String username, String password);
    String getUserID(String userName) throws DoesNotExistException;
    boolean usernameExist(String username) throws ImNotSureWhyException;
    boolean makePost(Post post);
    Post redefinePost(String code) throws DoesNotExistException, ImNotSureWhyException;
    boolean likePost(Post post, User liker) throws AlreadyThereException;
    boolean unlikePost(Post post, User unliker) throws DoesNotExistException;
    boolean dislikePost(Post post, User disliker) throws AlreadyThereException;
    boolean undislikePost(Post post, User undisliker) throws DoesNotExistException;
    boolean hidePost(Post post, User user) throws AlreadyThereException;
    boolean unhidePost(Post post, User user) throws DoesNotExistException;
    boolean deletePost(Post post, User user) throws DoesNotExistException, ImNotSureWhyException;
    boolean makeComment(Post post, Comment comment);
    boolean likeComment(Comment comment, User user);
    boolean dislikeComment(Comment comment, User user);
    boolean unlikeComment(Comment comment, User unliker) throws DoesNotExistException;
    boolean undislikeComment(Comment comment, User unliker) throws DoesNotExistException;
    Comment redefineComment(Post post, String code) throws DoesNotExistException, ImNotSureWhyException;
    boolean deleteComment(Comment comment, User user);

}
