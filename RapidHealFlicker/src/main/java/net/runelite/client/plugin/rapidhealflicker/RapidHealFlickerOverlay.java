package net.runelite.client.plugin.rapidhealflicker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

class RapidHealFlickerOverlay extends Overlay {

  private static final Color FLASH_COLOR = new Color(255, 0, 0, 70);
  private final Client client;
  private final RapidHealFlickerPlugin plugin;
  private final RapidHealFlickerConfig config;
  private int timeout;

  @Inject
  RapidHealFlickerOverlay(Client client, RapidHealFlickerPlugin plugin, RapidHealFlickerConfig config) {
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ALWAYS_ON_TOP);
    this.client = client;
    this.plugin = plugin;
    this.config = config;
  }

  @Override
  public Dimension render(Graphics2D graphics) {
//    if (plugin.isFlash() && config.flash()) {
//      final Color flash = graphics.getColor();
//      graphics.setColor(FLASH_COLOR);
//      graphics.fill(new Rectangle(client.getCanvas().getSize()));
//      graphics.setColor(flash);
//      timeout++;
//      if (timeout >= 50) {
//        timeout = 0;
//        plugin.setFlash(false);
//      }
//    }

    return null;
  }
}
