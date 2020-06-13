package com.longbridge.greendemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.longbridge.greendemo.model.Merchant;
import com.longbridge.greendemo.model.Permission;

/**
 * The interface Merchant repository.
 *
 * @author idisimagha dublin-green
 */
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

	@Query(value = "SELECT * FROM merchants t where t.status = :status", nativeQuery = true)
	public List<Merchant> findByStatus(@Param("status") boolean status);
	

}
