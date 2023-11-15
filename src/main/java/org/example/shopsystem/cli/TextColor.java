package org.example.shopsystem.cli;

public enum TextColor {
    RESET       (0),
    BOLD        (1),
    HIGHLIGHTED (2, false),
    ITALIC      (3),
    UNDERLINED  (4),
    BLINKING    (5, false),
    INVERTED    (7),
    HIDDEN      (8, false),
    CROSSED_OUT (9, true, false),
    BOLD_UNDERLINED(21, true, false),
    TEXT_BLACK       (30),
    TEXT_DARK_RED    (31),
    TEXT_DARK_GREEN  (32),
    TEXT_DARK_YELLOW (33),
    TEXT_DARK_BLUE   (34),
    TEXT_DARK_MAGENTA(35),
    TEXT_DARK_CYAN   (36),
    TEXT_LIGHT_GRAY  (37),
    BG_BLACK         (40),
    BG_DARK_RED      (41),
    BG_DARK_GREEN    (42),
    BG_DARK_YELLOW   (43),
    BG_DARK_BLUE     (44),
    BG_DARK_MAGENTA  (45),
    BG_DARK_CYAN     (46),
    BG_LIGHT_GRAY    (47),
    BOXED1            (51, true, false),
    BOXED2            (52, true, false),
    TEXT_DARK_GRAY    (90),
    TEXT_LIGHT_RED    (91),
    TEXT_LIGHT_GREEN  (92),
    TEXT_LIGHT_YELLOW (93),
    TEXT_LIGHT_BLUE   (94),
    TEXT_LIGHT_MAGENTA(95),
    TEXT_LIGHT_CYAN   (96),
    TEXT_WHITE        (97),
    BG_DARK_GRAY      (100),
    BG_LIGHT_RED      (101),
    BG_LIGHT_GREEN    (102),
    BG_LIGHT_YELLOW   (103),
    BG_LIGHT_BLUE     (104),
    BG_LIGHT_MAGENTA  (105),
    BG_LIGHT_CYAN     (106),
    BG_WHITE          (107),
    ;
    private final String sequence;
    private final int index;
    private final boolean inIde;
    private final boolean inTerminal;

    TextColor(int index) {
        this(index, true, true);
    }
    TextColor(int index, boolean inIde) {
        this(index, inIde, true);
    }
    TextColor(int index, boolean inIde, boolean inTerminal) {
        this.sequence = "\033[%dm".formatted(index);
        this.index = index;
        this.inIde = inIde;
        this.inTerminal = inTerminal;
    }

    @Override
    public String toString() {
        return sequence;
    }

    public String toDisplayString() {
        return "%s(%d%s%s)".formatted(
                name(),
                index,
                !inIde ? ", Not in IDE" : "",
                !inTerminal ? ", Not in Terminal" : ""
        );
    }

    public static void testValues() {

        for (int i=1; i<200; i++)
            testValue(i);

        testExamples(
                TEXT_BLACK,
                TEXT_DARK_GRAY,
                TEXT_LIGHT_GRAY,
                TEXT_WHITE,
                BG_BLACK,
                BG_DARK_GRAY,
                BG_LIGHT_GRAY,
                BG_WHITE
        );

        testCombi(TEXT_DARK_BLUE, BG_DARK_BLUE);
        testCombi(TEXT_DARK_BLUE, BG_LIGHT_BLUE);
        testCombi(TEXT_LIGHT_BLUE, BG_DARK_BLUE);
        testCombi(TEXT_LIGHT_BLUE, BG_LIGHT_BLUE);

        showColorMatrix();
    }

    private static void showColorMatrix() {
        TextColor[] bgColors = {
                null,
                BG_BLACK         ,
                BG_DARK_RED      ,
                BG_DARK_GREEN    ,
                BG_DARK_YELLOW   ,
                BG_DARK_BLUE     ,
                BG_DARK_MAGENTA  ,
                BG_DARK_CYAN     ,
                BG_LIGHT_GRAY    ,
                BG_DARK_GRAY     ,
                BG_LIGHT_RED     ,
                BG_LIGHT_GREEN   ,
                BG_LIGHT_YELLOW  ,
                BG_LIGHT_BLUE    ,
                BG_LIGHT_MAGENTA ,
                BG_LIGHT_CYAN    ,
                BG_WHITE         ,
        };
        TextColor[] textColors = {
                null,
                TEXT_BLACK         ,
                TEXT_DARK_RED      ,
                TEXT_DARK_GREEN    ,
                TEXT_DARK_YELLOW   ,
                TEXT_DARK_BLUE     ,
                TEXT_DARK_MAGENTA  ,
                TEXT_DARK_CYAN     ,
                TEXT_LIGHT_GRAY    ,
                TEXT_DARK_GRAY     ,
                TEXT_LIGHT_RED     ,
                TEXT_LIGHT_GREEN   ,
                TEXT_LIGHT_YELLOW  ,
                TEXT_LIGHT_BLUE    ,
                TEXT_LIGHT_MAGENTA ,
                TEXT_LIGHT_CYAN    ,
                TEXT_WHITE         ,
        };
        System.out.print("####");
        for (TextColor bgColor : bgColors)
            if (bgColor==null)
                System.out.print(" ---");
            else
                System.out.printf(" %3d", bgColor.index);
        System.out.println();

        for (TextColor textColor : textColors) {
            if (textColor==null)
                System.out.print("--- ");
            else
                System.out.printf("%3d ", textColor.index);
            for (TextColor bgColor : bgColors) {
                System.out.printf("%s%s####%s", textColor==null ? "" : textColor, bgColor==null ? "" : bgColor, RESET);
            }
            System.out.println();
        }
    }

    private static void testCombi(TextColor tc1, TextColor tc2) {
        System.out.printf("%s%sTextText%s %s %s%n", tc1, tc2, RESET, tc1.toDisplayString(), tc2.toDisplayString() );
    }

    private static void testExamples(TextColor... colors) {
        for (TextColor tc : colors)
            testValue(tc.index);
    }

    private static void testValue(int i) {
        TextColor tc = findByInt(i);
        System.out.printf("%-10s -> \033[%dmTextText\033[0m %s%n", "\\033[%dm".formatted(i), i, tc==null ? "" : tc.toDisplayString() );
    }

    private static TextColor findByInt(int i) {
        for (TextColor tc : values())
            if (tc.index == i)
                return tc;
        return null;
    }

}
