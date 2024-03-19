// public class NBody {
//     public double readRadius(string str){
//         In in = new In(str);
//         while(!in.isEmpty()) {
//             int num = in.readInt();
//             double radius = in.readDouble();
//         }
//         return radius;
//     }
// }
public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName); 
        int numPlanets = in.readInt(); 
        double radius = in.readDouble(); 
        return radius;
    }

    public static Planet[] readPlanets(String fileName){//1.string should be upper case;2.readPlanets should return an array of bodys,not double
        In in = new In(fileName);
        int numPlanets = in.readInt();
        double radius = in.readDouble();
        Planet[] Bodys = new Planet[numPlanets];// clarify an array of class variable should be initialized first 
        for (int i = 0;i<numPlanets;i++){
            //public Planet(double xP, double yP, double xV, double yV, double m, String img){
            double xp = in.readDouble();
            double yp = in.readDouble();
            double xv = in.readDouble();
            double yv = in.readDouble();
            double m  = in.readDouble();
            String img = in.readString();
            Bodys[i] = new Planet(xp,yp,xv,yv,m,img);
        }
        return Bodys;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] Planets = readPlanets(filename);
        double R = readRadius(filename);
        String imageToDraw = "images/starfield.jpg";
        StdDraw.setScale(-R,R);
        StdDraw.clear();
        StdDraw.picture(0, 0, imageToDraw, 2*R, 2*R);
        for (int i = 0 ;i<Planets.length;i++){
            Planets[i].draw();
        }
        StdDraw.show();

        StdDraw.enableDoubleBuffering();

        double time  = 0;
        while (time<=T){
            double xForces[] = new double[Planets.length];
            double yForces[] = new double[Planets.length];
            for(int j = 0;j<Planets.length;j++){
                xForces[j] = Planets[j].calcNetForceExertedByX(Planets);
                yForces[j] = Planets[j].calcNetForceExertedByY(Planets);
            }
            for(int j = 0;j<Planets.length;j++){
                Planets[j].update(dt, xForces[j], yForces[j]);
            }

            StdDraw.picture(0, 0, imageToDraw, 2*R, 2*R);
            for (int j = 0 ;j<Planets.length;j++){
                Planets[j].draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        StdOut.printf("%d\n", Planets.length);
        StdOut.printf("%.2e\n", R);
        for (int i = 0; i < Planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
                        Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);   
        }
    }
}
