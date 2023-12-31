/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, regarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package com.aionemu.gameserver.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javolution.util.FastList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author KID
 */
public class AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminService.class);
    private static final Logger itemLog = LoggerFactory.getLogger("GMITEMRESTRICTION");
    private FastList<Integer> list;
    private static AdminService instance = new AdminService();

    public static AdminService getInstance() {
        return instance;
    }

    public AdminService() {
        list = FastList.newInstance();
        if (AdminConfig.ENABLE_TRADEITEM_RESTRICTION) {
            reload();
        }
    }

    public void reload() {
        if (list.size() > 0) {
            list.clear();
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("./config/administration/item.restriction.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.trim().length() == 0) {
                    continue;
                }

                String pt = line.split("#")[0].replaceAll(" ", "");
                list.add(Integer.parseInt(pt));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("AdminService loaded " + list.size() + " operational items.");
    }

    public boolean canOperate(Player player, Player target, Item item, String type) {
        return canOperate(player, target, item.getItemId(), type);
    }

    public boolean canOperate(Player player, Player target, int itemId, String type) {
        if (!AdminConfig.ENABLE_TRADEITEM_RESTRICTION) {
            return true;
        }

        if (target != null && target.getAccessLevel() > 0) //allow between gms
        {
            return true;
        }

        if (player.getAccessLevel() > 0) {
            boolean value = list.contains(itemId);
            String str = "GM " + player.getName() + "|" + player.getObjectId() + " (" + type + "): " + itemId + "|result=" + value;
            if (target != null) {
                str += "|target=" + target.getName() + "|" + target.getObjectId();
            }
            itemLog.info(str);
            if (!value) {
                PacketSendUtility.sendMessage(player, "You cannot use " + type + " with this item.");
            }

            return value;
        } else {
            return true;
        }
    }
}
