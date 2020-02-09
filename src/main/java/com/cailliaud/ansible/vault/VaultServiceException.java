package com.cailliaud.ansible.vault;

public class VaultServiceException extends Exception {



  /**
   * 
   */
  private static final long serialVersionUID = -1308244733513358079L;
  
  public VaultServiceException(String message) {
    super(message);

  }
  
  public VaultServiceException(String message, Throwable throwable) {
    super(message,throwable);

  }
  
  

}
