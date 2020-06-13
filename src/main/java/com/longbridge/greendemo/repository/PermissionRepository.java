package com.longbridge.greendemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.longbridge.greendemo.model.Permission;

/**
 * The interface Permission repository.
 *
 * @author idisimagha dublin-green
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	@Query(value = "SELECT * FROM permissions t where t.status = :status", nativeQuery = true)
	public List<Permission> findByStatus(@Param("status") boolean status);
	
	@Query(value = "SELECT * FROM permissions t where t.status = :status AND t.name = :permissionName AND t.role_name = :roleName", nativeQuery = true)
	public Permission findByStatusAndNameAndRoleName(@Param("status") boolean status, String permissionName,String roleName);

}
