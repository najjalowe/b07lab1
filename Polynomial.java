import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial{
    double [] nonzero;
    int [] exponent;

    public Polynomial(){
        nonzero = new double[1];
        exponent = new int[1];
        nonzero[0]=0;
        exponent[0]=0;
    }

    public Polynomial(double [] entries, int [] powers){//count non zero entries then make nonzero length
        nonzero = new double[entries.length];//at most entries will have all coefficients be non zero
        exponent = new int[powers.length];
        for(int i=0;i<entries.length;i++)
        {
            nonzero[i]=entries[i];
            exponent[i]=powers[i];
        }
    }

    public Polynomial(File f)throws IOException{
        BufferedReader input = new BufferedReader(new FileReader(f));
		String p = input.readLine();
        String[] pArray = p.split("(?=[+-])");//separate into exp and value pairs including sign
        nonzero = new double[pArray.length];
        exponent = new int[pArray.length];
        int i =0;

        for (String elem : pArray) {
            String[] elemArray = elem.split("[x]");
            nonzero[i]= Double.parseDouble(elemArray[0]);
            if(!(elem.contains("x"))){
                exponent[i]=0;// a constant
            }
            else if(elem.endsWith("x")){
                exponent[i]=1;//exponent is 1
            }
            else{
                exponent[i] = Integer.parseInt(elemArray[1]);
            }
            i++;
        }
        input.close();
    }

    public int countexp(Polynomial p, Polynomial q){//counts # distinct exponents of two polynomial
        int pLastExp = p.exponent[p.exponent.length-1];
        int qLastExp = q.exponent[q.exponent.length-1];
        int j=0;
        int k=0;
        int count =0;
        for(int i=0;i<=Math.max(pLastExp,qLastExp);i++){
            if(j<p.exponent.length){
                if(p.exponent[j]==i){
                    j++;
                    count++;
                    if(k<q.exponent.length&&q.exponent[k]==i)
                        k++;
                }
            }
            if(k<q.exponent.length&&q.exponent[k]==i){
                k++;
                count++;
            }
        }
        return count;
    }

    public Polynomial add(Polynomial p){
        int numberExp = countexp(p, this);//# of distinct exponents with p and calling object
        double inputvalues [];
        int inputpowers [];
        inputvalues = new double[numberExp];
        inputpowers = new int[numberExp];
        int pLastExp = p.exponent[p.exponent.length-1];
        int thisLastExp = this.exponent[this.exponent.length-1];
        int x=0;//track index of input arrays
        int y=0;//track index of p
        int z=0;//track index of the calling object; this
        for(int i=0; i<=Math.max(pLastExp,thisLastExp); i++){
            double sum=0;
            if(y<p.exponent.length&&p.exponent[y]==i){
                 sum = sum+p.nonzero[y];
                 y++;
                 if(z<this.exponent.length&&this.exponent[z]==i){
                    sum = sum+this.nonzero[z];
                    z++;
                 } 
            }
            else if(z<this.exponent.length&&this.exponent[z]==i){
                sum = sum+this.nonzero[z];
                z++;
            }
            if(sum!=0){
                inputpowers[x]=i;
                inputvalues[x]=sum;
                x++;
            }
        }
            Polynomial result = new Polynomial(inputvalues, inputpowers);
            return result;
    }

    public double evaluate(double x){
        double sum = 0;
        for(int i=0;i<this.nonzero.length;i++)
            {
                sum = sum+(this.nonzero[i]*(Math.pow(x,this.exponent[i])));
            }
        return sum;
    }

    public boolean hasRoot(double root){
        if (evaluate(root)==0){
            return true;
        }
        return false;
    }

    public Polynomial multiply(Polynomial p){
        Polynomial sum = new Polynomial();
        for(int i=0;i<p.exponent.length;i++){//for every element in p
            Polynomial partial = new Polynomial(this.nonzero, this.exponent);
            for(int j=0;j<this.exponent.length;j++){//for every element in calling object
                partial.exponent[j]+=p.exponent[i];
                partial.nonzero[j]*=p.nonzero[i];
            }
            sum=sum.add(partial);//keep adding partial olynomials to the total
        }
        return sum;
    }

    public void saveToFile(String fileName)throws IOException{
        File f = new File(fileName);
        f.createNewFile();
        FileWriter output = new FileWriter(fileName);
        String polynomial = new String();
        String convert = new String();
        for(int i = 0; i<this.exponent.length; i++){
            if(this.exponent[i]==0){//if constant
                convert = Double.toString(this.nonzero[0]);
            }
            else if(this.exponent[i]==1){// if exp = 1
                convert = Double.toString(this.nonzero[0]).concat("x");

            }
            else{
                convert = Double.toString(this.nonzero[0]).concat("x").concat(Integer.toString(this.exponent[i]));
            }
            polynomial = polynomial.concat(convert);
        }
        output.write(polynomial);
        output.close();
    }
}