package org.alfabank.soacorn.db.repository;

import org.alfabank.soacorn.db.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Integer> {
}
