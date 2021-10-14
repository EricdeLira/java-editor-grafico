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

}
