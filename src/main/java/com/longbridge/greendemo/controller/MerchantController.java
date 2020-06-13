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
import com.longbridge.greendemo.model.Merchant;
import com.longbridge.greendemo.model.Permission;
import com.longbridge.greendemo.repository.MerchantRepository;
import com.longbridge.greendemo.repository.PermissionRepository;
import com.longbridge.greendemo.settings.AppRolesListEnum;
import com.longbridge.greendemo.settings.PermissionsHashMap;

/**
 * The type Merchant controller. Merchant api controller
 * 
 * @author idisimagha dublin-green
 */
@RestController
@RequestMapping("/api/v1/merchant")
@CrossOrigin(origins = "http://localhost:8080")
public class MerchantController {

	@Autowired
	private UserDao userRepository;
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	

	/**
	 * Get all merchants list.
	 *
	 * @return the list
	 */
	@GetMapping("/getAllMerchants")
	public List<Merchant> getAllMerchants() {
		return merchantRepository.findAll();
	}

	/**
	 * Get approved merchants list.
	 *
	 * @return the list
	 */
	@GetMapping("/getApprovedMerchants")
	public List<Merchant> getApprovedMerchants() {
		return merchantRepository.findByStatus(true);
	}

	/**
	 * Get not approved permission list.
	 *
	 * @return the list
	 */
	@GetMapping("/getUnapprovedMerchants")
	public List<Merchant> getUnapprovedMerchants() {
		return merchantRepository.findByStatus(false);
	}

	/**
	 * Gets merchant by id.
	 *
	 * @param id
	 * @return the merchant
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/getMerchantById/{id}")
	public ResponseEntity<Merchant> getMerchantById(@PathVariable(value = "id") Long merchantId) throws ResourceNotFoundException {
		Merchant merchant = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new ResourceNotFoundException("Merchant not found on :: " + merchantId));
		return ResponseEntity.ok().body(merchant);
	}

	/**
	 * Create merchant.
	 *
	 * @param merchantObj
	 * @param userId
	 * @return the code
	 * @throws ResourceNotFoundException
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PostMapping("/createCode/{merchantObj}/{userId}")
	public @Valid Merchant createMerchant(
			@Valid @RequestBody Merchant merchantObj,
			@PathVariable(value = "userId") Long userId)
			throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_CREATE_MERCHANTS";
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
			merchantObj.setCreatedAt(new Date());
			merchantObj.setCreatedBy(user.getUsername());
			merchantObj.setUpdatedAt(new Date());
			merchantObj.setUpdatedBy(user.getUsername());
		}	

		return merchantRepository.save(merchantObj);
	}

	/**
	 * Approve merchant
	 *
	 * @param merchantId
	 * @param userId
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/approveMerchant/{id}/{userId}")
	public ResponseEntity<Merchant> approveMerchant(@PathVariable(value = "id") Long merchantId,
			@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {

		final String PERMISSION_KEY = "CAN_APPROVE_MERCHANTS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		Merchant merchantToUpdate = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new ResourceNotFoundException("Merchant not found on :: " + merchantId));
		Merchant updatedMerchant;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			merchantToUpdate.setStatus(true);
			merchantToUpdate.setUpdatedAt(new Date());
			merchantToUpdate.setUpdatedBy(user.getUsername());
			updatedMerchant = merchantRepository.save(merchantToUpdate);
		}
		
		return ResponseEntity.ok(updatedMerchant);

	}

	/**
	 * Disapprove merchant
	 *
	 * @param id  
	 * @param userId  
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 * @throws PermissionNotMatchException 
	 * @throws RoleNotMatchException 
	 */
	@PutMapping("/disapproveMerchant/{id}/{userId}")
	public ResponseEntity<Merchant> disapproveMerchant(@PathVariable(value = "id") Long merchantId, @PathVariable(value = "userId") Long userId)
			throws ResourceNotFoundException, PermissionNotMatchException, RoleNotMatchException {
		final String PERMISSION_KEY = "CAN_DISAPPROVE_PERMISSIONS";
		final String PERMISSION_NEEDED = PermissionsHashMap.getValueByKey(PERMISSION_KEY);			
		Permission permission;
		
		if(PERMISSION_NEEDED == null){
			throw new PermissionNotMatchException("Permission key is invalid not does not have permission (" + PERMISSION_KEY + ")");
		}else {
			permission = permissionRepository.findByStatusAndNameAndRoleName(true, PERMISSION_NEEDED, AppRolesListEnum.ROLE_ADMIN.toString());
		}
		
		Merchant merchantToUpdate = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new ResourceNotFoundException("Merchant not found on :: " + merchantId));
		Merchant updatedMerchant;

		DAOUser user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		if(!user.getRole().equalsIgnoreCase(AppRolesListEnum.ROLE_ADMIN.toString())) {
			throw new RoleNotMatchException(user.getUsername() + " does not have " + AppRolesListEnum.ROLE_ADMIN.toString() + " role");
		}else if(!permission.getStatus()) {
			throw new PermissionNotMatchException("Permission not enabled (" + PERMISSION_NEEDED + ")");
		}else {
			merchantToUpdate.setStatus(false);
			merchantToUpdate.setUpdatedAt(new Date());
			merchantToUpdate.setUpdatedBy(user.getUsername());
			updatedMerchant = merchantRepository.save(merchantToUpdate);
		}
		
		return ResponseEntity.ok(updatedMerchant);
	}


}
