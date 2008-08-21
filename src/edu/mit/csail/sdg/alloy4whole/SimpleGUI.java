/*
 * Alloy Analyzer 4 -- Copyright (c) 2006-2008, Felix Chang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.mit.csail.sdg.alloy4whole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.prefs.Preferences;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import static java.awt.event.KeyEvent.VK_PAGE_UP;
import static java.awt.event.KeyEvent.VK_PAGE_DOWN;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;
import static java.awt.event.KeyEvent.VK_Z;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import kodkod.engine.fol2sat.HigherOrderDeclException;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4compiler.ast.Command;
import edu.mit.csail.sdg.alloy4compiler.ast.Expr;
import edu.mit.csail.sdg.alloy4compiler.ast.ExprVar;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig.Field;
import edu.mit.csail.sdg.alloy4compiler.parser.CompUtil;
import edu.mit.csail.sdg.alloy4compiler.parser.DebugTree;
import edu.mit.csail.sdg.alloy4compiler.parser.Module;
import edu.mit.csail.sdg.alloy4compiler.sim.SimContext;
import edu.mit.csail.sdg.alloy4compiler.sim.SimTupleset;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Options;
import edu.mit.csail.sdg.alloy4compiler.translator.A4SolutionReader;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Tuple;
import edu.mit.csail.sdg.alloy4compiler.translator.A4TupleSet;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Options.SatSolver;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Computer;
import edu.mit.csail.sdg.alloy4.ErrorFatal;
import edu.mit.csail.sdg.alloy4.ErrorType;
import edu.mit.csail.sdg.alloy4.MailBug;
import edu.mit.csail.sdg.alloy4.MacUtil;
import edu.mit.csail.sdg.alloy4.OurAntiAlias;
import edu.mit.csail.sdg.alloy4.OurBorder;
import edu.mit.csail.sdg.alloy4.OurCombobox;
import edu.mit.csail.sdg.alloy4.OurDialog;
import edu.mit.csail.sdg.alloy4.OurTabbedEditor;
import edu.mit.csail.sdg.alloy4.OurTextArea;
import edu.mit.csail.sdg.alloy4.OurUtil;
import edu.mit.csail.sdg.alloy4.Pair;
import edu.mit.csail.sdg.alloy4.Pos;
import edu.mit.csail.sdg.alloy4.Runner;
import edu.mit.csail.sdg.alloy4.Subprocess;
import edu.mit.csail.sdg.alloy4.Util;
import edu.mit.csail.sdg.alloy4.Version;
import edu.mit.csail.sdg.alloy4.WorkerEngine;
import edu.mit.csail.sdg.alloy4.XMLNode;
import edu.mit.csail.sdg.alloy4.Util.BooleanPref;
import edu.mit.csail.sdg.alloy4.Util.IntPref;
import edu.mit.csail.sdg.alloy4.Util.StringPref;
import edu.mit.csail.sdg.alloy4.WorkerEngine.WorkerCallback;
import edu.mit.csail.sdg.alloy4viz.VizGUI;
import edu.mit.csail.sdg.alloy4whole.SimpleReporter.SimpleCallback1;
import edu.mit.csail.sdg.alloy4whole.SimpleReporter.SimpleTask1;
import edu.mit.csail.sdg.alloy4whole.SimpleReporter.SimpleTask2;

/**
 * Simple graphical interface for accessing various features of the analyzer.
 *
 * <p> Except noted below, methods in this class can only be called by the AWT event thread.
 *
 * <p> The methods that might get called from other threads are:
 * <br> (1) the run() method in SatRunner is launched from a fresh thread
 * <br> (2) the run() method in the instance watcher (in constructor) is launched from a fresh thread
 */

public final class SimpleGUI implements ComponentListener {

    /** The latest welcome screen; each time we update the welcome screen, we increment this number. */
    private static final int welcomeLevel = 2;

    // Verify that the graphics environment is set up
    static {
        try {
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        } catch(Throwable ex) {
            System.err.println("Unable to start the graphical environment.");
            System.err.println("If you're on Mac OS X:");
            System.err.println("   Please make sure you are running as the current local user.");
            System.err.println("If you're on Linux or FreeBSD:");
            System.err.println("   Please make sure your X Windows is configured.");
            System.err.println("   You can verify this by typing \"xhost\"; it should not give an error message.");
            System.err.flush();
            System.exit(1);
        }
    }

    //======== The Preferences ======================================================================================//
    //======== Note: you must make sure each preference has a unique key ============================================//

    /** The list of allowable memory sizes. */
    private List<Integer> allowedMemorySizes;

    /** True if Alloy Analyzer should let warning be nonfatal. */
    private static final BooleanPref WarningNonfatal = new BooleanPref("WarningNonfatal");

    /** True if Alloy Analyzer should automatically visualize the latest instance. */
    private static final BooleanPref AutoVisualize = new BooleanPref("AutoVisualize");

    /** True if Alloy Analyzer should insist on antialias. */
    private static final BooleanPref AntiAlias = new BooleanPref("AntiAlias");

    /** True if Alloy Analyzer should record the raw Kodkod input and output. */
    private static final BooleanPref RecordKodkod = new BooleanPref("RecordKodkod");

    /** True if Alloy Analyzer should enable the new Implicit This name resolution. */
    private static final BooleanPref ImplicitThis = new BooleanPref("ImplicitThis");

    /** The latest X corrdinate of the Alloy Analyzer's main window. */
    private static final IntPref AnalyzerX = new IntPref("AnalyzerX",0,-1,65535);

    /** The latest Y corrdinate of the Alloy Analyzer's main window. */
    private static final IntPref AnalyzerY = new IntPref("AnalyzerY",0,-1,65535);

    /** The latest width of the Alloy Analyzer's main window. */
    private static final IntPref AnalyzerWidth = new IntPref("AnalyzerWidth",0,-1,65535);

    /** The latest height of the Alloy Analyzer's main window. */
    private static final IntPref AnalyzerHeight = new IntPref("AnalyzerHeight",0,-1,65535);

    /** The latest font size of the Alloy Analyzer. */
    private static final IntPref FontSize = new IntPref("FontSize",9,12,72);

    /** The latest font name of the Alloy Analyzer. */
    private static final StringPref FontName = new StringPref("FontName","Lucida Grande");

    /** The latest tab distance of the Alloy Analyzer. */
    private static final IntPref TabSize = new IntPref("TabSize",1,2,16);

    /** The latest welcome screen that the user has seen. */
    private static final IntPref Welcome = new IntPref("Welcome",0,0,1000);

    /** Whether syntax highlighting should be disabled or not. */
    private static final BooleanPref SyntaxDisabled = new BooleanPref("SyntaxHighlightingDisabled");

    /** The skolem depth. */
    private static final IntPref SkolemDepth = new IntPref("SkolemDepth3",0,1,4);

    /** The unsat core minimization strategy. */
    private static final IntPref CoreMinimization = new IntPref("CoreMinimization",0,2,2);

    /** The amount of memory (in M) to allocate for Kodkod and the SAT solvers. */
    private static final IntPref SubMemory = new IntPref("SubMemory",16,768,65535);

    /** The amount of stack (in K) to allocate for Kodkod and the SAT solvers. */
    private static final IntPref SubStack = new IntPref("SubStack",16,8192,65536);

    /** The first file in Alloy Analyzer's "open recent" list. */
    private static final StringPref Model0 = new StringPref("Model0");

    /** The second file in Alloy Analyzer's "open recent" list. */
    private static final StringPref Model1 = new StringPref("Model1");

    /** The third file in Alloy Analyzer's "open recent" list. */
    private static final StringPref Model2 = new StringPref("Model2");

    /** The fourth file in Alloy Analyzer's "open recent" list. */
    private static final StringPref Model3 = new StringPref("Model3");

    /** This enum defines the set of possible message verbosity levels. */
    private enum Verbosity {
        /** Level 0. */  DEFAULT("0", "low"),
        /** Level 1. */  VERBOSE("1", "medium"),
        /** Level 2. */  DEBUG("2", "high"),
        /** Level 3. */  FULLDEBUG("3", "debug only");
        /** Returns true if it is greater than or equal to "other". */
        public boolean geq(Verbosity other) { return ordinal() >= other.ordinal(); }
        /** This is a unique String for this value; it should be kept consistent in future versions. */
        private final String id;
        /** This is the label that the toString() method will return. */
        private final String label;
        /** Constructs a new Verbosity value with the given id and label. */
        private Verbosity(String id, String label) { this.id=id; this.label=label; }
        /** Given an id, return the enum value corresponding to it (if there's no match, then return DEFAULT). */
        private static Verbosity parse(String id) {
            for(Verbosity vb:values()) if (vb.id.equals(id)) return vb;
            return DEFAULT;
        }
        /** Returns the human-readable label for this enum value. */
        @Override public final String toString() { return label; }
        /** Saves this value into the Java preference object. */
        private void set() { Preferences.userNodeForPackage(Util.class).put("Verbosity",id); }
        /** Reads the current value of the Java preference object (if it's not set, then return DEFAULT). */
        private static Verbosity get() { return parse(Preferences.userNodeForPackage(Util.class).get("Verbosity","")); }
    };

    //===================================================================================================//

    /** The uncaught exception handler. */
    private final MailBug exitReporter;

    /** The JFrame for the main window. */
    private JFrame frame;

    /** The JFrame for the visualizer window. */
    private VizGUI viz;

    /** The "File", "Edit", "Run", "Option", "Window", and "Help" menus. */
    private JMenu filemenu, editmenu, runmenu, optmenu, windowmenu, windowmenu2, helpmenu;

    /** The toolbar. */
    private JToolBar toolbar;

    /** The various toolbar buttons. */
    private JButton runbutton, stopbutton, showbutton;

    /** The Splitpane. */
    private JSplitPane splitpane;

    /** The JLabel that displays the current line/column position, etc. */
    private JLabel status;

    /** Whether the editor has the focus, or the log window has the focus. */
    private boolean lastFocusIsOnEditor = true;

    /** The text editor. */
    private OurTabbedEditor text;

    /** The "message panel" on the right. */
    private SwingLogPanel log;

    /** The scrollpane containing the "message panel". */
    private JScrollPane logpane;

    /** The last "find" that the user issued. */
    private String lastFind = "";

    /** The last find is case-sensitive or not. */
    private boolean lastFindCaseSensitive = true;

    /** The last find is forward or not. */
    private boolean lastFindForward = true;

    /** The icon for a "checked" menu item. */
    private static final Icon iconYes = OurUtil.loadIcon("images/menu1.gif");

    /** The icon for an "unchecked" menu item. */
    private static final Icon iconNo = OurUtil.loadIcon("images/menu0.gif");

    /** The system-specific file separator (forward-slash on UNIX, back-slash on Windows, etc.) */
    private static final String fs = System.getProperty("file.separator");

    /** The darker background color (for the MessageLog window and the Toolbar and the Status Bar, etc.) */
    private static final Color background = new Color(0.9f, 0.9f, 0.9f);

