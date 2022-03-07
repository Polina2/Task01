package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.vsu.cs.util.JTableUtils;

import javax.swing.*;
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
    private JButton buttonDelete;
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
    private final JFileChooser fileChooserSave;
    private int currentParagraph;

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

    private Style.ListAttribute getRadioButtonListAttribute() {
        if (radioButtonWithoutList.isSelected())
            return Style.ListAttribute.LIST_ATTRIBUTE_WITHOUT_LIST;
        if (radioButtonNumbered.isSelected())
            return Style.ListAttribute.LIST_ATTRIBUTE_NUMBERED;
        if (radioButtonMarked.isSelected())
            return Style.ListAttribute.LIST_ATTRIBUTE_MARKED;
        return Style.ListAttribute.LIST_ATTRIBUTE_WITHOUT_LIST;
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

        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(radioButtonWithoutList);
        bg2.add(radioButtonNumbered);
        bg2.add(radioButtonMarked);

        tableParagraphs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableParagraphs.rowAtPoint(e.getPoint());
                if (SwingUtilities.isLeftMouseButton(e)) {
                    currentParagraph = row;
                    Paragraph paragraph = document.getParagraphs().get(row);
                    textAreaText.setText(paragraph.forPrinting(document.getWidth()));
                    spinnerLeft.setValue(paragraph.getLeftIndent());
                    spinnerRight.setValue(paragraph.getRightIndent());
                    spinnerUp.setValue(paragraph.getUpIndent());
                    spinnerDown.setValue(paragraph.getDownIndent());
                    spinnerRedLine.setValue(paragraph.getRedLine());
                    //set selected alignment
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
            }
        });
        buttonUpdateParagraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Paragraph paragraph = document.getParagraphs().get(currentParagraph);
                paragraph.setName(Objects.requireNonNull(Utils.tableToArray(tableParagraphs))[currentParagraph]);
                paragraph.setContents(textAreaText.getText());
                paragraph.setLeftIndent((int) spinnerLeft.getValue());
                paragraph.setRightIndent((int) spinnerRight.getValue());
                paragraph.setUpIndent((int) spinnerUp.getValue());
                paragraph.setDownIndent((int) spinnerDown.getValue());
                paragraph.setRedLine((int) spinnerRedLine.getValue());
                paragraph.setAlignment(getRadioButtonAlignment());
                paragraph.setListAttribute(getRadioButtonListAttribute());
                paragraph.setNumberFrom(Integer.parseInt(textFieldNumberFrom.getText()));
                paragraph.setMarker(textFieldMarker.getText().charAt(0));
                //style
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
        panelMain.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        scrollPaneText = new JScrollPane();
        panelMain.add(scrollPaneText, new GridConstraints(2, 0, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textAreaText = new JTextArea();
        textAreaText.setColumns(40);
        scrollPaneText.setViewportView(textAreaText);
        panelSettings = new JPanel();
        panelSettings.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
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
        panelSettings.add(buttonSave, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelT = new JLabel();
        labelT.setText("Text");
        panelMain.add(labelT, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelAlignment = new JPanel();
        panelAlignment.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelAlignment, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        radioButtonLeft = new JRadioButton();
        radioButtonLeft.setText("Left Alignment");
        panelAlignment.add(radioButtonLeft, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonRight = new JRadioButton();
        radioButtonRight.setText("Right Alignment");
        panelAlignment.add(radioButtonRight, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonCenter = new JRadioButton();
        radioButtonCenter.setText("Center Alignment");
        panelAlignment.add(radioButtonCenter, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonWidth = new JRadioButton();
        radioButtonWidth.setText("Width Alignment");
        panelAlignment.add(radioButtonWidth, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelListAttribute = new JPanel();
        panelListAttribute.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelListAttribute, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        radioButtonWithoutList = new JRadioButton();
        radioButtonWithoutList.setText("Without List");
        panelListAttribute.add(radioButtonWithoutList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonNumbered = new JRadioButton();
        radioButtonNumbered.setText("Numbered List");
        panelListAttribute.add(radioButtonNumbered, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radioButtonMarked = new JRadioButton();
        radioButtonMarked.setText("Marked List");
        panelListAttribute.add(radioButtonMarked, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldNumberFrom = new JTextField();
        panelListAttribute.add(textFieldNumberFrom, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldMarker = new JTextField();
        panelListAttribute.add(textFieldMarker, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        panelParagraphButtons = new JPanel();
        panelParagraphButtons.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelParagraphButtons, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonAddParagraph = new JButton();
        buttonAddParagraph.setText("+");
        panelParagraphButtons.add(buttonAddParagraph, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonDelete = new JButton();
        buttonDelete.setText("-");
        panelParagraphButtons.add(buttonDelete, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonUpdateParagraph = new JButton();
        buttonUpdateParagraph.setText("Update");
        panelParagraphButtons.add(buttonUpdateParagraph, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelStyleButtons = new JPanel();
        panelStyleButtons.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelStyleButtons, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonAddStyle = new JButton();
        buttonAddStyle.setText("+");
        panelStyleButtons.add(buttonAddStyle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonDeleteStyle = new JButton();
        buttonDeleteStyle.setText("-");
        panelStyleButtons.add(buttonDeleteStyle, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
