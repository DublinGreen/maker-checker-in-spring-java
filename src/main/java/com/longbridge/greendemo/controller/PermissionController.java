package com.longbridge.greendemo.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longbridge.greendemo.exception.PermissionNotMatchException;
import com.longbridge.greendemo.exception.ResourceNotFoundException;
import com.longbridge.greendemo.exception.RoleNotMatchException;
import com.longbridge.greendemo.jwt.DAOUser;
import com.longbridge.greendemo.jwt.UserDao;
import com.longbridge.greendemo.model.Permission;
import com.longbridge.greendemo.repository.PermissionRepository;
import com.longbridge.greendemo.settings.AppRolesListEnum;
import com.longbridge.greendemo.settings.PermissionsHashMap;

/**
 * The type Permission controller. Permission api controller
 * 
 * @author idisimagha dublin-green
 */
@RestController
@RequestMapping("/api/v1/permission")
@CrossOrigin(origins = "http://localhost:8080")
public class PermissionController {

	@Autowired
	private UserDao userRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;

	/**
	 * Get all permission list.
	 *
	 * @return the list
	 */
	@GetMapping("/getAllPermissions")
	public List<Permission> getAllPermissions() {
		return permissionRepository.findAll();
	}

	/**
	 * Get approved permission list.
	 *
	 * @return the list
	 */
	@GetMapping("/getApprovedPermissions")
	public List<Permission> getApprovedPermissions() {
		return permissionRepository.findByStatus(true);
	}

	/**
	 * Get not approved permission list.
	 *
	 * @return the list
	 */
	@GetMapping("/getUnapprovedPermissions")
	public List<Permission> getUnapprovedPermissions() {
		return permissionRepository.findByStatus(false);
	}

	/**
	 * Gets permission by id.
	 *
	 * @param id
	 * @return the code
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/getPermissionById/{id}")
	public ResponseEntity<Permission> getPermissionById(@PathVariable(value = "id") Long permissionId) throws ResourceNotFoundException {
		Permission permission = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new ResourceNotFoundException("Permission not found on :: " + permissionId));
		return ResponseEntity.ok().body(permission);
	}

	/**
	 * Create permission.
	 *
	 * @param permissionObj
	 * @param userId
	 * @return the code
	 * @throws ResourceNotFoundException
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PostMapping("/createCode/{codeName}/{code}/{description}/{userId}")
	public @Valid Permission createCode(
			@Valid @RequestBody Permission permissionObj,
			@PathVariable(value = "userId") Long userId)
			throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_CREATE_PERMISSIONS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
			System.out.println("permission.getName() : " + permission.getName());
		}
		
		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		
		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			permissionObj.setCreatedAt(new Date());
			permissionObj.setCreatedBy(user.getUsername());
			permissionObj.setUpdatedAt(new Date());
			permissionObj.setUpdatedBy(user.getUsername());
		}	

		return permissionRepository.save(permissionObj);
	}

	/**
	 * Update permission response entity.
	 *
	 * @param id
	 * @param newPermissionName
	 * @param newRole
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws RoleNotMatchException 
	 * @throws PermissionNotMatchException 
	 */
	@PutMapping("/updateCode/{id}/{newPermissionName}/{newRole}/{userId}")
	public ResponseEntity<Permission> updatePermission(@PathVariable(value = "id") Long permissionId,
			@PathVariable(value = "newPermissionName") String newPermissionName, 
			@PathVariable(value = "newRole") String newRole,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, RoleNotMatchException, PermissionNotMatchException {

		final String PERMISSION_KEY = "CAN_UPDATE_PERMISSIONS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		Permission permissionToFind = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new ResourceNotFoundException("Permission not found on :: " + permissionId));
		Permission updatedPermission;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		
		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			permissionToFind.setName(newPermissionName);
			permissionToFind.setRoleName(newRole);
			permissionToFind.setUpdatedAt(new Date());
			permissionToFind.setUpdatedBy(user.getUsername());
			updatedPermission = permissionRepository.save(permissionToFind);
		}
		
		return ResponseEntity.ok(updatedPermission);

	}

	/**
	 * Approve permission
	 *
	 * @param permissionId
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/approvePermission/{id}/{userId}")
	public ResponseEntity<Permission> approvePermission(@PathVariable(value = "id") Long permissionId,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_APPROVE_PERMISSIONS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		Permission permissionToUpdate = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new ResourceNotFoundException("Permission not found on :: " + permissionId));
		Permission updatedPermission;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			permissionToUpdate.setStatus(true);
			permissionToUpdate.setUpdatedAt(new Date());
			permissionToUpdate.setUpdatedBy(user.getUsername());
			updatedPermission = permissionRepository.save(permissionToUpdate);
		}
		
		return ResponseEntity.ok(updatedPermission);

	}

	/**
	 * Disapprove permission
	 *
	 * @param id  
	 * @param userId  
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/disapprovePermission/{id}/{userId}")
	public ResponseEntity<Permission> disapprovePermission(@PathVariable(value = "id") Long permissionId, @PathVariable(value = "userId") Long userId)
			throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {
		final String PERMISSION_KEY = "CAN_DISAPPROVE_PERMISSIONS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		Permission permissionToUpdate = permissionRepository.findById(permissionId)
				.orElseThrow(() -> new ResourceNotFoundException("Permission not found on :: " + permissionId));
		Permission updatedPermission;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			permissionToUpdate.setStatus(false);
			permissionToUpdate.setUpdatedAt(new Date());
			permissionToUpdate.setUpdatedBy(user.getUsername());
			updatedPermission = permissionRepository.save(permissionToUpdate);
		}
		
		return ResponseEntity.ok(updatedPermission);
	}


}
