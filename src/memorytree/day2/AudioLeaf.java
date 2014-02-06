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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import processing.core.PGraphics;
import toxi.audio.AudioBuffer;
import toxi.audio.AudioSource;
import toxi.audio.JOALUtil;
import toxi.geom.AABB;
import toxi.geom.Vec3D;
import toxi.processing.ToxiclibsSupport;

public class AudioLeaf extends Leaf {

    private String url;
    private JOALUtil audioSys;
    private AudioBuffer buffer;
    private AudioSource source;

    private long lastPlayTime;

    public AudioLeaf(String audioURL) {
        this.url = audioURL;
    }

    @Override
    public void doSomething(PGraphics g) {
        long now = System.currentTimeMillis();
        long duration = buffer.getSampleSize() * 1000 / buffer.getFrequency();
        if (now - lastPlayTime > duration) {
            System.out.println("playing: " + url);
            source.play();
            lastPlayTime = now;
        }
    }

    @Override
    public void draw(ToxiclibsSupport gfx) {
        PGraphics pg = gfx.getGraphics();
        pg.fill(255, 0, 0);
        pg.noStroke();
        gfx.box(new AABB(pos, 2));
        updateScreenPos(pg);
    }

    @Override
    public boolean init() {
        audioSys = JOALUtil.getInstance();
        try {
            URL audioURL = new URL(url);
            InputStream stream = audioURL.openStream();
            buffer = audioSys.loadBuffer(stream);
            source = audioSys.generateSource();
            return true;
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setPosition(Vec3D pos) {
        this.pos = pos;
        source.set(pos);
        source.updatePosition();
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + url;
    }
}
