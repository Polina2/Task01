package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

public class Paragraph extends Style {
    private String name;
    private String contents;
    private Style style;

    private boolean isAlignmentFromStyle = false;
    private boolean isListAttributeFromStyle = false;
    private boolean isLeftIndentFromStyle = false;
    private boolean isRightIndentFromStyle = false;
    private boolean isUpIndentFromStyle = false;
    private boolean isDownIndentFromStyle = false;
    private boolean isRedLineFromStyle = false;

    public Paragraph() {
        name = "";
        contents = "";
        style = new Style();
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
        this.setLeftIndent(style.getLeftIndent());
        this.setRightIndent(style.getRightIndent());
        this.setUpIndent(style.getUpIndent());
        this.setDownIndent(style.getDownIndent());
        this.setRedLine(style.getRedLine());
        this.setAlignment(style.getAlignment());
        this.setListAttribute(style.getListAttribute());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String forPrinting(int width) {
        StringBuilder res = new StringBuilder();
        res.append("\n".repeat(Math.max(0, this.getUpIndent())));
        String[] strings = contents.split("\n");
        for (int strInd = 0; strInd < strings.length; strInd++) {
            String[] words = strings[strInd].split(" ");
            int index = 0;
            int start = 0;
            boolean wasRedLineOrListChar = false;
            while (index < words.length) {
                int w = width - this.getLeftIndent() - this.getRightIndent();

                if (this.getListAttribute() == ListAttribute.LIST_ATTRIBUTE_NUMBERED)
                    w -= 3;
                else if (this.getListAttribute() == ListAttribute.LIST_ATTRIBUTE_MARKED)
                    w -= 2;
                else if (!wasRedLineOrListChar) {
                    res.append(" ".repeat(Math.max(0, this.getRedLine())));
                    w -= this.getRedLine();
                    wasRedLineOrListChar = true;
                }

                while (index < words.length && words[index].length() <= w) {
                    w -= words[index].length()+1;
                    index++;
                }

                res.append(" ".repeat(Math.max(0, this.getLeftIndent())));
                if (this.getListAttribute() == ListAttribute.LIST_ATTRIBUTE_NUMBERED) {
                    if (!wasRedLineOrListChar) {
                        res.append(strInd + this.getNumberFrom()).append(". ");
                        wasRedLineOrListChar = true;
                    } else {
                        res.append("   ");
                    }
                } else if (this.getListAttribute() == ListAttribute.LIST_ATTRIBUTE_MARKED) {
                    if (!wasRedLineOrListChar) {
                        res.append(this.getMarker()).append(" ");
                        wasRedLineOrListChar = true;
                    } else {
                        res.append("  ");
                    }
                }

                switch (this.getAlignment()) {
                    case ALIGNMENT_LEFT -> res.append(leftAlignment(words, start, index, w));
                    case ALIGNMENT_RIGHT -> res.append(rightAlignment(words, start, index, w));
                    case ALIGNMENT_CENTER -> res.append(centerAlignment(words, start, index, w));
                    case ALIGNMENT_WIDTH -> res.append(widthAlignment(words, start, index, w));
                }

                res.append(" ".repeat(Math.max(0, this.getRightIndent()))).append("\n");
                start = index;
            }
        }
        res.append("\n".repeat(Math.max(0, this.getDownIndent())));
        return res.toString();
    }

    private static String leftAlignment(String[] words, int start, int end, int spaces) {
        StringBuilder res = new StringBuilder();
        for (int i = start; i < end; i++) {
            res.append(words[i]);
            if (i < end - 1)
                res.append(" ");
        }
        res.append(" ".repeat(Math.max(0, spaces)));//spaces >= 0
        return res.toString();
    }

    private static String rightAlignment(String[] words, int start, int end, int spaces) {
        StringBuilder res = new StringBuilder();
        res.append(" ".repeat(Math.max(0, spaces)));//spaces >= 0
        for (int i = start; i < end; i++) {
            res.append(words[i]);
            if (i < end - 1)
                res.append(" ");
        }
        return res.toString();
    }

    private static String centerAlignment(String[] words, int start, int end, int spaces) {
        StringBuilder res = new StringBuilder();
        res.append(" ".repeat(spaces/2));
        for (int i = start; i < end; i++) {
            res.append(words[i]);
            if (i < end - 1)
                res.append(" ");
        }
        res.append(" ".repeat(spaces - spaces/2));
        return res.toString();
    }

    private static String widthAlignment(String[] words, int start, int end, int spaces) {
        StringBuilder res = new StringBuilder();
        int wordCount = end - start;
        int gap = (wordCount>1)?(spaces/(wordCount-1)):0;
        int remainder = spaces%(wordCount-1);
        for (int i = start; i < end; i++) {
            res.append(words[i]);
            if (i < end - 1)
                res.append(" ".repeat((remainder-- > 0)?(gap + 1):gap));
        }
        return res.toString();
    }

    public boolean isAlignmentFromStyle() {
        return isAlignmentFromStyle;
    }

    public void setAlignmentFromStyle(boolean alignmentFromStyle) {
        isAlignmentFromStyle = alignmentFromStyle;
    }

    public boolean isListAttributeFromStyle() {
        return isListAttributeFromStyle;
    }

    public void setListAttributeFromStyle(boolean listAttributeFromStyle) {
        isListAttributeFromStyle = listAttributeFromStyle;
    }

    public boolean isLeftIndentFromStyle() {
        return isLeftIndentFromStyle;
    }

    public void setLeftIndentFromStyle(boolean leftIndentFromStyle) {
        isLeftIndentFromStyle = leftIndentFromStyle;
    }

    public boolean isRightIndentFromStyle() {
        return isRightIndentFromStyle;
    }

    public void setRightIndentFromStyle(boolean rightIndentFromStyle) {
        isRightIndentFromStyle = rightIndentFromStyle;
    }

    public boolean isUpIndentFromStyle() {
        return isUpIndentFromStyle;
    }

    public void setUpIndentFromStyle(boolean upIndentFromStyle) {
        isUpIndentFromStyle = upIndentFromStyle;
    }

    public boolean isDownIndentFromStyle() {
        return isDownIndentFromStyle;
    }

    public void setDownIndentFromStyle(boolean downIndentFromStyle) {
        isDownIndentFromStyle = downIndentFromStyle;
    }

    public boolean isRedLineFromStyle() {
        return isRedLineFromStyle;
    }

    public void setRedLineFromStyle(boolean redLineFromStyle) {
        isRedLineFromStyle = redLineFromStyle;
    }
}
