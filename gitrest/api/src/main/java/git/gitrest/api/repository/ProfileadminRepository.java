package git.gitrest.api.repository;

import git.gitrest.api.model.Profileadmin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileadminRepository extends CrudRepository<Profileadmin, Long> {

    Profileadmin findByEmail(String email);

}
