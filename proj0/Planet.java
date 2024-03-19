/**
 * creating a basic version of the body class
 */

 public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass  = m;
        imgFileName = img;
    }

    public Planet(Planet b) {
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(Planet supp){ 
        double dx = this.xxPos-supp.xxPos;// notice that any variable and method should be given a type
        double dy = this.yyPos-supp.yyPos;
        double distance = Math.sqrt(dx*dx+dy*dy);
        return distance;

    }

    public double calcForceExertedBy(Planet b){
        double F = G*this.mass*b.mass/(this.calcDistance(b)*this.calcDistance(b));
        return F;
    }

    public double calcForceExertedByX(Planet b){
        double Fx = this.calcForceExertedBy(b)*(b.xxPos-this.xxPos)/this.calcDistance(b);
        return Fx;
    }

    public double calcForceExertedByY(Planet b){
        double Fy = this.calcForceExertedBy(b)*(b.yyPos-this.yyPos)/this.calcDistance(b);
        return Fy;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets){
        double netF = 0;
        for (int i = 0;i<allPlanets.length;i++){
                 if (this.equals(allPlanets[i]) == true){
                    continue;
                 }
                 netF+=this.calcForceExertedByX(allPlanets[i]);
        }
        return netF;
        
    }

    public double calcNetForceExertedByY(Planet[] allPlanets){
        double netF = 0;
        for (int i = 0;i<allPlanets.length;i++){
                 if (this.equals(allPlanets[i]) == true){//in java ,we use true instead of True（lower case)
                    continue;
                 }
                 netF+=this.calcForceExertedByY(allPlanets[i]);
        }
        return netF;
        
    }

    public void update (double dt,double fX,double fY){
        double ax = fX / this.mass;
        double ay = fY / this.mass;
        double accx = dt*ax;
        double accy = dt*ay;
        this.xxVel+=accx;
        this.yyVel+=accy;
        this.xxPos+=this.xxVel*dt;
        this.yyPos+=this.yyVel*dt;
    }

    public void draw(){
        String imageToDraw = "images/"+this.imgFileName;
        StdDraw.picture(this.xxPos,this.yyPos,imageToDraw);
    }

    
}

