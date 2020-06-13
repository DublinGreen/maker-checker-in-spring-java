package com.longbridge.greendemo.controller;

import com.longbridge.greendemo.exception.PermissionNotMatchException;
import com.longbridge.greendemo.exception.ResourceNotFoundException;
import com.longbridge.greendemo.exception.RoleNotMatchException;
import com.longbridge.greendemo.jwt.DAOUser;
import com.longbridge.greendemo.jwt.UserDao;
import com.longbridge.greendemo.model.Setting;
import com.longbridge.greendemo.repository.PermissionRepository;
import com.longbridge.greendemo.repository.SettingRepository;
import com.longbridge.greendemo.model.Permission;
import com.longbridge.greendemo.settings.AppRolesListEnum;
import com.longbridge.greendemo.settings.PermissionsHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

/**
 * The type Permission controller. Permission api controller
 * 
 * @author idisimagha dublin-green
 */
@RestController
@RequestMapping("/api/v1/setting")
@CrossOrigin(origins = "http://localhost:8080")
public class SettingController {

	@Autowired
	private SettingRepository settingRepository;

	@Autowired
	private UserDao userRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
		
	/**
	 * Get all setting list.
	 *
	 * @return the list
	 */
	@GetMapping("/getAllSettings")
	public List<Setting> getAllSettings() {
		return settingRepository.findAll();
	}

	/**
	 * Get approved permission list.
	 *
	 * @return the list
	 */
	@GetMapping("/getApprovedSettings")
	public List<Setting> getApprovedSettings() {
		return settingRepository.findByStatus(true);
	}

	/**
	 * Get not approved setting list.
	 *
	 * @return the list
	 */
	@GetMapping("/getUnapprovedSettings")
	public List<Setting> getUnapprovedSettings() {
		return settingRepository.findByStatus(false);
	}

	/**
	 * Gets setting by id.
	 *
	 * @param id
	 * @return the setting
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/getSettingById/{id}")
	public ResponseEntity<Setting> getSettingById(@PathVariable(value = "id") Long settingId)
			throws ResourceNotFoundException {
		Setting setting = settingRepository.findById(settingId)
				.orElseThrow(() -> new ResourceNotFoundException("Setting not found on :: " + settingId));
		return ResponseEntity.ok().body(setting);
	}

	/**
	 * Create setting.
	 *
	 * @param settingnName
	 * @param userId
	 * @return the setting
	 * @throws ResourceNotFoundException 
	 * @throws RoleNotMatchException 
	 * @throws PermissionNotMatchException 
	 */
	@PostMapping("/createSetting/{settingName}/{userId}")
	public Setting createSetting(
			@PathVariable(value = "userId") Long userId,
			@Valid @RequestBody Setting setting) throws ResourceNotFoundException, RoleNotMatchException, PermissionNotMatchException {
		
		final String PERMISSION_KEY = "CAN_CREATE_SETTINGS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
				
		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
				
		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			setting.setCreatedAt(new Date());
			setting.setCreatedBy(user.getUsername());
			setting.setUpdatedAt(new Date());
			setting.setUpdatedBy(user.getUsername());
		}		
		return settingRepository.save(setting);
		
	}

	/**
	 * Update setting response entity.
	 *
	 * @param id
	 * @param newSettingName
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/updateSetting/{id}/{newSettingName}/{userId}")
	public ResponseEntity<Setting> updateSetting(@PathVariable(value = "id") Long settingId,
			@PathVariable(value = "newSettingName") String newSettingName, @PathVariable(value = "userId") Long userId)
			throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_UPDATE_SETTINGS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		Setting setting = settingRepository.findById(settingId)
				.orElseThrow(() -> new ResourceNotFoundException("Setting not found on :: " + settingId));
		
		Setting updatedSetting;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			setting.setName(newSettingName);
			setting.setUpdatedAt(new Date());
			setting.setUpdatedBy(user.getUsername());
			updatedSetting = settingRepository.save(setting);
		}
		

		return ResponseEntity.ok(updatedSetting);
	}

	/**
	 * Approve setting
	 *
	 * @param settingId
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/approveSetting/{id}/{userId}")
	public ResponseEntity<Setting> approveSetting(@PathVariable(value = "id") Long settingId,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_APPROVE_SETTINGS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		Setting setting = settingRepository.findById(settingId)
				.orElseThrow(() -> new ResourceNotFoundException("Setting not found on :: " + settingId));
		
		Setting updatedSetting;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			setting.setStatus(true);
			setting.setUpdatedAt(new Date());
			setting.setUpdatedBy(user.getUsername());
			updatedSetting = settingRepository.save(setting);
		}
		return ResponseEntity.ok(updatedSetting);		
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
	public ResponseEntity<Setting> disapproveSetting(@PathVariable(value = "id") Long settingId,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		
		final String PERMISSION_KEY = "CAN_DISAPPROVE_SETTINGS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		Setting setting = settingRepository.findById(settingId)
				.orElseThrow(() -> new ResourceNotFoundException("Setting not found on :: " + settingId));
		
		Setting updatedSetting;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			setting.setStatus(false);
			setting.setUpdatedAt(new Date());
			setting.setUpdatedBy(user.getUsername());
			updatedSetting = settingRepository.save(setting);
		}
		return ResponseEntity.ok(updatedSetting);
	}

	/**
	 * Delete setting map.
	 *
	 * @param id
	 * @param userId
	 * @return the map
	 * @throws ResourceNotFoundException 
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 * @throws Exception the exception
	 */
	@DeleteMapping("/deleteSetting/{id}/{userId}")
	public Map<String, Boolean> deleteSetting(@PathVariable(value = "id") Long settingId , @PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {
		
		Map<String, Boolean> response;
				
		Setting setting = settingRepository.findById(settingId)
				.orElseThrow(() -> new ResourceNotFoundException("Setting not found on :: " + settingId));

		final String PERMISSION_KEY = "CAN_DELETE_SETTINGS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		
		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			settingRepository.delete(setting);
			response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
		}

		return response;
		
	}
}
