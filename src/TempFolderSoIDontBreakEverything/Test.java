import java.util.ArrayList;

public class Test {

    public static void main(String[] args) throws AlreadyThereException, ExistingUsernameException, ImNotSureWhyException, BlockedException, InvalidOperationException, DoesNotExistException {
        
        DataWriter dw = new DataWriter();

        User Lucas1 = new User("Lucas1", "00000");
        dw.createUser(Lucas1);
        User Lucas2 = new User("Lucas2", "000000");
        dw.createUser(Lucas2);

        User Lucas3 = new User("Lucas3", "00000");
        dw.createUser(Lucas3);
        User Lucas4 = new User("Lucas4", "000000");
        dw.createUser(Lucas4);

        User Lucas5 = new User("Lucas5", "00000");
        dw.createUser(Lucas5);
        User Lucas6 = new User("Lucas6", "000000");
        dw.createUser(Lucas6);

        dw.addFriends(Lucas1, Lucas2);
        dw.addFriends(Lucas2, Lucas1);

        Post post = new Post("Testing123123123", null, Lucas1);

        dw.makePost(post);

        dw.likePost(post, Lucas2);

        Comment comment = new Comment(post, "WOOOOOOOOO", Lucas2);

        dw.makeComment(post, comment);

        dw.getUsers();

        ArrayList<String[]> users = dw.getUsers();
        ArrayList<String> posts = dw.getPosts();
        ArrayList<String> comments = dw.getComments(post);

        for(String[] s : users) {
            System.out.println(s[0] + ", " + s[1]);
        }
        System.out.println();

        for(String s : posts) {
            System.out.println(s);
        }

        System.out.println();

        for (String s : comments) {
            System.out.println(s);
        }




        
    }
}
