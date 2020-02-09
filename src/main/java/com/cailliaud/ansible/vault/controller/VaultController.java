package com.cailliaud.ansible.vault.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cailliaud.ansible.vault.VaultQuery;
import com.cailliaud.ansible.vault.VaultServiceException;
import com.cailliaud.ansible.vault.business.AnsibleVault;
import lombok.extern.slf4j.Slf4j;

@RestController
public class VaultController {

  @Autowired
  private AnsibleVault vault;
  
  @PostMapping("/vault/encrypt")
  public String encryptData(@RequestBody VaultQuery query) throws  VaultServiceException{

    return vault.encryptText(query.getText(),query.getPassphrase());
  }
  
  @PostMapping("/vault/decrypt")
  public String decryptData(@RequestBody VaultQuery query) throws VaultServiceException{

    return vault.decryptText(query.getText(),query.getPassphrase());
  }
  
  
}
