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
package quest.katalam;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

//By Evil_dnk

public class _23004TrustaShulack extends QuestHandler {

    private final static int questId = 23004;

    public _23004TrustaShulack() {
        super(questId);
    }

    public void register() {
        qe.registerQuestNpc(801134).addOnQuestStart(questId);
        qe.registerQuestNpc(801134).addOnTalkEvent(questId);
        qe.registerQuestNpc(801274).addOnTalkEvent(questId);
        qe.registerQuestNpc(230452).addOnKillEvent(questId);
        qe.registerQuestNpc(230453).addOnKillEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 801134) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                }
                else {
                    return sendQuestStartDialog(env);
                }
            }
        }
        else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 801134) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 10){
                        return sendQuestDialog(env, 2375);
                    }
                }
                if (dialog == DialogAction.SELECT_QUEST_REWARD) {
                    changeQuestStep(env,10, 11, true);
                        return sendQuestEndDialog(env);

                }
        }
            if (targetId == 801274) {
                if (dialog == DialogAction.USE_OBJECT) {
                        return sendQuestDialog(env, 1352);
                }
                if (dialog == DialogAction.SETPRO1) {
                    return closeDialogWindow(env);
                }

            }
        }
        else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 801134) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            switch (env.getTargetId()) {
                case 230453:
                case 230452:
                    if (qs.getQuestVarById(0) < 10) {
                        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                        updateQuestStatus(env);
                        return true;
                    }
            }
        }
        return false;
    }
}

