package com.cm.pojo;


public class PoemSet {
    private String title;
    private String poemVerses;
    private String poemOptions;
    private String answer;

    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {

        this.title = title;
    }

    public String getPoemVerses() {
        return poemVerses;
    }

    public void setPoemVerses(String poemVerses) {
        this.poemVerses = poemVerses;
    }
    public String getPoemOptions() {
        return poemOptions;
    }

    public void setPoemOptions(String poemOptions) {
        this.poemOptions = poemOptions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {

        this.answer = answer;
    }
}
