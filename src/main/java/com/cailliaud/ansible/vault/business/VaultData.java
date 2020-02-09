package com.cailliaud.ansible.vault.business;

import java.io.File;
import java.util.UUID;
import lombok.Getter;

@Getter
public class VaultData {
  private static final String TMP_PASSPHRASE_NAME = "tmp_passphrase";
  private static final String TMP_CONTENT_NAME = "tmp_content";
  
  private UUID id;
  private String workingDir;
  private String passphraseFile;
  private String contentFile;
  
  public VaultData(String parentDir) {
    this.id =  UUID.randomUUID();
    this.workingDir=parentDir+File.separator+this.id;
    this.passphraseFile = this.workingDir+File.separator+TMP_PASSPHRASE_NAME;
    this.contentFile =  this.workingDir+File.separator+TMP_CONTENT_NAME;
  }

}
