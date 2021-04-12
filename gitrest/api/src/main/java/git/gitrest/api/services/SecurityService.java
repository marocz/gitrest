package git.gitrest.api.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
