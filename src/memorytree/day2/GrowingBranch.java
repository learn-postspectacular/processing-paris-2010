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

import java.util.ArrayList;
import java.util.List;

import memorytree.day1.Branch;
import toxi.geom.Ray3D;
import toxi.geom.Vec3D;
import toxi.processing.ToxiclibsSupport;

public class GrowingBranch {

    private Leaf leaf;
    private Branch branch;
    private float progress;

    List<GrowingBranch> children;

    public GrowingBranch(Branch b) {
        this.branch = b;
        this.progress = 0;
        this.children =
                new ArrayList<GrowingBranch>(branch.getChildren().size());
    }

    public void draw(ToxiclibsSupport gfx) {
        Vec3D endPos = branch.getBranchRay().getPointAtDistance(progress);
        gfx.getGraphics().stroke(branch.getDepth() * 20);
        gfx.line(branch.getBranchRay(), endPos);
        for (GrowingBranch b : children) {
            b.draw(gfx);
        }
        if (leaf != null) {
            leaf.setPosition(endPos);
            leaf.draw(gfx);
        }
    }

    public List<GrowingBranch> grow(float speed) {
        List<GrowingBranch> branches = new ArrayList<GrowingBranch>(3);
        if (leaf != null && progress < branch.getLength()) {
            progress += speed;
            Ray3D ray = branch.getBranchRay();
            Vec3D p = ray.getPointAtDistance(progress);
            float growthDistance = ray.distanceTo(p);
            for (Branch b : branch.getChildren()) {
                float dist = ray.distanceTo(b.getBranchRay());
                if (growthDistance >= dist) {
                    GrowingBranch gb = new GrowingBranch(b);
                    children.add(gb);
                    branches.add(gb);
                }
            }
        }
        for (GrowingBranch b : children) {
            List<GrowingBranch> childBranches = b.grow(speed);
            if (childBranches != null) {
                branches.addAll(childBranches);
            }
        }
        return branches;
    }

    public void setLeaf(Leaf leaf) {
        this.leaf = leaf;
    }

}
