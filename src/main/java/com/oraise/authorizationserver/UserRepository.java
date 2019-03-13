package com.oraise.authorizationserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Find by username user.
   *
   * @param username the username
   * @return the user
   */
  User findByUsername(String username);
}
