/**
 * Letter
 */
public class Letter {
 
    Character _char;
    Double weight;
    String enconding;
 
    public Letter(Character c, Double weight) {
        this._char = c;
        this.weight = weight;
        this.enconding = "";
    }
 
    //Getters and setters

    public Double getWeight() {
        return this.weight;
    }

    public String getCode() {
        return this.enconding;
    }
 
    @Override
    public String toString() {
        return "Letter [char=" + this._char + ", weight=" + weight.toString() + "]";
    }
}