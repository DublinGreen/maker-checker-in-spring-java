package com.longbridge.greendemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

import io.swagger.annotations.ApiModelProperty;

/**
 * The type User controller.
 *
 * @author idisimagha dublin-green
 */
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

	@Autowired
	private UserDao userRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	/**
	 * Get all users list.
	 *
	 * @return List
	 */
	@GetMapping("/getAllUsers")
	@ApiModelProperty(required = false, hidden = true)
	public List<DAOUser> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Gets users by id.
	 *
	 * @param userId the user id
	 * @return the users by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/getUsersById/{id}")
	public ResponseEntity<DAOUser> getUsersById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		return ResponseEntity.ok().body(user);
	}
	
	/**
	 * Approve user
	 *
	 * @param id
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/approveUser/{id}/{userId}")
	public ResponseEntity<DAOUser> approveUser(@PathVariable(value = "id") Long id,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_APPROVE_USERS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		DAOUser userPerformingOperation = (DAOUser) userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
//		System.out.println("userPerformingOperation  ::: " + userPerformingOperation.getUsername());
		
		DAOUser updatedUser;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
//		System.out.println("user  ::: " + user.getUsername());
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		if(!((DAOUser) userPerformingOperation).getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(((DAOUser) userPerformingOperation).getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			user.setStatus(true);
			updatedUser = userRepository.save(user);
		}
		return ResponseEntity.ok(updatedUser);		
	}
	
	/**
	 * Disapprove user
	 *
	 * @param id
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/disApproveUser/{id}/{userId}")
	public ResponseEntity<DAOUser> disApproveUser(@PathVariable(value = "id") Long id,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_DISAPPROVE_USERS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		DAOUser userPerformingOperation = (DAOUser) userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
//		System.out.println("userPerformingOperation  ::: " + userPerformingOperation.getUsername());
		
		DAOUser updatedUser;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
//		System.out.println("user  ::: " + user.getUsername());
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		if(!((DAOUser) userPerformingOperation).getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(((DAOUser) userPerformingOperation).getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			user.setStatus(false);
			updatedUser = userRepository.save(user);
		}
		return ResponseEntity.ok(updatedUser);		
	}


}
