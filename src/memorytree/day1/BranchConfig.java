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

import java.util.Random;

public class BranchConfig {

    private int maxDepth;

    private float branchJitter;

    private float branchReduce;

    private float branchChance;

    private int numBranches;

    private Random rnd = new Random();

    public BranchConfig(int maxDepth, float branchChance, float branchJitter,
            float branchReduce, int numBranches) {
        this.maxDepth = maxDepth;
        this.branchChance = branchChance;
        this.branchJitter = branchJitter;
        this.branchReduce = branchReduce;
        this.numBranches = numBranches;
    }

    /**
     * @return the branchChance
     */
    public float getBranchChance() {
        return branchChance;
    }

    /**
     * @return the branchJitter
     */
    public float getBranchJitter() {
        return branchJitter;
    }

    /**
     * @return the branchReduce
     */
    public float getBranchReduce() {
        return branchReduce;
    }

    /**
     * @return the maxDepth
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * @return the numBranches
     */
    public int getNumBranches() {
        return numBranches;
    }

    /**
     * @return the rnd
     */
    public Random getRnd() {
        return rnd;
    }

    public void seed(long seed) {
        rnd.setSeed(seed);
    }

    /**
     * @param branchChance
     *            the branchChance to set
     */
    public void setBranchChance(float branchChance) {
        this.branchChance = branchChance;
    }

    /**
     * @param branchJitter
     *            the branchJitter to set
     */
    public void setBranchJitter(float branchJitter) {
        this.branchJitter = branchJitter;
    }

    /**
     * @param branchReduce
     *            the branchReduce to set
     */
    public void setBranchReduce(float branchReduce) {
        this.branchReduce = branchReduce;
    }

    /**
     * @param maxDepth
     *            the maxDepth to set
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * @param numBranches
     *            the numBranches to set
     */
    public void setNumBranches(int numBranches) {
        this.numBranches = numBranches;
    }
}
