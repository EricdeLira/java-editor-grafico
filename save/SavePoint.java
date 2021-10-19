package save;

public class SavePoint {
    
    String nome;
    double coord[];
    int cor[];
    int esp;

    public SavePoint(String nome, double coord[], int cor[], int esp){
        this.nome = nome;
        this.coord = coord;
        this.cor = cor;
        this.esp = esp;
    }

    public String getNome(){
        return this.nome;
    }

    public double[] getCoord(){
        return this.coord;
    }

    public int[] getCor(){
        return this.cor;
    }

    public int getEsp(){
        return this.esp;
    }
}
