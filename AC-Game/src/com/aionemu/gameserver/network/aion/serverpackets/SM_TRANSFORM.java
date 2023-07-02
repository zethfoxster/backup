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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.TransformType;

/**
 * @author Sweetkr, xTz
 */
public class SM_TRANSFORM extends AionServerPacket {

    private Creature creature;
    private int state;
    private int modelId;
    private boolean applyEffect;
    private int panelId;

    public SM_TRANSFORM(Creature creature, boolean applyEffect) {
        this.creature = creature;
        this.state = creature.getState();
        modelId = creature.getTransformModel().getModelId();
        this.applyEffect = applyEffect;
    }

    public SM_TRANSFORM(Creature creature, int panelId, boolean applyEffect) {
        this.creature = creature;
        this.state = creature.getState();
        modelId = creature.getTransformModel().getModelId();
        this.panelId = panelId;
        this.applyEffect = applyEffect;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(modelId);
        writeD(creature.getObjectId());
        writeD(modelId);
        writeH(state);
        writeF(0.25f);
        writeF(2.0f);
        writeC(applyEffect && creature.getTransformModel().getType() == TransformType.NONE ? 1 : 0);
        writeD(creature.getTransformModel().getType().getId());
        writeC(0);
        writeC(0);
        writeC(0);
        writeC(0);
        writeC(0);
        writeC(npcTemplate != null && !isMoveNpc(npcTemplate.getTemplateId()) && npcTemplate.getStatsTemplate().getRunSpeed() == 0 ? 1 : 0);
        writeD(panelId); // display panel
    }

    /* Exception for Steam Tachysphere and Rentus Tanks
     * FIXME!: fix handling and remove! */
    private boolean isMoveNpc(int npcId) {
        switch (npcId) {
            case 217384:
            case 218611:
            case 218610:
                return true;
        }
        return false;
    }
}
