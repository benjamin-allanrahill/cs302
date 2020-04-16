/**
 * Letter
 */
public class Letter {
 
    private Character _char;
    private Double weight;
 
    public Letter(Character c, Double weight) {
        this._char = c;
        this.weight = weight;
    }
 
    //Getters and setters

    public Double getWeight() {
        return this.weight;
    }
 
    @Override
    public String toString() {
        return "Letter [char=" + this._char + ", weight=" + weight.toString() + "]";
    }
}