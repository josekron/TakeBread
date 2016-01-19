package com.jaherrera.takebread.user.exception;


public class UserServiceException extends java.lang.RuntimeException{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 949157807618322326L;
	
	/**
	 * Instantiates a new experience activities service exception.
	 */
	public UserServiceException() {
		super();
	}

	/**
	 * Instantiates a new experience activities service exception.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @param arg3 the arg3
	 */
	public UserServiceException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * Instantiates a new experience activities service exception.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 */
	public UserServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Instantiates a new experience activities service exception.
	 *
	 * @param arg0 the arg0
	 */
	public UserServiceException(String arg0) {
		super(arg0);
	}

	/**
	 * Instantiates a new experience activities service exception.
	 *
	 * @param arg0 the arg0
	 */
	public UserServiceException(Throwable arg0) {
		super(arg0);
	}

}
