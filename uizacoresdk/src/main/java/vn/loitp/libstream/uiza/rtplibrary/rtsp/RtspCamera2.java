package vn.loitp.libstream.uiza.rtplibrary.rtsp;

import android.content.Context;
import android.media.MediaCodec;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.SurfaceView;
import android.view.TextureView;

import java.nio.ByteBuffer;

import vn.loitp.libstream.uiza.rtplibrary.base.Camera2Base;
import vn.loitp.libstream.uiza.rtplibrary.view.LightOpenGlView;
import vn.loitp.libstream.uiza.rtplibrary.view.OpenGlView;
import vn.loitp.libstream.uiza.rtsp.rtsp.Protocol;
import vn.loitp.libstream.uiza.rtsp.rtsp.RtspClient;
import vn.loitp.libstream.uiza.rtsp.utils.ConnectCheckerRtsp;

/**
 * More documentation see:
 * {@link Camera2Base}
 * <p>
 * Created by pedro on 4/06/17.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RtspCamera2 extends Camera2Base {

    private RtspClient rtspClient;

    public RtspCamera2(SurfaceView surfaceView, ConnectCheckerRtsp connectCheckerRtsp) {
        super(surfaceView);
        rtspClient = new RtspClient(connectCheckerRtsp);
    }

    public RtspCamera2(TextureView textureView, ConnectCheckerRtsp connectCheckerRtsp) {
        super(textureView);
        rtspClient = new RtspClient(connectCheckerRtsp);
    }

    public RtspCamera2(OpenGlView openGlView, ConnectCheckerRtsp connectCheckerRtsp) {
        super(openGlView);
        rtspClient = new RtspClient(connectCheckerRtsp);
    }

    public RtspCamera2(LightOpenGlView lightOpenGlView, ConnectCheckerRtsp connectCheckerRtsp) {
        super(lightOpenGlView);
        rtspClient = new RtspClient(connectCheckerRtsp);
    }

    public RtspCamera2(Context context, boolean useOpengl, ConnectCheckerRtsp connectCheckerRtsp) {
        super(context, useOpengl);
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
    }

    @Override
    protected void stopStreamRtp() {
        rtspClient.disconnect();
    }

    @Override
    protected void getAacDataRtp(ByteBuffer aacBuffer, MediaCodec.BufferInfo info) {
        rtspClient.sendAudio(aacBuffer, info);
    }

    @Override
    protected void onSPSandPPSRtp(ByteBuffer sps, ByteBuffer pps) {
        ByteBuffer newSps = sps.duplicate();
        ByteBuffer newPps = pps.duplicate();
        rtspClient.setSPSandPPS(newSps, newPps);
        rtspClient.connect();
    }

    @Override
    protected void getH264DataRtp(ByteBuffer h264Buffer, MediaCodec.BufferInfo info) {
        rtspClient.sendVideo(h264Buffer, info);
    }
}

