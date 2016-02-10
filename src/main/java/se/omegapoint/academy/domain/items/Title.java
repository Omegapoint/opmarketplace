package se.omegapoint.academy.domain.items;

public final class Title {

    public static final int MAX_LENGTH = 50;
    public static final String ILLEGAL_LENGTH = "Title can only be " + MAX_LENGTH + " characters long.";
    public static final String ILLEGAL_CHARACTERS = "Title can only contain letters, digits and spaces";

    private final String text;

    public Title(String text) {
        if (text.length() > MAX_LENGTH || text.length() < 0)
            throw new IllegalArgumentException(ILLEGAL_LENGTH);
        else if (!text.matches("(\\w+\\s*)+"))
            throw new IllegalArgumentException(ILLEGAL_CHARACTERS);
        this.text = text;
    }

    public String text(){
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Title title = (Title) o;

        return text != null ? text.equals(title.text) : title.text == null;

    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }
}
