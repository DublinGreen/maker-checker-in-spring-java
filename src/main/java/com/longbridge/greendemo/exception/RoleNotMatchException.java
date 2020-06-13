package com.longbridge.greendemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Role not match exception.
 *
 * @author idisimagha dublin-green
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoleNotMatchException extends Exception {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * Instantiates a new Role not found exception.
   *
   * @param message the message
   */
  public RoleNotMatchException(String message) {
    super(message);
  }
}
