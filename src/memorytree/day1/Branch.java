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

package memorytree.day1;

import java.util.ArrayList;
import java.util.List;

import processing.core.PGraphics;
import toxi.geom.Ray3D;
import toxi.geom.Vec3D;
import toxi.math.MathUtils;

public class Branch {

    protected List<Branch> children = new ArrayList<Branch>();
    protected int depth;
    protected Ray3D branchRay;
    protected float length;
    protected BranchConfig config;

    public Branch(BranchConfig config, Ray3D dir, float len, int depth) {
        this.config = config;
        this.branchRay = dir;
        this.length = len;
        this.depth = depth;
    }

    public void draw(PGraphics gfx) {
        Vec3D endPos = branchRay.getPointAtDistance(length);
        gfx.strokeWeight(10 - depth);
        gfx.line(branchRay.x, branchRay.y, branchRay.z, endPos.x, endPos.y,
                endPos.z);
        for (Branch b : children) {
            b.draw(gfx);
        }
    }

    /**
     * @return the branchRay
     */
    public Ray3D getBranchRay() {
        return branchRay;
    }

    /**
     * @return the children
     */
    public List<Branch> getChildren() {
        return children;
    }

    /**
     * @return the config
     */
    public BranchConfig getConfig() {
        return config;
    }

    public int getDepth() {
        return depth;
    }

    /**
     * @return the length
     */
    public float getLength() {
        return length;
    }

    public void grow() {
        if (depth < config.getMaxDepth()) {
            Vec3D newPos = branchRay.getPointAtDistance(length);
            Branch child;
            // do we branch?
            if (MathUtils.random(config.getRnd(), 1f) < config
                    .getBranchChance()) {
                for (int i = 0; i < config.getNumBranches(); i++) {
                    Vec3D newDir =
                            branchRay.getDirection().jitter(config.getRnd(),
                                    config.getBranchJitter());
                    child =
                            new Branch(config, new Ray3D(newPos, newDir),
                                    length * config.getBranchReduce(),
                                    depth + 1);
                    children.add(child);
                    child.grow();
                }
            } else {
                child =
                        new Branch(config, new Ray3D(newPos, branchRay
                                .getDirection()), length, depth);
                children.add(child);
                child.grow();
            }
        }
    }
}
