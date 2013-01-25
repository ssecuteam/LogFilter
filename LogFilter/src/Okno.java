import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.event.ListDataListener;

class Okno extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// window size
	private final static int WIDTH = 800;
	private final static int LENGTH = 600;

	//private ArrayList<String> tabNames=new ArrayList<String>();
	
	// declaration of components
	private JPanel mainJPanel, menuJPanel, buttonJPanel;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	//private JList logList, blackList, regularList, filteredList, processList;
	
	
	private JButton btnOpenLog, btnOpenBlack, btnOpenRegular, btnFilter;
	private JScrollPane temp_scroll = new JScrollPane();
	private JViewport temp_view = new JViewport();
	private JComboBox<String> comboBoxLogFile;
	private JComboBox<String> comboBoxBlackListFile;
	private JComboBox<String> comboBoxRegExFile;
	private ComboBoxModel<String> comboBoxModel;

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
		
		JLabel logLabel=new JLabel("Log file");
		JLabel blackListLabel=new JLabel("Black list file");
		JLabel regExLabel=new JLabel("Regular expression file");
		
		
		class cDefaultComboBoxModel extends DefaultComboBoxModel<String>{
			@Override
			public int getSize() {
				int tabCount=tabbedPane.getComponentCount();//tabNames.size();
				
				if(tabCount>0)
					tabCount--;
				
				System.out.println("getSize: " +tabCount);
				return tabCount;
			}
			
			@Override
			public String getElementAt(int i) {
				System.out.println("getElementAt: " + i);
				return tabbedPane.getTitleAt(i);
			}
			
			 @Override
			public void setSelectedItem(Object anObject) {
				System.out.println("setSelected: " + anObject.toString());
				super.setSelectedItem(anObject);
			}
		}
		
		comboBoxLogFile=new JComboBox<String>(new cDefaultComboBoxModel());
		comboBoxBlackListFile=new JComboBox<String>(new cDefaultComboBoxModel());
		comboBoxRegExFile=new JComboBox<String>(new cDefaultComboBoxModel());
		
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

		// setting menu components
		buttonJPanel.add(btnOpenLog);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(btnOpenBlack);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(btnOpenRegular);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		logLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		blackListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		regExLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		buttonJPanel.add(logLabel);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(comboBoxLogFile);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(blackListLabel);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(comboBoxBlackListFile);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(regExLabel);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		buttonJPanel.add(comboBoxRegExFile);
		buttonJPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		buttonJPanel.add(btnFilter);
		// setting up panels
		menuJPanel.add(buttonJPanel);

		mainJPanel.add(menuJPanel, BorderLayout.WEST);
		mainJPanel.add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(mainJPanel);

		pack();
		setVisible(true);
	}
