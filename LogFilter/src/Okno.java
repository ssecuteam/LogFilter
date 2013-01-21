import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

class Okno extends JFrame {

	// window size
	private final static int WIDTH = 800;
	private final static int LENGTH = 600;

	// declaration of components
	private JPanel mainJPanel, menuJPanel, buttonJPanel;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JList logList, blackList, regularList, filteredList, processList;
	private JButton btnOpenLog, btnOpenBlack, btnOpenRegular, btnFilter;
	private JScrollPane temp_scroll = new JScrollPane();
	private JViewport temp_view = new JViewport();

	JFileChooser chooseFileDialog = new JFileChooser();

	LoadBtnActionListener loadBtnActionListener = new LoadBtnActionListener();
	FilterBtnActionListener filterBtnActionListener=new FilterBtnActionListener();

	public static void main(String[] args) {
		Okno okno = new Okno();
	}

	public Okno() {

		// setting window name
		super("Log Filter");
		setLayout(); // must be the first called method

	}

	private void setLayout() {

		// default close operation
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// get screen resolution
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		// set JFrame size
		setMinimumSize(new Dimension(WIDTH, LENGTH));
		setPreferredSize(new Dimension(WIDTH, LENGTH));

		// set Frame location
		this.setLocation(screenSize.width / 2 - this.getSize().width / 2, screenSize.height / 2 - this.getSize().height
				/ 2);

		// creating panels
		mainJPanel = new JPanel(new BorderLayout());
		menuJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		// tabbedPane = new JTabbedPane(JTabbedPane.TOP); // has to be initiated
		// before for addJListTab method
		buttonJPanel = new JPanel();
		buttonJPanel.setLayout(new BoxLayout(buttonJPanel, BoxLayout.Y_AXIS));
		
		// menu buttons
		// creating menu buttons
		btnOpenLog = new JButton("Open Log");
		btnOpenBlack = new JButton("Open Black List");
		btnOpenRegular = new JButton("Open Regular List");
		btnFilter = new JButton("Filter");
		
		// setting menu buttons size
		Dimension btnSize = new Dimension(135, 25);
		btnOpenLog.setPreferredSize(btnSize);
		btnOpenLog.setMinimumSize(btnSize);
		btnOpenLog.setMaximumSize(btnSize);
		btnOpenBlack.setPreferredSize(btnSize);
		btnOpenBlack.setMinimumSize(btnSize);
		btnOpenBlack.setMaximumSize(btnSize);
		btnOpenRegular.setPreferredSize(btnSize);
		btnOpenRegular.setMinimumSize(btnSize);
		btnOpenRegular.setMaximumSize(btnSize);

		btnFilter.setPreferredSize(btnSize);
		btnFilter.setMinimumSize(btnSize);
		btnFilter.setMaximumSize(btnSize);
		
		// setting menu button alignment
		btnOpenLog.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOpenBlack.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOpenRegular.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnFilter.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// adding ActionListener to buttons
		btnOpenLog.addActionListener(loadBtnActionListener);
		btnOpenBlack.addActionListener(loadBtnActionListener);
		btnOpenRegular.addActionListener(loadBtnActionListener);
		btnFilter.addActionListener(filterBtnActionListener);

		// creating JLists
	//	logList = new JList(); // log list
	//	blackList = new JList(); // black list
	//	regularList = new JList(); // regular expressions list
	//	filteredList = new JList(); // results, filtered list
	//	processList = new JList(); // device processes list

		// setting menu components
		buttonJPanel.add(btnOpenLog);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(btnOpenBlack);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(btnOpenRegular);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(btnFilter);
		// setting up panels
		menuJPanel.add(buttonJPanel);

		//tabbedPane.addTab("Logcat LOG", null, new JScrollPane(logList), null);
	//	tabbedPane.addTab("Black List", null, new JScrollPane(blackList), null);
	//	tabbedPane.addTab("Regular List", null, new JScrollPane(regularList), null);
//		tabbedPane.addTab("Filtered List", null, new JScrollPane(filteredList), null);

		mainJPanel.add(menuJPanel, BorderLayout.WEST);
		mainJPanel.add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(mainJPanel);

		// this.add(new JPanel());
		pack();
		setVisible(true);
	}

	// addJListTab method for adding JList tabs to tabbedPane
	public JList<String> addJListTab(String tab_name, ArrayList<String> list) {
		JList<String> temp_list=new JList(list.toArray());

		Boolean new_tab = true;
		if (tabbedPane.getComponentCount() > 0) {
			int i = 0;
			for (Component tab : tabbedPane.getComponents()) {
				i++;
				System.out.println(tabbedPane.getTitleAt(i - 1));
				if (tabbedPane.getTitleAt(i - 1).equals(tab_name)) {
					new_tab = false;
				}
			}
			if (new_tab)
				tabbedPane.addTab(tab_name, null, new JScrollPane(temp_list), null);
		} else {
			tabbedPane.addTab(tab_name, null, new JScrollPane(temp_list), null);
		}
		System.out.println(temp_list);
		pack();
		return temp_list;
	}

