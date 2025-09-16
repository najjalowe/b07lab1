public class Polynomial{
    double [] coefficient;

    public Polynomial(){
        coefficient = new double[1];
        coefficient[0] = 0;
    }

    public Polynomial(double [] entries){
        coefficient = new double[entries.length];
        for(int i=0;i<entries.length;i++)
        {
            coefficient[i]=entries[i];
        }
    }

    public Polynomial add(Polynomial p){
        if(p.coefficient.length>(this.coefficient.length)){//if p is longer than calling object
            double [] input = new double[p.coefficient.length];
            for(int i=0;i<this.coefficient.length;i++)//add coefficients up to shorter length then put into input
            {
                input[i]=p.coefficient[i]+this.coefficient[i];
            }
            for(int i=this.coefficient.length;i<p.coefficient.length;i++)//get remaining coefficients from longer array then put into input
            {
                input[i]=p.coefficient[i];
            }
            Polynomial result = new Polynomial(input);
            return result;
        }
        else{//if calling object is longer than p
            double [] input = new double[this.coefficient.length];
            for(int i=0;i<p.coefficient.length;i++)
            {
                input[i]=p.coefficient[i]+this.coefficient[i];
            }
            for(int i=p.coefficient.length;i<this.coefficient.length;i++)
            {
                input[i]=this.coefficient[i];
            }
            Polynomial result = new Polynomial(input);
            return result;
        }
    }

    public double evaluate(double x){
        double sum = 0;
        for(int i=0;i<this.coefficient.length;i++)
            {
                sum = sum+(this.coefficient[i]*(Math.pow(x,i)));
            }
        return sum;
    }

    public boolean hasRoot(double root){
        if (evaluate(root)==0){
            return true;
        }
        return false;
    }
}