    /** Whether we should force ".als" when opening a file. */
    private boolean openAlsOnly = true;

    /** If subrunning==true: 0 means SAT solving; 1 means metamodel; 2 means enumeration. */
    private int subrunningTask = 0;

    /** The amount of memory (in MB) currently allocated for this.subprocess */
    private int subMemoryNow = 0;

    /** The amount of stack (in KB) currently allocated for this.subprocess */
    private int subStackNow = 0;

    /** The list of commands (this field will be cleared to null when the text buffer is edited). */
    private List<Command> commands = null;

    /** The latest executed command. */
    private int latestCommand = 0;

    /** The current choices of SAT solver. */
    private List<SatSolver> satChoices;

     /** The most recent Alloy version (as queried from alloy.mit.edu); -1 if alloy.mit.edu has not replied yet. */
    private int latestAlloyVersion = (-1);

    /** The most recent Alloy version name (as queried from alloy.mit.edu); "unknown" if alloy.mit.edu has not replied yet. */
    private String latestAlloyVersionName = "unknown";

    /** If it's not "", then it is the XML filename for the latest satisfying instance or the latest metamodel. */
    private String latestInstance = "";

    /** If it's not "", then it is the latest instance or metamodel during the most recent click of "Execute". */
    private String latestAutoInstance = "";

    /** If true, that means the event handlers should return a Runner encapsulating them, rather than perform the actual work. */
    private boolean wrap = false;

    //====== helper methods =================================================//

    /** Inserts "filename" into the "recently opened file list". */
    private void addHistory(String filename) {
        String name0=Model0.get(), name1=Model1.get(), name2=Model2.get();
        if (name0.equals(filename)) return; else {Model0.set(filename); Model1.set(name0);}
        if (name1.equals(filename)) return; else Model2.set(name1);
        if (name2.equals(filename)) return; else Model3.set(name2);
    }

    /** Sets the flag "lastFocusIsOnEditor" to be true. */
    private Runner notifyFocusGained() {
        if (wrap) return wrapMe();
        lastFocusIsOnEditor=true;
        return null;
    }

    /** Sets the flag "lastFocusIsOnEditor" to be false. */
    void notifyFocusLost() { lastFocusIsOnEditor=false; }

    /** Updates the status bar at the bottom of the screen. */
    private Runner notifyChange() {
        if (wrap) return wrapMe();
        commands=null;
        if (text==null) return null; // If this was called prior to the "text" being fully initialized
        if (Util.onMac()) frame.getRootPane().putClientProperty("windowModified", Boolean.valueOf(text.do_modified()));
        if (text.do_isFile()) frame.setTitle(text.do_getFilename()); else frame.setTitle("Alloy Analyzer "+Version.version());
        toolbar.setBorder(new OurBorder(false, false, text.do_getTabCount()<=1, false));
        try {
            int c=text.do_text().getCaretPosition();
            int y=text.do_text().do_getLineOfOffset(c)+1;
            int x=c-text.do_text().do_getLineStartOffset(y-1)+1;
            status.setText("<html>&nbsp; Line "+y+", Column "+x
                    +(text.do_modified()?" <b style=\"color:#B43333;\">[modified]</b></html>":"</html>"));
        } catch(BadLocationException ex) {
            status.setText("<html>&nbsp; Line ?, Column ?"
                    +(text.do_modified()?" <b style=\"color:#B43333;\">[modified]</b></html>":"</html>"));
        }
        return null;
    }

    /** Helper method that returns a hopefully very short name for a file name. */
    public static String slightlyShorterFilename(String name) {
        if (name.toLowerCase(Locale.US).endsWith(".als")) {
            int i=name.lastIndexOf('/');
            if (i>=0) name=name.substring(i+1);
            i=name.lastIndexOf('\\');
            if (i>=0) name=name.substring(i+1);
            return name.substring(0, name.length()-4);
        } else if (name.toLowerCase(Locale.US).endsWith(".xml")) {
            int i=name.lastIndexOf('/');
            if (i>0) i=name.lastIndexOf('/', i-1);
            if (i>=0) name=name.substring(i+1);
            i=name.lastIndexOf('\\');
            if (i>0) i=name.lastIndexOf('\\', i-1);
            if (i>=0) name=name.substring(i+1);
            return name.substring(0, name.length()-4);
        }
        return name;
    }

    /** Copy the required files from the JAR into a temporary directory. */
    private void copyFromJAR() {
        // Compute the appropriate platform
        String os = System.getProperty("os.name").toLowerCase(Locale.US).replace(' ','-');
        if (os.startsWith("mac-")) os="mac"; else if (os.startsWith("windows-")) os="windows";
        String arch = System.getProperty("os.arch").toLowerCase(Locale.US).replace(' ','-');
        if (arch.equals("powerpc")) arch="ppc-"+os; else arch=arch.replaceAll("\\Ai[3456]86\\z","x86")+"-"+os;
        // Find out the appropriate Alloy directory
        final String platformBinary=Helper.alloyHome()+fs+"binary";
        // Write a few test files
        try {
            (new File(platformBinary)).mkdirs();
            Util.writeAll(platformBinary+fs+"tmp.cnf", "p cnf 3 1\n1 0\n");
        } catch(Err er) {
            // The error will be caught later by the "berkmin" or "spear" test
        }
        // Copy the platform-dependent binaries
        Util.copy(true, false, platformBinary,
           arch+"/libminisat.so", arch+"/libminisatx1.so", arch+"/libminisat.jnilib",
           arch+"/libminisatprover.so", arch+"/libminisatproverx1.so", arch+"/libminisatprover.jnilib",
           arch+"/libzchaff.so", arch+"/libzchaffx1.so", arch+"/libzchaff.jnilib",
           arch+"/berkmin", arch+"/spear");
        Util.copy(false, false, platformBinary,
           arch+"/minisat.dll", arch+"/minisatprover.dll", arch+"/zchaff.dll",
           arch+"/berkmin.exe", arch+"/spear.exe");
        // Copy the model files
        Util.copy(false, true, Helper.alloyHome(),
           "models/book/appendixA/addressBook1.als", "models/book/appendixA/addressBook2.als", "models/book/appendixA/barbers.als",
           "models/book/appendixA/closure.als", "models/book/appendixA/distribution.als", "models/book/appendixA/phones.als",
           "models/book/appendixA/prison.als", "models/book/appendixA/properties.als", "models/book/appendixA/ring.als",
           "models/book/appendixA/spanning.als", "models/book/appendixA/tree.als", "models/book/appendixA/tube.als", "models/book/appendixA/undirected.als",
           "models/book/appendixE/hotel.thm", "models/book/appendixE/p300-hotel.als", "models/book/appendixE/p303-hotel.als", "models/book/appendixE/p306-hotel.als",
           "models/book/chapter2/addressBook1a.als", "models/book/chapter2/addressBook1b.als", "models/book/chapter2/addressBook1c.als",
           "models/book/chapter2/addressBook1d.als", "models/book/chapter2/addressBook1e.als", "models/book/chapter2/addressBook1f.als",
           "models/book/chapter2/addressBook1g.als", "models/book/chapter2/addressBook1h.als", "models/book/chapter2/addressBook2a.als",
           "models/book/chapter2/addressBook2b.als", "models/book/chapter2/addressBook2c.als", "models/book/chapter2/addressBook2d.als",
           "models/book/chapter2/addressBook2e.als", "models/book/chapter2/addressBook3a.als", "models/book/chapter2/addressBook3b.als",
           "models/book/chapter2/addressBook3c.als", "models/book/chapter2/addressBook3d.als", "models/book/chapter2/theme.thm",
           "models/book/chapter4/filesystem.als", "models/book/chapter4/grandpa1.als",
           "models/book/chapter4/grandpa2.als", "models/book/chapter4/grandpa3.als", "models/book/chapter4/lights.als",
           "models/book/chapter5/addressBook.als", "models/book/chapter5/lists.als", "models/book/chapter5/sets1.als", "models/book/chapter5/sets2.als",
           "models/book/chapter6/hotel.thm", "models/book/chapter6/hotel1.als", "models/book/chapter6/hotel2.als",
           "models/book/chapter6/hotel3.als", "models/book/chapter6/hotel4.als", "models/book/chapter6/mediaAssets.als",
           "models/book/chapter6/memory/abstractMemory.als", "models/book/chapter6/memory/cacheMemory.als",
           "models/book/chapter6/memory/checkCache.als", "models/book/chapter6/memory/checkFixedSize.als",
           "models/book/chapter6/memory/fixedSizeMemory.als", "models/book/chapter6/memory/fixedSizeMemory_H.als",
           "models/book/chapter6/ringElection.thm", "models/book/chapter6/ringElection1.als", "models/book/chapter6/ringElection2.als",
           "models/examples/algorithms/dijkstra.als", "models/examples/algorithms/dijkstra.thm",
           "models/examples/algorithms/messaging.als", "models/examples/algorithms/messaging.thm",
           "models/examples/algorithms/opt_spantree.als", "models/examples/algorithms/opt_spantree.thm",
           "models/examples/algorithms/peterson.als",
           "models/examples/algorithms/ringlead.als", "models/examples/algorithms/ringlead.thm",
           "models/examples/algorithms/s_ringlead.als",
           "models/examples/algorithms/stable_mutex_ring.als", "models/examples/algorithms/stable_mutex_ring.thm",
           "models/examples/algorithms/stable_orient_ring.als", "models/examples/algorithms/stable_orient_ring.thm",
           "models/examples/algorithms/stable_ringlead.als", "models/examples/algorithms/stable_ringlead.thm",
           "models/examples/case_studies/INSLabel.als", "models/examples/case_studies/chord.als",
           "models/examples/case_studies/chord2.als", "models/examples/case_studies/chordbugmodel.als",
           "models/examples/case_studies/com.als", "models/examples/case_studies/firewire.als", "models/examples/case_studies/firewire.thm",
           "models/examples/case_studies/ins.als", "models/examples/case_studies/iolus.als",
           "models/examples/case_studies/sync.als", "models/examples/case_studies/syncimpl.als",
           "models/examples/puzzles/farmer.als", "models/examples/puzzles/farmer.thm",
           "models/examples/puzzles/handshake.als", "models/examples/puzzles/handshake.thm",
           "models/examples/puzzles/hanoi.als", "models/examples/puzzles/hanoi.thm",
           "models/examples/systems/file_system.als", "models/examples/systems/file_system.thm",
           "models/examples/systems/javatypes_soundness.als",
           "models/examples/systems/lists.als", "models/examples/systems/lists.thm",
           "models/examples/systems/marksweepgc.als", "models/examples/systems/views.als",
           "models/examples/toys/birthday.als", "models/examples/toys/birthday.thm",
           "models/examples/toys/ceilingsAndFloors.als", "models/examples/toys/ceilingsAndFloors.thm",
           "models/examples/toys/genealogy.als", "models/examples/toys/genealogy.thm",
           "models/examples/toys/grandpa.als", "models/examples/toys/grandpa.thm",
           "models/examples/toys/javatypes.als", "models/examples/toys/life.als", "models/examples/toys/life.thm",
           "models/examples/toys/numbering.als", "models/examples/toys/railway.als", "models/examples/toys/railway.thm",
           "models/examples/toys/trivial.als",
           "models/examples/tutorial/farmer.als",
           "models/util/boolean.als", "models/util/graph.als", "models/util/integer.als", "models/util/natural.als",
           "models/util/ordering.als", "models/util/relation.als", "models/util/seqrel.als", "models/util/sequence.als",
           "models/util/sequniv.als", "models/util/ternary.als", "models/util/time.als"
           );
        // Record the locations
        System.setProperty("alloy.theme0", Helper.alloyHome()+fs+"models");
        System.setProperty("alloy.home", Helper.alloyHome());
    }

