/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PV112;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.net.URL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author MiškoHu
 */
public class Box {
    
    private Texture box;
    private GL2 gl;
    public float[] pos = new float[3];
    
    public Box(GLAutoDrawable glad, float x, float y, float z){
        gl = glad.getGL().getGL2();
        URL url = getClass().getResource("/resources/steelBox.jpg");
        try {
            TextureData data = TextureIO.newTextureData(glad.getGLProfile(), url, true, "jpg");
            box = new Texture(gl, data);            
        } catch (IOException e) {
            System.err.println("Loading textures failed." + e);
        }
        pos[0] = x;
        pos[1] = y;
        pos[2] = z;
    }
    
    public void drawBox(float d){        
        box.bind(gl);        
        gl.glTranslatef(pos[0], pos[1], pos[2]);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 2*d, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(d, 2*d, -d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 2*d, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-d, 2*d, d);
        
        gl.glNormal3f(1, 0, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 2*d, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(d, 2*d, -d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(d, 0, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(d, 0, d);
        
        gl.glNormal3f(0, 0, 1);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 2*d, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-d, 2*d, d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 0, d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(d, 0, d);

        gl.glNormal3f(-1, 0, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-d, 2*d, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-d, 2*d, -d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 0, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-d, 0, d);
        
        gl.glNormal3f(0, 0, -1);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 2*d, -d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-d, 2*d, -d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 0, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(d, 0, -d);
        
        gl.glNormal3f(0, -1, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 0, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-d, 0, d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 0, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(d, 0, -d);
	gl.glEnd();
    }
    public void drawBoxCrane(float d){
        box.bind(gl);       
        gl.glTranslatef(0, -1, 1.25f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0, 1, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 2*d, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(d, 2*d, -d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 2*d, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-d, 2*d, d);
        
        gl.glNormal3f(1, 0, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 2*d, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(d, 2*d, -d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(d, 0, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(d, 0, d);
        
        gl.glNormal3f(0, 0, 1);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 2*d, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-d, 2*d, d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 0, d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(d, 0, d);

        gl.glNormal3f(-1, 0, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(-d, 2*d, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-d, 2*d, -d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 0, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-d, 0, d);
        
        gl.glNormal3f(0, 0, -1);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 2*d, -d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-d, 2*d, -d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 0, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(d, 0, -d);
        
        gl.glNormal3f(0, -1, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(d, 0, d);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(-d, 0, d);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-d, 0, -d);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(d, 0, -d);
	gl.glEnd();
    }
}
