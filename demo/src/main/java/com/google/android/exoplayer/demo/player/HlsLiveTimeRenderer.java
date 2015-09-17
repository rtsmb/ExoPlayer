package com.google.android.exoplayer.demo.player;

import android.os.Looper;

import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.hls.HlsChunkSource;

/**
 * Created by seb on 17/09/15.
 */
public class HlsLiveTimeRenderer extends TrackRenderer {
    private final MediaCodecAudioTrackRenderer audioRenderer;
    private final HlsChunkSource chunkSource;

    public interface LiveTimeRenderer {
        void onLiveTimeUpdate(long[] availableTimeRange, long currentTimeMs);
    }

    LiveTimeRenderer liveTimeRenderer;

    public HlsLiveTimeRenderer(HlsChunkSource chunkSource, MediaCodecAudioTrackRenderer audioRenderer, LiveTimeRenderer liveTimeRenderer, Looper mainLooper) {
        this.chunkSource = chunkSource;
        this.audioRenderer = audioRenderer;
        this.liveTimeRenderer = liveTimeRenderer;
    }

    @Override
    protected boolean doPrepare(long positionUs) throws ExoPlaybackException {
        return true;
    }

    @Override
    protected boolean isEnded() {
        return false;
    }

    @Override
    protected boolean isReady() {
        return true;
    }

    @Override
    protected void doSomeWork(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
        liveTimeRenderer.onLiveTimeUpdate(
                chunkSource.getLiveRangeMs(),
                chunkSource.convertLiveTrackPositionMs(audioRenderer.getPositionUs()));
    }

    @Override
    protected void maybeThrowError() throws ExoPlaybackException {

    }

    @Override
    protected long getDurationUs() {
        return 0;
    }

    @Override
    protected long getBufferedPositionUs() {
        return 0;
    }

    @Override
    protected void seekTo(long positionUs) throws ExoPlaybackException {
    }


}