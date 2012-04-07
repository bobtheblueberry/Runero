package org.gcreator.runero;

/*
 * Copyright (C) 2008 Clam <clamisgood@gmail.com>
 * Copyright (C) 2011 IsmAvatar <IsmAvatar@gmail.com>
 * 
 * This file is part of LateralGM.
 * LateralGM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ErrorDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int DEBUG_HEIGHT = 200;

    protected JTextArea debugInfo;
    protected JButton copy;
    protected JButton ok;

    private static JButton makeButton(String name, ActionListener listener) {
        JButton but = new JButton(name);
        but.addActionListener(listener);
        return but;
    }

    public ErrorDialog(Frame parent, String title, String message, Throwable e) {
        this(parent, title, message, throwableToString(e));
    }

    public ErrorDialog(Frame parent, String title, String message, String debugInfo) {
        super(parent, title, true);
        setResizable(false);

        this.debugInfo = new JTextArea(debugInfo);
        JScrollPane scroll = new JScrollPane(this.debugInfo);

        Dimension dim = new Dimension(scroll.getWidth(), DEBUG_HEIGHT);
        scroll.setPreferredSize(dim);
        copy = makeButton("Copy", this);
        ok = makeButton("Ok", this);
        dim = new Dimension(Math.max(copy.getPreferredSize().width, ok.getPreferredSize().width),
                copy.getPreferredSize().height);
        copy.setPreferredSize(dim);
        ok.setPreferredSize(dim);
        JOptionPane wtfwjd = new JOptionPane(new Object[] { message, scroll }, JOptionPane.ERROR_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new JButton[] { copy, ok });
        add(wtfwjd);
        setSize(640, 480);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    protected static String throwableToString(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == copy) {
            debugInfo.selectAll();
            debugInfo.copy();
        } else if (e.getSource() == ok)
            dispose();
    }
}