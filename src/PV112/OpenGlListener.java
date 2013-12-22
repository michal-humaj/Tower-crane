package PV112;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class OpenGlListener implements GLEventListener {

    GLU glu = new GLU();
    GLUT glut = new GLUT();
    private ObjLoader objLoader;
    private SkyBox skyBox;
    private Texture magnetTexture;
    public boolean magnetOn = false;
    public Box carriedBox = null;
    public List<Box> boxes = new ArrayList<Box>();
    public float[] cameraPos = {20, 20, 0};
    public double[] cameraLookVector = {0, -1, -1};
    public float craneR = 0;
    public float craneF = 0;
    public float craneU = 5;
    public int view = 0;
    public double[] magnetPos = new double[3];
    public float[] camMatrix = new float[16];
    private AnimationThread at = new AnimationThread();

    public OpenGlListener(){
        Thread thread = new Thread(at);
        thread.start();
    }
    
    
    @Override
    // metoda volana pri vytvoreni okna OpenGL
    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        objLoader = new ObjLoader("/resources/craneModel.obj", glad);
        objLoader.load();

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        //gl.glEnable(GL2.GL_CULL_FACE);
        //gl.glCullFace(GL2.GL_BACK);

        skyBox = new SkyBox(glad);
        boxes.add(new Box(glad, 7, 0, 0));
        boxes.add(new Box(glad, 13, 0, 0));
        boxes.add(new Box(glad, 16, 0, 0));
        boxes.add(new Box(glad, -22, 0, 8));
        boxes.add(new Box(glad, -3, 0, 14));
        boxes.add(new Box(glad, 4, 0, -13));
        boxes.add(new Box(glad, -9, 0, 1));
        boxes.add(new Box(glad, 4, 0, 7));
        boxes.add(new Box(glad, 15, 0, 8));
        boxes.add(new Box(glad, -2, 0, -18));

        URL url = getClass().getResource("/resources/box.jpg");
        try {
            TextureData data = TextureIO.newTextureData(glad.getGLProfile(), url, true, "jpg");
            magnetTexture = new Texture(gl, data);
        } catch (IOException e) {
            System.err.println("Loading textures failed." + e);
        }
    }

    @Override
    // metoda volana pri zatvoreni okna OpenGL
    public void dispose(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
    }

    @Override
    // metoda volana pri kazdom prekresleni obrazovky     
    public void display(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        double sin = Math.sin(craneR / (180f / Math.PI));
        double cos = Math.cos(craneR / (180f / Math.PI));
        if (view == 0) {
            glu.gluLookAt(sin * (-17.1 + craneF), 20f, cos * (-17.1 + craneF),
                    sin * (-17.1 + craneF), 0f, cos * (-17.1 + craneF),
                    -sin, 0, -cos);            
        } else if (view == 1) {
            glu.gluLookAt(cameraPos[0], cameraPos[1], cameraPos[2],
                    cameraPos[0] + cameraLookVector[0], cameraPos[1] + cameraLookVector[1], cameraPos[2] + cameraLookVector[2],
                    0, 1, 0);
        } else {
            glu.gluLookAt(sin * -2, 20, cos * -2,
                     (sin * -2) + cameraLookVector[0], 20 + cameraLookVector[1], (cos * -2) + cameraLookVector[2],
                    0, 1, 0);
        }
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, camMatrix, 0);  
        gl.glRotatef(craneR, 0, 1, 0);
        float[] light1pos = {2, 20, 0 , 1};        
        float[] diffuse = {1f, 1f, 1f}; 
        float[] specular = {1f, 1f, 1f};  
        float[] ambient = {0f, 0f, 0f};
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, specular, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light1pos, 0);        
        gl.glTranslatef(0, 0, craneF);        
        float[] light0pos = {0, 20, -17, 1};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0pos, 0);
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_SPOT_CUTOFF, 10);
        float[] dir = {0, -1, 0};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPOT_DIRECTION, dir, 0);
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_SPOT_EXPONENT, 0);
        gl.glLoadMatrixf(camMatrix, 0);       
         
        skyBox.drawFloor();
        skyBox.drawSky(500);
        objLoader.draw1();
        gl.glRotatef(craneR, 0, 1, 0);
        objLoader.draw2();
        gl.glTranslatef(0, 0, craneF);       
        objLoader.draw3();
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(0, craneU, -17);
        gl.glVertex3f(0, 20.5f, -17);
        gl.glEnd();
        gl.glTranslatef(0, craneU, -17);        
        gl.glRotatef(90, 1, 0, 0);
        magnetTexture.bind(gl);
        glut.glutSolidCylinder(0.5f, 0.25f, 30, 30);   
        
        magnetPos[0] = sin * (-17 + craneF);
        magnetPos[1] = craneU;
        magnetPos[2] = cos * (-17 + craneF);
        
        if (magnetOn) {            
            if (carriedBox == null) {                
                for (int i = 0; i < boxes.size(); i++) {                    
                    double[] substract = {magnetPos[0] - boxes.get(i).pos[0],
                        magnetPos[1] - boxes.get(i).pos[1] + 2,
                        magnetPos[2] - boxes.get(i).pos[2]};
                    double dist = Math.sqrt(Math.pow(substract[0], 2) + 
                            Math.pow(substract[1], 2) +
                            Math.pow(substract[2], 2));                    
                    if (dist < 4.4f) {
                        carriedBox = boxes.get(i);
                    }
                }
            } else {
                carriedBox.drawBoxCrane(1);                
            }
        }
        for (int i = 0; i < boxes.size(); i++) {
            gl.glLoadMatrixf(camMatrix, 0);
            if (boxes.get(i) == carriedBox) {
                continue;
            }
            boxes.get(i).drawBox(1);            
        }        
    }

    @Override
    // metoda volana pri zmene velkosti okna
    public void reshape(GLAutoDrawable glad, int x, int y, int width, int height) {
        GL2 gl = glad.getGL().getGL2();
        gl.glViewport(x, y, width, height);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, (double) width / height, 0.3f, 1500);
    }
}
