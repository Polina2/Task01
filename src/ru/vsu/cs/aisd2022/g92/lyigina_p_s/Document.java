package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

import java.util.ArrayList;

public class Document {
    private ArrayList<Paragraph> paragraphs;
    private ArrayList<Style> styles;

    public Document() {
        this.paragraphs = new ArrayList<>();
        this.styles = new ArrayList<>();
    }

    public ArrayList<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(ArrayList<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public void addParagraph() {
        paragraphs.add(new Paragraph());
    }

    public void deleteParagraph(int index) {
        paragraphs.remove(index);
    }

    public ArrayList<Style> getStyles() {
        return styles;
    }

    public void setStyles(ArrayList<Style> styles) {
        this.styles = styles;
    }

    public void addStyle() {
        styles.add(new Style());
    }

    public void deleteStyle(int index) {
        styles.remove(index);
    }
}
