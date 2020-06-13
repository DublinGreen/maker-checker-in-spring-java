package com.longbridge.greendemo.settings;

import java.util.HashMap;

public class PermissionsHashMap {
		
	private static HashMap<String, String> permissionsMap = new HashMap<>();
	
	public static void initPermissionsHashMap() {
		permissionsMap.put("CAN_CREATE_SETTINGS", "CAN CREATE SETTINGS");
		permissionsMap.put("CAN_APPROVE_SETTINGS", "CAN APPROVE SETTINGS");
		permissionsMap.put("CAN_DISAPPROVE_SETTINGS", "CAN DISAPPROVE SETTINGS");
		permissionsMap.put("CAN_UPDATE_SETTINGS", "CAN UPDATE SETTINGS");
		permissionsMap.put("CAN_DELETE_SETTINGS", "CAN DELETE SETTINGS");
		
		permissionsMap.put("CAN_CREATE_CODES", "CAN CREATE CODES");
		permissionsMap.put("CAN_APPROVE_CODES", "CAN APPROVE CODES");
		permissionsMap.put("CAN_DISAPPROVE_CODES", "CAN DISAPPROVE CODES");
		permissionsMap.put("CAN_UPDATE_CODES", "CAN UPDATE CODES");
		permissionsMap.put("CAN_DELETE_CODES", "CAN DELETE CODES");
		
		permissionsMap.put("CAN_CREATE_USERS", "CAN CREATE USERS");
		permissionsMap.put("CAN_APPROVE_USERS", "CAN APPROVE USERS");
		permissionsMap.put("CAN_DISAPPROVE_USERS", "CAN DISAPPROVE USERS");
		permissionsMap.put("CAN_UPDATE_USERS", "CAN UPDATE USERS");
		permissionsMap.put("CAN_DELETE_USERS", "CAN DELETE USERS");
		
		permissionsMap.put("CAN_CREATE_PERMISSIONS", "CAN CREATE PERMISSIONS");
		permissionsMap.put("CAN_APPROVE_PERMISSIONS", "CAN APPROVE PERMISSIONS");
		permissionsMap.put("CAN_DISAPPROVE_PERMISSIONS", "CAN DISAPPROVE PERMISSIONS");
		permissionsMap.put("CAN_UPDATE_PERMISSIONS", "CAN UPDATE PERMISSIONS");
		permissionsMap.put("CAN_DELETE_PERMISSIONS", "CAN DELETE PERMISSIONS");
		
		permissionsMap.put("CAN_CREATE_MERCHANTS", "CAN CREATE MERCHANTS");
		permissionsMap.put("CAN_APPROVE_MERCHANTS", "CAN APPROVE MERCHANTS");
		permissionsMap.put("CAN_DISAPPROVE_MERCHANTS", "CAN DISAPPROVE MERCHANTS");
		permissionsMap.put("CAN_UPDATE_MERCHANTS", "CAN UPDATE MERCHANTS");
		permissionsMap.put("CAN_DELETE_MERCHANTS", "CAN DELETE MERCHANTS");

	}
	
	public static String getValueByKey(String key) {
		initPermissionsHashMap();// init map first.
		return permissionsMap.get(key);
	}
	
	
}
