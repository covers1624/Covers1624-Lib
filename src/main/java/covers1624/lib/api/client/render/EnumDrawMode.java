package covers1624.lib.api.client.render;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by covers1624 on 3/26/2016.
 * Way to force certain draw modes.
 */
public enum EnumDrawMode {

    POINTS(GL_POINTS),
    LINES(GL_LINES),
    LINE_LOOP(GL_LINE_LOOP),
    LINE_STRIP(GL_LINE_STRIP),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL_TRIANGLE_FAN),
    QUADS(GL_QUADS),
    QUAD_STRIP(GL_QUAD_STRIP),
    POLYGON(GL_POLYGON);

    private int glCode;

    EnumDrawMode(int glCode) {
        this.glCode = glCode;
    }

    public int getGlCode(){
        return glCode;
    }

}
