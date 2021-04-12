package git.gitrest.api.repository;

import git.gitrest.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);
    User findByUsername(String username);
    User findByEmailIgnoreCase(String email);
    User findByEmail(String email);

}




