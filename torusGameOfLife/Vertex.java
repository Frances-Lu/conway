package torusGameOfLife;

public class Vertex {

    public double x; //x-coord
    public double y; //y-coord
    public double z; //z-coord

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void printVertex() {
        System.out.println("x: " + this.x);
        System.out.println("y: " + this.y);
        System.out.println("z: " + this.z);
    }

    /**
     * Subtracts v2 from v1
     * That is, v1 - v2.
     */
    public static Vertex subtract(Vertex v1, Vertex v2) {
        double x = v1.x - v2.x;
        double y = v1.y - v2.y;
        double z = v1.z - v2.z;

        return new Vertex(x,y,z);
    }

    /** This method provides the cross product of two vectors that start from
     * the reference vector.
     */
    public static Vertex crossProduct(Vertex referenceV, Vertex v1, Vertex v2) {
        Vertex v1New = subtract(v1, referenceV);
        Vertex v2New = subtract(v2, referenceV);

        double x = (v1New.y * v2New.z) - (v2New.y * v1New.z);
        double y = (v1New.x * v2New.z) - (v2New.x * v1New.z);
        double z = (v1New.x * v2New.y) - (v2New.x * v1New.y);

        return new Vertex(x,y,z);

    }

}