package vn.loitp.libstream.uiza.rtplibrary.rtsp;

import android.media.MediaCodec;

import java.nio.ByteBuffer;

import vn.loitp.libstream.uiza.rtplibrary.base.OnlyAudioBase;
import vn.loitp.libstream.uiza.rtsp.rtsp.Protocol;
import vn.loitp.libstream.uiza.rtsp.rtsp.RtspClient;
import vn.loitp.libstream.uiza.rtsp.utils.ConnectCheckerRtsp;

/**
 * More documentation see:
 * {@link OnlyAudioBase}
 * <p>
 * Created by pedro on 10/07/18.
 */
public class RtspOnlyAudio extends OnlyAudioBase {

    private RtspClient rtspClient;

    public RtspOnlyAudio(ConnectCheckerRtsp connectCheckerRtsp) {
        super();
        rtspClient = new RtspClient(connectCheckerRtsp);
    }

    /**
     * Internet protocol used.
     *
     * @param protocol Could be Protocol.TCP or Protocol.UDP.
     */
    public void setProtocol(Protocol protocol) {
        rtspClient.setProtocol(protocol);
    }

    @Override
    public void setAuthorization(String user, String password) {
        rtspClient.setAuthorization(user, password);
    }

    @Override
    protected void prepareAudioRtp(boolean isStereo, int sampleRate) {
        rtspClient.setIsStereo(isStereo);
        rtspClient.setSampleRate(sampleRate);
    }

    @Override
    protected void startStreamRtp(String url) {
        rtspClient.setUrl(url);
        rtspClient.connect();
    }

    @Override
    protected void stopStreamRtp() {
        rtspClient.disconnect();
    }

    @Override
    protected void getAacDataRtp(ByteBuffer aacBuffer, MediaCodec.BufferInfo info) {
        rtspClient.sendAudio(aacBuffer, info);
    }
}