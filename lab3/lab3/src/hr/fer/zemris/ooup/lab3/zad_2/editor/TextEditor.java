package hr.fer.zemris.ooup.lab3.zad_2.editor;

import hr.fer.zemris.ooup.lab3.zad_2.actions.*;
import hr.fer.zemris.ooup.lab3.zad_2.icon.IconManager;
import hr.fer.zemris.ooup.lab3.zad_2.observers.clipboard.ClipboardStack;
import hr.fer.zemris.ooup.lab3.zad_2.observers.stack.UndoManager;
import hr.fer.zemris.ooup.lab3.zad_2.plugins.IPlugin;
import hr.fer.zemris.ooup.lab3.zad_2.plugins.PluginsFactory;
import hr.fer.zemris.ooup.lab3.zad_2.position.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class TextEditor extends JFrame {

    private TextEditorModel model;
    private TextEditorArea area;
    private ClipboardStack clipboard;
    private UndoManager undoManager;

    // Actions

    private CopyAction copyAction;
    private CutAction cutAction;
    private PasteAction pasteAction;
    private PastePopAction pastePopAction;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private OpenAction openAction;
    private SaveAction saveAction;
    private DeleteSelectionAction deleteAction;
    private ClearAction clearTextAction;
    private Action moveToStartAction;
    private Action moveToEndAction;

    private Path filePath = null;

    public TextEditor(TextEditorModel model) {
        this.model = model;
        this.clipboard = new ClipboardStack();
        this.undoManager = UndoManager.getInstance();
        this.area = new TextEditorArea(model);
        setFocusTraversalKeysEnabled(false);
        initActions();
        initCursorObserver();
        initTextObserver();
        initKeyListeners();
        initGUI();
    }

    private void initGUI() {
        setSize(500, 300);
        setTitle("JNotepad");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initEditor();
        initMenuBar();
        initToolbar();
        initStatusBar();
    }

    private void initStatusBar() {
        JStatusBar statusBar = new JStatusBar(model);
        model.addTextObserver(statusBar);
        model.addCursorObserver(statusBar);
        add(statusBar, BorderLayout.PAGE_END);
    }

    private void initToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFocusTraversalKeysEnabled(false);

        toolBar.add(openAction);
        toolBar.add(saveAction);
        toolBar.addSeparator();
        toolBar.add(undoAction);
        toolBar.add(redoAction);
        toolBar.addSeparator();
        toolBar.add(copyAction);
        toolBar.add(cutAction);
        toolBar.add(pasteAction);
        toolBar.setFloatable(false);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openAction);
        fileMenu.add(saveAction);
        fileMenu.add(new AbstractAction("Exit", IconManager.EXIT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        // File menu actions
        JMenu editMenu = new JMenu("Edit");
        // Edit menu actions

        editMenu.add(undoAction);
        editMenu.add(redoAction);
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);
        editMenu.add(pastePopAction);
        editMenu.add(deleteAction);
        editMenu.add(clearTextAction);

        JMenu moveMenu = new JMenu("Move");
        moveMenu.add(moveToStartAction);
        moveMenu.add(moveToEndAction);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(moveMenu);

        initPlugins(menuBar);
        setJMenuBar(menuBar);
    }

    private void initPlugins(JMenuBar menuBar) {
        JMenu pluginsMenu = new JMenu("Plugins");
        List<IPlugin> plugins = PluginsFactory.getPlugins();
        plugins.forEach((plugin) -> {
            Action pluginAction = new AbstractAction(plugin.getName()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    plugin.execute(model, undoManager, clipboard);
                }
            };
            pluginsMenu.add(pluginAction);
        });
        menuBar.add(pluginsMenu);
    }

    private void initEditor() {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setAutoscrolls(true);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void initActions() {
        copyAction = new CopyAction("Copy", IconManager.COPY, this);
        copyAction.setEnabled(false);
        copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        model.addSelectionObserver(copyAction);

        cutAction = new CutAction("Cut", IconManager.CUT, this);
        cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        cutAction.setEnabled(false);
        model.addSelectionObserver(cutAction);

        pasteAction = new PasteAction("Paste", IconManager.PASTE, this);
        pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        pasteAction.setEnabled(false);

        pastePopAction = new PastePopAction("Paste 'n' Pop", null, this);
        pastePopAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
        pastePopAction.setEnabled(false);

        clipboard.addClipboardObserver(pasteAction);
        clipboard.addClipboardObserver(pastePopAction);

        undoAction = new UndoAction("Undo", IconManager.UNDO, this);
        undoAction.setEnabled(false);
        undoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
        redoAction = new RedoAction("Redo", IconManager.REDO, this);
        redoAction.setEnabled(false);
        redoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y"));
        undoManager.addUndoObserver(undoAction);
        undoManager.addRedoObserver(redoAction);

        openAction = new OpenAction("Open", IconManager.OPEN, this);
        openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));

        saveAction = new SaveAction("Save", IconManager.SAVE, this);
        saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));

        deleteAction = new DeleteSelectionAction("Delete selection", null, this);
        deleteAction.setEnabled(false);
        model.addSelectionObserver(deleteAction);

        clearTextAction = new ClearAction("Clear text", null, model);
        moveToEndAction = new MoveAction("Move cursor to end", null, model, true);
        moveToStartAction = new MoveAction("Move cursor to start", null, model, false);
    }

    private void initKeyListeners() {
        initCursorKeyListeners();
        initSelectionKeyListeners();
        initDeletionKeyListeners();
        initPrintableKeyListeners();
    }

    private void initPrintableKeyListeners() {
        area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 0:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_BACK_SPACE:
                    case KeyEvent.VK_DELETE:
                    case KeyEvent.CHAR_UNDEFINED:
                        return;
                    default:
                        if(!e.isActionKey() && !e.isMetaDown() && e.getKeyCode() != KeyEvent.VK_SHIFT &&
                                e.getKeyCode() != KeyEvent.VK_ALT && e.getKeyCode() != KeyEvent.VK_ALT_GRAPH &&
                                !e.isControlDown() && e.getKeyCode() != KeyEvent.VK_ESCAPE) {
                            char c = e.getKeyChar();
                            if (c == '\t') {
                                model.insert("    ");
                            } else {
                                model.insert(c);
                            }
                        }
                        break;
                }
            }
        });
    }

    private void initDeletionKeyListeners() {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
        area.getInputMap().put(stroke, "BACKSPACE");
        Action backspaceAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.hasSelection()) {
                    model.deleteRange(model.getSelectionRange());
                } else {
                    model.deleteBefore();
                }
            }
        };
        area.getActionMap().put("BACKSPACE", backspaceAction);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        area.getInputMap().put(stroke, "DELETE");
        Action deleteAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.hasSelection()) {
                    model.deleteRange(model.getSelectionRange());
                } else {
                    model.deleteAfter();
                }
            }
        };
        area.getActionMap().put("DELETE", deleteAction);
    }

    private void initSelectionKeyListeners() {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.SHIFT_DOWN_MASK, false);
        area.getInputMap().put(stroke, "SELECTION_UP");
        Action upAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveSelectionUp();
            }
        };
        area.getActionMap().put("SELECTION_UP", upAction);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.SHIFT_DOWN_MASK, false);
        area.getInputMap().put(stroke, "SELECTION_DOWN");
        Action downAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveSelectionDown();
            }
        };
        area.getActionMap().put("SELECTION_DOWN", downAction);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.SHIFT_DOWN_MASK, false);
        area.getInputMap().put(stroke, "SELECTION_LEFT");
        Action leftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveSelectionLeft();
            }
        };
        area.getActionMap().put("SELECTION_LEFT", leftAction);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.SHIFT_DOWN_MASK, false);
        area.getInputMap().put(stroke, "SELECTION_RIGHT");
        Action rightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveSelectionRight();
            }
        };
        area.getActionMap().put("SELECTION_RIGHT", rightAction);
    }

    private void initCursorKeyListeners() {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        area.getInputMap().put(stroke, "CURSOR_UP");
        Action upAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveCursorUp();
                model.setSelectionRange(null);
            }
        };
        area.getActionMap().put("CURSOR_UP", upAction);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        area.getInputMap().put(stroke, "CURSOR_DOWN");
        Action downAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveCursorDown();
                model.setSelectionRange(null);
            }
        };
        area.getActionMap().put("CURSOR_DOWN", downAction);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
        area.getInputMap().put(stroke, "CURSOR_LEFT");
        Action leftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveCursorLeft();
                model.setSelectionRange(null);
            }
        };
        area.getActionMap().put("CURSOR_LEFT", leftAction);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
        area.getInputMap().put(stroke, "CURSOR_RIGHT");
        Action rightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.moveCursorRight();
                model.setSelectionRange(null);
            }
        };
        area.getActionMap().put("CURSOR_RIGHT", rightAction);
    }

    private void initCursorObserver() {
        model.addCursorObserver((Location location) -> {
            area.revalidate();
            area.repaint();
        });
    }

    private void initTextObserver() {
        model.addTextObserver(() -> {
            area.revalidate();
            area.repaint();
        });
    }

    // Getters and setters

    public TextEditorModel getModel() {
        return model;
    }

    public ClipboardStack getClipboard() {
        return clipboard;
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean requestFocusInWindow() {
        return area.requestFocusInWindow();
    }

    // Main method

    public static void main(String[] args) {
        PluginsFactory.getPlugins();
        SwingUtilities.invokeLater(() -> {
            TextEditorModel model = new TextEditorModel("Ja sam jedan jaaaaaaaaaaaaaaaaaaaaaaaaako duu\tuuuuuuuuugiiiiiiiii reeedaaak text\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nNovi redak\nJos jedan novi.");
            JFrame frame = new TextEditor(model);
            frame.setVisible(true);
        });
    }
}
