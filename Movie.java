import java.util.ArrayList;

public abstract class Movie implements Comparable<Movie>{

    private String name;
    private int devNum;

    private String genre;


    public String getGenre() {
        return genre;
    }

    public Movie(String name, int devNum) {
        this.name = name;
        this.devNum = devNum;
        this.genre = this.getClass().getSimpleName().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public int getDevNum() {
        return devNum;
    }

    public boolean equals(Object obj){
        if(obj.getClass() == this.getClass()){
            return this.getName().equals(((Movie)obj).getName()) && this.getDevNum() == ((Movie) obj).getDevNum();
        }else
            return false;
    }
    @Override
    public int compareTo(Movie o) {
        return this.getName().compareTo(o.getName()) !=0 ? this.getName().compareTo(o.getName()) :
                this.getDevNum()-o.getDevNum();
    }

    public void resingFromOneDev(){
        devNum--;
    }
}
