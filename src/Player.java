public enum Player {
    X, O;

    public Player other() {
        return this == X ? O : X;
    }

    @Override
    public String toString() {
        return this == X ? "X" : "O";
    }
}
