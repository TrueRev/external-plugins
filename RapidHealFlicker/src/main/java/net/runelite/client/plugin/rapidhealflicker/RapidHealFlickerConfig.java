package net.runelite.client.plugin.rapidhealflicker;
/*
 * Copyright (c) 2018, Andrew EP | ElPinche256 <https://github.com/ElPinche256>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigTitleSection;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Title;

@ConfigGroup("RapidHealFlickerConfig")
public interface RapidHealFlickerConfig extends Config {

  @ConfigTitleSection(
    position = 0,
    keyName = "mainConfig",
    name = "Main Config",
    description = ""
  )
  default Title mainConfig() {
    return new Title();
  }

  @ConfigItem(
    keyName = "toggle",
    name = "Toggle",
    description = "Toggles the clicker.",
    position = 0
  )
  default Keybind toggle() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
    keyName = "miniDelay",
    name = "Minimum Delay",
    description = "",
    position = 1,
    titleSection = "mainConfig"
  )
  default int min() {
    return 48000;
  }

  @ConfigItem(
    keyName = "maxiDelay",
    name = "Maximum Delay",
    description = "",
    position = 2,
    titleSection = "mainConfig"
  )
  default int max() {
    return 59800;
  }

  @ConfigItem(
    keyName = "target",
    name = "Delay Target",
    description = "",
    position = 3,
    titleSection = "mainConfig"
  )
  default int target() {
    return 54521;
  }

  @ConfigItem(
    keyName = "deviation",
    name = "Delay Deviation",
    description = "",
    position = 4,
    titleSection = "mainConfig"
  )
  default int deviation() {
    return 1200;
  }

  @ConfigItem(
    keyName = "maxDelayBetweenDoubleClick",
    name = "Max Double Click Delay",
    description = "Max Delay Between Double Click",
    position = 5,
    titleSection = "mainConfig"
  )
  default int maxDelayBetweenDoubleClick() {
    return 800;
  }

  @ConfigItem(
          keyName = "weightedDistribution",
          name = "Weighted Distribution",
          description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
          position = 6,
          titleSection = "mainConfig"
  )
  default boolean weightedDistribution() {
    return false;
  }

  @ConfigTitleSection(
    position = 1,
    keyName = "helperConfig",
    name = "Helper Config",
    description = ""
  )
  default Title helperConfig() {
    return new Title();
  }

  @ConfigItem(
    keyName = "enabledInNightMareZone",
    name = "Enable only in NMZ",
    description = "",
    position = 0,
    titleSection = "helperConfig"
  )
  default boolean enableOnlyInNMZ() {
    return false;
  }
}