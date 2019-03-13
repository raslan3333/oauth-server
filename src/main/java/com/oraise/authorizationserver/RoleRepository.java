package com.oraise.authorizationserver;

import org.springframework.data.repository.CrudRepository;

/**
 * The interface Role repository.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    /**
     * Find by name role.
     *
     * @param name the name
     * @return the role
     */
    Role findByName(String name);
}
