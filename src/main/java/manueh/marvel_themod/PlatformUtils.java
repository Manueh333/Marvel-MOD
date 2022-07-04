package manueh.marvel_themod;

import java.nio.file.Path;

public interface PlatformUtils {
  static PlatformUtils getInstance() {
    return PlatformUtilsImpl.getInstance();
  }
  
  boolean isDedicatedServer();
  
  Path getConfigPath();
}
