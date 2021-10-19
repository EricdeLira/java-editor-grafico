package save;

public class SaveLine {
    
    String nome;
    double pontos[][];
    int cor[];
    int esp;

    public SaveLine(String nome, double coord[][], int cor[], int esp){
        this.nome = nome;
        this.pontos = coord;
        this.cor = cor;
        this.esp = esp;
    }

    public String getNome(){
        return this.nome;
    }

    public double[][] getCoord(){
        return this.pontos;
    }

    public int[] getCor(){
        return this.cor;
    }

    public int getEsp(){
        return this.esp;
    }

}