/*
	// addJListTab method for adding JList tabs to tabbedPane
		public JList<String> addJListTab(String tab_name, ArrayList<String> list) {
			JList<String> temp_list=new JList(list.toArray());
			Boolean new_tab = true;
			int tabNumber=0;
			//checking if there are any tab components
				if (tabbedPane.getComponentCount() > 0) {
					//if tab components >0
					for (int i=0; i<tabbedPane.getComponentCount(); i++, tabNumber++) {
						//checking each component in tab pane
						System.out.println("tabNumber: " + tabbedPane.getComponent(tabNumber).getName());
						
						if(tabbedPane.getComponent(i) instanceof javax.swing.JScrollPane){//.getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")){
							System.out.println("i: " + tabbedPane.getComponent(i).getClass().toString());
								if (tabbedPane.getTitleAt(tabNumber).equals(tab_name)) {
									//if tab_name already existed - set temp_list as reference to this tab
									temp_scroll = (JScrollPane) tabbedPane.getComponentAt(tabNumber);
									temp_view = (JViewport) temp_scroll.getViewport();
									temp_list = (JList<String>) temp_view.getComponent(0);
									new_tab = false;
								}		
						}else
						{
							tabNumber--;
						}
						
						
						// end if tabbedPane.getComponent(i).getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")
								
								
								
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
*/
	/*
	// getJListFromTabName returns JList object from tabbedPane
		public JList getJListFromTabName(String tab_name) {
			JList<String> temp_list=new JList();
			int tabNumber=0;
			//checking if there are any tab components
				if (tabbedPane.getComponentCount() > 0) {
					//if tab components >0
					for (int i=0; i<tabbedPane.getComponentCount(); i++, tabNumber++) {
						//checking each component in tab pane
						System.out.println(tabbedPane.getComponent(i).getName());
						if(tabbedPane.getComponent(i) instanceof javax.swing.JScrollPane){//.getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")){
							//if component is not tab label with button
								if (tabbedPane.getTitleAt(tabNumber).equals(tab_name)) {
									//if tab_name already existed - set temp_list as reference to this tab
									temp_scroll = (JScrollPane) tabbedPane.getComponentAt(tabNumber);
									temp_view = (JViewport) temp_scroll.getViewport();
									temp_list = (JList<String>) temp_view.getComponent(0);
								} else {
									System.out.println("There's no such tab in tabbedPane");
									temp_list = null;
								}
					}else{
						tabNumber--;// end if tabbedPane.getComponent(i).getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")
					}
					}//end of for

				} else {			
					System.out.println("There's no such tab in tabbedPane");
					temp_list = null;		
				}
			pack();
			return temp_list;
				
	}*/

	
	// addJListTab method for adding JList tabs to tabbedPane
		public JList<String> addJListTab(String tab_name, ArrayList<String> list) {
	        JList<String> temp_list=new JList(list.toArray());
	        Boolean new_tab = true;
	        int tabNumber=0;
	        //checking if there are any tab components
	              if (tabbedPane.getComponentCount() > 0) {
	                   //if tab components >0
	                   for (int i=0; i<tabbedPane.getComponentCount(); i++, tabNumber++) {
	                         //checking each component in tab pane                         
	                         if(tabbedPane.getComponent(i) instanceof javax.swing.JScrollPane){//.getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")){
	                        	//if component is scroll pane
	                                     if (tabbedPane.getTitleAt(tabNumber).equals(tab_name)) {
	                                           //if tab_name already existed - set temp_list as reference to this tab
	                                           temp_scroll = (JScrollPane) tabbedPane.getComponentAt(tabNumber);
	                                           temp_view = (JViewport) temp_scroll.getViewport();
	                                           temp_list = (JList<String>) temp_view.getComponent(0);
	                                           new_tab = false;
	                                           System.out.println("zak³adka istnieje");
	                                     }           
	                         }else
	                         {
	                               tabNumber--;
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
			public JList<String> getJListFromTabName(String tab_name) {
				JList<String> temp_list=new JList();
				int tabNumber=0;
				//checking if there are any tab components
					if (tabbedPane.getComponentCount() > 0) {
						//if tab components >0
						for (int i=0; i<tabbedPane.getComponentCount(); i++, tabNumber++) {
							//checking each component in tab pane
							//System.out.println(tabbedPane.getComponent(tabNumber).getName());
							if(tabbedPane.getComponent(i) instanceof javax.swing.JScrollPane){//if(tabbedPane.getComponent(i).getClass().getName().equals("javax.swing.plaf.basic.BasicTabbedPaneUI$TabContainer")){
								//if component is scroll pane
								System.out.println("getJListFromTabName - this tabname was found ");
								
									if (tabbedPane.getTitleAt(tabNumber).equals(tab_name)) {
										//if tab_name already existed - set temp_list as reference to this tab
										temp_scroll = (JScrollPane) tabbedPane.getComponentAt(tabNumber);
										temp_view = (JViewport) temp_scroll.getViewport();
										temp_list = (JList<String>) temp_view.getComponent(0);
										System.out.println("getJListFromTabName - this tabneme was found: " + tabbedPane.getTitleAt(tabNumber));
										return temp_list;
									} else {
										System.out.println("getJListFromTabName - this tabneme was NOT found ");
									//	System.out.println("There's no such tab in tabbedPane");
										temp_list = null;
									}
							} else {
								tabNumber--;
							}
						}

					} else {			
						System.out.println("getJListFromTabName: getComponentCount() <= 0 There's no such tab in tabbedPane");
						temp_list = null;		
					}
				pack();
			return temp_list;
		}
	
	
	class LoadBtnActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("LoadBtnActionListener - getComponentCount: " + tabbedPane.getComponentCount());
			
			for (int i=0; i<tabbedPane.getComponentCount(); i++) {
				
				System.out.println(tabbedPane.getComponent(i).getClass().getName());
				
				if(tabbedPane.getComponent(i) instanceof javax.swing.JScrollPane)
				{
					System.out.println("Jest panel");
				}
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
			//trzeba odswiezyc combobox zeby wyswietlil poprawna liste z nazwami zakladek
			comboBoxLogFile.updateUI();
			comboBoxBlackListFile.updateUI();
			comboBoxRegExFile.updateUI();
		}
	}
	
	class FilterBtnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			/*cFilterFrame filterPanel=new cFilterFrame(Okno.this);
			filterPanel.show();*/
			JList<String> logList=getJListFromTabName(comboBoxLogFile.getSelectedItem().toString());
			System.out.println("comboBoxLogFile.getSelectedItem().toString(): " + comboBoxLogFile.getSelectedItem().toString());
			ListModel<String> listModel=logList.getModel();
			
			ArrayList<String> arrayListLog=new ArrayList<String>();
			for(int i=0; i<listModel.getSize(); i++)
				arrayListLog.add(listModel.getElementAt(i));
			
			JList<String> blackList=getJListFromTabName(comboBoxBlackListFile.getSelectedItem().toString());
			ListModel<String> blackListModel=blackList.getModel();
			
			ArrayList<String> arrayListblackList=new ArrayList<String>();
			for(int i=0; i<blackListModel.getSize(); i++){
				arrayListblackList.add(blackListModel.getElementAt(i));
			}

			JList<String> regExList=getJListFromTabName(comboBoxRegExFile.getSelectedItem().toString());
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
