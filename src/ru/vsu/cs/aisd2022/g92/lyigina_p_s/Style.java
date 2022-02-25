package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

public class Style {
    private String name;
    private int leftIndent;
    private int rightIndent;
    private int upIndent;
    private int downIndent;
    private int redLine;
    private Alignment alignment;
    private ListAttribute listAttribute;
    private int numberFrom = 0;
    private char marker = ' ';

    enum Alignment {
        ALIGNMENT_LEFT,
        ALIGNMENT_RIGHT,
        ALIGNMENT_CENTER,
        ALIGNMENT_WIDTH
    }

    enum ListAttribute {
        LIST_ATTRIBUTE_WITHOUT_LIST,
        LIST_ATTRIBUTE_NUMBERED,
        LIST_ATTRIBUTE_MARKED
    }

    public Style(String name, int leftIndent, int rightIndent, int upIndent, int downIndent,
                 int redLine, Alignment alignment, ListAttribute listAttribute) {
        this.name = name;
        this.leftIndent = leftIndent;
        this.rightIndent = rightIndent;
        this.upIndent = upIndent;
        this.downIndent = downIndent;
        this.redLine = redLine;
        this.alignment = alignment;
        this.listAttribute = listAttribute;
        if (listAttribute == ListAttribute.LIST_ATTRIBUTE_NUMBERED)
            this.numberFrom = 1;
        else if (listAttribute == ListAttribute.LIST_ATTRIBUTE_MARKED)
            this.marker = '-';
    }

    public Style() {
        this("", 0, 0, 0, 0, 0,
                Alignment.ALIGNMENT_LEFT, ListAttribute.LIST_ATTRIBUTE_WITHOUT_LIST);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberFrom(int number) {
        if (listAttribute == ListAttribute.LIST_ATTRIBUTE_NUMBERED)
            this.numberFrom = number;
    }

    protected int getNumberFrom() {
        return numberFrom;
    }

    public void setMarker(char marker) {
        if (listAttribute == ListAttribute.LIST_ATTRIBUTE_MARKED)
            this.marker = marker;
    }

    protected char getMarker() {
        return marker;
    }

    public void setLeftIndent(int indent) {
        if (indent >= 0)
            this.leftIndent = indent;
    }

    protected int getLeftIndent() {
        return leftIndent;
    }

    public void setRightIndent(int indent) {
        if (indent >= 0)
            this.rightIndent = indent;
    }

    protected int getRightIndent() {
        return rightIndent;
    }

    public void setUpIndent(int indent) {
        if (indent >= 0)
            this.upIndent = indent;
    }

    protected int getUpIndent() {
        return upIndent;
    }

    public void setDownIndent(int indent) {
        if (indent >= 0)
            this.downIndent = indent;
    }

    protected int getDownIndent() {
        return downIndent;
    }

    public void setRedLine(int indent) {
        if (indent >= 0)
            this.redLine = indent;
    }

    protected int getRedLine() {
        return redLine;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    protected Alignment getAlignment() {
        return alignment;
    }

    public void setListAttribute(ListAttribute attribute) {
        this.listAttribute = attribute;
    }

    protected ListAttribute getListAttribute() {
        return listAttribute;
    }
}