	// DO
	// POPRAWIENIA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	// getJListFromTabName returns JList object from tabbedPane
	public JList getJListFromTabName(String tabName) {
		JList temp_list = new JList();
		
		if (tabbedPane.getComponentCount() > 0) {
			System.out.println("ilosc komp: " +tabbedPane.getComponentCount());
			for (int i = 1; i <= tabbedPane.getComponentCount(); i++) {
				System.out.println("title: " +tabbedPane.getTitleAt(i - 1) + ", tabName: " + tabName);
				if (tabbedPane.getTitleAt(i - 1) == tabName) {
					temp_scroll = (JScrollPane) tabbedPane.getComponentAt(i - 1);
					temp_view = (JViewport) temp_scroll.getViewport();
					temp_list = (JList) temp_view.getComponent(0);
					return temp_list;
				} else {
					System.out.println("There's no such tab in tabbedPane");
					temp_list = null;
				}
			}
		} else {
			System.out.println("There's no tabs in tabbedPane");
			temp_list = null;
		}
		return temp_list;
	}

	class LoadBtnActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println(getJListFromTabName("nowa"));
			chooseFileDialog.setCurrentDirectory(new File("."));
			int returnVal = chooseFileDialog.showOpenDialog((Component) event.getSource());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				 File file = chooseFileDialog.getSelectedFile();
				 try {
					addJListTab(file.getName(), LogFilter.wczytaniePliku(file.getAbsolutePath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
				
					e.printStackTrace();
				}
			}
		}

	}
	
	class FilterBtnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Window window=new Window(new cFilterFrame(Okno.this));
			cFilterFrame filterPanel=new cFilterFrame(Okno.this);
			filterPanel.show();
			///window.setAlwaysOnTop(true);
			//window.show();
		}
		
	}
	
	private class cFilterFrame extends JFrame implements ActionListener{
		private JComboBox<String> logFile;
		private JComboBox<String> blackListFile;
		private JComboBox<String> regExFile;
		
		public cFilterFrame(JFrame frame) {
			super("Filter");
			setLocation(frame.getX()+frame.getWidth()/3, frame.getY()+frame.getHeight()/3);
			setLayout(new BorderLayout());
			JPanel choosePanel=new JPanel(new GridLayout(3, 2,10,10));
			JPanel buttonPanel=new JPanel(new GridLayout(1,1,10,10));
			
			String[] tabNames=tabNames();
		
			JLabel logLabel=new JLabel("Choose log file");
			logFile=new JComboBox<String>(tabNames);
			
			JLabel blackListLabel=new JLabel("Choose black list file");
			blackListFile=new JComboBox<String>(tabNames);
			
			JLabel regExLabel=new JLabel("Choose regular expression file");
			regExFile=new JComboBox<String>(tabNames);
			
			choosePanel.add(logLabel);
			choosePanel.add(logFile);
			choosePanel.add(blackListLabel);
			choosePanel.add(blackListFile);
			choosePanel.add(regExLabel);
			choosePanel.add(regExFile);
			
			JButton btnFilter= new JButton("Filter");
			//JButton btnCancel=new JButton("Cancel");
			buttonPanel.add(btnFilter);
			btnFilter.addActionListener(this);
		//	buttonPanel.add(btnCancel);
			
			add(choosePanel);
			add(buttonPanel,BorderLayout.SOUTH);
			setAlwaysOnTop(true);
			pack();
			setVisible(true);
		}
		
		private String[] tabNames(){
			int tabCount=tabbedPane.getComponentCount();
			String[] names=new String[tabCount];
	
			for (int i = 0; i < tabCount; i++)
				names[i]=tabbedPane.getTitleAt(i);
				
			return names;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		
	
			

			System.out.println(logFile.getSelectedItem());
			System.out.println(blackListFile.getSelectedItem());
			System.out.println(regExFile.getSelectedItem());
			
			JList<String> logList=getJListFromTabName(logFile.getSelectedItem().toString());
			System.out.println(logFile.getSelectedItem().toString());
			ListModel<String> listModel=logList.getModel();
			
			ArrayList<String> arrayListLog=new ArrayList<String>();
			System.out.println("SIze: " + listModel.getSize());
			for(int i=0; i<listModel.getSize(); i++)
				arrayListLog.add(listModel.getElementAt(i));
	
			
			JList<String> blackList=getJListFromTabName(blackListFile.getSelectedItem().toString());
			System.out.println(logFile.getSelectedItem().toString());
			ListModel<String> blackListModel=blackList.getModel();
			
			ArrayList<String> arrayListblackList=new ArrayList<String>();
			System.out.println("SIze: " + listModel.getSize());
			for(int i=0; i<blackListModel.getSize(); i++)
				arrayListblackList.add(blackListModel.getElementAt(i));

			JList<String> regExList=getJListFromTabName(blackListFile.getSelectedItem().toString());
			System.out.println(logFile.getSelectedItem().toString());
			ListModel<String> regExListModel=regExList.getModel();
			
			ArrayList<String> arrayListregEx=new ArrayList<String>();
			System.out.println("SIze: " + listModel.getSize());
			for(int i=0; i<regExListModel.getSize(); i++)
				arrayListregEx.add(regExListModel.getElementAt(i));
				
		}
		}

}
