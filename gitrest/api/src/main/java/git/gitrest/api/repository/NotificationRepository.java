package git.gitrest.api.repository;

import git.gitrest.api.model.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findByEmail(String email);

}
