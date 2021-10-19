package save;

public class SaveCircle {
    
    String nome;
    double centro[];
    double raio;
    int cor[];
    int esp;

    public SaveCircle(String nome, double coord[], double raio, int cor[], int esp){
        this.nome = nome;
        this.centro = coord;
        this.raio = raio;
        this.cor = cor;
        this.esp = esp;
    }

    public String getNome(){
        return this.nome;
    }

    public double[] getCoord(){
        return this.centro;
    }

    public double getRaio(){
        return raio;
    }

    public int[] getCor(){
        return this.cor;
    }

    public int getEsp(){
        return this.esp;
    }

}
