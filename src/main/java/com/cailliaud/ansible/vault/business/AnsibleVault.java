package com.cailliaud.ansible.vault.business;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import com.cailliaud.ansible.vault.VaultServiceException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AnsibleVault {

  @Value("${directory.tmp}")
  private String tmpDirParent;

  @PostConstruct
  public void init() {
    Assert.isTrue(Files.isDirectory(Paths.get(tmpDirParent)),
        "Temporary directory does not exists. Check application properties (directory.tmp)");
  }

  public String encryptText(String text, String passphrase) throws VaultServiceException {
    return doAnsibleAction("encrypt", text, passphrase);

  }

  public String decryptText(String text, String passphrase) throws VaultServiceException {
    return doAnsibleAction("decrypt", text, passphrase);
  }

  private String doAnsibleAction(String action, String text, String passphrase)
      throws VaultServiceException {
    String data = "";

    VaultData vaulData = new VaultData(tmpDirParent);
    setUpWorkspace(vaulData, text, passphrase);
    performAnsibleCommand(action, vaulData);
    try {
      data = Files.readString(Paths.get(vaulData.getContentFile()));
    } catch (IOException e) {
      throw new VaultServiceException("Content generated cannot be read.",e);
    }finally {
      cleanUpWorkspace(vaulData);
    }
    
    return data;

  }

  private void performAnsibleCommand(String action, VaultData vaulData)
      throws VaultServiceException {
    try {
      ProcessBuilder builder = new ProcessBuilder();
      builder.command("ansible-vault", action, "--vault-password-file",
          vaulData.getPassphraseFile(), vaulData.getContentFile());
      builder.directory(new File(vaulData.getWorkingDir()));
      Process process = builder.start();
      int exitCode = process.waitFor();
      assert exitCode == 0;
    } catch (InterruptedException | IOException e) {
      log.debug("Ansible vault command failed",e);
      throw new VaultServiceException("Ansible vault command failed");
    }
  }


  private void setUpWorkspace(VaultData vaultData, String text, String passphrase)
      throws VaultServiceException {
    try {


      log.debug("tmp dir created {}", vaultData.getWorkingDir());
      Path workingDir = Paths.get(vaultData.getWorkingDir());
      Files.createDirectory(workingDir);

      Path passphraseFile = Paths.get(vaultData.getPassphraseFile());
      Files.writeString(passphraseFile, passphrase);
      log.debug("passphrase filer created {}", passphraseFile);

      Path contentFile = Paths.get(vaultData.getContentFile());
      Files.writeString(contentFile, text);
      log.debug("content File created {}", contentFile);

    } catch (IOException io) {
      log.error("Cannot create necessary files and directories to perform ansible vault", io);
      throw new VaultServiceException(
          "Cannot create necessary files and directories to perform ansible vault");
    }
  }


  private void cleanUpWorkspace(VaultData vaultData) throws VaultServiceException {
    Path workingDir = Paths.get(vaultData.getWorkingDir());
    try {
      Files.walk(workingDir).sorted(Comparator.reverseOrder()).map(Path::toFile)
          .forEach(File::delete);

    } catch (IOException io) {
      log.error("Cannot delete files and directories created", io);
      throw new VaultServiceException("Cannot delete files and directories created");
    }
  }


}
