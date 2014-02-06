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
import java.util.concurrent.ConcurrentLinkedQueue;

import memorytree.day1.Branch;
import memorytree.day1.BranchConfig;
import processing.core.PApplet;
import toxi.audio.JOALUtil;
import toxi.geom.Ray3D;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;
import toxi.processing.ToxiclibsSupport;

public class TreeApp extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[] { "memorytree.day2.TreeApp" });
    }

    private ContentManager contentManager;
    private Branch tree;
    private GrowingBranch growingTree;
    private ConcurrentLinkedQueue<GrowingBranch> availableBranches =
            new ConcurrentLinkedQueue<GrowingBranch>();

    private ToxiclibsSupport gfx;
    private JOALUtil audioSys;

    private List<Leaf> leaves = new ArrayList<Leaf>();
    private Leaf selectedLeaf;
    private Vec2D camRot = new Vec2D();
    private Vec2D targetCamRot = new Vec2D();
    private float currZoom = 2;
    private float targetZoom = 2;

    public void addLeaf(Leaf leaf) {
        try {
            while (availableBranches.peek() == null) {
                Thread.sleep(250);
            }
            GrowingBranch b = availableBranches.poll();
            b.setLeaf(leaf);
            leaves.add(leaf);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void draw() {
        background(255);
        hint(ENABLE_DEPTH_TEST);
        pushMatrix();
        translate(width / 2, height * 0.8f, 0);
        camRot.interpolateToSelf(targetCamRot, 0.1f);
        currZoom += (targetZoom - currZoom) * 0.1f;
        rotateX(camRot.x);
        rotateY(camRot.y);
        scale(currZoom);
        List<GrowingBranch> branches = growingTree.grow(1);
        for (GrowingBranch b : branches) {
            availableBranches.offer(b);
        }
        growingTree.draw(gfx);
        popMatrix();
        hint(DISABLE_DEPTH_TEST);
        if (selectedLeaf != null) {
            selectedLeaf.doSomething(g);
        }
    }

    private void initAudio() {
        audioSys = JOALUtil.getInstance();
        audioSys.init();
    }

    private void initContentManager() {
        contentManager = new ContentManager(this);
        contentManager.init();
        contentManager.start();
    }

    private void initTree() {
        BranchConfig config = new BranchConfig(10, 0.9f, 0.5f, 0.8f, 3);
        config.seed(23);
        tree =
                new Branch(config, new Ray3D(new Vec3D(), new Vec3D(0, -1, 0)),
                        50, 0);
        tree.grow();
        growingTree = new GrowingBranch(tree);
        availableBranches.offer(growingTree);
    }

    @Override
    public void keyPressed() {
        if (keyCode == UP) {
            targetCamRot.x += 0.1;
        }
        if (keyCode == DOWN) {
            targetCamRot.x -= 0.1;
        }
        if (keyCode == LEFT) {
            targetCamRot.y += 0.1;
        }
        if (keyCode == RIGHT) {
            targetCamRot.y -= 0.1;
        }
        if (key == '-') {
            targetZoom = max(targetZoom - 0.1f, 1);
        }
        if (key == '=') {
            targetZoom = min(targetZoom + 0.1f, 8);
        }
    }

    @Override
    public void mouseMoved() {
        selectedLeaf = null;
        Vec2D mousePos = new Vec2D(mouseX, mouseY);
        for (Leaf l : leaves) {
            float dist = l.screenPos.distanceTo(mousePos);
            if (dist < 15) {
                selectedLeaf = l;
                return;
            }
        }
    }

    @Override
    public void setup() {
        size(1024, 576, OPENGL);
        initAudio();
        initContentManager();
        initTree();
        gfx = new ToxiclibsSupport(this);
    }

    @Override
    public void stop() {
        audioSys.shutdown();
    }
}
