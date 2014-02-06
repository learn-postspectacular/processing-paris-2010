/*
 * This file is part of The Memory Tree/ProcessingParis project.
 * 
 * Copyright 2010 Karsten Schmidt (PostSpectacular Ltd.)
 * 
 * MemoryTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MemoryTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MemoryTree. If not, see <http://www.gnu.org/licenses/>.
 */

package memorytree.day2;

import processing.core.PGraphics;
import toxi.geom.AABB;
import toxi.geom.Vec3D;
import toxi.processing.ToxiclibsSupport;

public class TextLeaf extends Leaf {

    private String content;

    public TextLeaf(String txt) {
        this.content = txt;
    }

    @Override
    public void doSomething(PGraphics g) {
        g.fill(0);
        g.text(content, screenPos.x, screenPos.y);
    }

    @Override
    public void draw(ToxiclibsSupport gfx) {
        PGraphics pg = gfx.getGraphics();
        pg.fill(0, 224, 255);
        pg.noStroke();
        gfx.box(new AABB(pos, 2));
        updateScreenPos(pg);
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void setPosition(Vec3D pos) {
        this.pos = pos;
    }

}
