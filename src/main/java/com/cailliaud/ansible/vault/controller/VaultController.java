package com.cailliaud.ansible.vault.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.cailliaud.ansible.vault.VaultQuery;
import com.cailliaud.ansible.vault.VaultServiceException;
import com.cailliaud.ansible.vault.business.AnsibleVault;

@RestController
public class VaultController {

  @Autowired
  private AnsibleVault vault;

  @PostMapping("/vault/encrypt")
  public String encryptData(@RequestBody VaultQuery query) {

    try {
      return vault.encryptText(query.getText(), query.getPassphrase());
    } catch (VaultServiceException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Cannot execute ansible-vault task. Contact Administrator", e);

    }
  }

  @PostMapping("/vault/decrypt")
  public String decryptData(@RequestBody VaultQuery query) throws VaultServiceException {
    try {
      return vault.decryptText(query.getText(), query.getPassphrase());
    } catch (VaultServiceException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Cannot execute ansible-vault task. Contact Administrator", e);

    }
  }


}
