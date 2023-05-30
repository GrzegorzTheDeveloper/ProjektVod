import java.util.ArrayList;

public abstract class ListyFilmow extends ArrayList<Movie> {

    Klient klient;

    public ListyFilmow(Klient klient) {
        this.klient = klient;
    }
    public String toString(){

        Cennik cennik = Cennik.pobierzCennik();

        String lista =klient.getName() + ":";

        if(this.isEmpty())
            lista += " -- pusto";
        else
            lista += "\n";



        for(Movie element : this){

            int cena = cennik.pobierzCene(element.getGenre(), element.getName(), klient, element.getDevNum());

            lista += element.getName() + ", typ: " +
                    element.getGenre() + " ile: " + element.getDevNum() + " urzadzenia,"
                    + (cena == -1 ? " ceny brak": " cena: " + cena) + " \n";
        }
        return lista;
    }

    public Klient getKlient() {
        return klient;
    }
}
