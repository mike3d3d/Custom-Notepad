import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;


public class Notepad extends JFrame implements ActionListener, WindowListener {


    JTextArea textArea = new JTextArea(); //Creates area of typable text
    File filename;

    public Notepad() 
    {
		
        Font font=new Font("Arial",Font.PLAIN,15); //Initializes default font
        Container container = getContentPane(); //Initializes default container		
        JMenuBar menuBar = new JMenuBar(); //Initializes menu bar  
        //Elements in the top menu bar
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");
        container.setLayout(new BorderLayout());

        //Initializes scroll bar on the side of text area
		JScrollPane scrollBar = new JScrollPane(textArea);
		scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollBar.setVisible(true);

        //Initializes text area to default font, and proper text wrapping for nice formatting
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        container.add(scrollBar);


        createMenuItem(file, "New"); //Initializes "New" choice inside "File" in the menuBar
        createMenuItem(file, "Open"); //Initializes "Open" choice inside "File" in the menuBar
        createMenuItem(file, "Save"); //Initializes "Save" choice inside "File" in the menuBar
        file.addSeparator();
        createMenuItem(file, "Exit"); //Initializes "Exit" choice inside "File" in the menuBar

        createMenuItem(edit, "Copy"); //Initializes "Copy" choice inside "Edit" in the menuBar
        createMenuItem(edit, "Paste"); //Initializes "Paste" choice inside "Edit" in the menuBar
        createMenuItem(edit, "Cut"); //Initializes "Cut" choice inside "Edit" in the menuBar

        createMenuItem(help, "Dark Mode");
        createMenuItem(help, "Light Mode");
        help.addSeparator();
        createMenuItem(help, "About Notepad"); //Initializes "About Notepad" choice inside "Help" in the menuBar
        

        //Adds "File", "Edit", and "Help" to the menuBar and initializes it
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);

        //Initializes default icon, window size, title, and visibility
        setJMenuBar(menuBar);
        setIconImage(Toolkit.getDefaultToolkit().getImage("gope.gif"));
		addWindowListener(this);
		setSize(500,500);
		setTitle("Untitled.txt - Notepad");        
        
        setVisible(true);

    }

    public void createMenuItem(JMenu men, String str) { //Allows for easy creation of new JMenuItems

        JMenuItem item = new JMenuItem(str);
        item.addActionListener(this);
        men.add(item);
    }

    //Clears current text area, reads selected file, and outputs to textArea
    public void openFile(String name) throws IOException {
        
        BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(name)));
        String str;
        textArea.setText("");
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        while((str = read.readLine()) != null) {

            textArea.setText(textArea.getText() + str + "\r\n");
        }

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        read.close();
    }

    //Takes current textArea data and writes it to DataOutputStream
    public void saveFile(String name) throws IOException {

        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream data = new DataOutputStream(new FileOutputStream(name));
        data.writeBytes(textArea.getText());
        data.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void actionPerformed(ActionEvent a) {

        JFileChooser fileChooser = new JFileChooser();

        if (a.getActionCommand().equals("New")) { //Sets default paramters when clicking on "New"

            this.setTitle("Untitled.txt - Notepad");
            textArea.setText("");
            filename = null;

        } else if (a.getActionCommand().equals("Open")) {

            int ret = fileChooser.showDialog(null,"Open");
            if(ret == JFileChooser.APPROVE_OPTION) {
                try{
					File file = fileChooser.getSelectedFile();
					openFile(file.getAbsolutePath());
					this.setTitle(file.getName()+ " - Notepad");
					filename = file;
				}catch(IOException ex1){}
            }

        } else if (a.getActionCommand().equals("Save")) {

            if(filename != null) {

                fileChooser.setCurrentDirectory(filename);
                fileChooser.setSelectedFile(filename);
            } else {

                fileChooser.setSelectedFile(new File("Untitled.txt"));
            }

            int ret = fileChooser.showSaveDialog(null);
            if(ret == JFileChooser.APPROVE_OPTION) {
                try{
					
					File file = fileChooser.getSelectedFile();
					saveFile(file.getAbsolutePath());
					this.setTitle(file.getName()+ " - Notepad");
					filename = file;
					
				}catch(Exception ex2){}
            }
        } else if (a.getActionCommand().equals("Exit")) { //Upon clicking "Exit"
            System.exit(0);
        } else if (a.getActionCommand().equals("Copy")) {
            textArea.copy();
        } else if (a.getActionCommand().equals("Paste")) {
            textArea.paste();
        } else if (a.getActionCommand().equals("Cut")) {
            textArea.cut();
        } else if (a.getActionCommand().equals("About Notepad")) {
            JOptionPane.showMessageDialog(this, "Created by Michael Skolnick (https://github.com/mike3d3d)");

        } else if (a.getActionCommand().equals("Dark Mode")) {
            textArea.setBackground(Color.BLACK);
            textArea.setForeground(Color.WHITE);            
        } else if (a.getActionCommand().equals("Light Mode")) {
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
        }
    }

    //Methods to satisfy WindowListener implementation
    public void windowOpened(WindowEvent a){}
    public void windowDeactivated(WindowEvent a){}
	public void windowActivated(WindowEvent a){}
	public void windowDeiconified(WindowEvent a){}
	public void windowIconified(WindowEvent a){}
	public void windowClosed(WindowEvent a){}
    public void windowClosing(WindowEvent a) {
		System.exit(0);
	}
	


    public static void main(String[] args) {
        
        new Notepad();
    }

}
