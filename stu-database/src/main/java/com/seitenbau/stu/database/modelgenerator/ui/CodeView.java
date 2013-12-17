package com.seitenbau.stu.database.modelgenerator.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class CodeView extends JPanel
{

  private static final long serialVersionUID = 1L;

  private final RSyntaxTextArea source;

  private final RTextScrollPane scrollPane;
  
  private final Font baseFont;

  public CodeView()
  {
    source = new RSyntaxTextArea();
    baseFont = source.getFont();
    source.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    source.setEditable(false);
    source.setCodeFoldingEnabled(true);
    source.setAntiAliasingEnabled(true);

    scrollPane = new RTextScrollPane(source);
    scrollPane.setViewportView(source);

    JToolBar toolBar = new JToolBar("Still draggable");
    // toolBar.add();
    toolBar.add(createResetButton("100%"));
    toolBar.add(createZoomButton("+", 1));
    toolBar.add(createZoomButton("-", -1));
    
    
    setLayout(new MigLayout("fill", "0[grow, fill]0", "0[]0[grow, fill]0"));
    add(toolBar, "wrap");
    add(scrollPane);
  }
  
  public CodeView(String syntaxStyle)
  {
    this();
    source.setSyntaxEditingStyle(syntaxStyle);
  }

  public void setSource(String text)
  {
    source.setText(text);
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        scrollPane.getHorizontalScrollBar().setValue(0);
        scrollPane.getVerticalScrollBar().setValue(0);
      }
    });
  }

  public void clear()
  {
    source.setText("");
  }
  
  private void zoom(int delta)
  {
    Font font = source.getFont();
    source.setFont(font.deriveFont(font.getSize2D() + delta));
  }

  protected JButton createResetButton(String text)
  {
    JButton button = new JButton(text);
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        source.setFont(baseFont);
      }
      
    });
    return button;
  }

  protected JButton createZoomButton(String text, final int delta)
  {
    JButton button = new JButton(text);
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        zoom(delta);
      }
      
    });
    return button;
  }

}
