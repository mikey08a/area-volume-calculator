package areacalc;

public class Calculation {
    
    public static double getArea(String shape,double length,double width){
        //calculations for rectangle/triangle
        if(shape.equals("Rectangle")){
            return length * width;
        }else{
            return length * width / 2;
        }
    }
    public static double getArea(String shape,double radius){
        //calculation for circle
        return Math.PI*Math.pow(radius, 2);
    }
    
    public static double getVolume(String shape,double length,double width,
            double height){
        //calculation for pyramid or rectangular prism
        if(shape.equals("Pyramid")){
            return length * width * height / 3;
        }else{
            return length * width * height;
        }
    }
    public static double getVolume(String shape,double radius){
        //calculation for sphere
        return 4 * Math.PI * Math.pow(radius, 3) / 3;
    }
    public static double getVolume(String shape,double radius,double height){
        //calculation for cylinder
        return Math.PI * Math.pow(radius,2) * height;
    }
}
