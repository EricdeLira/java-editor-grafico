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

}
