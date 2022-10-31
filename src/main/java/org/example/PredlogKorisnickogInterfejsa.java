package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PredlogKorisnickogInterfejsa extends JDialog {
    private JPanel contentPane;
    private JButton buttonClose;
    private JButton buttonSave;
    private JButton buttonOpenTop;

    private JTextArea textAreaTop;
    private JTextArea textAreaBottom;
    private JTextArea textAreaNew;
    private JButton buttonOpenBottom;
    private JButton buttonBottomToNew;
    private JButton buttonTopToNew;
    String directory;
    String selection;

    public PredlogKorisnickogInterfejsa() {
        setContentPane(contentPane);
        setModal(true);
        //    getRootPane().setDefaultButton(buttonOpen);

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        });


        // call onCancel() when cross is clicked
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onButtonClose();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        buttonOpenTop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonTop();
            }
        });
        buttonOpenBottom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonBottom();  }
        });


        //bottom to new button
        buttonBottomToNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonGetSelection(textAreaBottom);
            }
        });
        //top to new button
        buttonTopToNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonGetSelection(textAreaTop);
            }
        });


        //save the text from new to file
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonSave();
            }
        });
    }

    //function save file
    public void saveFile(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileWriter out = null;
        try {
            file = new File(directory, filename); // Create a file object
            out = new FileWriter(file); // And a char stream to write it
            textAreaNew.getLineCount(); // Get text from the text area
            String s = textAreaNew.getText();
            out.write(s);
        }
        // Display messages if something goes wrong
        catch (IOException e) {
            textAreaNew.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        // Always be sure to close the input stream!
        finally {
            try {
                if (out != null)
                    out.close();
            }
            catch (IOException e) {
            }
        }
    }


    private void onButtonSave() {
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.SAVE);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        saveFile(directory, f.getFile()); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }


    private void onButtonClose() {
        // add your code here if necessary
        dispose();
    }
    private void onButtonTop(){
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory);
        f.setVisible(true);
        directory = f.getDirectory();
        loadAndDisplayFile(directory, f.getFile(), textAreaTop);
        f.dispose();
    }
    private void onButtonBottom(){
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory);

        f.setVisible(true);
        directory = f.getDirectory();
        loadAndDisplayFile(directory, f.getFile(), textAreaBottom);
        f.dispose();
    }
    public void loadAndDisplayFile(String directory, String filename, JTextArea txtArea) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileReader in = null;
        // Read and display the file contents. Since we're reading text, we
        // use a FileReader instead of a FileInputStream.
        try {
            file = new File(directory, filename); // Create a file object
            in = new FileReader(file); // And a char stream to read it
            char[] buffer = new char[4096]; // Read 4K characters at a time
            int len; // How many chars read each time
            txtArea.setText(""); // Clear the text area
            while ((len = in.read(buffer)) != -1) { // Read a batch of chars
                String s = new String(buffer, 0, len); // Convert to a string
                txtArea.append(s); // And display them
            }
            this.setTitle("FileViewer: " + filename); // Set the window title
            txtArea.setCaretPosition(0); // Go to start of file
        }
        // Display messages if something goes wrong
        catch (IOException e) {
            txtArea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        // Always be sure to close the input stream!
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    //copy selected text to new
    private void onButtonGetSelection(JTextArea textArea) {

        selection = textArea.getSelectedText();
        textAreaNew.append(selection);
    }




    public static void main(String[] args) {
        PredlogKorisnickogInterfejsa dialog = new PredlogKorisnickogInterfejsa();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}