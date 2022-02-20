package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

public class Style {
    private String name;
    protected int leftIndent;
    protected int rightIndent;
    protected int upIndent;
    protected int downIndent;
    protected int redLine;
    protected Alignment alignment;
    protected ListAttribute listAttribute;
    protected int numberFrom = 0;
    protected char marker = ' ';

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

    public void setMarker(char marker) {
        if (listAttribute == ListAttribute.LIST_ATTRIBUTE_MARKED)
            this.marker = marker;
    }
}
