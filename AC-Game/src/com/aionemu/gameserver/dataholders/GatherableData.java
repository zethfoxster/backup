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
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.gather.GatherableTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.List;

/**
 * @author ATracer
 */
@XmlRootElement(name = "gatherable_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class GatherableData {

    @XmlElement(name = "gatherable_template")
    private List<GatherableTemplate> gatherables;
    /**
     * A map containing all npc templates
     */
    private TIntObjectHashMap<GatherableTemplate> gatherableData = new TIntObjectHashMap<GatherableTemplate>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (GatherableTemplate gatherable : gatherables) {
            if (gatherable.getMaterials() != null) {
                Collections.sort(gatherable.getMaterials().getMaterial());
            }
            if (gatherable.getExtraMaterials() != null) {
                Collections.sort(gatherable.getExtraMaterials().getMaterial());
            }
            gatherableData.put(gatherable.getTemplateId(), gatherable);
        }
        gatherables = null;
    }

    public int size() {
        return gatherableData.size();
    }

    /**
     * /** Returns an {@link GatherableTemplate} object with given id.
     *
     * @param id id of GatherableTemplate
     * @return GatherableTemplate object containing data about Gatherable with
     * that id.
     */
    public GatherableTemplate getGatherableTemplate(int id) {
        return gatherableData.get(id);
    }
}
