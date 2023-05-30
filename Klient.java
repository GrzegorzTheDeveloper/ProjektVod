import java.util.ArrayList;

public class Klient {

    private String name;
    private float wallet;
    private Abonament membership;

    private ListaZyczen listaZyczen = new ListaZyczen(this);
    private Koszyk koszykKinoman;

    public Abonament isMembership() {
        return membership;
    }



    public Klient(String name, int wallet, Abonament membership) {
        this.name = name;
        this.wallet = wallet;
        this.membership = membership;
    }

    public void dodaj(Movie movie){
        listaZyczen.add(movie);
    }

    public ListaZyczen pobierzListeZyczen(){
        return listaZyczen;
    }

    public void przepakuj(Koszyk koszyk){

        Cennik cennik = Cennik.pobierzCennik();

        for(Movie movie:listaZyczen){
            if(cennik.pobierzCene(movie.getGenre(), movie.getName(), this, movie.getDevNum())>=0){
                koszyk.add(movie);
            }
        }
        for(Movie movie:koszyk){
            listaZyczen.remove(movie);
        }

        koszykKinoman = koszyk;
    }

    public void zaplac(String typPlatnosci, boolean odkladac){

        float suma =0;
        Cennik cennik = Cennik.pobierzCennik();
        ArrayList<Movie> zakupioneFilmy = new ArrayList<>();
        for(Movie movie:koszykKinoman){
            suma += cennik.pobierzCene(movie.getGenre(), movie.getName(),
                    this, movie.getDevNum()) * movie.getDevNum();
            zakupioneFilmy.add(movie);
        }

        int iter = 0;
        if(odkladac && suma>wallet){
            while(suma>wallet){

                Movie currentMovie = zakupioneFilmy.get(iter);
                if(!Cennik.pobierzCennik().czyWieloktornosciaJednego(currentMovie) || currentMovie.getDevNum() == 0) {
                    if(currentMovie.getDevNum() == 0)
                        koszykKinoman.remove(currentMovie);
                    iter++;
                    continue;
                }
                zakupioneFilmy.remove(currentMovie);
                koszykKinoman.get(iter).resingFromOneDev();
                suma -= cennik.pobierzCene(currentMovie.getGenre(),
                                currentMovie.getName(), this, currentMovie.getDevNum());
            }
        }

        for(Movie movie:zakupioneFilmy){
            koszykKinoman.remove(movie);
        }

        if(typPlatnosci.toLowerCase().compareTo("karta")==0){
            suma *= 1.02;
        }

        wallet -= suma;
    }

    public String getName() {
        return name;
    }

    public float pobierzPortfel(){
        return wallet;
    }

    public Koszyk pobierzKoszyk(){
        return koszykKinoman;
    }
}
