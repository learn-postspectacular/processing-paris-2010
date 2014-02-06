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

import toxi.data.feeds.AtomEntry;
import toxi.data.feeds.AtomFeed;

public class FeedLoader extends Thread {

    private final String feedURL;
    private final List<FeedListener> listeners = new ArrayList<FeedListener>();
    private final long delay;

    /**
     * Creates new FeedLoader instance.
     * 
     * @param url
     *            feed Url
     * @param delay
     *            wait time in milliseconds
     */
    public FeedLoader(String url, long delay) {
        this.feedURL = url;
        this.delay = delay;
    }

    public void addListener(FeedListener l) {
        listeners.add(l);
    }

    @Override
    public void run() {
        try {
            while (true) {
                AtomFeed feed = AtomFeed.newFromURL(feedURL);
                if (feed != null) {
                    for (AtomEntry entry : feed) {
                        System.out.println("received entry: " + entry.id);
                        for (FeedListener l : listeners) {
                            l.newFeedEntry(entry);
                        }
                    }
                }
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
        }
    }

}
