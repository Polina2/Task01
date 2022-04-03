package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.vsu.cs.util.JTableUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Objects;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneText;
    private JScrollPane scrollPaneParagraphs;
    private JScrollPane scrollPaneStyles;
    private JPanel panelSettings;
    private JButton buttonSave;
    private JLabel labelS;
    private JLabel labelP;
    private JLabel labelT;
    private JSpinner spinnerLeft;
    private JSpinner spinnerRight;
    private JSpinner spinnerUp;
    private JSpinner spinnerDown;
    private JSpinner spinnerRedLine;
    private JTextArea textAreaText;
    private JTable tableParagraphs;
    private JTable tableStyles;
    private JPanel panelParagraphButtons;
    private JButton buttonAddParagraph;
    private JButton buttonDeleteParagraph;
    private JPanel panelStyleButtons;
    private JButton buttonAddStyle;
    private JButton buttonDeleteStyle;
    private JPanel panelAlignment;
    private JRadioButton radioButtonLeft;
    private JRadioButton radioButtonRight;
    private JRadioButton radioButtonCenter;
    private JRadioButton radioButtonWidth;
    private JPanel panelListAttribute;
    private JRadioButton radioButtonWithoutList;
    private JRadioButton radioButtonNumbered;
    private JRadioButton radioButtonMarked;
    private JButton buttonUpdateParagraph;
    private JTextField textFieldNumberFrom;
    private JTextField textFieldMarker;
    private JComboBox<Style> comboBoxStyles;
    private JButton buttonUpdateStyle;
    private JSpinner spinnerWidth;
    private JTextArea textAreaFinal;
    private JButton buttonShow;
    private JRadioButton radioButtonAlignFromStyle;
    private JRadioButton radioButtonListFromStyle;
    private JRadioButton radioButtonLeftIndFromStyle;
    private JRadioButton radioButtonRightIndFromStyle;
    private JRadioButton radioButtonUpIndFromStyle;
    private JRadioButton radioButtonDownIndFromStyle;
    private JRadioButton radioButtonRedLineFromStyle;
    private final JFileChooser fileChooserSave;
    private int currentParagraph = -1;
    private int currentStyle;

    private Style.Alignment getRadioButtonAlignment() {
        if (radioButtonLeft.isSelected())
            return Style.Alignment.ALIGNMENT_LEFT;
        if (radioButtonRight.isSelected())
            return Style.Alignment.ALIGNMENT_RIGHT;
        if (radioButtonCenter.isSelected())
            return Style.Alignment.ALIGNMENT_CENTER;
        if (radioButtonWidth.isSelected())
            return Style.Alignment.ALIGNMENT_WIDTH;
        return Style.Alignment.ALIGNMENT_LEFT;
    }

    private void setRadioButtonAlignment(Style.Alignment alignment) {
        if (alignment == Style.Alignment.ALIGNMENT_LEFT)
            radioButtonLeft.setSelected(true);
        else if (alignment == Style.Alignment.ALIGNMENT_RIGHT)
            radioButtonRight.setSelected(true);
        else if (alignment == Style.Alignment.ALIGNMENT_CENTER)
            radioButtonCenter.setSelected(true);
        else if (alignment == Style.Alignment.ALIGNMENT_WIDTH)
            radioButtonWidth.setSelected(true);
    }

    private Style.ListAttribute getRadioButtonListAttribute() {
        if (radioButtonWithoutList.isSelected())
            return Style.ListAttribute.LIST_ATTRIBUTE_WITHOUT_LIST;
        if (radioButtonNumbered.isSelected())
            return Style.ListAttribute.LIST_ATTRIBUTE_NUMBERED;
        if (radioButtonMarked.isSelected())
            return Style.ListAttribute.LIST_ATTRIBUTE_MARKED;
        return Style.ListAttribute.LIST_ATTRIBUTE_WITHOUT_LIST;
    }

    private void setRadioButtonListAttribute(Style.ListAttribute listAttribute) {
        if (listAttribute == Style.ListAttribute.LIST_ATTRIBUTE_WITHOUT_LIST)
            radioButtonWithoutList.setSelected(true);
        else if (listAttribute == Style.ListAttribute.LIST_ATTRIBUTE_NUMBERED)
            radioButtonNumbered.setSelected(true);
        else if (listAttribute == Style.ListAttribute.LIST_ATTRIBUTE_MARKED)
            radioButtonMarked.setSelected(true);
    }

    public FrameMain() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        fileChooserSave = new JFileChooser();
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserSave.addChoosableFileFilter(filter);
        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        JTableUtils.initJTableForArray(tableParagraphs, 100, true, false, false, false);
        JTableUtils.initJTableForArray(tableStyles, 100, true, false, false, false);

        Document document = new Document();

        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(radioButtonLeft);
        bg1.add(radioButtonRight);
        bg1.add(radioButtonCenter);
        bg1.add(radioButtonWidth);
        bg1.add(radioButtonAlignFromStyle);

        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(radioButtonWithoutList);
        bg2.add(radioButtonNumbered);
        bg2.add(radioButtonMarked);
        bg2.add(radioButtonListFromStyle);

        comboBoxStyles.addItem(new Style());
        //without style
        spinnerWidth.setValue(40);

        tableParagraphs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableParagraphs.rowAtPoint(e.getPoint());
                if (SwingUtilities.isLeftMouseButton(e)) {
                    currentParagraph = row;
                    Paragraph paragraph = document.getParagraphs().get(row);
                    textAreaText.setText(paragraph.getContents());
                    radioButtonLeftIndFromStyle.setEnabled(true);
                    radioButtonRightIndFromStyle.setEnabled(true);
                    radioButtonUpIndFromStyle.setEnabled(true);
                    radioButtonDownIndFromStyle.setEnabled(true);
                    radioButtonRedLineFromStyle.setEnabled(true);

                    radioButtonAlignFromStyle.setSelected(paragraph.isAlignmentFromStyle());
                    radioButtonListFromStyle.setSelected(paragraph.isListAttributeFromStyle());
                    radioButtonLeftIndFromStyle.setSelected(paragraph.isLeftIndentFromStyle());
                    radioButtonRightIndFromStyle.setSelected(paragraph.isRightIndentFromStyle());
                    radioButtonUpIndFromStyle.setSelected(paragraph.isUpIndentFromStyle());
                    radioButtonDownIndFromStyle.setSelected(paragraph.isDownIndentFromStyle());
                    radioButtonRedLineFromStyle.setSelected(paragraph.isRedLineFromStyle());
                    //textAreaFinal.setText("" + paragraph.getLeftIndent() + ' ' + paragraph.isLeftIndentFromStyle());

                    spinnerLeft.setValue(paragraph.getLeftIndent());
                    spinnerRight.setValue(paragraph.getRightIndent());
                    spinnerUp.setValue(paragraph.getUpIndent());
                    spinnerDown.setValue(paragraph.getDownIndent());
                    spinnerRedLine.setValue(paragraph.getRedLine());
                    setRadioButtonAlignment(paragraph.getAlignment());
                    setRadioButtonListAttribute(paragraph.getListAttribute());
                    if (paragraph.getListAttribute() == Style.ListAttribute.LIST_ATTRIBUTE_NUMBERED)
                        textFieldNumberFrom.setText("" + paragraph.getNumberFrom());
                    if (paragraph.getListAttribute() == Style.ListAttribute.LIST_ATTRIBUTE_MARKED)
                        textFieldMarker.setText("" + paragraph.getMarker());
                    comboBoxStyles.setSelectedItem(paragraph.getStyle());


                    //other

                }
            }
        });

        tableStyles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentParagraph = -1;
                int row = tableStyles.rowAtPoint(e.getPoint());
                if (SwingUtilities.isLeftMouseButton(e)) {
                    currentStyle = row;
                    Style style = document.getStyles().get(row);
                    spinnerLeft.setValue(style.getLeftIndent());
                    spinnerRight.setValue(style.getRightIndent());
                    spinnerUp.setValue(style.getUpIndent());
                    spinnerDown.setValue(style.getDownIndent());
                    spinnerRedLine.setValue(style.getRedLine());
                    setRadioButtonAlignment(style.getAlignment());
                    setRadioButtonListAttribute(style.getListAttribute());
                    if (style.getListAttribute() == Style.ListAttribute.LIST_ATTRIBUTE_NUMBERED)
                        textFieldNumberFrom.setText("" + style.getNumberFrom());
                    if (style.getListAttribute() == Style.ListAttribute.LIST_ATTRIBUTE_MARKED)
                        textFieldMarker.setText("" + style.getMarker());
                    radioButtonLeftIndFromStyle.setEnabled(false);
                    radioButtonRightIndFromStyle.setEnabled(false);
                    radioButtonUpIndFromStyle.setEnabled(false);
                    radioButtonDownIndFromStyle.setEnabled(false);
                    radioButtonRedLineFromStyle.setEnabled(false);
                    spinnerLeft.setEnabled(true);
                    spinnerRight.setEnabled(true);
                    spinnerUp.setEnabled(true);
                    spinnerDown.setEnabled(true);
                    spinnerRedLine.setEnabled(true);
                    //other
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooserSave.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    Utils.textToFile(document.toText(), fileChooserSave.getSelectedFile().getPath());
                }
            }
        });
        buttonAddParagraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                document.addParagraph();
                Utils.listToTableP(document.getParagraphs(), tableParagraphs);
                currentParagraph = document.getParagraphs().size() - 1;
            }
        });
        buttonAddStyle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                document.addStyle();
                Utils.listToTableS(document.getStyles(), tableStyles);
                currentStyle = document.getStyles().size() - 1;
                comboBoxStyles.addItem(document.getStyles().get(currentStyle));
            }
        });
        buttonUpdateParagraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Paragraph paragraph = document.getParagraphs().get(currentParagraph);
                paragraph.setName(Objects.requireNonNull(Utils.tableToArray(tableParagraphs))[currentParagraph]);
                paragraph.setContents(textAreaText.getText());

                paragraph.setStyle((Style) Objects.requireNonNull(comboBoxStyles.getSelectedItem()));
                //textAreaFinal.setText("" + radioButtonLeftIndFromStyle.isSelected());
                if (!paragraph.isLeftIndentFromStyle())
                    paragraph.setLeftIndent((int) spinnerLeft.getValue());
                else
                    paragraph.setLeftIndent(paragraph.getStyle().getLeftIndent());
                if (!paragraph.isRightIndentFromStyle())
                    paragraph.setRightIndent((int) spinnerRight.getValue());
                else
                    paragraph.setRightIndent(paragraph.getStyle().getRightIndent());
                if (!paragraph.isUpIndentFromStyle())
                    paragraph.setUpIndent((int) spinnerUp.getValue());
                else
                    paragraph.setUpIndent(paragraph.getStyle().getUpIndent());
                if (!paragraph.isDownIndentFromStyle())
                    paragraph.setDownIndent((int) spinnerDown.getValue());
                else
                    paragraph.setDownIndent(paragraph.getStyle().getDownIndent());
                if (!paragraph.isRedLineFromStyle())
                    paragraph.setRedLine((int) spinnerRedLine.getValue());
                else
                    paragraph.setRedLine(paragraph.getStyle().getRedLine());
                if (!paragraph.isAlignmentFromStyle())
                    paragraph.setAlignment(getRadioButtonAlignment());
                else
                    paragraph.setAlignment(paragraph.getStyle().getAlignment());
                if (!paragraph.isListAttributeFromStyle()) {
                    paragraph.setListAttribute(getRadioButtonListAttribute());
                    paragraph.setNumberFrom(Integer.parseInt(textFieldNumberFrom.getText()));
                    paragraph.setMarker(textFieldMarker.getText().charAt(0));
                } else {
                    paragraph.setListAttribute(paragraph.getStyle().getListAttribute());
                    paragraph.setNumberFrom(paragraph.getStyle().getNumberFrom());
                    paragraph.setMarker(paragraph.getStyle().getMarker());
                }

                //so on
            }
        });
        buttonUpdateStyle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Style style = document.getStyles().get(currentStyle);
                style.setName(Objects.requireNonNull(Utils.tableToArray(tableStyles))[currentStyle]);
                style.setLeftIndent((int) spinnerLeft.getValue());
                style.setRightIndent((int) spinnerRight.getValue());
                style.setUpIndent((int) spinnerUp.getValue());
                style.setDownIndent((int) spinnerDown.getValue());
                style.setRedLine((int) spinnerRedLine.getValue());
                style.setAlignment(getRadioButtonAlignment());
                style.setListAttribute(getRadioButtonListAttribute());
                style.setNumberFrom(Integer.parseInt(textFieldNumberFrom.getText()));
                style.setMarker(textFieldMarker.getText().charAt(0));

                for (Paragraph paragraph : document.getParagraphs()) {
                    if (paragraph.getStyle() == style) {
                        if (paragraph.isAlignmentFromStyle())
                            paragraph.setAlignment(style.getAlignment());
                        if (paragraph.isListAttributeFromStyle()) {
                            paragraph.setListAttribute(style.getListAttribute());
                            paragraph.setNumberFrom(style.getNumberFrom());
                            paragraph.setMarker(style.getMarker());
                        }
                        if (paragraph.isLeftIndentFromStyle())
                            paragraph.setLeftIndent(style.getLeftIndent());
                        if (paragraph.isRightIndentFromStyle())
                            paragraph.setRightIndent(style.getRightIndent());
                        if (paragraph.isUpIndentFromStyle())
                            paragraph.setUpIndent(style.getUpIndent());
                        if (paragraph.isDownIndentFromStyle())
                            paragraph.setDownIndent(style.getDownIndent());
                        if (paragraph.isRedLineFromStyle())
                            paragraph.setRedLine(style.getRedLine());
                        //and so on
                    }
                }
            }
        });
        buttonDeleteParagraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                document.getParagraphs().remove(currentParagraph);
                Utils.listToTableP(document.getParagraphs(), tableParagraphs);
            }
        });
        buttonDeleteStyle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                document.getStyles().remove(currentStyle);
                Utils.listToTableS(document.getStyles(), tableStyles);
            }
        });
        spinnerWidth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                document.setWidth((int) spinnerWidth.getValue());
            }
        });
        buttonShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder res = new StringBuilder();
                for (String p : document.toText()) {
                    res.append(p).append("\n");
                }
                textAreaFinal.setText(res.toString());
            }
        });
        /*
        comboBoxStyles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Paragraph paragraph = document.getParagraphs().get(currentParagraph);
                paragraph.setStyle((Style) Objects.requireNonNull(comboBoxStyles.getSelectedItem()));
                spinnerLeft.setValue(paragraph.getLeftIndent());
                radioButtonLeftIndFromStyle.setSelected(true);
                spinnerRight.setValue(paragraph.getRightIndent());
                radioButtonRightIndFromStyle.setSelected(true);
                spinnerUp.setValue(paragraph.getUpIndent());
                radioButtonUpIndFromStyle.setSelected(true);
                spinnerDown.setValue(paragraph.getDownIndent());
                radioButtonDownIndFromStyle.setSelected(true);
                spinnerRedLine.setValue(paragraph.getRedLine());
                radioButtonRedLineFromStyle.setSelected(true);
                //setRadioButtonAlignment(paragraph.getAlignment());
                radioButtonAlignFromStyle.setSelected(true);
                //paragraph.setAlignmentFromStyle(true);
                //setRadioButtonListAttribute(paragraph.getListAttribute());
                radioButtonListFromStyle.setSelected(true);
                //paragraph.setListAttributeFromStyle(true);
                if (paragraph.getListAttribute() == Style.ListAttribute.LIST_ATTRIBUTE_NUMBERED)
                    textFieldNumberFrom.setText("" + paragraph.getNumberFrom());
                if (paragraph.getListAttribute() == Style.ListAttribute.LIST_ATTRIBUTE_MARKED)
                    textFieldMarker.setText("" + paragraph.getMarker());
            }
        });
        */
        radioButtonAlignFromStyle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (currentParagraph >= 0)
                    document.getParagraphs().get(currentParagraph).setAlignmentFromStyle(radioButtonAlignFromStyle.isSelected());
            }
        });
        radioButtonListFromStyle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (currentParagraph >= 0)
                    document.getParagraphs().get(currentParagraph).setListAttributeFromStyle(radioButtonListFromStyle.isSelected());
            }
        });
        radioButtonLeftIndFromStyle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (currentParagraph >= 0)
                    document.getParagraphs().get(currentParagraph).setLeftIndentFromStyle(radioButtonLeftIndFromStyle.isSelected());
                spinnerLeft.setEnabled(!radioButtonLeftIndFromStyle.isSelected());
            }
        });
        radioButtonRightIndFromStyle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (currentParagraph >= 0)
                    document.getParagraphs().get(currentParagraph).setRightIndentFromStyle(radioButtonRightIndFromStyle.isSelected());
                spinnerRight.setEnabled(!radioButtonRightIndFromStyle.isSelected());
            }
        });
        radioButtonUpIndFromStyle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (currentParagraph >= 0)
                    document.getParagraphs().get(currentParagraph).setUpIndentFromStyle(radioButtonUpIndFromStyle.isSelected());
                spinnerUp.setEnabled(!radioButtonUpIndFromStyle.isSelected());
            }
        });
        radioButtonDownIndFromStyle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (currentParagraph >= 0)
                    document.getParagraphs().get(currentParagraph).setDownIndentFromStyle(radioButtonDownIndFromStyle.isSelected());
                spinnerDown.setEnabled(!radioButtonDownIndFromStyle.isSelected());
            }
        });
        radioButtonRedLineFromStyle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (currentParagraph >= 0)
                    document.getParagraphs().get(currentParagraph).setRedLineFromStyle(radioButtonRedLineFromStyle.isSelected());
                spinnerRedLine.setEnabled(!radioButtonRedLineFromStyle.isSelected());
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(7, 5, new Insets(0, 0, 0, 0), -1, -1));
        scrollPaneText = new JScrollPane();
        panelMain.add(scrollPaneText, new GridConstraints(2, 0, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 100), null, 0, false));
        textAreaText = new JTextArea();
        textAreaText.setColumns(40);
        scrollPaneText.setViewportView(textAreaText);
        panelSettings = new JPanel();
        panelSettings.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelSettings, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Red line");
        panelSettings.add(label1, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Left");
        panelSettings.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Down");
        panelSettings.add(label3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Up");
        panelSettings.add(label4, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Right");
        panelSettings.add(label5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerLeft = new JSpinner();
        panelSettings.add(spinnerLeft, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerRight = new JSpinner();
        panelSettings.add(spinnerRight, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerUp = new JSpinner();
        panelSettings.add(spinnerUp, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerDown = new JSpinner();
        panelSettings.add(spinnerDown, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerRedLine = new JSpinner();
        panelSettings.add(spinnerRedLine, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonSave = new JButton();
        buttonSave.setText("Save");
        panelSettings.add(buttonSave, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Width");
        panelSettings.add(label6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerWidth = new JSpinner();
        panelSettings.add(spinnerWidth, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonShow = new JButton();
        buttonShow.setText("Show");
        panelSettings.add(buttonShow, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonLeftIndFromStyle = new JRadioButton();
        radioButtonLeftIndFromStyle.setText("From Style");
        panelSettings.add(radioButtonLeftIndFromStyle, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonRightIndFromStyle = new JRadioButton();
        radioButtonRightIndFromStyle.setText("From Style");
        panelSettings.add(radioButtonRightIndFromStyle, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonUpIndFromStyle = new JRadioButton();
        radioButtonUpIndFromStyle.setText("From Style");
        panelSettings.add(radioButtonUpIndFromStyle, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonDownIndFromStyle = new JRadioButton();
        radioButtonDownIndFromStyle.setText("From Style");
        panelSettings.add(radioButtonDownIndFromStyle, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonRedLineFromStyle = new JRadioButton();
        radioButtonRedLineFromStyle.setText("From Style");
        panelSettings.add(radioButtonRedLineFromStyle, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelT = new JLabel();
        labelT.setText("Text");
        panelMain.add(labelT, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelAlignment = new JPanel();
        panelAlignment.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelAlignment, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        radioButtonLeft = new JRadioButton();
        radioButtonLeft.setSelected(true);
        radioButtonLeft.setText("Left Alignment");
        panelAlignment.add(radioButtonLeft, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonRight = new JRadioButton();
        radioButtonRight.setText("Right Alignment");
        panelAlignment.add(radioButtonRight, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonCenter = new JRadioButton();
        radioButtonCenter.setText("Center Alignment");
        panelAlignment.add(radioButtonCenter, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonWidth = new JRadioButton();
        radioButtonWidth.setText("Width Alignment");
        panelAlignment.add(radioButtonWidth, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonAlignFromStyle = new JRadioButton();
        radioButtonAlignFromStyle.setText("From Style");
        panelAlignment.add(radioButtonAlignFromStyle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelListAttribute = new JPanel();
        panelListAttribute.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelListAttribute, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        radioButtonWithoutList = new JRadioButton();
        radioButtonWithoutList.setSelected(true);
        radioButtonWithoutList.setText("Without List");
        panelListAttribute.add(radioButtonWithoutList, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonNumbered = new JRadioButton();
        radioButtonNumbered.setText("Numbered List");
        panelListAttribute.add(radioButtonNumbered, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonMarked = new JRadioButton();
        radioButtonMarked.setText("Marked List");
        panelListAttribute.add(radioButtonMarked, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldNumberFrom = new JTextField();
        textFieldNumberFrom.setText("1");
        panelListAttribute.add(textFieldNumberFrom, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldMarker = new JTextField();
        textFieldMarker.setText("-");
        panelListAttribute.add(textFieldMarker, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        radioButtonListFromStyle = new JRadioButton();
        radioButtonListFromStyle.setText("From Style");
        panelListAttribute.add(radioButtonListFromStyle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelParagraphButtons = new JPanel();
        panelParagraphButtons.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelParagraphButtons, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonAddParagraph = new JButton();
        buttonAddParagraph.setText("+");
        panelParagraphButtons.add(buttonAddParagraph, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonDeleteParagraph = new JButton();
        buttonDeleteParagraph.setText("-");
        panelParagraphButtons.add(buttonDeleteParagraph, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonUpdateParagraph = new JButton();
        buttonUpdateParagraph.setText("Update");
        panelParagraphButtons.add(buttonUpdateParagraph, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelStyleButtons = new JPanel();
        panelStyleButtons.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelStyleButtons, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonAddStyle = new JButton();
        buttonAddStyle.setText("+");
        panelStyleButtons.add(buttonAddStyle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonDeleteStyle = new JButton();
        buttonDeleteStyle.setText("-");
        panelStyleButtons.add(buttonDeleteStyle, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonUpdateStyle = new JButton();
        buttonUpdateStyle.setText("Update");
        panelStyleButtons.add(buttonUpdateStyle, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelP = new JLabel();
        labelP.setText("Paragraphs");
        panelMain.add(labelP, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneParagraphs = new JScrollPane();
        panelMain.add(scrollPaneParagraphs, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableParagraphs = new JTable();
        scrollPaneParagraphs.setViewportView(tableParagraphs);
        labelS = new JLabel();
        labelS.setText("Styles");
        panelMain.add(labelS, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneStyles = new JScrollPane();
        panelMain.add(scrollPaneStyles, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableStyles = new JTable();
        scrollPaneStyles.setViewportView(tableStyles);
        comboBoxStyles = new JComboBox();
        panelMain.add(comboBoxStyles, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Final");
        panelMain.add(label7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textAreaFinal = new JTextArea();
        textAreaFinal.setEditable(false);
        panelMain.add(textAreaFinal, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 200), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
