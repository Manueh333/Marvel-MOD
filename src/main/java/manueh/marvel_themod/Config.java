package manueh.marvel_themod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;

import manueh.marvel_themod.core.enums.TimeGemAPI;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;


public class Config {
  public static Config INSTANCE;
  
  public final int random_tick_rate = 4;
  
  public final boolean log_placement = true;
  
  private final ResourceLocation[] blacklisted_blocks = new ResourceLocation[0];
  
  private final ResourceLocation[] blacklisted_blockentities = new ResourceLocation[0];
  
  private final Tier[] tiers = new Tier[] { new Tier("normal", 4, 4, 1), new Tier("compressed", 36, 4, 1), new Tier("double_compressed", 324, 4, 1) };
  
  public String online_mode = "";
  
  public static void initialize() {
    Gson gson = (new GsonBuilder()).disableInnerClassSerialization().registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).setPrettyPrinting().create();
    Path configDir = PlatformUtils.getInstance().getConfigPath();
    Logger logger = LogManager.getLogger("marvel-config");
    MarkerManager.Log4jMarker marker = new MarkerManager.Log4jMarker("marvel_themod");
    Config config = null;
    try {
      Files.createDirectories(configDir.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
    } catch (IOException e) {
      logger.warn((Marker)marker, "Failed to create directory required for marvel_themod config, using default config.");
      config = new Config();
    } 
    if (config == null)
      if (Files.exists(configDir, new java.nio.file.LinkOption[0])) {
        try {
          BufferedReader reader = Files.newBufferedReader(configDir);
          try {
            config = (Config)gson.fromJson(reader, Config.class);
            if (reader != null)
              reader.close(); 
          } catch (Throwable throwable) {
            if (reader != null)
              try {
                reader.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } catch (IOException e) {
          logger.warn((Marker)marker, "Failed to read marvel_themod config file, using default config.");
          config = new Config();
        } 
      } else {
        config = new Config();
        try {
          BufferedWriter writer = Files.newBufferedWriter(configDir, new OpenOption[] { StandardOpenOption.CREATE_NEW });
          try {
            gson.toJson(config, writer);
            if (writer != null)
              writer.close(); 
          } catch (Throwable throwable) {
            if (writer != null)
              try {
                writer.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } catch (IOException e) {
          logger.warn((Marker)marker, "Failed to save default marvel_themod config file.");
        } 
      }  
    INSTANCE = config;
    INSTANCE.onConfigLoaded();
  }
  
  private void onConfigLoaded() {
    this.online_mode = this.online_mode.toUpperCase();
    if (!this.online_mode.equals("ONLINE") && !this.online_mode.equals("RESTART"))
      this.online_mode = ""; 
    for (Tier tier : this.tiers)
      ((TimeGemImpl) TimeGemAPI.INSTANCE).registerTier(new ResourceLocation("marvel_themod", tier.name), tier.max_speed, tier.xz_range, tier.y_range);
    for (ResourceLocation id : this.blacklisted_blocks)
      TimeGemAPI.INSTANCE.blacklistBlock(id);
    for (ResourceLocation id : this.blacklisted_blockentities)
      TimeGemAPI.INSTANCE.blacklistBlockEntity(id);
  }
  
  static class Tier {
    final String name;
    
    final int max_speed;
    
    final int xz_range;
    
    final int y_range;
    
    Tier(String name, int max_speed, int xz_range, int y_range) {
      this.name = name;
      this.max_speed = max_speed;
      this.xz_range = xz_range;
      this.y_range = y_range;
    }
  }
  }