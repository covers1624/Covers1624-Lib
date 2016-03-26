package covers1624.lib.client.render;

import covers1624.lib.api.client.render.EnumDrawMode;
import covers1624.lib.math.Vector3;
import covers1624.lib.util.LogHelper;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;

/**
 * Created by covers1624 on 3/25/2016.
 */
//TODO Ability for push and pop to be called more than once.
public class RenderStateManager {
    private static final VertexBuffer tempBuffer = new VertexBuffer(2097152);
    public final VertexBuffer buffer;

    private Vector3 offset;

    public RenderStateManager(VertexBuffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Pushes the current offset to a buffer.
     * WILL REMOVE PREVIOUS PUSH!
     */
    public void pushOffset() {
        if (offset != null) {
            LogHelper.bigWarn("Attempting to push VertexBuffer offset when the previous offset hasn't been popped!");
        }
        offset = getOffset();
    }

    public void popOffset() {
        setBufferOffset(offset);
        offset = null;
    }

    /**
     * Gets the current offset stored in the VertexBuffer.
     *
     * @return
     */
    public Vector3 getOffset() {
        return new Vector3(buffer.xOffset, buffer.yOffset, buffer.zOffset);
    }

    /**
     * Sets the current VertexBuffer offset.
     */
    public void setBufferOffset(Vector3 offset) {
        buffer.setTranslation(offset.x, offset.y, offset.z);
    }

    /**
     * Adds to the buffer offset.
     *
     * @param offset Offset to add.
     */
    public void addBufferOffset(Vector3 offset) {
        Vector3 curOffset = getOffset();
        curOffset.add(offset);
        setBufferOffset(curOffset);
    }

    /**
     * Removes from the buffer offset.
     *
     * @param offset Offset to subtract.
     */
    public void removeBufferOffset(Vector3 offset) {
        Vector3 curOffset = getOffset();
        curOffset.subtract(offset);
        setBufferOffset(curOffset);
    }

    public void setVertexFormat(EnumDrawMode drawMode, VertexFormat vertexFormat) {

    }

    public VertexBuffer.State createVertexState(EnumDrawMode drawMode, VertexFormat vertexFormat){
        if (tempBuffer.isDrawing){
            LogHelper.error("TempBuffer is already drawing!");
            tempBuffer.finishDrawing();
        }
        tempBuffer.reset();
        tempBuffer.begin(drawMode.getGlCode(), vertexFormat);
        VertexBuffer.State state = tempBuffer.getVertexState();
        tempBuffer.finishDrawing();
        return state;

    }


}
