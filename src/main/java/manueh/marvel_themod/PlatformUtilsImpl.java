package manueh.marvel_themod;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.nio.file.Path;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

public final class PlatformUtilsImpl implements PlatformUtils {
  private static final Supplier<PlatformUtils> instance = Suppliers.memoize(PlatformUtilsImpl::new);
  
  public static PlatformUtils getInstance() {
    return instance.get();
  }
  
  public boolean isDedicatedServer() {
    return (FMLLoader.getDist() == Dist.DEDICATED_SERVER);
  }
  
  public Path getConfigPath() {
    return FMLPaths.CONFIGDIR.get().resolve("sci4me/Torcherino.cfg");
  }
}
