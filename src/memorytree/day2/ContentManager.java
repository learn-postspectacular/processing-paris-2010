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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import toxi.data.feeds.AtomEntry;
import toxi.data.feeds.AtomLink;

public class ContentManager extends Thread {

    private static final int MINUTE = 60000;

    /**
     * Growing name index of already known items
     */
    private final ConcurrentHashMap<String, AtomEntry> index =
            new ConcurrentHashMap<String, AtomEntry>();

    private final List<FeedLoader> feeds = new ArrayList<FeedLoader>();

    private final ConcurrentLinkedQueue<Leaf> queuedItems =
            new ConcurrentLinkedQueue<Leaf>();

    private TreeApp app;

    public ContentManager(TreeApp app) {
        this.app = app;
    }

    public void init() {
        // twitter feed
        FeedLoader f =
                new FeedLoader(
                        "http://search.twitter.com/search.atom?q=memtree", 6000);
        f.addListener(new FeedListener() {

            @Override
            public void newFeedEntry(AtomEntry e) {
                AtomEntry indexed = index.get(e.id);
                // check if new item?
                if (indexed == null) {
                    index.put(e.id, e);
                    TextLeaf leaf = new TextLeaf(e.title);
                    queuedItems.offer(leaf);
                }
            }
        });
        feeds.add(f);
        f.start();
        // voice mails
        f =
                new FeedLoader(
                        "http://memorytree.postspectacular.com/audiofeed.php",
                        1 * MINUTE);
        f.addListener(new FeedListener() {

            @Override
            public void newFeedEntry(AtomEntry e) {
                AtomEntry indexedItem = index.get(e.id);
                if (indexedItem == null) {
                    index.put(e.id, e);
                    List<AtomLink> enclosures =
                            e.getEnclosuresForType("audio/wav");
                    if (enclosures != null) {
                        String audioURL = enclosures.get(0).href;
                        AudioLeaf leaf = new AudioLeaf(audioURL);
                        queuedItems.offer(leaf);
                    }
                }
            }
        });
        feeds.add(f);
        f.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Leaf leaf = queuedItems.poll();
                // System.out.println("processing leaf: " + leaf);
                if (leaf != null) {
                    if (leaf.init()) {
                        app.addLeaf(leaf);
                        Thread.sleep(1000);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
