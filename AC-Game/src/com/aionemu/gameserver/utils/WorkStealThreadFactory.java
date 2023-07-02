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
package com.aionemu.gameserver.utils;

import com.aionemu.commons.utils.concurrent.PriorityThreadFactory;
import com.aionemu.commons.utils.internal.chmv8.ForkJoinPool;
import com.aionemu.commons.utils.internal.chmv8.ForkJoinPool.ForkJoinWorkerThreadFactory;
import com.aionemu.commons.utils.internal.chmv8.ForkJoinWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rolandas
 */
public class WorkStealThreadFactory extends PriorityThreadFactory implements ForkJoinWorkerThreadFactory {

    public WorkStealThreadFactory(String namePrefix) {
        super(namePrefix, Thread.NORM_PRIORITY);
    }

    public void setDefaultPool(ForkJoinPool pool) {
        if (pool == null) {
            pool = ForkJoinPool.commonPool();
        }
        super.setDefaultPool(pool);
    }

    @Override
    public ForkJoinPool getDefaultPool() {
        return (ForkJoinPool) super.getDefaultPool();
    }

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new WorkStealThread(pool);
    }

    private static class WorkStealThread extends ForkJoinWorkerThread {

        private static final Logger log = LoggerFactory.getLogger(WorkStealThread.class);

        public WorkStealThread(ForkJoinPool pool) {
            super(pool);
        }

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        protected void onTermination(Throwable exception) {
            if (exception != null) {
                log.error("Error - Thread: " + this.getName() + " terminated abnormaly: " + exception);
            }
            super.onTermination(exception);
        }
    }
}