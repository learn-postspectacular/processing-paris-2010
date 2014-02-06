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
import processing.core.PImage;
import toxi.data.feeds.AtomFeed;

public class AtomTest extends PApplet implements ContentListener {

    public static void main(String[] args) {
        PApplet.main(new String[] { "memorytree.AtomTest" });
    }

    private AtomFeed feed;
    private PImage currentImage;

    @Override
    public void draw() {
        background(random(255));
        if (currentImage != null) {
            image(currentImage, mouseX, mouseY, 256, 256);
        }
    }

    @Override
    public void newImageLoaded(PImage img) {
        currentImage = img;
    }

    @Override
    public void setup() {
        size(1024, 576);
        ContentLoader loader =
                new ContentLoader(
                        this,
                        "http://api.flickr.com/services/feeds/groups_pool.gne?id=1360988@N25&lang=en-us&format=atom");
        loader.addListener(this);
        loader.start();
    }
}
