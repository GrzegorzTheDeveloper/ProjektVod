public class VODTest {

    // cena programów danego typu z koszyka
    static int cena(Koszyk k, String typ) {
        int sum = 0;
        Cennik cennik = Cennik.pobierzCennik();
        for(Movie movie:k){
            if(movie.getGenre().compareTo(typ) == 0)
                sum += cennik.pobierzCene(typ, movie.getName(), k.getKlient(), movie.getDevNum()) * movie.getDevNum();
        }
        return sum;
    }

    public static void main(String[] args) {

        // cennik
        Cennik cennik = Cennik.pobierzCennik();

        // dodawanie nowych cen do cennika
        cennik.dodaj("dramat", "Król lew", 5, 3, 12, 6); 	// jeśli klient ma abonament: 5 zł/urządzenie (z dowolną liczbą urządzeń)
        // jeśli klient nie ma abonamentu: do 3 urządzeń za 12 zł/urządzenie,
        // w przeciwnym przypadku (wpp.) 6 zł/urządzenie

        cennik.dodaj("obyczaj", "Król Lear", 2, 15, 10);	// niezależnie od tego, czy klient posiada abonament czy nie
        // do 2 urządzeń za 15 zł/urządzenie, wpp. 10 zł/urządzenie

        cennik.dodaj("komedia", "Królowa", 7, 14);		// 7 zł/urządzenie jeśli klient ma abonament, wpp. 14 zł/urządzenie

        cennik.dodaj("sensacja", "Król Artur");			// darmowy dostęp


        // Klient Kinoman deklaruje kwotę 60 zł na zamównienia, posiada abonament w serwisie
        Klient kinoman = new Klient("Kinoman", 60, Abonament.TAK);

        // Klient Kinoman dodaje do listy życzeń różne programy:
        // "Król Lear" typu obyczajowego na 4 urządzeniach,
        // "Król Artur" typu sensacyjnego na 3 urządzeniach,
        // "Król lew" typu "dramat" na 2 urządzeniach,
        // "Korona" typu komediowego na 2 urządzeniach,
        kinoman.dodaj(new Obyczaj("Król Lear", 4));
        kinoman.dodaj(new Sensacja("Król Artur", 3));
        kinoman.dodaj(new Dramat("Król lew", 2));
        kinoman.dodaj(new Komedia("Korona", 2));


        // Lista życzeń klienta Kinoman
        ListaZyczen listaKinomana = kinoman.pobierzListeZyczen();

        System.out.println("Lista życzeń klienta " + listaKinomana);

//         Przed płaceniem, klient przepakuje programy z listy życzeń do koszyka.
//         Możliwe, że na liście życzeń są programy niemające ceny w cenniku,
//         w takim przypadku zostałyby usunięte z koszyka (ale nie z listy życzeń)

        Koszyk koszykKinomana = new Koszyk(kinoman);
        kinoman.przepakuj(koszykKinomana);

        // Co jest na liście życzeń klienta Kinomana
        System.out.println("Po przepakowaniu, lista życzeń klienta " + kinoman.pobierzListeZyczen());

        // Co jest w koszyku klienta Kinoman
        System.out.println("Po przepakowaniu, koszyk klienta " + koszykKinomana);
//
//        // Ile wynosi cena wszystkich programów typu obyczajowego w koszyku klienta Kinoman
        System.out.println("Programy obyczajowe w koszyku klienta Kinoman kosztowały:  " + cena(koszykKinomana, "obyczaj"));
//
//        // Klient zapłaci...
        kinoman.zaplac("karta", false);	// płaci kartą płatniczą, prowizja 2%
//        // true oznacza, że w przypadku braku środków aplikacja sam odłoży nadmiarowe programy,
//        // wpp. rezygnacja z płacenia razem z czyszczeniem koszyka i listy życzeń
//
//        // Ile klientowi Kinoman zostało pieniędzy?
        System.out.println("Po zapłaceniu, klientowi Kinoman zostało: " + kinoman.pobierzPortfel() + " zł");
//
//        // Mogło klientowi zabraknąć srodków, wtedy opcjonalnie programy mogą być odkładane,
//        // wpp. koszyk jest pusty po zapłaceniu
        System.out.println("Po zapłaceniu, koszyk klienta " + kinoman.pobierzKoszyk());
        System.out.println("Po zapłaceniu, koszyk klienta " + koszykKinomana);
//
        // Teraz przychodzi klient Krytyk,
        // deklaruje 60 zł na zamówienia
        Klient krytyk = new Klient("Krytyk", 60, Abonament.NIE);

        // Zamówił za dużo jak na tę kwotę
        krytyk.dodaj(new Dramat("Król lew", 2));
        krytyk.dodaj(new Komedia("Królowa", 3));

        // Co klient Krytyk ma na swojej liście życzeń
        System.out.println("Lista życzeń klienta " + krytyk.pobierzListeZyczen());

        Koszyk koszykKrytyka = new Koszyk(krytyk);
        krytyk.przepakuj(koszykKrytyka);

        // Co jest na liście życzeń klienta Krytyk
        System.out.println("Po przepakowaniu, lista życzeń klienta " + krytyk.pobierzListeZyczen());

        // A co jest w koszyku klienta Krytyk
        System.out.println("Po przepakowaniu, koszyk klienta " + krytyk.pobierzKoszyk());

        // klient Krytyk płaci
        krytyk.zaplac("przelew", true);	// płaci przelewem, bez prowizji

        // Ile klientowi Krytyk zostało pieniędzy?
        System.out.println("Po zapłaceniu, klientowi Krytyk zostało: " + krytyk.pobierzPortfel() + " zł");

        // Co zostało w koszyku klienta Krytyk (za mało pieniędzy miał)
        System.out.println("Po zapłaceniu, koszyk klienta " + koszykKrytyka);

    }
}
