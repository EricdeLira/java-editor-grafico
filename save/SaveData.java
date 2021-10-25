package save;

import java.util.ArrayList;

public class SaveData {
    
    ArrayList<SavePoint> pontos = new ArrayList<SavePoint>();
    ArrayList<SaveLine> retas = new ArrayList<SaveLine>();
    ArrayList<SaveCircle> circulos = new ArrayList<SaveCircle>();
    ArrayList<SaveLine> triangulos = new ArrayList<SaveLine>();
    ArrayList<SaveLine> retangulos = new ArrayList<SaveLine>();
    ArrayList<SaveLine> poligonos = new ArrayList<SaveLine>();

    public SaveData(){

    }

    public void addPoint(String nome, double coord[], int cor[], int esp){
        boolean exists = false;
        for(SavePoint p : pontos){
            if(p.getNome().equals(nome)){
                p.cor = cor;
                p.esp = esp;
                p.coord = coord;
                exists = true;
            }
        }
        if(!exists){
            SavePoint pt = new SavePoint(nome, coord, cor, esp);
            pontos.add(pt);
        }
    }
    public void addLine(String nome, double coord[][], int cor[], int esp){
        boolean exists = false;
        for(SaveLine l: retas){
            if(l.getNome().equals(nome)){
                l.cor = cor;
                l.esp = esp;
                l.pontos = coord;
                exists = true;
            }
        }
        if(!exists){
            SaveLine li = new SaveLine(nome, coord, cor, esp);
            retas.add(li);
        }
    }

    public void addCircle(String nome, double coord[], double raio, int cor[], int esp){
        boolean exists = false;
        for(SaveCircle c: circulos){
            if(c.getNome().equals(nome)){
                c.cor = cor;
                c.esp = esp;
                c.raio = raio;
                c.centro = coord;
                exists = true;
            }
        }
        if(!exists){
            SaveCircle cr = new SaveCircle(nome, coord, raio, cor, esp);
            circulos.add(cr);
        }
    }

    public void addTriangle(String nome, double coord[][], int cor[], int esp){
        boolean exists = false;
        for(SaveLine t: triangulos){
            if(t.getNome().equals(nome)){
                t.cor = cor;
                t.esp = esp;
                t.pontos = coord;
                exists = true;
            }
        }
        if(!exists){
            SaveLine tr = new SaveLine(nome, coord, cor, esp);
            triangulos.add(tr);
        }
    }

    public void addRectangle(String nome, double coord[][], int cor[], int esp){
        boolean exists = false;
        for(SaveLine r: retangulos){
            if(r.getNome().equals(nome)){
                r.cor = cor;
                r.pontos = coord;
                r.esp = esp;
                exists = true;
            }
        }
        if(!exists){
            SaveLine rec = new SaveLine(nome, coord, cor, esp);
            retangulos.add(rec);
        }
    }

    public void addPolygon(String nome, double coord[][], int cor[], int esp){
        boolean exists = false;
        for(SaveLine p: poligonos){
            if(p.getNome().equals(nome)){
                p.cor = cor;
                p.pontos = coord;
                p.esp = esp;
                exists = true;
            }
        }
        if(!exists){
            SaveLine poly = new SaveLine(nome, coord, cor, esp);
            poligonos.add(poly);
            
        }
    }

    public void clearMemory(){
        pontos.clear();
        retas.clear();
        circulos.clear();
        triangulos.clear();
        retangulos.clear();
        poligonos.clear();
    }
}
