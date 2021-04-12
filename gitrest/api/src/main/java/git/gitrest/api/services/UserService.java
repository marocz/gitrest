package git.gitrest.api.services;


import git.gitrest.api.entities.User;

public interface UserService {
    void save(User user);
    void adminsave(User user);
    User findByUsername(String username);
    void setProfilepics(String username, String picsurl);
    String getProfilepics(String username);
    boolean checkIfValidOldPassword(final User user, final String oldPassword);


}
