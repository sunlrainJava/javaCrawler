package com.java.crawler.javaCrawler.domain;

public class MoveInfoDto {

    private String id;
    private String movieId;
    private String movieName;
    private String company;
    private String ArtName;
    private String resulet;
    private String Attrbute;
    private String descrip;

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getArtName() {
        return ArtName;
    }

    public void setArtName(String artName) {
        ArtName = artName;
    }

    public String getResulet() {
        return resulet;
    }

    public void setResulet(String resulet) {
        this.resulet = resulet;
    }

    public String getAttrbute() {
        return Attrbute;
    }

    public void setAttrbute(String attrbute) {
        Attrbute = attrbute;
    }
}