    /** Called when this window is resized. */
    public void componentResized(ComponentEvent e) {
        componentMoved(e);
    }

    /** Called when this window is moved. */
    public void componentMoved(ComponentEvent e) {
        AnalyzerWidth.set(frame.getWidth());
        AnalyzerHeight.set(frame.getHeight());
        AnalyzerX.set(frame.getX());
        AnalyzerY.set(frame.getY());
    }

    /** Called when this window is shown. */
    public void componentShown(ComponentEvent e) {}

    /** Called when this window is hidden. */
    public void componentHidden(ComponentEvent e) {}

    /** Wraps the calling method into a Runnable whose run() will call the calling method with (false) as the only argument. */
    private Runner wrapMe() {
        final String name;
        try { throw new Exception(); } catch(Exception ex) { name = ex.getStackTrace()[1].getMethodName(); }
        Method[] methods = getClass().getDeclaredMethods();
        Method m=null;
        for(int i=0; i<methods.length; i++) if (methods[i].getName().equals(name)) { m=methods[i]; break; }
        final Method method=m;
        return new Runner() {
            private static final long serialVersionUID = 1L;
            public void run() {
                try {
                    method.setAccessible(true);
                    method.invoke(SimpleGUI.this, new Object[]{});
                } catch (Throwable ex) {
                    ex = new IllegalArgumentException("Failed call to "+name+"()", ex);
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), ex);
                }
            }
            public void run(Object arg) { run(); }
        };
    }

    /** Wraps the calling method into a Runnable whose run() will call the calling method with (false,argument) as the two arguments. */
    private Runner wrapMe(final Object argument) {
        final String name;
        try { throw new Exception(); } catch(Exception ex) { name = ex.getStackTrace()[1].getMethodName(); }
        Method[] methods = getClass().getDeclaredMethods();
        Method m=null;
        for(int i=0; i<methods.length; i++) if (methods[i].getName().equals(name)) { m=methods[i]; break; }
        final Method method=m;
        return new Runner() {
            private static final long serialVersionUID = 1L;
            public void run(Object arg) {
                try {
                    method.setAccessible(true);
                    method.invoke(SimpleGUI.this, new Object[]{arg});
                } catch (Throwable ex) {
                    ex = new IllegalArgumentException("Failed call to "+name+"("+arg+")", ex);
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), ex);
                }
            }
            public void run() { run(argument); }
        };
    }

    //===============================================================================================================//

    /** This method refreshes the "file" menu. */
    private Runner doRefreshFile() {
        if (wrap) return wrapMe();
        try {
            wrap = true;
            filemenu.removeAll();
            OurUtil.makeMenuItem(filemenu, "New",     VK_N, VK_N, doNew());
            OurUtil.makeMenuItem(filemenu, "Open...", VK_O, VK_O, doOpen());
            if (!Util.onMac())
                OurUtil.makeMenuItem(filemenu, "Open Sample Models...", VK_ALT, VK_O, doBuiltin());
            else
                OurUtil.makeMenuItem(filemenu, "Open Sample Models...", doBuiltin());
            JMenu recentmenu;
            filemenu.add(recentmenu = new JMenu("Open Recent"));
            OurUtil.makeMenuItem(filemenu, "Reload all", VK_R, VK_R, doReloadAll());
            OurUtil.makeMenuItem(filemenu, "Save",       VK_S, VK_S, doSave());
            if (Util.onMac())
                OurUtil.makeMenuItem(filemenu, "Save As...", VK_SHIFT, VK_S, doSaveAs());
            else
                OurUtil.makeMenuItem(filemenu, "Save As...", VK_A, doSaveAs());
            OurUtil.makeMenuItem(filemenu,     "Close",                     VK_W, VK_W,                   doClose());
            OurUtil.makeMenuItem(filemenu,     "Clear Temporary Directory",                               doClearTemp());
            OurUtil.makeMenuItem(filemenu,     "Quit",                      VK_Q, (Util.onMac()?-1:VK_Q), doQuit());
            boolean found=false;
            for(Util.StringPref p: new Util.StringPref[]{ Model0, Model1, Model2, Model3 }) {
                final String name = p.get();
                if (name.length()==0) continue;
                found=true;
                OurUtil.makeMenuItem(recentmenu, name, doOpenFile(name));
            }
            recentmenu.addSeparator();
            OurUtil.makeMenuItem(recentmenu, "Clear Menu", doClearRecent());
            recentmenu.setEnabled(found);
        } finally {
            wrap = false;
        }
        return null;
    }

    /** This method performs File->New. */
    private Runner doNew() {
        if (!wrap) { text.do_newTab(); notifyChange(); doShow(); }
        return wrapMe();
    }

    /** This method performs File->Open. */
    private Runner doOpen() {
        if (wrap) return wrapMe();
        File file=OurDialog.askFile(frame, true, null, openAlsOnly?".als":"", ".als files");
        if (file!=null) {
            if (!file.getPath().toLowerCase(Locale.US).endsWith(".als")) openAlsOnly=false;
            Util.setCurrentDirectory(file.getParentFile());
            doOpenFile(file.getPath());
        }
        return null;
    }

    /** This method performs File->OpenBuiltinModels. */
    private Runner doBuiltin() {
        if (wrap) return wrapMe();
        File file=OurDialog.askFile(frame, true, Helper.alloyHome()+fs+"models", openAlsOnly?".als":"", ".als files");
        if (file!=null) {
            if (!file.getPath().toLowerCase(Locale.US).endsWith(".als")) openAlsOnly=false;
            doOpenFile(file.getPath());
        }
        return null;
    }

    /** This method performs File->ReloadAll. */
    private Runner doReloadAll() {
        if (!wrap) { for(int i=0; i<text.do_getTabCount(); i++) if (!text.do_refresh(i)) break; }
        return wrapMe();
    }

    /** This method performs File->ClearRecentFiles. */
    private Runner doClearRecent() {
        if (!wrap) { Model0.set(""); Model1.set(""); Model2.set(""); Model3.set(""); }
        return wrapMe();
    }

    /** This method performs File->Save. */
    private Runner doSave() {
        if (!wrap) {
           String ans = text.do_save(false);
           if (ans==null) return null;
           notifyChange();
           addHistory(ans);
           log.clearError();
        }
        return wrapMe();
    }

    /** This method performs File->SaveAs. */
    private Runner doSaveAs() {
        if (!wrap) {
           String ans = text.do_save(true);
           if (ans==null) return null;
           notifyChange();
           addHistory(ans);
           log.clearError();
        }
        return wrapMe();
    }

    /** This method clears the temporary files and then reinitialize the temporary directory. */
    private Runner doClearTemp() {
        if (!wrap) {
           Helper.clearTemporarySpace();
           copyFromJAR();
           log.logBold("Temporary directory has been cleared.\n\n");
           log.logDivider();
           log.flush();
        }
        return wrapMe();
    }

    /** This method performs File->Close. */
    private Runner doClose() {
        if (!wrap) text.do_close();
        return wrapMe();
    }

    /** This method performs File->Quit. */
    private Runner doQuit() {
        if (!wrap) if (text.do_closeAll()) {
            try { WorkerEngine.stop(); } finally { System.exit(0); }
        }
        return wrapMe();
    }

    //===============================================================================================================//

    /** This method refreshes the "edit" menu. */
    private Runner doRefreshEdit() {
        if (wrap) return wrapMe();
        try {
            wrap = true;
            boolean canUndo = text.do_text().do_canUndo();
            boolean canRedo = text.do_text().do_canRedo();
            editmenu.removeAll();
            OurUtil.makeMenuItem(editmenu, "Undo", VK_Z, VK_Z, doUndo(), canUndo);
            if (Util.onMac())
                OurUtil.makeMenuItem(editmenu, "Redo", VK_SHIFT, VK_Z, doRedo(), canRedo);
            else
                OurUtil.makeMenuItem(editmenu, "Redo", VK_Y, VK_Y, doRedo(), canRedo);
            editmenu.addSeparator();
            OurUtil.makeMenuItem(editmenu, "Cut",   VK_X, VK_X, doCut());
            OurUtil.makeMenuItem(editmenu, "Copy",  VK_C, VK_C, doCopy());
            OurUtil.makeMenuItem(editmenu, "Paste", VK_V, VK_V, doPaste());
            editmenu.addSeparator();
            OurUtil.makeMenuItem(editmenu, "Go To..."      , VK_T,         VK_T,         doGoto());
            OurUtil.makeMenuItem(editmenu, "Previous File" , VK_PAGE_UP,   VK_PAGE_UP,   doGotoPrevFile(), text.do_getTabCount()>1);
            OurUtil.makeMenuItem(editmenu, "Next File"     , VK_PAGE_DOWN, VK_PAGE_DOWN, doGotoNextFile(), text.do_getTabCount()>1);
            editmenu.addSeparator();
            OurUtil.makeMenuItem(editmenu, "Find...",   VK_F, VK_F, doFind());
            OurUtil.makeMenuItem(editmenu, "Find Next", VK_G, VK_G, doFindNext());
        } finally {
            wrap = false;
        }
        return null;
    }

    /** This method performs Edit->Undo. */
    private Runner doUndo() {
        if (!wrap) text.do_text().do_undo();
        return wrapMe();
    }

    /** This method performs Edit->Redo. */
    private Runner doRedo() {
        if (!wrap) text.do_text().do_redo();
        return wrapMe();
    }

    /** This method performs Edit->Copy. */
    private Runner doCopy() {
        if (!wrap) { if (lastFocusIsOnEditor) text.do_text().copy(); else log.copy(); }
        return wrapMe();
    }

    /** This method performs Edit->Cut. */
    private Runner doCut() {
        if (!wrap && lastFocusIsOnEditor) text.do_text().cut();
        return wrapMe();
    }

    /** This method performs Edit->Paste. */
    private Runner doPaste() {
        if (!wrap && lastFocusIsOnEditor) text.do_text().paste();
        return wrapMe();
    }

    /** This method performs Edit->Find. */
    private Runner doFind() {
        if (wrap) return wrapMe();
        JTextField x=OurUtil.textfield(lastFind,30);
        x.selectAll();
        JCheckBox c=new JCheckBox("Case Sensitive?",lastFindCaseSensitive);
        c.setMnemonic('c');
        JCheckBox b=new JCheckBox("Search Backward?",!lastFindForward);
        b.setMnemonic('b');
        if (!OurDialog.getInput(frame, "Find", "Text:", x, " ", c, b)) return null;
        if (x.getText().length()==0) return null;
        lastFind=x.getText();
        lastFindCaseSensitive=c.getModel().isSelected();
        lastFindForward=!b.getModel().isSelected();
        doFindNext();
        return null;
    }

    /** This method performs Edit->FindNext. */
    private Runner doFindNext() {
        if (wrap) return wrapMe();
        if (lastFind.length()==0) return null;
        OurTextArea t = text.do_text();
        String all = t.getText();
        int i = Util.indexOf(all, lastFind, t.getCaretPosition()+(lastFindForward?0:-1),lastFindForward,lastFindCaseSensitive);
        if (i<0) {
            i=Util.indexOf(all, lastFind, lastFindForward?0:(all.length()-1), lastFindForward, lastFindCaseSensitive);
            if (i<0) { log.logRed("The specified search string cannot be found."); return null; }
            log.logRed("Search wrapped.");
        } else {
            log.clearError();
        }
        if (lastFindForward) {
            t.setCaretPosition(i); t.moveCaretPosition(i+lastFind.length());
        } else {
            t.setCaretPosition(i+lastFind.length()); t.moveCaretPosition(i);
        }
        t.requestFocusInWindow();
        return null;
    }

    /** This method performs Edit->Goto. */
    private Runner doGoto() {
        if (wrap) return wrapMe();
        JTextField y = OurUtil.textfield("", 10);
        JTextField x = OurUtil.textfield("", 10);
        if (!OurDialog.getInput(frame,"Go To","Line Number:", y, "Column Number (optional):", x)) return null;
        try {
            OurTextArea t = text.do_text();
            int xx = 1, yy = Integer.parseInt(y.getText()), lineCount = t.do_getLineCount();
            if (yy<1) return null;
            if (yy>lineCount) {log.logRed("This file only has "+lineCount+" line(s)."); return null;}
            if (x.getText().length()!=0) xx=Integer.parseInt(x.getText());
            if (xx<1) {log.logRed("If the column number is specified, it must be 1 or greater."); return null;}
            int caret = t.do_getLineStartOffset(yy-1);
            int len = (yy==lineCount ? len=t.getText().length()+1 : t.do_getLineStartOffset(yy)) - caret;
            if (xx>len) xx=len;
            if (xx<1) xx=1;
            t.setSelectionStart(caret+xx-1);
            t.setSelectionEnd(caret+xx-1);
            t.requestFocusInWindow();
        } catch(NumberFormatException ex) {
            log.logRed("The number must be 1 or greater.");
        } catch(Throwable ex) {
            // This error is not important
        }
        return null;
    }

    /** This method performs Edit->GotoPrevFile. */
    private Runner doGotoPrevFile() {
        if (wrap) return wrapMe(); else {text.do_prev(); return null;}
    }

    /** This method performs Edit->GotoNextFile. */
    private Runner doGotoNextFile() {
        if (wrap) return wrapMe(); else {text.do_next(); return null;}
    }

    //===============================================================================================================//

    /** This method refreshes the "run" menu. */
    private Runner doRefreshRun() {
        if (wrap) return wrapMe();
        KeyStroke ac = KeyStroke.getKeyStroke(VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        try {
            wrap = true;
            runmenu.removeAll();
            OurUtil.makeMenuItem(runmenu, "Execute Latest Command", VK_E, VK_E, doExecuteLatest());
            runmenu.add(new JSeparator());
            OurUtil.makeMenuItem(runmenu, "Show Latest Instance",   VK_L, VK_L, doShowLatest(), latestInstance.length()>0);
            OurUtil.makeMenuItem(runmenu, "Show Metamodel",         VK_M, VK_M, doShowMetaModel());
            if (Version.experimental) {
               OurUtil.makeMenuItem(runmenu, "Show Parse Tree",     VK_P,       doShowParseTree());
            }
            OurUtil.makeMenuItem(runmenu, "Open Evaluator",         VK_V,       doLoadEvaluator());
        } finally {
            wrap = false;
        }
        List<Command> cp = commands;
        if (cp==null) {
            try {
                cp=CompUtil.parseOneModule_fromString(text.do_text().getText());
            }
            catch(Err e) {
                commands = null;
                runmenu.getItem(0).setEnabled(false);
                runmenu.getItem(3).setEnabled(false);
                text.do_highlight(new Pos(text.do_getFilename(), e.pos.x, e.pos.y, e.pos.x2, e.pos.y2));
                log.logRed(e.toString()+"\n\n");
                return null;
            }
            catch(Throwable e) {
                commands = null;
                runmenu.getItem(0).setEnabled(false);
                runmenu.getItem(3).setEnabled(false);
                log.logRed("Cannot parse the model.\n"+e.toString()+"\n\n");
                return null;
            }
            commands=cp;
        }
        text.do_removeAllHighlights();
        log.clearError(); // To clear any residual error message
        if (cp==null) { runmenu.getItem(0).setEnabled(false); runmenu.getItem(3).setEnabled(false); return null; }
        if (cp.size()==0) { runmenu.getItem(0).setEnabled(false); return null; }
        if (latestCommand>=cp.size()) latestCommand=cp.size()-1;
        runmenu.remove(0);
        try {
            wrap = true;
            for(int i=0; i<cp.size(); i++) {
                JMenuItem y = new JMenuItem(cp.get(i).toString(), null);
                y.addActionListener(doRun(i));
                if (i==latestCommand) { y.setMnemonic(VK_E); y.setAccelerator(ac); }
                runmenu.add(y,i);
            }
            if (cp.size()>=2) {
                JMenuItem y = new JMenuItem("Execute All", null);
                y.setMnemonic(VK_A);
                y.addActionListener(doRun(-1));
                runmenu.add(y,0);
                runmenu.add(new JSeparator(),1);
            }
        } finally {
            wrap = false;
        }
        return null;
    }

    /** This method executes a particular RUN or CHECK command. */
    private Runner doRun(Integer commandIndex) {
        if (wrap) return wrapMe(commandIndex);
        final int index = commandIndex;
        if (WorkerEngine.isBusy()) return null;
        if (index==(-2)) subrunningTask=1; else subrunningTask=0;
        latestAutoInstance="";
        if (index>=0) latestCommand=index;
        if (index==-1 && commands!=null) {
            latestCommand=commands.size()-1;
            if (latestCommand<0) latestCommand=0;
        }
        // To update the accelerator to point to the command actually chosen
        doRefreshRun();
        OurUtil.enableAll(runmenu);
        if (commands==null) return null;
        if (commands.size()==0 && index!=-2 && index!=-3) { log.logRed("There are no commands to execute.\n\n"); return null; }
        int i=index;
        if (i>=commands.size()) i=commands.size()-1;
        SimpleCallback1 cb = new SimpleCallback1(this, null, log, Verbosity.get().ordinal(), latestAlloyVersionName, latestAlloyVersion);
        SimpleTask1 task = new SimpleTask1();
        A4Options opt = new A4Options();
        opt.tempDirectory = Helper.alloyHome() + fs + "tmp";
        opt.solverDirectory = Helper.alloyHome() + fs + "binary";
        opt.recordKodkod = RecordKodkod.get();
        opt.skolemDepth = SkolemDepth.get();
        opt.coreMinimization = CoreMinimization.get();
        opt.originalFilename = Util.canon(text.do_getFilename());
        opt.solver = SatSolver.get();
        task.bundleIndex = i;
        task.bundleWarningNonFatal = WarningNonfatal.get();
        task.map = text.do_takeSnapshot();
        task.options = opt.dup();
        task.resolutionMode = (Version.experimental && ImplicitThis.get()) ? 2 : 1;
        task.tempdir = Helper.maketemp();
        try {
            runmenu.setEnabled(false);
            runbutton.setVisible(false);
            showbutton.setEnabled(false);
            stopbutton.setVisible(true);
            int newmem = SubMemory.get(), newstack = SubStack.get();
            if (newmem != subMemoryNow || newstack != subStackNow) WorkerEngine.stop();
            if ("yes".equals(System.getProperty("debug")) && Verbosity.get()==Verbosity.FULLDEBUG)
                WorkerEngine.runLocally(task, cb);
            else
                WorkerEngine.run(task, newmem, newstack, Helper.alloyHome()+fs+"binary", cb);
            subMemoryNow = newmem;
            subStackNow = newstack;
        } catch(Throwable ex) {
            WorkerEngine.stop();
            log.logBold("Fatal Error: Solver failed due to unknown reason.\n" +
              "One possible cause is that, in the Options menu, your specified\n" +
              "memory size is larger than the amount allowed by your OS.\n" +
              "Also, please make sure \"java\" is in your program path.\n");
            log.logDivider();
            log.flush();
            doStop(2);
        }
        return null;
    }

    /** This method stops the current run or check (how==0 means DONE, how==1 means FAIL, how==2 means STOP). */
    Runner doStop(Integer how) {
        if (wrap) return wrapMe(how);
        int h = how;
        if (h!=0) {
           if (h==2 && WorkerEngine.isBusy()) { WorkerEngine.stop(); log.logBold("\nSolving Stopped.\n"); log.logDivider(); }
           WorkerEngine.stop();
        }
        runmenu.setEnabled(true);
        runbutton.setVisible(true);
        showbutton.setEnabled(true);
        stopbutton.setVisible(false);
        if (latestAutoInstance.length()>0) {
           String f=latestAutoInstance;
           latestAutoInstance="";
           if (subrunningTask==2) viz.loadXML(f, true); else if (AutoVisualize.get() || subrunningTask==1) doVisualize("XML: "+f);
        }
        return null;
    }

    /** This method executes the latest command. */
    private Runner doExecuteLatest() {
        if (wrap) return wrapMe();
        doRefreshRun();
        OurUtil.enableAll(runmenu);
        if (commands==null) return null;
        int n=commands.size();
        if (n<=0) { log.logRed("There are no commands to execute.\n\n"); return null; }
        if (latestCommand>=n) latestCommand=n-1;
        if (latestCommand<0) latestCommand=0;
        return doRun(latestCommand);
    }

    /** This method displays the parse tree. */
    private Runner doShowParseTree() {
        if (wrap) return wrapMe();
        doRefreshRun();
        OurUtil.enableAll(runmenu);
        if (commands!=null) {
            Module world = null;
            try {
                int resolutionMode = (Version.experimental && ImplicitThis.get()) ? 2 : 1;
                A4Options opt = new A4Options();
                opt.tempDirectory = Helper.alloyHome() + fs + "tmp";
                opt.solverDirectory = Helper.alloyHome() + fs + "binary";
                opt.originalFilename = Util.canon(text.do_getFilename());
                world = CompUtil.parseEverything_fromFile(A4Reporter.NOP, text.do_takeSnapshot(), opt.originalFilename, resolutionMode);
            } catch(Err er) {
                text.do_highlight(er.pos);
                log.logRed(er.toString()+"\n\n");
                return null;
            }
            DebugTree.show(world, text);
        }
        return null;
    }

    /** This method displays the meta model. */
    private Runner doShowMetaModel() {
        if (wrap) return wrapMe();
        doRefreshRun();
        OurUtil.enableAll(runmenu);
        if (commands!=null) doRun(-2);
        return null;
    }

    /** This method displays the latest instance. */
    private Runner doShowLatest() {
        if (wrap) return wrapMe();
        if (latestInstance.length()==0)
           log.logRed("No previous instances are available for viewing.\n\n");
        else
           doVisualize("XML: "+latestInstance);
        return null;
    }

    /** This method happens when the user tries to load the evaluator from the main GUI. */
    private Runner doLoadEvaluator() {
        if (wrap) return wrapMe();
        log.logRed("Note: the evaluator is now in the visualizer.\n"
           +"Just click the \"Evaluator\" toolbar button\n"
           +"when an instance is shown in the visualizer.\n");
        log.flush();
        return null;
    }

    //===============================================================================================================//

    /** This method refreshes the "Window" menu for either the SimpleGUI window (isViz==false) or the VizGUI window (isViz==true). */
    private Runner doRefreshWindow(Boolean isViz) {
        if (wrap) return wrapMe(isViz);
        try {
            wrap = true;
            JMenu w = (isViz ? windowmenu2 : windowmenu);
            w.removeAll();
            if (isViz) {
                viz.addMinMaxActions(w);
            } else {
                OurUtil.makeMenuItem(w, "Minimize", VK_M, doMinimize(), iconNo);
                OurUtil.makeMenuItem(w, "Zoom",           doZoom(),     iconNo);
            }
            w.addSeparator();
            List<String> filenames=text.do_getFilenames();
            for(int i=0; i<filenames.size(); i++) {
                String f=filenames.get(i);
                JMenuItem it = new JMenuItem("Model: "+slightlyShorterFilename(f)+(text.do_modified(i) ? " *" : ""), null);
                it.setIcon((f.equals(text.do_getFilename()) && !isViz) ? iconYes : iconNo);
                it.addActionListener(f.equals(text.do_getFilename()) ? doShow() : doOpenFile(f));
                w.add(it);
            }
            if (viz!=null) for(String f:viz.getInstances()) {
                JMenuItem it = new JMenuItem("Instance: "+viz.getInstanceTitle(f), null);
                it.setIcon((isViz && f.equals(viz.getXMLfilename())) ? iconYes : iconNo);
                it.addActionListener(doVisualize("XML: "+f));
                w.add(it);
            }
        } finally {
            wrap = false;
        }
        return null;
    }

    /** This method minimizes the window. */
    private Runner doMinimize() {
        if (wrap) return wrapMe(); else {OurUtil.minimize(frame); return null;}
    }

    /** This method alternatingly maximizes or restores the window. */
    private Runner doZoom() {
        if (wrap) return wrapMe(); else {OurUtil.zoom(frame); return null;}
    }

    /** This method bring this window to the foreground. */
    private Runner doShow() {
        if (wrap) return wrapMe();
        OurUtil.show(frame);
        text.do_text().requestFocusInWindow();
        return null;
    }

    //===============================================================================================================//

    /** This method refreshes the "Option" menu. */
    private Runner doRefreshOption() {
        if (wrap) return wrapMe();
        try {
            wrap = true;
            optmenu.removeAll();
            OurUtil.makeMenuItem(optmenu, "Welcome Message at Start Up: "+(Welcome.get() < welcomeLevel ? "Yes" : "No"), doOptWelcome());
            //
            final SatSolver now = SatSolver.get();
            final JMenu sat = new JMenu("SAT Solver: "+now);
            for(SatSolver sc:satChoices) { OurUtil.makeMenuItem(sat, ""+sc, doOptSolver(sc), sc==now?iconYes:iconNo); }
            optmenu.add(sat);
            //
            OurUtil.makeMenuItem(optmenu, "Warnings are Fatal: "+(WarningNonfatal.get()?"No":"Yes"), doOptWarning());
            //
            final int mem = SubMemory.get();
            final JMenu subMemoryMenu = new JMenu("Maximum Memory to Use: " + mem + "M");
            for(int n: allowedMemorySizes) {
               OurUtil.makeMenuItem(subMemoryMenu, ""+n+"M", doOptMemory(n), n==mem?iconYes:iconNo);
            }
            optmenu.add(subMemoryMenu);
            //
            final int stack = SubStack.get();
            final JMenu subStackMenu = new JMenu("Maximum Stack to Use: " + stack + "k");
            boolean debug = "yes".equals(System.getProperty("debug"));
            for(int n: new int[]{16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536}) {
               if (debug || n>=1024) OurUtil.makeMenuItem(subStackMenu, ""+n+"k", doOptStack(n), n==stack?iconYes:iconNo);
            }
            optmenu.add(subStackMenu);
            //
            final Verbosity vnow = Verbosity.get();
            final JMenu verb = new JMenu("Message Verbosity: "+vnow);
            for(Verbosity vb:Verbosity.values()) { OurUtil.makeMenuItem(verb, ""+vb, doOptVerbosity(vb), vb==vnow?iconYes:iconNo); }
            optmenu.add(verb);
            //
            OurUtil.makeMenuItem(optmenu, "Syntax Highlighting: "+(SyntaxDisabled.get()?"No":"Yes"), doOptSyntaxHighlighting());
            //
            final int fontSize = FontSize.get();
            final JMenu size = new JMenu("Font Size: "+fontSize);
            for(int n: new Integer[]{9,10,11,12,14,16,18,20,22,24,26,28,32,36,40,44,48,54,60,66,72}) {
                OurUtil.makeMenuItem(size, ""+n, doOptFontsize(n), n==fontSize?iconYes:iconNo);
            }
            optmenu.add(size);
            //
            OurUtil.makeMenuItem(optmenu, "Font: "+FontName.get()+"...", doOptFontname());
            //
            if (Util.onMac() || Util.onWindows()) OurUtil.makeMenuItem(optmenu, "Use anti-aliasing: Yes", false);
            else OurUtil.makeMenuItem(optmenu, "Use anti-aliasing: "+(AntiAlias.get()?"Yes":"No"), doOptAntiAlias());
            //
            final int tabSize = TabSize.get();
            final JMenu tabSizeMenu = new JMenu("Tab Size: "+tabSize);
            for(int n=1; n<=12; n++) { OurUtil.makeMenuItem(tabSizeMenu, ""+n, doOptTabsize(n), n==tabSize?iconYes:iconNo); }
            optmenu.add(tabSizeMenu);
            //
            final int skDepth = SkolemDepth.get();
            final JMenu skDepthMenu = new JMenu("Skolem Depth: "+skDepth);
            for(int n=0; n<=4; n++) { OurUtil.makeMenuItem(skDepthMenu, ""+n, doOptSkolemDepth(n), n==skDepth?iconYes:iconNo); }
            optmenu.add(skDepthMenu);
            //
            final int min = CoreMinimization.get();
            final String[] minLabelLong=new String[]{"Slow (guarantees local minimum)", "Medium", "Fast (initial unsat core)"};
            final String[] minLabelShort=new String[]{"Slow", "Medium", "Fast"};
            final JMenu cmMenu = new JMenu("Unsat Core Minimization Strategy: "+minLabelShort[min]);
            for(int n=0; n<=2; n++) { OurUtil.makeMenuItem(cmMenu, minLabelLong[n], doOptCore(n), n==min?iconYes:iconNo); }
            if (now!=SatSolver.MiniSatProverJNI) cmMenu.setEnabled(false);
            optmenu.add(cmMenu);
            //
            OurUtil.makeMenuItem(optmenu, "Visualize Automatically: "+(AutoVisualize.get()?"Yes":"No"), doOptAutoVisualize());
            OurUtil.makeMenuItem(optmenu, "Record the Kodkod Input/Output: "+(RecordKodkod.get()?"Yes":"No"), doOptRecordKodkod());
            if (Version.experimental) OurUtil.makeMenuItem(optmenu, "Enable \"implicit this\" name resolution: "+(ImplicitThis.get()?"Yes":"No"), doOptImplicitThis());
        } finally {
            wrap = false;
        }
        return null;
    }

    /** This method toggles the "show welcome message at startup" checkbox. */
    private Runner doOptWelcome() {
        if (!wrap) Welcome.set(Welcome.get() < welcomeLevel ? welcomeLevel : 0);
        return wrapMe();
    }

    /** This method toggles the "warning is fatal" checkbox. */
    private Runner doOptWarning() {
        if (!wrap) WarningNonfatal.set(!WarningNonfatal.get());
        return wrapMe();
    }

    /** This method changes the SAT solver to the given solver. */
    private Runner doOptSolver(SatSolver solver) {
        if (!wrap) solver.set();
        return wrapMe(solver);
    }

    /** This method changes the amount of memory to use. */
    private Runner doOptMemory(Integer size) {
        if (!wrap) SubMemory.set(size);
        return wrapMe(size);
    }

    /** This method changes the amount of stack to use. */
    private Runner doOptStack(Integer size) {
        if (!wrap) SubStack.set(size);
        return wrapMe(size);
    }

    /** This method changes the message verbosity. */
    private Runner doOptVerbosity(Verbosity verbosity) {
        if (!wrap) verbosity.set();
        return wrapMe(verbosity);
    }

    /** This method changes the font name. */
    private Runner doOptFontname() {
        if (wrap) return wrapMe();
        int size=FontSize.get();
        String family=OurDialog.askFont(frame);
        if (family.length()>0) {
           FontName.set(family);
           text.do_setFont(family, size);
           status.setFont(new Font(family, Font.PLAIN, size));
           log.setFontName(family);
        }
        return null;
    }

    /** This method changes the font size. */
    private Runner doOptFontsize(Integer size) {
        if (wrap) return wrapMe(size);
        int n=size;
        FontSize.set(n);
        String family=FontName.get();
        text.do_setFont(family, n);
        status.setFont(new Font(family, Font.PLAIN, n));
        log.setFontSize(n);
        viz.doSetFontSize(n);
        return null;
    }

    /** This method changes the tab size. */
    private Runner doOptTabsize(Integer size) {
        if (!wrap) { TabSize.set(size.intValue()); text.do_setTabSize(size.intValue()); }
        return wrapMe(size);
    }

    /** This method changes the skolem depth. */
    private Runner doOptSkolemDepth(Integer size) {
        if (!wrap) SkolemDepth.set(size.intValue());
        return wrapMe(size);
    }

    /** This method changes the speed of unsat core minimization (larger integer means faster but less optimal). */
    private Runner doOptCore(Integer speed) {
        if (!wrap) CoreMinimization.set(speed.intValue());
        return wrapMe(speed);
    }

    /** This method toggles the "antialias" checkbox. */
    private Runner doOptAntiAlias() {
        if (!wrap) { boolean newValue = !AntiAlias.get(); AntiAlias.set(newValue); OurAntiAlias.enableAntiAlias(newValue); }
        return wrapMe();
    }

    /** This method toggles the "visualize automatically" checkbox. */
    private Runner doOptAutoVisualize() {
        if (!wrap) AutoVisualize.set(!AutoVisualize.get());
        return wrapMe();
    }

    /** This method toggles the "record Kodkod input/output" checkbox. */
    private Runner doOptRecordKodkod() {
        if (!wrap) RecordKodkod.set(!RecordKodkod.get());
        return wrapMe();
    }

    /** This method toggles the "enable new `implicit this' name resolution" checkbox. */
    private Runner doOptImplicitThis() {
        if (!wrap) ImplicitThis.set(!ImplicitThis.get());
        return wrapMe();
    }

    /** This method toggles the "syntax highlighting" checkbox. */
    private Runner doOptSyntaxHighlighting() {
        if (!wrap) {
            boolean flag = SyntaxDisabled.get();
            text.do_syntaxHighlighting(flag);
            SyntaxDisabled.set(!flag);
        }
        return wrapMe();
    }

    //===============================================================================================================//

    /** This method displays the about box. */
    private Runner doAbout() {
        if (wrap) return wrapMe();
        Icon icon = OurUtil.loadIcon("images/logo.gif");
        JButton dismiss = new JButton(Util.onMac() ? "Dismiss" : "Close");
        Object[] array = {
            icon,
            "Alloy Analyzer "+Version.version(),
            "Build date: "+Version.buildDate(),
            " ",
            "Lead developer: Felix Chang",
            "Engine developer: Emina Torlak",
            "Graphic design: Julie Pelaez",
            "Project lead: Daniel Jackson",
            " ",
            "More information at: http://alloy.mit.edu/",
            "Comments and questions to: alloy@mit.edu",
            " ",
            "Thanks to: Ilya Shlyakhter, Manu Sridharan, Derek Rayside, Jonathan Edwards, Gregory Dennis,",
            "Robert Seater, Edmond Lau, Vincent Yeung, Sam Daitch, Andrew Yip, Jongmin Baek, Ning Song,",
            "Arturo Arizpe, Li-kuo (Brian) Lin, Joseph Cohen, Jesse Pavel, Ian Schechter, and Uriel Schafer.",
            OurUtil.makeH(null,dismiss,null)};
        final JOptionPane about = new JOptionPane(array,
            JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        final JDialog dialog = about.createDialog(null, "About Alloy Analyzer "+Version.version());
        dismiss.addActionListener(Runner.createDispose(dialog));
        dialog.setVisible(true);
        return null;
    }

    /** This method displays the help html. */
    private Runner doHelp() {
        if (wrap) return wrapMe();
        try {
            int w=OurUtil.getScreenWidth(), h=OurUtil.getScreenHeight();
            final JFrame frame = new JFrame();
            final JEditorPane html1 = new JEditorPane("text/html", "");
            final JEditorPane html2 = new JEditorPane("text/html", "");
            final HTMLDocument doc1 = (HTMLDocument) (html1.getDocument()); doc1.setAsynchronousLoadPriority(-1);
            final HTMLDocument doc2 = (HTMLDocument) (html2.getDocument()); doc2.setAsynchronousLoadPriority(-1);
            html1.setPage(this.getClass().getResource("/help/Nav.html"));
            html2.setPage(this.getClass().getResource("/help/index.html"));
            HyperlinkListener hl=new HyperlinkListener() {
                public final void hyperlinkUpdate(HyperlinkEvent e) {
                    try {
                        if (e.getEventType()!=HyperlinkEvent.EventType.ACTIVATED) return;
                        if (e.getURL().getPath().endsWith("quit.htm")) { frame.dispose(); return; }
                        HTMLDocument doc = (HTMLDocument) (html2.getDocument());
                        doc.setAsynchronousLoadPriority(-1); // So that we can catch any exception that may occur
                        html2.setPage(e.getURL());
                        html2.requestFocusInWindow();
                    } catch(Throwable ex) { }
                }
            };
            html1.setEditable(false); html1.setBorder(new EmptyBorder(3,3,3,3)); html1.addHyperlinkListener(hl);
            html2.setEditable(false); html2.setBorder(new EmptyBorder(3,3,3,3)); html2.addHyperlinkListener(hl);
            JScrollPane scroll1 = OurUtil.scrollpane(html1);
            JScrollPane scroll2 = OurUtil.scrollpane(html2);
            JSplitPane split = OurUtil.splitpane(JSplitPane.HORIZONTAL_SPLIT, scroll1, scroll2, 150);
            split.setResizeWeight(0d);
            frame.setTitle("Alloy Analyzer Online Guide");
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(split, BorderLayout.CENTER);
            frame.pack();
            frame.setSize(w-w/10, h-h/10);
            frame.setLocation(w/20, h/20);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            html2.requestFocusInWindow();
        } catch(Throwable ex) { return null; }
        return null;
    }

    /** This method displays the license box. */
    private Runner doLicense() {
        if (wrap) return wrapMe();
        JButton dismiss = new JButton(Util.onMac() ? "Dismiss" : "Close");
        final String JAR = Util.jarPrefix();
        String alloytxt;
        try {alloytxt=Util.readAll(JAR + "LICENSES" + File.separator + "Alloy.txt");} catch(IOException ex) {return null;}
        final JTextArea text = OurUtil.textarea(alloytxt, 15, 85, false, false, new EmptyBorder(2, 2, 2, 2), new Font("Monospaced", Font.PLAIN, 12));
        final JComboBox combo = new OurCombobox(new String[]{"Alloy","Kodkod","JavaCup","SAT4J","ZChaff","MiniSat"}) {
            private static final long serialVersionUID = 1L;
            @Override public void do_changed(Object value) {
              if (value instanceof String) {
                 try {
                     String content = Util.readAll(JAR + "LICENSES" + File.separator + value + ".txt");
                     text.setText(content);
                 } catch(IOException ex) {
                     text.setText("Sorry: an error has occurred in displaying the license file.");
                 }
              }
              text.setCaretPosition(0);
           }
        };
        JScrollPane scroll = OurUtil.scrollpane(text, new LineBorder(Color.DARK_GRAY, 1));
        Object[] array = {
           "The source code for the Alloy Analyzer is available under the MIT license.",
           " ",
           "The Alloy Analyzer utilizes several third-party packages whose code may",
           "be distributed under a different license. We are extremely grateful to",
           "the authors of these packages for making their source code freely available.",
           " ",
           OurUtil.makeH(null, "See the copyright notice for: ", combo, null),
           " ",
           scroll,
           OurUtil.makeH(null, dismiss, null)};
        final JOptionPane about = new JOptionPane(array, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        final JDialog dialog = about.createDialog(null, "The Copyright Notices");
        dismiss.addActionListener(Runner.createDispose(dialog));
        dialog.setVisible(true);
        return null;
    }

    /** This method checks alloy.mit.edu to see if there is a newer version. */
    private Runner doCheckForUpdates() {
        if (wrap) return wrapMe();
        final String NEW_LINE = System.getProperty("line.separator");
        final String URL = "http://alloy.mit.edu/alloy4/download/alloy4.txt?buildnum="+Version.buildNumber()+"&builddate="+Version.buildDate();
        long now=System.currentTimeMillis();
        BufferedReader in = null;
        String result;
        try {
            URL url = new URL(URL);
            URLConnection connection = url.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder buf = new StringBuilder();
            for (String inputLine = in.readLine(); inputLine != null; inputLine = in.readLine()) {
                buf.append(inputLine);
                buf.append(NEW_LINE);
            }
            result=buf.toString();
        } catch (Throwable ex) {
            result="";
        } finally {
            Util.close(in);
        }
        // If the "check for update" took too long, then don't display the message, since it clutters up the message panel
        if (System.currentTimeMillis()-now >= 5000 || !result.startsWith("Alloy Build ")) return null;
        // Now that we're online, try to remove the old ill-conceived "Java WebStart" versions of Alloy4 (which consists of Alloy4 BETA1..BETA7)
        Subprocess.exec(20000, new String[]{"javaws","-silent","-offline","-uninstall","http://alloy.mit.edu/alloy4/download/alloy4.jnlp"});
        // Now, display the result of the alloy.mit.edu version polling
        try {
            wrap=true;
            SwingUtilities.invokeLater(doUpdate(result));
        } finally {
            wrap=false;
        }
        return null;
    }

    /** This method displays a message that "updated version of Alloy is available". */
    private Runner doUpdate(String arg) {
        if (wrap) return wrapMe(arg);
        String result=arg;
        int num=0;
        int len=result.length();
        boolean found=false;
        for(int i=0; ; i++) {
            if (i>=len) return null;
            char c=result.charAt(i);
            if (!(c>='0' && c<='9')) { if (!found) continue; result=result.substring(i); break; }
            found=true;
            num=num*10+(c-'0');
        }
        latestAlloyVersionName=result.trim();
        latestAlloyVersion=num;
        exitReporter.setLatestAlloyVersion(latestAlloyVersion, latestAlloyVersionName);
        if (latestAlloyVersion<=Version.buildNumber()) return null;
        log.logBold("An updated version of the Alloy Analyzer has been released.\n");
        log.log("Please visit alloy.mit.edu to download the latest version:\nVersion "+latestAlloyVersionName+"\n");
        log.logDivider();
        log.flush();
        return null;
    }

    /** This method changes the latest instance. */
    void doSetLatest(String arg) {
        latestInstance=arg;
        latestAutoInstance=arg;
    }

    /** The color to use for functions/predicate/paragraphs that contains part of the unsat core. */
    final Color supCoreColor = new Color(0.95f, 0.1f, 0.1f);

    /** The color to use for the unsat core. */
    final Color coreColor = new Color(0.9f, 0.4f, 0.4f);

    /** The color to use for functions/predicate used by the Unsat core. */
    final Color subCoreColor = new Color(0.9f, 0.7f, 0.7f);

    /** This method displays a particular instance or message. */
    @SuppressWarnings("unchecked")
    Runner doVisualize(String arg) {
        if (wrap) return wrapMe(arg);
        text.do_removeAllHighlights();
        if (arg.startsWith("MSG: ")) {
            OurDialog.showtext("Detailed Message", arg.substring(5));
        }
        if (arg.startsWith("CORE: ")) {
            String filename = Util.canon(arg.substring(6));
            Pair<Set<Pos>,Set<Pos>> hCore;
            Set<Pos> lCore;
            InputStream is = null;
            ObjectInputStream ois = null;
            try {
                is = new FileInputStream(filename);
                ois = new ObjectInputStream(is);
                hCore = (Pair<Set<Pos>,Set<Pos>>) ois.readObject();
                lCore = (Set<Pos>) ois.readObject();
            } catch(Throwable ex) {
                log.logRed("Error reading or parsing the core \""+filename+"\"\n");
                return null;
            } finally {
                Util.close(ois);
                Util.close(is);
            }
            text.do_removeAllHighlights();
            text.do_highlight(hCore.b, subCoreColor, false);
            text.do_highlight(hCore.a, coreColor, false);
            if (false) text.do_highlight(lCore, coreColor, false); // we are currently not highlighting the lowlevel core
        }
        if (arg.startsWith("POS: ")) {
            Scanner s=new Scanner(arg.substring(5));
            int x1=s.nextInt(), y1=s.nextInt(), x2=s.nextInt(), y2=s.nextInt();
            String f=s.nextLine();
            if (f.length()>0 && f.charAt(0)==' ') f=f.substring(1); // Get rid of the space after Y2
            Pos p=new Pos(Util.canon(f), x1, y1, x2, y2);
            text.do_highlight(p);
        }
        if (arg.startsWith("CNF: ")) {
            String filename=Util.canon(arg.substring(5));
            try { String text=Util.readAll(filename); OurDialog.showtext("Text Viewer", text); }
            catch(IOException ex) { log.logRed("Error reading the file \""+filename+"\"\n"); }
        }
        if (arg.startsWith("XML: ")) {
            viz.loadXML(Util.canon(arg.substring(5)), false);
        }
        return null;
    }

    /** This method opens a particular file. */
    private Runner doOpenFile(String arg) {
        if (wrap) return wrapMe(arg);
        String f=Util.canon(arg);
        if (!text.do_newTab(f)) return null;
        if (text.do_isFile()) addHistory(f);
        doShow();
        text.do_text().requestFocusInWindow();
        log.clearError();
        return null;
    }

    /** This object performs solution enumeration. */
    private final Computer enumerator = new Computer() {
        public String compute(String arg) {
            OurUtil.show(frame);
            if (WorkerEngine.isBusy())
                throw new RuntimeException("Alloy4 is currently executing a SAT solver command. Please wait until that command has finished.");
            SimpleCallback1 cb = new SimpleCallback1(SimpleGUI.this, viz, log, Verbosity.get().ordinal(), latestAlloyVersionName, latestAlloyVersion);
            SimpleTask2 task = new SimpleTask2();
            task.filename = arg;
            try {
                WorkerEngine.run(task, SubMemory.get(), SubStack.get(), Helper.alloyHome()+fs+"binary", cb);
            } catch(Throwable ex) {
                WorkerEngine.stop();
                log.logBold("Fatal Error: Solver failed due to unknown reason.\n" +
                  "One possible cause is that, in the Options menu, your specified\n" +
                  "memory size is larger than the amount allowed by your OS.\n" +
                  "Also, please make sure \"java\" is in your program path.\n");
                log.logDivider();
                log.flush();
                doStop(2);
                return arg;
            }
            subrunningTask=2;
            runmenu.setEnabled(false);
            runbutton.setVisible(false);
            showbutton.setEnabled(false);
            stopbutton.setVisible(true);
            return arg;
        }
        public void setSourceFile(String filename) { }
    };

    /** Converts an A4TupleSet into a SimTupleset object. */
    private static SimTupleset convert(Object object) throws Err {
        if (!(object instanceof A4TupleSet)) throw new ErrorFatal("Unexpected type error: expecting an A4TupleSet.");
        A4TupleSet s = (A4TupleSet)object;
        if (s.size()==0) return SimTupleset.EMPTY;
        List<Object[]> list = new ArrayList<Object[]>(s.size());
        int arity = s.arity();
        for(A4Tuple t: s) {
            Object[] array = new Object[arity];
            for(int i=0; i<t.arity(); i++) {
                if (t.atom(i).startsWith("fun/String$")) array[i]=t.atom(i).substring(11); else array[i]=t.atom(i);
                array[i] = SimTupleset.canon(array[i]);
            }
            list.add(array);
        }
        return SimTupleset.make(list);
    }

    /** Converts an A4Solution into a SimContext object. */
    public static SimContext convert(A4Solution ans) throws Err {
        SimContext ct = new SimContext(ans.getBitwidth(), ans.getMaxSeq());
        for(Sig s: ans.getAllReachableSigs()) {
            ct.assign(s, convert(ans.eval(s)));
            for(Field f: s.getFields())  if (f.boundingFormula!=null)  ct.assign(f, convert(ans.eval(f)));
        }
        for(ExprVar a:ans.getAllAtoms())   ct.assign(a, convert(ans.eval(a)));
        for(ExprVar a:ans.getAllSkolems()) ct.assign(a, convert(ans.eval(a)));
        return ct;
    }

    /** This object performs expression evaluation. */
    private static Computer evaluator = new Computer() {
        private String filename=null;
        public final void setSourceFile(String filename) {
            this.filename=filename;
        }
        public final String compute(String input) throws Exception {
            if (input.trim().length()==0) return ""; // Empty line
            Module root = null;
            A4Solution ans = null;
            try {
                Map<String,String> fc = new LinkedHashMap<String,String>();
                XMLNode x = new XMLNode(new File(filename));
                if (!x.is("alloy")) throw new Exception();
                String mainname=null;
                for(XMLNode sub: x) if (sub.is("instance")) {
                   mainname=sub.getAttribute("filename");
                   break;
                }
                if (mainname==null) throw new Exception();
                for(XMLNode sub: x) if (sub.is("source")) {
                   String name = sub.getAttribute("filename");
                   String content = sub.getAttribute("content");
                   fc.put(name, content);
                }
                root = CompUtil.parseEverything_fromFile(A4Reporter.NOP, fc, mainname, (Version.experimental && ImplicitThis.get()) ? 2 : 1);
                ans = A4SolutionReader.read(root.getAllReachableSigs(), x);
                for(ExprVar a:ans.getAllAtoms())   { root.addGlobal(a.label, a); }
                for(ExprVar a:ans.getAllSkolems()) { root.addGlobal(a.label, a); }
            } catch(Throwable ex) {
                throw new ErrorFatal("Failed to read or parse the XML file.");
            }
            try {
                Expr e = CompUtil.parseOneExpression_fromString(root, input);
                if ("yes".equals(System.getProperty("debug")) && Verbosity.get()==Verbosity.FULLDEBUG)
                   return convert(ans).visitThis(e).toString();
                else
                   return ans.eval(e).toString();
            } catch(HigherOrderDeclException ex) {
                throw new ErrorType("Higher-order quantification is not allowed in the evaluator.");
            }
        }
    };

    /** Returns true iff the output says "s SATISFIABLE" (while ignoring comment lines and value lines) */
    private static boolean isSat(String output) {
        int i=0, n=output.length();
        // skip COMMENT lines and VALUE lines
        while(i<n && (output.charAt(i)=='c' || output.charAt(i)=='v')) {
            while(i<n && (output.charAt(i)!='\r' && output.charAt(i)!='\n')) i++;
            while(i<n && (output.charAt(i)=='\r' || output.charAt(i)=='\n')) i++;
            continue;
        }
        return output.substring(i).startsWith("s SATISFIABLE");
    }

    //====== Main Method ====================================================//

    /** Main method that launches the program; this method might be called by an arbitrary thread. */
    public static void main(final String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { new SimpleGUI(args); }
        });
    }

    //====== Constructor ====================================================//

    private static boolean loadLibrary(String library) {
        try { System.loadLibrary(library);      return true; } catch(UnsatisfiedLinkError ex) { }
        try { System.loadLibrary(library+"x1"); return true; } catch(UnsatisfiedLinkError ex) { }
        try { System.loadLibrary(library+"x2"); return true; } catch(UnsatisfiedLinkError ex) { }
        try { System.loadLibrary(library+"x3"); return true; } catch(UnsatisfiedLinkError ex) { }
        try { System.loadLibrary(library+"x4"); return true; } catch(UnsatisfiedLinkError ex) { }
        try { System.loadLibrary(library+"x4"); return true; } catch(UnsatisfiedLinkError ex) { return false; }
    }

    /** Create a dummy task object for testing purpose. */
    private static final WorkerEngine.WorkerTask dummyTask = new WorkerEngine.WorkerTask() {
        private static final long serialVersionUID = 1L;
        public void run(WorkerCallback out) { }
    };

    /** The constructor; this method will be called by the AWT event thread, using the "invokeLater" method. */
    private SimpleGUI (final String[] args) {

        // Register an exception handler for uncaught exceptions
        exitReporter = new MailBug();
        Thread.setDefaultUncaughtExceptionHandler(exitReporter);

        // Enable better look-and-feel
        if (Util.onMac() || Util.onWindows()) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Alloy Analyzer "+Version.version());
            System.setProperty("com.apple.mrj.application.growbox.intrudes","true");
            System.setProperty("com.apple.mrj.application.live-resize","true");
            System.setProperty("com.apple.macos.useScreenMenuBar","true");
            System.setProperty("apple.laf.useScreenMenuBar","true");
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Throwable e) { }
        }

        // Figure out the desired x, y, width, and height
        int screenWidth=OurUtil.getScreenWidth(), screenHeight=OurUtil.getScreenHeight();
        int width=AnalyzerWidth.get();
        if (width<=0) width=screenWidth/10*8; else if (width<100) width=100;
        if (width>screenWidth) width=screenWidth;
        int height=AnalyzerHeight.get();
        if (height<=0) height=screenHeight/10*8; else if (height<100) height=100;
        if (height>screenHeight) height=screenHeight;
        int x=AnalyzerX.get(); if (x<0) x=screenWidth/10; if (x>screenWidth-100) x=screenWidth-100;
        int y=AnalyzerY.get(); if (y<0) y=screenHeight/10; if (y>screenHeight-100) y=screenHeight-100;

        // Put up a slash screen
        final JFrame frame = new JFrame("Alloy Analyzer");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.pack();
        if (!Util.onMac() && !Util.onWindows()) {
           // many Window managers do not respect ICCCM2; this should help avoid the Title Bar being shifted "off screen"
           if (x<30) { if (x<0) x=0; width=width-(30-x);   x=30; }
           if (y<30) { if (y<0) y=0; height=height-(30-y); y=30; }
           if (width<100) width=100;
           if (height<100) height=100;
        }
        frame.setSize(width,height);
        frame.setLocation(x,y);
        frame.setVisible(true);
        frame.setTitle("Alloy Analyzer "+Version.version()+" loading... please wait...");
        final int windowWidth = width;
        // We intentionally call setVisible(true) first before settings the "please wait" title,
        // since we want the minimized window title on Linux/FreeBSD to just say Alloy Analyzer

        // Test the allowed memory sizes
        final WorkerEngine.WorkerCallback c = new WorkerEngine.WorkerCallback() {
            private final List<Integer> allowed = new ArrayList<Integer>();
            private final List<Integer> toTry = new ArrayList<Integer>(Arrays.asList(256,512,768,1024,1536,2048,3072,4096));
            private int mem;
            public synchronized void callback(Object msg) {
                if (toTry.size()==0) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() { SimpleGUI.this.frame=frame; SimpleGUI.this.finishInit(args, allowed, windowWidth); }
                    });
                    return;
                }
                try { mem=toTry.remove(0); WorkerEngine.stop(); WorkerEngine.run(dummyTask, mem, 128, "", this); return; } catch(IOException ex) { fail(); }
            }
            public synchronized void done() {
                //System.out.println("Alloy4 can use "+mem+"M"); System.out.flush();
                allowed.add(mem);
                callback(null);
            }
            public synchronized void fail() {
                //System.out.println("Alloy4 cannot use "+mem+"M"); System.out.flush();
                callback(null);
            }
        };
        c.callback(null);
    }

    private void finishInit(String[] args, List<Integer> initialAllowedMemorySizes, int width) {

        // Add the listeners
        try {
            wrap = true;
            frame.addWindowListener(doQuit());
        } finally {
            wrap = false;
        }
        frame.addComponentListener(this);

        // initialize the "allowed memory sizes" array
        allowedMemorySizes = new ArrayList<Integer>(initialAllowedMemorySizes);
        int newmem = SubMemory.get();
        if (!allowedMemorySizes.contains(newmem)) {
           int newmemlen = allowedMemorySizes.size();
           if (allowedMemorySizes.contains(768) || newmemlen==0)
              SubMemory.set(768); // a nice default value
           else
              SubMemory.set(allowedMemorySizes.get(newmemlen-1));
        }

        // Choose the appropriate font
        int fontSize=FontSize.get();
        String fontName=FontName.get();
        while(true) {
            if (!OurDialog.hasFont(fontName)) fontName="Lucida Grande"; else break;
            if (!OurDialog.hasFont(fontName)) fontName="Verdana"; else break;
            if (!OurDialog.hasFont(fontName)) fontName="Courier New"; else break;
            if (!OurDialog.hasFont(fontName)) fontName="Lucida Grande";
            break;
        }
        FontName.set(fontName);

        // Copy required files from the JAR
        copyFromJAR();
        final String binary = Helper.alloyHome()+fs+"binary";

        // Create the menu bar
        JMenuBar bar = new JMenuBar();
        try {
            wrap = true;
            filemenu = OurUtil.makeMenu(bar, "File", VK_F, doRefreshFile());
            editmenu = OurUtil.makeMenu(bar, "Edit", VK_E, doRefreshEdit());
            runmenu = OurUtil.makeMenu(bar, "Execute", VK_X, doRefreshRun());
            optmenu = OurUtil.makeMenu(bar, "Options", VK_O, doRefreshOption());
            windowmenu = OurUtil.makeMenu(bar, "Window", VK_W, doRefreshWindow(false));
            windowmenu2 = OurUtil.makeMenu(null, "Window", VK_W, doRefreshWindow(true));
            helpmenu = OurUtil.makeMenu(bar, "Help", VK_H, null);
            if (!Util.onMac()) OurUtil.makeMenuItem(helpmenu, "About Alloy...", VK_A, doAbout());
            OurUtil.makeMenuItem(helpmenu, "Quick Guide",                       VK_Q, doHelp());
            OurUtil.makeMenuItem(helpmenu, "See the Copyright Notices...",      VK_L, doLicense());
        } finally {
            wrap = false;
        }

        // Pre-load the visualizer
        viz = new VizGUI(false, "", windowmenu2, enumerator, evaluator);
        viz.doSetFontSize(FontSize.get());

        // Create the toolbar
        try {
            wrap = true;
            toolbar = new JToolBar();
            toolbar.setFloatable(false);
            if (!Util.onMac()) toolbar.setBackground(background);
            toolbar.add(OurUtil.button("New", "Starts a new blank model", "images/24_new.gif", doNew()));
            toolbar.add(OurUtil.button("Open", "Opens an existing model", "images/24_open.gif", doOpen()));
            toolbar.add(OurUtil.button("Reload", "Reload all the models from disk", "images/24_reload.gif", doReloadAll()));
            toolbar.add(OurUtil.button("Save", "Saves the current model", "images/24_save.gif", doSave()));
            toolbar.add(runbutton=OurUtil.button("Execute", "Executes the latest command", "images/24_execute.gif", doExecuteLatest()));
            toolbar.add(stopbutton=OurUtil.button("Stop", "Stops the current analysis", "images/24_execute_abort2.gif", doStop(2)));
            stopbutton.setVisible(false);
            toolbar.add(showbutton=OurUtil.button("Show", "Shows the latest instance", "images/24_graph.gif", doShowLatest()));
            toolbar.add(Box.createHorizontalGlue());
            toolbar.setBorder(new OurBorder(false,false,false,false));
        } finally {
            wrap = false;
        }

        // Choose the antiAlias setting
        OurAntiAlias.enableAntiAlias(AntiAlias.get());

        // Create the message area
        logpane = OurUtil.scrollpane(null);
        log = new SwingLogPanel(logpane, fontName, fontSize, background, Color.BLACK, new Color(.7f,.2f,.2f), this);

        // Create the text area
        wrap = true;
        Runner chg = notifyChange(), focus = notifyFocusGained();
        wrap = false;
        text = new OurTabbedEditor(chg, focus, frame, fontName, fontSize, TabSize.get());
        text.do_syntaxHighlighting(! SyntaxDisabled.get());

        // Add everything to the frame, then display the frame
        Container all=frame.getContentPane();
        all.setLayout(new BorderLayout());
        all.removeAll();
        JPanel lefthalf=new JPanel();
        lefthalf.setLayout(new BorderLayout());
        lefthalf.add(toolbar, BorderLayout.NORTH);
        lefthalf.add(text, BorderLayout.CENTER);
        splitpane = OurUtil.splitpane(JSplitPane.HORIZONTAL_SPLIT, lefthalf, logpane, width/2);
        splitpane.setResizeWeight(0.5D);
        status = OurUtil.make(OurAntiAlias.label(" "), new Font(fontName, Font.PLAIN, fontSize), Color.BLACK, background);
        status.setBorder(new OurBorder(true,false,false,false));
        all.add(splitpane, BorderLayout.CENTER);
        all.add(status, BorderLayout.SOUTH);

        // Generate some informative log messages
        log.logBold("Alloy Analyzer "+Version.version()+" (build date: "+Version.buildDate()+")\n\n");

        // If on Mac, then register an application listener
        try {
            wrap = true;
            if (Util.onMac()) MacUtil.registerApplicationListener(doShow(), doAbout(), doOpenFile(""), doQuit());
        } finally {
            wrap = false;
        }

        // Add the new JNI location to the java.library.path
        try {
            System.setProperty("java.library.path", binary);
            // The above line is actually useless on Sun JDK/JRE (see Sun's bug ID 4280189)
            // The following 4 lines should work for Sun's JDK/JRE (though they probably won't work for others)
            String[] newarray = new String[]{binary};
            java.lang.reflect.Field old = ClassLoader.class.getDeclaredField("usr_paths");
            old.setAccessible(true);
            old.set(null,newarray);
        } catch (Throwable ex) { }

        // Testing the SAT solvers
        if (1==1) {
            satChoices = SatSolver.values().makeCopy();
            String test1 = Subprocess.exec(20000, new String[]{binary+fs+"berkmin", binary+fs+"tmp.cnf"});
            if (!isSat(test1)) satChoices.remove(SatSolver.BerkMinPIPE);
            String test2 = Subprocess.exec(20000, new String[]{binary+fs+"spear", "--model", "--dimacs", binary+fs+"tmp.cnf"});
            if (!isSat(test2)) satChoices.remove(SatSolver.SpearPIPE);
            if (!loadLibrary("minisat")) {
                log.logBold("Warning: JNI-based SAT solver does not work on this platform.\n");
                log.log("This is okay, since you can still use SAT4J as the solver.\n"+
                "For more information, please visit http://alloy.mit.edu/alloy4/\n");
                log.logDivider();
                log.flush();
                satChoices.remove(SatSolver.MiniSatJNI);
            }
            if (!loadLibrary("minisatprover")) satChoices.remove(SatSolver.MiniSatProverJNI);
            if (!loadLibrary("zchaff"))        satChoices.remove(SatSolver.ZChaffJNI);
            SatSolver now = SatSolver.get();
            if (!satChoices.contains(now)) {
                now=SatSolver.ZChaffJNI;
                if (!satChoices.contains(now)) now=SatSolver.SAT4J;
                now.set();
            }
            if (now==SatSolver.SAT4J && satChoices.size()>3 && satChoices.contains(SatSolver.CNF) && satChoices.contains(SatSolver.KK)) {
                log.logBold("Warning: Alloy4 defaults to SAT4J since it is pure Java and very reliable.\n");
                log.log("For faster performance, go to Options menu and try another solver like MiniSat.\n");
                log.log("If these native solvers fail on your computer, remember to change back to SAT4J.\n");
                log.logDivider();
                log.flush();
            }
        }

        // If the temporary directory has become too big, then tell the user they can "clear temporary directory".
        long space = Helper.computeTemporarySpaceUsed();
        if (space<0 || space>=20*1024768) {
            if (space<0) log.logBold("Warning: Alloy4's temporary directory has exceeded 1024M.\n");
            else log.logBold("Warning: Alloy4's temporary directory now uses "+(space/1024768)+"M.\n");
            log.log("To clear the temporary directory,\n"
            +"go to the File menu and click \"Clear Temporary Directory\"\n");
            log.logDivider();
            log.flush();
        }

        // Refreshes all the menu items
        doRefreshFile(); OurUtil.enableAll(filemenu);
        doRefreshEdit(); OurUtil.enableAll(editmenu);
        doRefreshRun(); OurUtil.enableAll(runmenu);
        doRefreshOption();
        doRefreshWindow(false); OurUtil.enableAll(windowmenu);
        frame.setJMenuBar(bar);

        // Open the given file, if a filename is given in the command line
        for(String f:args) if (f.toLowerCase(Locale.US).endsWith(".als")) {
            File file = new File(f);
            if (file.exists() && file.isFile()) doOpenFile(file.getPath());
        }

        // Update the title and status bar
        notifyChange();

        // Start a separate thread to query alloy.mit.edu to see if an updated version of Alloy has been released or not
        try {
            wrap = true;
            new Thread(doCheckForUpdates()).start();
        } finally {
            wrap = false;
        }

        // Launch the welcome screen if needed
        if (!"yes".equals(System.getProperty("debug")) && Welcome.get() < welcomeLevel) {
            String dismiss = Util.onMac() ? "Dismiss" : "Close";
            JCheckBox again = new JCheckBox("Show this message every time you start the Alloy Analyzer");
            again.setSelected(true);
            JOptionPane.showOptionDialog(frame, new Object[]{
                "Thank you for using the Alloy Analyzer "+Version.version(),
                " ",
                "Version 4 of the Alloy Analyzer is a complete rewrite,",
                "offering improvements in robustness, performance and usability.",
                "Models written in Alloy 3 will require some small alterations to run in Alloy 4.",
                " ",
                "Here are some quick tips:",
                " ",
                "* Function calls now use [ ] instead of ( )",
                "  For more details, please see http://alloy.mit.edu/alloy4/quickguide/",
                " ",
                "* The Execute button always executes the latest command.",
                "  To choose which command to execute, go to the Execute menu.",
                " ",
                "* The Alloy Analyzer comes with a variety of sample models.",
                "  To see them, go to the File menu and click Open Sample Models.",
                " ",
                again
            }, "Welcome", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{dismiss}, dismiss);
            if (!again.isSelected()) Welcome.set(welcomeLevel);
        }
    }
}
