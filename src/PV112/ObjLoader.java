package PV112;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class ObjLoader {

    private String path;
    private List<Integer> o = new ArrayList<Integer>();
    private List<float[]> vertices;
    private List<float[]> normals;
    private List<float[]> textures;
    private List<int[]> vertexIndices;
    private List<int[]> normalIndices;
    private List<int[]> textureIndices;
    private BufferedReader inReader;
    private BufferedReader mtlReader;
    private Texture crane;
    private GL2 gl;
    private Map<String, Texture> textureFiles = new HashMap<String, Texture>();
    private final GLAutoDrawable glad;

    public ObjLoader(String path, GLAutoDrawable glad) {
        this.path = path;
        this.glad = glad;
        this.gl = glad.getGL().getGL2();
    }

    public void load() {
        /**
         * Mesh containing the loaded object
         */
        vertices = new ArrayList<float[]>();
        normals = new ArrayList<float[]>();
        textures = new ArrayList<float[]>();
        vertexIndices = new ArrayList<int[]>();
        normalIndices = new ArrayList<int[]>();
        textureIndices = new ArrayList<int[]>();

        String line;
        try {
            inReader = new BufferedReader(new InputStreamReader(
                    this.getClass().getResource(path).openStream()));

            mtlReader = new BufferedReader(new InputStreamReader(
                    this.getClass().getResource(path + ".mtl").openStream()));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        
        URL url = getClass().getResource("/resources/machinery_crane02_beams_col.jpg");
        try {
            TextureData data = TextureIO.newTextureData(glad.getGLProfile(), url, true, "jpg");
            crane = new Texture(gl, data);            
        } catch (IOException e) {
            System.err.println("Loading textures failed." + e);
        }
        
        try {
            while ((line = inReader.readLine()) != null) {

                if (line.startsWith("v ")) {

                    String[] vertStr = line.split("\\s+");
                    float[] vertex = new float[3];

                    vertex[0] = Float.parseFloat(vertStr[1]);
                    vertex[1] = Float.parseFloat(vertStr[2]);
                    vertex[2] = Float.parseFloat(vertStr[3]);
                    vertices.add(vertex);

                } else if (line.startsWith("vn ")) {

                    String[] normStr = line.split("\\s+");
                    float[] normal = new float[3];

                    normal[0] = Float.parseFloat(normStr[1]);
                    normal[1] = Float.parseFloat(normStr[2]);
                    normal[2] = Float.parseFloat(normStr[3]);
                    normals.add(normal);

                } else if (line.startsWith("vt ")) {

                    String[] textStr = line.split("\\s+");
                    float[] texture = new float[2];

                    texture[0] = Float.parseFloat(textStr[1]);
                    texture[1] = Float.parseFloat(textStr[2]);
                    textures.add(texture);

                } else if (line.startsWith("f ")) {

                    String[] faceStr = line.split("\\s+");
                    int[] faceVert = new int[3];

                    faceVert[0] = Integer.parseInt(faceStr[1].split("/")[0]) - 1;
                    faceVert[1] = Integer.parseInt(faceStr[2].split("/")[0]) - 1;
                    faceVert[2] = Integer.parseInt(faceStr[3].split("/")[0]) - 1;
                    vertexIndices.add(faceVert);

                    int[] faceText = new int[3];

                    faceText[0] = Integer.parseInt(faceStr[1].split("/")[1]) - 1;
                    faceText[1] = Integer.parseInt(faceStr[2].split("/")[1]) - 1;
                    faceText[2] = Integer.parseInt(faceStr[3].split("/")[1]) - 1;
                    textureIndices.add(faceText);

                    int[] faceNorm = new int[3];

                    faceNorm[0] = Integer.parseInt(faceStr[1].split("/")[2]) - 1;
                    faceNorm[1] = Integer.parseInt(faceStr[2].split("/")[2]) - 1;
                    faceNorm[2] = Integer.parseInt(faceStr[3].split("/")[2]) - 1;
                    normalIndices.add(faceNorm);

                } else if (line.startsWith("o ")) {
                    o.add(vertexIndices.size());
                }
            }
        } catch (IOException ex) {
            System.out.println("Unable to load " + path + " file: " + ex.getMessage());
        }
    }
    
    public void draw1(){  
        crane.bind(gl);
        gl.glBegin(GL2.GL_TRIANGLES);      
        for (int i = 0; i < o.get(1); i++) {
            int[] triangle = vertexIndices.get(i);
            int[] triangleNormal = normalIndices.get(i);
            int[] texture = textureIndices.get(i);
                    
            float[] v1 = vertices.get(triangle[0]);
            float[] v2 = vertices.get(triangle[1]);
            float[] v3 = vertices.get(triangle[2]);
            float[] n1 = normals.get(triangleNormal[0]);
            float[] n2 = normals.get(triangleNormal[1]);
            float[] n3 = normals.get(triangleNormal[2]);
            float[] t1 = textures.get(texture[0]);
            float[] t2 = textures.get(texture[1]);
            float[] t3 = textures.get(texture[2]);           
            
            gl.glNormal3fv(n1, 0);
            gl.glTexCoord2f(t1[0], t1[1]);
            gl.glVertex3fv(v1, 0);
            gl.glNormal3fv(n2, 0);
            gl.glTexCoord2f(t2[0], t2[1]);
            gl.glVertex3fv(v2, 0);
            gl.glNormal3fv(n3, 0);
            gl.glTexCoord2f(t3[0], t3[1]);
            gl.glVertex3fv(v3, 0);
        }
        gl.glEnd();
    }
    
    public void draw2(){
        crane.bind(gl);
        gl.glBegin(GL2.GL_TRIANGLES);      
        for (int i = o.get(1); i < o.get(2); i++) {
            int[] triangle = vertexIndices.get(i);
            int[] triangleNormal = normalIndices.get(i);
            int[] texture = textureIndices.get(i);
                    
            float[] v1 = vertices.get(triangle[0]);
            float[] v2 = vertices.get(triangle[1]);
            float[] v3 = vertices.get(triangle[2]);
            float[] n1 = normals.get(triangleNormal[0]);
            float[] n2 = normals.get(triangleNormal[1]);
            float[] n3 = normals.get(triangleNormal[2]);
            float[] t1 = textures.get(texture[0]);
            float[] t2 = textures.get(texture[1]);
            float[] t3 = textures.get(texture[2]);           
            
            gl.glNormal3fv(n1, 0);
            gl.glTexCoord2f(t1[0], t1[1]);
            gl.glVertex3fv(v1, 0);
            gl.glNormal3fv(n2, 0);
            gl.glTexCoord2f(t2[0], t2[1]);
            gl.glVertex3fv(v2, 0);
            gl.glNormal3fv(n3, 0);
            gl.glTexCoord2f(t3[0], t3[1]);
            gl.glVertex3fv(v3, 0);
        }
        gl.glEnd();
    }
    
    public void draw3(){
        crane.bind(gl);
        gl.glBegin(GL2.GL_TRIANGLES);      
        for (int i = o.get(2); i < vertexIndices.size(); i++) {
            int[] triangle = vertexIndices.get(i);
            int[] triangleNormal = normalIndices.get(i);
            int[] texture = textureIndices.get(i);
                    
            float[] v1 = vertices.get(triangle[0]);
            float[] v2 = vertices.get(triangle[1]);
            float[] v3 = vertices.get(triangle[2]);
            float[] n1 = normals.get(triangleNormal[0]);
            float[] n2 = normals.get(triangleNormal[1]);
            float[] n3 = normals.get(triangleNormal[2]);
            float[] t1 = textures.get(texture[0]);
            float[] t2 = textures.get(texture[1]);
            float[] t3 = textures.get(texture[2]);           
            
            gl.glNormal3fv(n1, 0);
            gl.glTexCoord2f(t1[0], t1[1]);
            gl.glVertex3fv(v1, 0);
            gl.glNormal3fv(n2, 0);
            gl.glTexCoord2f(t2[0], t2[1]);
            gl.glVertex3fv(v2, 0);
            gl.glNormal3fv(n3, 0);
            gl.glTexCoord2f(t3[0], t3[1]);
            gl.glVertex3fv(v3, 0);
        }
        gl.glEnd();
        
    }
    public List<float[]> getVertices(){
        return vertices;
    }

    public List<float[]> getNormals() {
        return normals;
    }

    public List<int[]> getVertexIndices() {
        return vertexIndices;
    }

    public List<int[]> getNormalIndices() {
        return normalIndices;
    }

    public List<int[]> getTextureIndices() {
        return textureIndices;
    }

    public List<float[]> getTextures() {
        return textures;
    }
}