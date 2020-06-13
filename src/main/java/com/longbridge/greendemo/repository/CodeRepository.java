package com.longbridge.greendemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.longbridge.greendemo.model.Code;

/**
 * The interface Code repository.
 *
 * @author idisimagha dublin-green
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
	
	@Query(value = "SELECT * FROM codes t where t.status = :status", nativeQuery = true)
	public List<Code> findByStatus(@Param("status") boolean status);
}
