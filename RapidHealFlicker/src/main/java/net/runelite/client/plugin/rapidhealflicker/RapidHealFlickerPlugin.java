package net.runelite.client.plugin.rapidhealflicker;

import com.google.inject.Provides;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Point;
import net.runelite.api.Skill;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;
import net.runelite.client.config.ConfigManager;
import org.jetbrains.annotations.NotNull;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
  name = "Rapid Heal Flick",
  description = "Double clicks rapid heal for NMZ.",
  enabledByDefault = false,
  type = PluginType.UTILITY
)
@Slf4j
public class RapidHealFlickerPlugin extends Plugin
{
  private static final int[] NMZ_MAP_REGION = {9033};

  @Inject
  private Client client;

  @Inject
  private RapidHealFlickerConfig config;

  @Inject
  private RapidHealFlickerOverlay overlay;

  @Inject
  OverlayManager overlayManager;

  @Inject
  private KeyManager keyManager;
  private ExecutorService executorService;
  private Point point;
  private Random random;
  private boolean run;

  @Getter(AccessLevel.PACKAGE)
  @Setter(AccessLevel.PACKAGE)
  private boolean flash;

  @Provides
  RapidHealFlickerConfig getConfig(ConfigManager configManager) {

    return configManager.getConfig(RapidHealFlickerConfig.class);
  }

  @Override
  protected void startUp() {
    overlayManager.add(overlay);
    keyManager.registerKeyListener(hotkeyListener);
    executorService = Executors.newSingleThreadExecutor();
    random = new Random();
  }

  @Override
  protected void shutDown() {
    overlayManager.remove(overlay);
    keyManager.unregisterKeyListener(hotkeyListener);
    executorService.shutdown();
    random = null;
  }

  private void mouseEvent(int id, @NotNull Point point) {
    MouseEvent e = new MouseEvent(
      client.getCanvas(), id,
      System.currentTimeMillis(),
      0, point.getX(), point.getY(),
      1, false, 1
    );

    client.getCanvas().dispatchEvent(e);
  }

  public void click(Point p) {
    assert !client.isClientThread();

    if (client.isStretchedEnabled()) {
      final Dimension stretched = client.getStretchedDimensions();
      final Dimension real = client.getRealDimensions();
      final double width = (stretched.width / real.getWidth());
      final double height = (stretched.height / real.getHeight());
      final Point point = new Point((int) (p.getX() * width), (int) (p.getY() * height));
      mouseEvent(501, point);
      mouseEvent(502, point);
      mouseEvent(500, point);

      return;
    }

    mouseEvent(501, p);
    mouseEvent(502, p);
    mouseEvent(500, p);
  }

  private HotkeyListener hotkeyListener = new HotkeyListener(() -> config.toggle()) {
    @Override
    public void hotkeyPressed() {
      run = !run;

      if (!run) {
        return;
      }

      point = client.getMouseCanvasPosition();

      executorService.submit(() -> {
        while (run) {
          if (client.getGameState() != GameState.LOGGED_IN) {
            run = false;
            break;
          }

          if (isInNightmareZone()) {
            run = false;
            break;
          }

          try {
            System.out.println(firstClickDelay());
            System.out.println(secondClickDelay());

            click(point);
            Thread.sleep(firstClickDelay());
            click(point);
            Thread.sleep(secondClickDelay());
          }
          catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }
  };

  private long firstClickDelay() {
    return Math.round((Math.random()*((config.maxDelayBetweenDoubleClick()-200)+1))+200);
  }

  private long secondClickDelay() {
    if (config.weightedDistribution()) {
      return (long) clamp(
              (-Math.log(Math.abs(random.nextGaussian()))) * config.deviation() + config.target());
    }
    else {
      return (long) clamp(
              Math.round(random.nextGaussian() * config.deviation() + config.target()));
    }
  }

  private double clamp(double val) {
    return Math.max(config.min(), Math.min(config.max(), val));
  }

  public boolean isInNightmareZone() {
    if (client.getLocalPlayer() == null || !config.enableOnlyInNMZ()) {
      return false;
    }

    return client.getLocalPlayer().getWorldLocation().getPlane() > 0 && Arrays.equals(client.getMapRegions(), NMZ_MAP_REGION);
  }
}