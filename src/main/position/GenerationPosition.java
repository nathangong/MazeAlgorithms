package main.position;

public class GenerationPosition extends Position {
    private final GenerationPosition prevPosition;

    public GenerationPosition(int i, int j, GenerationPosition prevPosition) {
        super(i, j);

        this.prevPosition = prevPosition;
    }

    public GenerationPosition getPrevPosition() {
        return prevPosition;
    }
}
