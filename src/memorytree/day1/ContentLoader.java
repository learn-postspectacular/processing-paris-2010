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
import java.util.Iterator;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import toxi.data.feeds.AtomEntry;
import toxi.data.feeds.AtomFeed;
import toxi.data.feeds.AtomLink;

public class ContentLoader extends Thread {

    private String feedURL;
    private PApplet app;
    private List<ContentListener> listeners = new ArrayList<ContentListener>();

    public ContentLoader(PApplet app, String url) {
        this.app = app;
        this.feedURL = url;
    }

    public void addListener(ContentListener l) {
        listeners.add(l);
    }

    @Override
    public void run() {
        AtomFeed feed = AtomFeed.newFromURL(feedURL);
        if (feed != null) {
            Iterator<AtomEntry> iterator = feed.iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    iterator = feed.iterator();
                }
                AtomEntry entry = iterator.next();
                List<AtomLink> enclosures =
                        entry.getEnclosuresForType("image/jpeg");
                if (enclosures != null) {
                    PImage img = app.loadImage(enclosures.get(0).href);
                    if (img != null) {
                        // notify all listeners about new image
                        for (ContentListener l : listeners) {
                            l.newImageLoaded(img);
                        }
                    }
                }
            }
        }
    }

}
