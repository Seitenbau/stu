package com.seitenbau.testing.gui.db;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DumpDatabaseLog extends JDialog
{
  private static final long serialVersionUID = 1L;

  private JTextArea textArea;
  
  private JButton okButton;

  public DumpDatabaseLog() {
    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    textArea = new JTextArea(30, 80);
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(
        textArea,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    okButton = new JButton();
    okButton.setText("Ok");
    okButton.setSize(100, 100);
    getContentPane().add(okButton);
    setSize(getPreferredSize());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    setTitle("sb-testing-db : dump Database Log");
  }

  public void append(String text)
  {
    textArea.append(text);
  }
  
  public void finish()
  {
    okButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        setVisible(false);
        dispose();
      }
    });

    okButton.setVisible(true);
  }
}
