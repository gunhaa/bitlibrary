package bitcopark.library.controller.search;

public enum LikeStatus {

    NOT_LIKED(0),
    LIKED(1);

    private final int value;

    LikeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
