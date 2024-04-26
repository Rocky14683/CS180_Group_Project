package TempFolderSoIDontBreakEverything;

// package DatabaseFolder;
// import UserFolder.User;

/**
 * DataBase
 * <p>
 * Interface for the DataWrtier class
 *
 * @author Lucas Abreu, L12
 * @version 4/1/24
 */

public interface Database {

    boolean createUser(User user);

    boolean redefineUser(String userId);

    boolean addFriends(User newFreiend, User user);

    boolean removeFriend(User oldFreiendId, User userId);

    boolean blockUser(User newBlockId, User userId);

    boolean unblockUser(User oldBlock, User user);

    boolean logIn(String username, String password);

    void setJob(String job);

    Object getReturnObject();

    void setInputObject(Object[] object);

    boolean updateUser(User user, String username, String password);
}
