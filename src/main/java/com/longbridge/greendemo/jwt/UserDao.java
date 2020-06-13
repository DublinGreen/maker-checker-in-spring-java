package com.longbridge.greendemo.jwt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<DAOUser, Long> {
	DAOUser findByUsername(String username);
	
	@Query(value = "SELECT * FROM autousers t where t.status = :status", nativeQuery = true)
	public List<DAOUser> findByStatus(@Param("status") boolean status);
}