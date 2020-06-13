package com.longbridge.greendemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.longbridge.greendemo.model.Setting;

/**
 * The interface Setting repository.
 *
 * @author idisimagha dublin-green
 */
@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

	@Query(value = "SELECT * FROM settings t where t.status = :status", nativeQuery = true)
	public List<Setting> findByStatus(@Param("status") boolean status);

}
