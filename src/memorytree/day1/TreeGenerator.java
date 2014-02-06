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

import processing.core.PApplet;
import toxi.geom.Ray3D;
import toxi.geom.Vec3D;

public class TreeGenerator extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[] { "memorytree.TreeGenerator" });
    }

    private Branch tree;

    private void createTree() {
        BranchConfig config = new BranchConfig(10, 0.9f, 0.5f, 0.8f, 3);
        config.seed(23);
        tree =
                new Branch(config, new Ray3D(new Vec3D(), new Vec3D(0, 1, 0)),
                        50, 0);
        tree.grow();
    }

    @Override
    public void draw() {
        background(255);
        translate(width / 2, height / 2, 0);
        rotateX(mouseY * 0.01f);
        rotateY(mouseX * 0.01f);
        tree.draw(g);
    }

    @Override
    public void keyPressed() {
        createTree();
    }

    @Override
    public void setup() {
        size(1024, 576, OPENGL);
        createTree();
    }
}
