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
package quest.crafting;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Gigi,Modifly by Newlives@aioncore 29-1-2015
 */
public class _29003ExpertAethertappingExpert extends QuestHandler {

    private final static int questId = 29003;

    public _29003ExpertAethertappingExpert() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(204257).addOnQuestStart(questId);
        qe.registerQuestNpc(204257).addOnTalkEvent(questId);
        qe.registerQuestNpc(798800).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 204257) { // Ustarn
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    if (giveQuestItem(env, 182207142, 1)) {
                        return sendQuestDialog(env, 1011);
                    } else {
                        return true;
                    }
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        }

        if (qs == null) {
            return false;
        }

        if (qs != null && qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 798800: { // Agehia
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(env);
                            return sendQuestDialog(env, 2375);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 798800) { // Agehia
                if (env.getDialogId() == DialogAction.CHECK_USER_HAS_QUEST_ITEM.id()) {
                    return sendQuestDialog(env, 5);
                } else {
                    player.getSkillList().addSkill(player, 30003, 400);
                    removeQuestItem(env, 182207142, 1);
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
