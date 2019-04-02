package bd.ohedulalam.bjlsProject.repository;

import bd.ohedulalam.bjlsProject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
* we create this repository for persisting our Member model to
* the database and retrieving them
 * To do so, it extends Spring Data JPA's JpaRepository interface
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //Here java 8 Optional modifier is used to handle null values.

    Optional<Member> findByUsernameOrEmail(String username, String email);
    Optional<Member> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


   Optional<Member> findById(long id);
}
