package com.mscorp.meepleserver.repositories;

import com.mscorp.meepleserver.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
