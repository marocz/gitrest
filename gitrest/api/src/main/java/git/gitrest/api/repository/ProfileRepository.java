package git.gitrest.api.repository;

import git.gitrest.api.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {

    Profile findByEmail(String email);

}
