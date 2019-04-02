package bd.ohedulalam.bjlsProject.repository;

import bd.ohedulalam.bjlsProject.model.Role;
import bd.ohedulalam.bjlsProject.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * we create this repository for persisting our Role model to
 * the database and retrieving them
 * To do so, it extends Spring Data JPA's JpaRepository interface
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
