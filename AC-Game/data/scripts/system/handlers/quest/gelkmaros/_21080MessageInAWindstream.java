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
package quest.gelkmaros;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Cheatkiller
 *
 */
public class _21080MessageInAWindstream extends QuestHandler {

    private final static int questId = 21080;

    public _21080MessageInAWindstream() {
        super(questId);
    }

    public void register() {
        qe.registerQuestNpc(799231).addOnQuestStart(questId);
        qe.registerQuestNpc(799231).addOnTalkEvent(questId);
        qe.registerQuestNpc(799427).addOnTalkEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("ANTAGOR_CANYON_220070000"), questId);
        qe.registerOnEnterZone(ZoneName.get("GELKMAROS_FORTRESS_220070000"), questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 799231) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env, 182207939, 1);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 799427) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 3) {
                        return sendQuestDialog(env, 2034);
                    }
                } else if (dialog == DialogAction.SETPRO4) {
                    removeQuestItem(env, 182207939, 1);
                    return defaultCloseDialog(env, 3, 4);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799231) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                }
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (zoneName == ZoneName.get("ANTAGOR_CANYON_220070000")) {
                if (var < 3) {
                    changeQuestStep(env, var, var + 1, false);
                    return true;
                }
            } else if (zoneName == ZoneName.get("GELKMAROS_FORTRESS_220070000") && var == 4) {
                changeQuestStep(env, 4, 4, true);
                return true;
            }
        }
        return false;
    }
}
