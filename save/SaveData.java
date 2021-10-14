package save;

import java.util.ArrayList;

public class SaveData {
    
    ArrayList<SavePoint> pontos = new ArrayList<SavePoint>();
    ArrayList<SaveLine> retas = new ArrayList<SaveLine>();
    ArrayList<SaveCircle> circulos = new ArrayList<SaveCircle>();

    public SaveData(){

    }

    public void addPoint(String nome, double coord[], int cor[], int esp){
        SavePoint pt = new SavePoint(nome, coord, cor, esp);
        pontos.add(pt);
    }

    public void addLine(String nome, double coord[][], int cor[], int esp){
        SaveLine li = new SaveLine(nome, coord, cor, esp);
        retas.add(li);
    }

    public void addCircle(String nome, double coord[], double raio, int cor[], int esp){
        SaveCircle cr = new SaveCircle(nome, coord, raio, cor, esp);
        circulos.add(cr);
    }

}
