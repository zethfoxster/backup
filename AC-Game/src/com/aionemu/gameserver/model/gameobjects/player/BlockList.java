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
package com.aionemu.gameserver.model.gameobjects.player;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a players list of blocked users<br />
 * Blocks via a player's CommonData
 *
 * @author Ben
 */
public class BlockList implements Iterable<BlockedPlayer> {

    /**
     * The maximum number of users a block list can contain
     */
    public static final int MAX_BLOCKS = 10;
    // Indexes blocked players by their player ID
    private final Map<Integer, BlockedPlayer> blockedList;

    /**
     * Constructs a new (empty) blocked list
     */
    public BlockList() {
        this(new ConcurrentHashMap<Integer, BlockedPlayer>());
    }

    /**
     * Constructs a new blocked list with the given initial items
     *
     * @param initialList A map of blocked players indexed by their object IDs
     */
    public BlockList(Map<Integer, BlockedPlayer> initialList) {
        this.blockedList = new ConcurrentHashMap<Integer, BlockedPlayer>(initialList);

    }

    /**
     * Adds a player to the blocked users list<br />
     * <ul>
     * <li>Does not send packets or update the database</li>
     * </ul>
     *
     * @param playerToBlock The player to be blocked
     * @param reason        The reason for blocking this user
     */
    public void add(BlockedPlayer plr) {
        blockedList.put(plr.getObjId(), plr);
    }

    /**
     * Removes a player from the blocked users list<br />
     * <ul>
     * <li>Does not send packets or update the database</li>
     * </ul>
     *
     * @param objIdOfPlayer
     */
    public void remove(int objIdOfPlayer) {
        blockedList.remove(objIdOfPlayer);
    }

    /**
     * Returns the blocked player with this name if they exist
     *
     * @param name
     * @return CommonData of player with this name, null if not blocked
     */
    public BlockedPlayer getBlockedPlayer(String name) {
        Iterator<BlockedPlayer> iterator = blockedList.values().iterator();

        while (iterator.hasNext()) {
            BlockedPlayer entry = iterator.next();
            if (entry.getName().equalsIgnoreCase(name)) {
                return entry;
            }
        }
        return null;
    }

    public BlockedPlayer getBlockedPlayer(int playerObjId) {
        return blockedList.get(playerObjId);
    }

    public boolean contains(int playerObjectId) {
        return blockedList.containsKey(playerObjectId);
    }

    /**
     * Returns the number of blocked players in this list
     *
     * @return blockedList.size()
     */
    public int getSize() {
        return blockedList.size();
    }

    public boolean isFull() {
        return getSize() >= MAX_BLOCKS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<BlockedPlayer> iterator() {
        return blockedList.values().iterator();
    }
}
