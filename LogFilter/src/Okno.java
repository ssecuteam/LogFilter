import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

class Okno extends JFrame {

     //window size
         private final static int WIDTH = 800;
         private final static int LENGTH = 600;

     //declaration of components
         private JPanel mainJPanel, menuJPanel, buttonJPanel;
         private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
         private JList logList, blackList, regularList, filteredList, processList;
         private JButton btnOpenLog, btnOpenBlack, btnOpenRegular;
         private JScrollPane temp_scroll= new JScrollPane();
         private JViewport temp_view = new JViewport();

         JFileChooser chooseFileDialog = new JFileChooser();

         LoadBtnActionListener loadBtnActionListener = this.new LoadBtnActionListener();


         public static void main(String[] args) {
 	        Okno okno = new Okno();
 	        	
 	  }
         
     public Okno(){
    	 
    	 
         //setting window name
         super("Log Filter");
         setLayout(); //must be the first called method


     }


     private void setLayout(){

         //default close operation
             this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         //get screen resolution
             Toolkit toolkit = Toolkit.getDefaultToolkit();
             Dimension screenSize = toolkit.getScreenSize();

         //set JFrame size
             setMinimumSize(new Dimension(WIDTH,LENGTH));
             setPreferredSize(new Dimension(WIDTH, LENGTH));


         //set Frame location
  this.setLocation(screenSize.width/2-this.getSize().width/2, 
screenSize.height/2-this.getSize().height/2);

     //creating panels
         mainJPanel = new JPanel(new BorderLayout());
         menuJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5) );
         //tabbedPane = new JTabbedPane(JTabbedPane.TOP);   // has to be initiated before for addJListTab method
         buttonJPanel = new JPanel();
         buttonJPanel.setLayout(new BoxLayout(buttonJPanel, 
BoxLayout.Y_AXIS));

     //menu buttons
         //creating menu buttons
             btnOpenLog = new JButton("Open Log");
             btnOpenBlack = new JButton("Open Black List");
             btnOpenRegular = new JButton("Open Regular List");

         //setting menu buttons size
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

         //setting menu button alignment
             btnOpenLog.setAlignmentX(Component.CENTER_ALIGNMENT);
             btnOpenBlack.setAlignmentX(Component.CENTER_ALIGNMENT);
btnOpenRegular.setAlignmentX(Component.CENTER_ALIGNMENT);

         //adding ActionListener to buttons
             btnOpenLog.addActionListener(loadBtnActionListener);
             btnOpenBlack.addActionListener(loadBtnActionListener);
             btnOpenRegular.addActionListener(loadBtnActionListener);

     //creating JLists
         logList = new JList(); // log list
         blackList = new JList(); // black list
         regularList = new JList(); // regular expressions list
         filteredList = new JList(); // results, filtered list
         processList = new JList(); // device processes list

     //setting menu components
         buttonJPanel.add(btnOpenLog);
         buttonJPanel.add(Box.createRigidArea(new Dimension(0,10)));
         buttonJPanel.add(btnOpenBlack);
         buttonJPanel.add(Box.createRigidArea(new Dimension(0,10)));
         buttonJPanel.add(btnOpenRegular);
     //setting up panels
         menuJPanel.add(buttonJPanel);

         tabbedPane.addTab("Logcat LOG", null, new JScrollPane(logList), 
null);
         tabbedPane.addTab("Black List", null, new 
JScrollPane(blackList), null);
         tabbedPane.addTab("Regular List", null, new 
JScrollPane(regularList), null);
         tabbedPane.addTab("Filtered List", null, new 
JScrollPane(filteredList), null);

         mainJPanel.add(menuJPanel, BorderLayout.WEST);
         mainJPanel.add(tabbedPane, BorderLayout.CENTER);
         getContentPane().add(mainJPanel);

     //this.add(new JPanel());
     pack();
     setVisible(true);
     }


     //addJListTab method for adding JList tabs to tabbedPane
     public JList<String> addJListTab (String tab_name, String value){
         DefaultListModel<String> listModel = new DefaultListModel<String>();
         listModel.addElement(value);
         JList<String> temp_list = new JList<String>(listModel);
         Boolean new_tab = true;
         if(tabbedPane.getComponentCount()>0){
             int i=0;
             for(Component tab :tabbedPane.getComponents() ){
                 i++;
                 System.out.println(tabbedPane.getTitleAt(i-1));
                 if (tabbedPane.getTitleAt(i-1).equals(tab_name)){
                     System.out.println("w if"+tabbedPane.getTitleAt(i-1)+" ======= "+ tab_name);
                     temp_scroll= 
(JScrollPane)tabbedPane.getComponentAt(i-1);
                     temp_view = (JViewport)temp_scroll.getViewport();
                     temp_list = (JList<String>) temp_view.getComponent(0);
                     new_tab=false;
                 }
             }
             if (new_tab) tabbedPane.addTab(tab_name, null, new 
JScrollPane(temp_list), null);
         } else {
             tabbedPane.addTab(tab_name, null, new 
JScrollPane(temp_list), null);
         }
         System.out.println(temp_list);
         pack();
         return temp_list;
     }

//DO POPRAWIENIA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 

     //getJListFromTabName returns JList object from tabbedPane
     public JList getJListFromTabName(String tabName){
         JList temp_list = new JList();
         if(tabbedPane.getComponentCount()>0){
             for(int i=1; i==tabbedPane.getComponentCount();i++){
                 if (tabbedPane.getTitleAt(i-1)==tabName){
                     temp_scroll= 
(JScrollPane)tabbedPane.getComponentAt(i-1);
                     temp_view = (JViewport)temp_scroll.getViewport();
                     temp_list = (JList) temp_view.getComponent(0);
                 } else {
                     System.out.println("There's no such tab in tabbedPane");
                     temp_list=null;
                 }
             }
         } else {
             System.out.println("There's no tabs in tabbedPane");
             temp_list=null;
         }
         return temp_list;
     }


//    @Override
//    public void actionPerformed(ActionEvent event) {
//        String eventCaller = event.getActionCommand();
//        chooseFileDialog.setCurrentDirectory(new File("."));
//        System.out.println(event);
//        tabbedPane.addTab("nowa", null, new JScrollPane(), null);
//        if(eventCaller=="Open Log"){
//                int returnVal = chooseFileDialog.showOpenDialog((Component) event.getSource());
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                   // File file = chooseFileDialog.getSelectedFile();
//                    addJListTab("nowa");
//                } else {
//
//                }
//        }
//    }


     class LoadBtnActionListener implements ActionListener{

         @Override
         public void actionPerformed(ActionEvent event) {

             addJListTab("nowa","jeden");
             System.out.println(getJListFromTabName("nowa"));
             chooseFileDialog.setCurrentDirectory(new File("."));
                    int returnVal = chooseFileDialog.showOpenDialog((Component) event.getSource());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                       // File file = chooseFileDialog.getSelectedFile();

                   }
         }

     }

}
