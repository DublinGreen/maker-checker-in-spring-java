package com.longbridge.greendemo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.longbridge.greendemo.model.Code;
import com.longbridge.greendemo.model.Permission;
import com.longbridge.greendemo.repository.CodeRepository;
import com.longbridge.greendemo.repository.PermissionRepository;
import com.longbridge.greendemo.settings.AppRolesListEnum;
import com.longbridge.greendemo.settings.PermissionsHashMap;

/**
 * The type Permission controller. Permission api controller
 * 
 * @author idisimagha dublin-green
 */
@RestController
@RequestMapping("/api/v1/code")
@CrossOrigin(origins = "http://localhost:8080")
public class CodeController {

	@Autowired
	private CodeRepository codeRepository;

	@Autowired
	private UserDao userRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;

	/**
	 * Get all code list.
	 *
	 * @return the list
	 */
	@GetMapping("/getAllCodes")
	public List<Code> getAllCodes() {
		return codeRepository.findAll();
	}

	/**
	 * Get approved permission list.
	 *
	 * @return the list
	 */
	@GetMapping("/getApprovedCodes")
	public List<Code> getApprovedPermissions() {
		return codeRepository.findByStatus(true);
	}

	/**
	 * Get not approved permission list.
	 *
	 * @return the list
	 */
	@GetMapping("/getUnapprovedCodes")
	public List<Code> getUnapprovedCodes() {
		return codeRepository.findByStatus(false);
	}

	/**
	 * Gets code by id.
	 *
	 * @param id
	 * @return the code
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/getCodeById/{id}")
	public ResponseEntity<Code> getCodeById(@PathVariable(value = "id") Long codeId) throws ResourceNotFoundException {
		Code code = codeRepository.findById(codeId)
				.orElseThrow(() -> new ResourceNotFoundException("Code not found on :: " + codeId));
		return ResponseEntity.ok().body(code);
	}

	/**
	 * Create cod.
	 *
	 * @param codeObj
	 * @param userId
	 * @return the code
	 * @throws ResourceNotFoundException
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PostMapping("/createCode/{codeObj}/{userId}")
	public Code createCode(
			@Valid @RequestBody Code codeObj,
			@PathVariable(value = "userId") Long userId)
			throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_CREATE_CODES";
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
			codeObj.setCreatedAt(new Date());
			codeObj.setCreatedBy(user.getUsername());
			codeObj.setUpdatedAt(new Date());
			codeObj.setUpdatedBy(user.getUsername());
		}	

		return codeRepository.save(codeObj);
	}

	/**
	 * Update code response entity.
	 *
	 * @param id
	 * @param newCodeName
	 * @param newCode
	 * @param newCodeDescription
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws RoleNotMatchException 
	 * @throws PermissionNotMatchException 
	 */
	@PutMapping("/updateCode/{id}/{newCodeName}/{newCode}/{newCodeDescription}/{newPermissionName}/{userId}")
	public ResponseEntity<Code> updateCode(@PathVariable(value = "id") Long codeId,
			@PathVariable(value = "newCodeName") String newCodeName, @PathVariable(value = "newCode") Long newCode,
			@PathVariable(value = "newCodeDescription") String newCodeDescription,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, RoleNotMatchException, PermissionNotMatchException {

		final String PERMISSION_KEY = "CAN_UPDATE_CODES";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		Code code = codeRepository.findById(codeId)
				.orElseThrow(() -> new ResourceNotFoundException("Code not found on :: " + codeId));
		Code updatedCode;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		
		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			code.setName(newCodeName);
			code.setCode(newCode);
			code.setDescription(newCodeDescription);
			code.setUpdatedAt(new Date());
			code.setUpdatedBy(user.getUsername());
			updatedCode = codeRepository.save(code);
		}
		
		return ResponseEntity.ok(updatedCode);

	}

	/**
	 * Approve code
	 *
	 * @param codeId
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/approveCode/{id}/{userId}")
	public ResponseEntity<Code> approveCode(@PathVariable(value = "id") Long codeId,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_APPROVE_CODES";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		Code code = codeRepository.findById(codeId)
				.orElseThrow(() -> new ResourceNotFoundException("Code not found on :: " + codeId));
		Code updatedCode;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			code.setStatus(true);
			code.setUpdatedAt(new Date());
			code.setUpdatedBy(user.getUsername());
			updatedCode = codeRepository.save(code);
		}
		
		return ResponseEntity.ok(updatedCode);

	}

	/**
	 * Disapprove code
	 *
	 * @param id  
	 * @param userId  
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/disapproveCode/{id}/{userId}")
	public ResponseEntity<Code> disapproveCode(@PathVariable(value = "id") Long codeId, @PathVariable(value = "userId") Long userId)
			throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {
		final String PERMISSION_KEY = "CAN_DISAPPROVE_CODES";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		Code code = codeRepository.findById(codeId)
				.orElseThrow(() -> new ResourceNotFoundException("Code not found on :: " + codeId));
		Code updatedCode;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			code.setStatus(false);
			code.setUpdatedAt(new Date());
			code.setUpdatedBy(user.getUsername());
			updatedCode = codeRepository.save(code);
		}
		
		return ResponseEntity.ok(updatedCode);
	}

	/**
	 * Delete code map.
	 *
	 * @param id
	 * @param userId
	 * @return the map
	 * @throws ResourceNotFoundException 
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 * @throws Exception the exception
	 */
	@DeleteMapping("/deleteCode/{id}/{userId}")
	public Map<String, Boolean> deleteCode(@PathVariable(value = "id") Long codeId,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {
		
		Map<String, Boolean> response = new HashMap<>();;
			
		Code code = codeRepository.findById(codeId)
				.orElseThrow(() -> new ResourceNotFoundException("Code not found on :: " + codeId));
		
		final String PERMISSION_KEY = "CAN_DELETE_CODES";
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
			codeRepository.delete(code);
			response.put("deleted", Boolean.TRUE);
		}
		
		return response;
		
	}
}
