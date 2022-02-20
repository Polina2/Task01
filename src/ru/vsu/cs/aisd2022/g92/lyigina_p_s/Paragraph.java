package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

public class Paragraph extends Style {
    private String name;
    private String contents;
    private Style style;

    public Paragraph() {
        name = "";
        contents = "";
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
        this.leftIndent = style.leftIndent;
        this.rightIndent = style.rightIndent;
        this.upIndent = style.upIndent;
        this.downIndent = style.downIndent;
        this.redLine = style.redLine;
        this.alignment = style.alignment;
        this.listAttribute = style.listAttribute;
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

    public void setLeftIndent(int indent) {
        this.leftIndent = indent;
    }

    public void setRightIndent(int indent) {
        this.rightIndent = indent;
    }

    public void setUpIndent(int indent) {
        this.upIndent = indent;
    }

    public void setDownIndent(int indent) {
        this.downIndent = indent;
    }

    public void setRedLine(int indent) {
        this.redLine = indent;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setListAttribute(ListAttribute attribute) {
        this.listAttribute = attribute;
    }
}
