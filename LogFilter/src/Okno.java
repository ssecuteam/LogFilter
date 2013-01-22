import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.ListModel;

class Okno extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// window size
	private final static int WIDTH = 800;
	private final static int LENGTH = 600;

	// declaration of components
	private JPanel mainJPanel, menuJPanel, buttonJPanel;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	//private JList logList, blackList, regularList, filteredList, processList;
	
	
	private JButton btnOpenLog, btnOpenBlack, btnOpenRegular, btnFilter;
	private JScrollPane temp_scroll = new JScrollPane();
	private JViewport temp_view = new JViewport();

	JFileChooser chooseFileDialog = new JFileChooser();

	LoadBtnActionListener loadBtnActionListener = new LoadBtnActionListener();
	FilterBtnActionListener filterBtnActionListener=new FilterBtnActionListener();

	public static void main(String[] args) {
		new Okno();
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
		this.setLocation(screenSize.width / 2 - this.getSize().width / 2, screenSize.height / 2 - this.getSize().height	/ 2);

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
		
		//creating JLists
//        logList = new JList(); // log list
//        blackList = new JList(); // black list
//        regularList = new JList(); // regular expressions list
//        filteredList = new JList(); // results, filtered list
//        processList = new JList(); // device processes list

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
		
		
//		tabbedPane.addTab("Logcat LOG", null, new JScrollPane(logList), null);
//		tabbedPane.setTabComponentAt(0, new ButtonTabComponent(tabbedPane));
//		tabbedPane.addTab("Black List", null, new JScrollPane(blackList), null);
//		tabbedPane.setTabComponentAt(1, new ButtonTabComponent(tabbedPane));
//		tabbedPane.addTab("Regular List", null, new JScrollPane(regularList), null);
//		tabbedPane.setTabComponentAt(2, new ButtonTabComponent(tabbedPane));
//		tabbedPane.addTab("Filtered List", null, new JScrollPane(filteredList), null);
//		tabbedPane.setTabComponentAt(3, new ButtonTabComponent(tabbedPane));
		
		
		
		
		
		
		mainJPanel.add(menuJPanel, BorderLayout.WEST);
		mainJPanel.add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(mainJPanel);

		pack();
		setVisible(true);
	}

	// addJListTab method for adding JList tabs to tabbedPane
		public JList<String> addJListTab(String tab_name, ArrayList<String> list) {
			JList<String> temp_list=new JList(list.toArray());
			Boolean new_tab = true;
			//checking if there are any tab components
				if (tabbedPane.getComponentCount() > 0) {
					//if tab components >0
					for (int i=0; i<tabbedPane.getComponentCount()-1; i++) {
						//checking each component in tab pane
						System.out.println(tabbedPane.getComponent(i).getName());
						if(tabbedPane.getComponent(i).getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")){
							//if component is not tab label with button
								if (tabbedPane.getTitleAt(i).equals(tab_name)) {
									//if tab_name already existed - set temp_list as reference to this tab
									temp_scroll = (JScrollPane) tabbedPane.getComponentAt(i);
									temp_view = (JViewport) temp_scroll.getViewport();
									temp_list = (JList<String>) temp_view.getComponent(0);
									new_tab = false;
								}		
						}// end if tabbedPane.getComponent(i).getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")
								
								
								
					}
					if (new_tab){
						//adding any later tab: tabbedPane.getComponentCount()-2 cause: index is from "0" and count is with new tab and label
						tabbedPane.addTab(tab_name, null, new JScrollPane(temp_list), null);
						tabbedPane.setTabComponentAt(tabbedPane.getComponentCount()-2, new ButtonTabComponent(tabbedPane));
					}
				} else {			
					//adding first Tab
					tabbedPane.addTab(tab_name, null, new JScrollPane(temp_list), null);			
					tabbedPane.setTabComponentAt(tabbedPane.getComponentCount()-1, new ButtonTabComponent(tabbedPane));			
				}
			pack();
			return temp_list;
		} //end of addJListTab

	// getJListFromTabName returns JList object from tabbedPane
		public JList getJListFromTabName(String tab_name) {
			JList<String> temp_list=new JList();
			//checking if there are any tab components
				if (tabbedPane.getComponentCount() > 0) {
					//if tab components >0
					for (int i=0; i<tabbedPane.getComponentCount()-1; i++) {
						//checking each component in tab pane
						System.out.println(tabbedPane.getComponent(i).getName());
						if(tabbedPane.getComponent(i).getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")){
							//if component is not tab label with button
								if (tabbedPane.getTitleAt(i).equals(tab_name)) {
									//if tab_name already existed - set temp_list as reference to this tab
									temp_scroll = (JScrollPane) tabbedPane.getComponentAt(i);
									temp_view = (JViewport) temp_scroll.getViewport();
									temp_list = (JList<String>) temp_view.getComponent(0);
								} else {
									System.out.println("There's no such tab in tabbedPane");
									temp_list = null;
								}
					}// end if tabbedPane.getComponent(i).getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")
					}//end of for

				} else {			
					System.out.println("There's no such tab in tabbedPane");
					temp_list = null;		
				}
			pack();
			return temp_list;
				
	}

	class LoadBtnActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			
			
			for (int i=0; i<tabbedPane.getComponentCount(); i++) {
				
				System.out.println(tabbedPane.getComponent(i).getClass().getName());
			}
			
			
			
			
			
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
			cFilterFrame filterPanel=new cFilterFrame(Okno.this);
			filterPanel.show();
		}
	}
	
	private class cFilterFrame extends JFrame implements ActionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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
		
			buttonPanel.add(btnFilter);
			btnFilter.addActionListener(this);
			
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
			JList<String> logList=getJListFromTabName(logFile.getSelectedItem().toString());
			ListModel<String> listModel=logList.getModel();
			
			ArrayList<String> arrayListLog=new ArrayList<String>();
			for(int i=0; i<listModel.getSize(); i++)
				arrayListLog.add(listModel.getElementAt(i));
			
			JList<String> blackList=getJListFromTabName(blackListFile.getSelectedItem().toString());
			ListModel<String> blackListModel=blackList.getModel();
			
			ArrayList<String> arrayListblackList=new ArrayList<String>();
			for(int i=0; i<blackListModel.getSize(); i++){
				arrayListblackList.add(blackListModel.getElementAt(i));
			}

			JList<String> regExList=getJListFromTabName(regExFile.getSelectedItem().toString());
			ListModel<String> regExListModel=regExList.getModel();
			
			ArrayList<String> arrayListregEx=new ArrayList<String>();
			for(int i=0; i<regExListModel.getSize(); i++)
				arrayListregEx.add(regExListModel.getElementAt(i));
			
			ArrayList<String> filterredLog=new ArrayList<String>();
				try {
					filterredLog=LogFilter.filterWithRegExExpressions(LogFilter.filterWithBlackList(arrayListLog,arrayListblackList),arrayListregEx);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			
			addJListTab("Result", filterredLog);
			
			try {
				BufferedWriter logFileWriter = new BufferedWriter(new FileWriter("wynik.txt"));
				for(String filtrred:filterredLog){
					logFileWriter.write(filtrred);
					logFileWriter.newLine();
				}
				logFileWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}
		}

}
