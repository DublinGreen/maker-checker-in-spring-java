package com.longbridge.greendemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Permission not found exception.
 *
 * @author idisimagha dublin-green
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PermissionNotMatchException extends Exception {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * Instantiates a new Permission not found exception.
   *
   * @param message the message
   */
  public PermissionNotMatchException(String message) {
    super(message);
  }
}
