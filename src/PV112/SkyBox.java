package PV112;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.net.URL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MiškoHu
 */
public class SkyBox {

    private Texture floor;
    private Texture sky;
    private GL2 gl;

    public SkyBox(GLAutoDrawable glad) {
        gl = glad.getGL().getGL2();
        URL url = getClass().getResource("/resources/concrete.jpg");
        try {
            TextureData data = TextureIO.newTextureData(glad.getGLProfile(), url, true, "jpg");
            floor = new Texture(gl, data);
            url = getClass().getResource("/resources/skybox2.jpg");
            data = TextureIO.newTextureData(glad.getGLProfile(), url, true, "jpg");
            sky = new Texture(gl, data);
        } catch (IOException e) {
            System.err.println("Loading textures failed." + e);
        }
    }

    public void drawFloor() {
        floor.bind(gl);
        
        gl.glBegin(GL2.GL_QUADS);        
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                gl.glNormal3f(0, 1, 0);
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(50f * i - 500, 0, 50f * (j + 1) - 500);
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(50f * (i + 1) - 500, 0, 50f * (j + 1) - 500);
                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(50f * (i + 1) - 500, 0, 50f * j - 500);
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(50f * i - 500, 0, 50f * j - 500);
            }
        }
        gl.glEnd();
        
    }

    public void drawSky(float d) {
        gl.glDisable(GL2.GL_LIGHTING);
        sky.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        //Left
        gl.glTexCoord2f(0, 0.6666f);
        gl.glVertex3f(-d, -d-1+(d/3), d+1);
        gl.glTexCoord2f(0.25f, 0.6666f);
        gl.glVertex3f(-d, -d-1+(d/3), -d-1);
        gl.glTexCoord2f(0.25f, 0.334f);
        gl.glVertex3f(-d, d+1+(d/3), -d-1);
        gl.glTexCoord2f(0, 0.334f);
        gl.glVertex3f(-d, d+1+(d/3), d+1);        
        //Front        
        gl.glTexCoord2f(0.25f, 0.6666f);
        gl.glVertex3f(-d-1, -d-1+(d/3), -d);
        gl.glTexCoord2f(0.5f, 0.6666f);
        gl.glVertex3f(d+1, -d-1+(d/3), -d);
        gl.glTexCoord2f(0.5f, 0.334f);
        gl.glVertex3f(d+1, d+1+(d/3), -d);
        gl.glTexCoord2f(0.25f, 0.334f);
        gl.glVertex3f(-d-1, d+1+(d/3), -d);
        //Right        
        gl.glTexCoord2f(0.5f, 0.6666f);
        gl.glVertex3f(d, -d-1+(d/3), -d-1);
        gl.glTexCoord2f(0.75f, 0.6666f);
        gl.glVertex3f(d, -d-1+(d/3), d+1);
        gl.glTexCoord2f(0.75f, 0.334f);
        gl.glVertex3f(d, d+1+(d/3), d+1);
        gl.glTexCoord2f(0.5f, 0.334f);
        gl.glVertex3f(d, d+1+(d/3), -d-1);
        //Back      
        gl.glTexCoord2f(0.75f, 0.6666f);
        gl.glVertex3f(d+1, -d-1+(d/3), d);
        gl.glTexCoord2f(1f, 0.6666f);
        gl.glVertex3f(-d-1, -d-1+(d/3), d);
        gl.glTexCoord2f(1f, 0.334f);
        gl.glVertex3f(-d-1, d+1+(d/3), d);
        gl.glTexCoord2f(0.75f, 0.334f);
        gl.glVertex3f(d+1, d+1+(d/3), d);
        //Top        
        gl.glTexCoord2f(0.251f, 0.334f);
        gl.glVertex3f(-d-1, d+(d/3), -d-1);
        gl.glTexCoord2f(0.499f, 0.334f);
        gl.glVertex3f(d+1, d+(d/3), -d-1);
        gl.glTexCoord2f(0.499f, 0);
        gl.glVertex3f(d+1, d+(d/3), d+1);
        gl.glTexCoord2f(0.251f, 0);
        gl.glVertex3f(-d-1, d+(d/3), d+1);        
        gl.glEnd();
        gl.glEnable(GL2.GL_LIGHTING);
    }
}
