package Code.GameArgs;

public class PieceLoadEvent extends GameEvent{
    private byte piece;

    public PieceLoadEvent(String name, byte piece) {
        super(name);
        this.piece = piece;
    }

    public byte getPiece() {
        return piece;
    }
}
