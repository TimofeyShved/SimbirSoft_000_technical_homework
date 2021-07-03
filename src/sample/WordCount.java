package sample;

public class WordCount {
    private String word;
    private int count;

    WordCount(String word, int count){
        this.word = word;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getWord() {
        return word;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String toString() {return this.word+"/"+this.count;}
}
