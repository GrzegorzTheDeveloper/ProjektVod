import java.util.HashMap;
import java.util.Map;

public class Cennik {

    private Map<String, Map<String, int[]>> map;
    private static Cennik cennikObiekt;

    public Cennik() {
        map = new HashMap<>();
    }

    public static Cennik pobierzCennik(){
        if(cennikObiekt == null)
            cennikObiekt = new Cennik();
        return cennikObiekt;

    }

    public void dodaj(String genre, String title, int priceMembership, int devPriceLimit, int standardPrice, int priceAboveDevLimit){
        int[] priceTab = {priceMembership, devPriceLimit,standardPrice, priceAboveDevLimit};
        Map<String, int[]> subMap = new HashMap<>();
        subMap.put(title,priceTab);
        map.put(genre, subMap);
    }

    public void dodaj(String genre, String title, int devPriceLimit, int standardPrice, int priceAboveDevLimit){
        int[] priceTab = {devPriceLimit,standardPrice, priceAboveDevLimit};
        Map<String, int[]> subMap = new HashMap<>();
        subMap.put(title,priceTab);
        map.put(genre, subMap);
    }

    public void dodaj(String genre, String title, int priceMembership, int standardPrice){
        int[] priceTab = {priceMembership, standardPrice};
        Map<String, int[]> subMap = new HashMap<>();
        subMap.put(title,priceTab);
        map.put(genre, subMap);
    }

    public void dodaj(String genre, String title){
        Map<String, int[]> subMap = new HashMap<>();
        subMap.put(title,null);
        map.put(genre, subMap);
    }

    public Map<String, Map<String, int[]>> getMap() {
        return map;
    }

    public boolean czyWieloktornosciaJednego(Movie movie){
        Map<String, int[]> subMap = map.get(movie.getGenre());

        for(Map.Entry<String, int[]> entry : subMap.entrySet()){
            if(entry.getKey().compareTo(movie.getName())==0){
                int[] tablicaCen = entry.getValue();
                if(tablicaCen == null){
                    return false;
                }
                if(tablicaCen.length==4){
                    if(movie.getDevNum()>tablicaCen[1])
                        return false;
                }else if(tablicaCen.length == 3){
                    if(movie.getDevNum()>tablicaCen[0])
                        return false;
                }
            }
        }
        return true;
    }

    public int pobierzCene(String gatunek, String tytul, Klient klient, int devNum){

        Abonament abonament = klient.isMembership();
        Map<String, int[]> subMap = map.get(gatunek);

        for(Map.Entry<String, int[]> entry : subMap.entrySet()) {
            String nazwa = entry.getKey();
            int[] ceny = entry.getValue();
            if(ceny == null)
                return 0;
            if(tytul.compareTo(nazwa) == 0){
                if(abonament == Abonament.TAK) {
                    if (ceny.length == 2 || ceny.length == 4) {
                        return ceny[0];
                    }
                }

                if(ceny.length == 4){
                    if(devNum <= ceny[1])
                        return ceny[2];
                    else
                        return ceny[3];
                }else if(ceny.length==3){
                    if(devNum <= ceny[0])
                        return ceny[1];
                    else
                        return ceny[2];
                }else
                    return ceny[1];


            }
        }

        return -1;
    }
}
