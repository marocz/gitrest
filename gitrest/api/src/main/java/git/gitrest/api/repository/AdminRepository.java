package git.gitrest.api.repository;

import git.gitrest.api.model.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {

    Admin findByEmail(String email);

}
