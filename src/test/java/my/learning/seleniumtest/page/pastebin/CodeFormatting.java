package my.learning.seleniumtest.page.pastebin;

public enum CodeFormatting {

    BASH("8", "bash"),
    C("9", "c"),
    C_SHARP("14", "csharp");

    private final String value;
    private final String cssClass;

    CodeFormatting(String value, String cssClass) {
        this.value = value;
        this.cssClass = cssClass;
    }

    public String getValue() {
        return value;
    }

    public String getCssClass() {
        return cssClass;
    }
}
