package org.quelea.windows.main;

import com.walker.devolay.DevolayFrameFourCCType;
import com.walker.devolay.DevolaySender;
import com.walker.devolay.DevolayVideoFrame;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.quelea.data.displayable.Displayable;
import java.nio.ByteBuffer;


public abstract class DisplayableDrawer {

    private DisplayCanvas canvas;

    public DisplayableDrawer() {
        this.canvas = null;
    }

    public void setCanvas(DisplayCanvas canvas) {
        this.canvas = canvas;
    }

    public DisplayCanvas getCanvas() {
        return canvas;
    }

    public abstract void draw(Displayable displayable);

    public abstract void clear();

    public abstract void requestFocus();

    protected void sendNDISnapshot(){
        DevolaySender sender = getCanvas().getNDISender();
        DevolayVideoFrame videoFrame = getCanvas().getNDIVideoFrame();

        // If there is no sender, just return
        if( sender == null )
            return;

        int width = (int)getCanvas().getWidth();
        int height = (int)getCanvas().getHeight();

        videoFrame.setResolution(width, height);
        videoFrame.setFourCCType(DevolayFrameFourCCType.BGRA);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        WritableImage image = getCanvas().snapshot(parameters, null);

        byte[] buffer;
        buffer = new byte[width * height * 4];
        ByteBuffer bytebuffer = ByteBuffer.allocateDirect(width * height * 4);

        PixelReader pixelReader = image.getPixelReader();
        pixelReader.getPixels(0, 0,
                width,height,
                PixelFormat.getByteBgraInstance(),
                buffer,
                0,
                4*width);
        bytebuffer.put(buffer, 0,width * height * 4 );

        videoFrame.setData(bytebuffer);
        sender.sendVideoFrame(videoFrame);

    }
}
