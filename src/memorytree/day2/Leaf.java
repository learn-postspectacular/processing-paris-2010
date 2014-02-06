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
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;
import toxi.processing.ToxiclibsSupport;

public abstract class Leaf {

    protected Vec3D pos;

    public Vec2D screenPos = new Vec2D();

    public abstract void doSomething(PGraphics g);

    public abstract void draw(ToxiclibsSupport gfx);

    public abstract boolean init();

    public abstract void setPosition(Vec3D pos);

    protected void updateScreenPos(PGraphics gfx) {
        screenPos.x = gfx.screenX(pos.x, pos.y, pos.z);
        screenPos.y = gfx.screenY(pos.x, pos.y, pos.z);
    }
}
