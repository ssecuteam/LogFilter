import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;




public class CustomListCellRenderer extends JLabel implements ListCellRenderer {
    public CustomListCellRenderer() {
        setOpaque(true);
    }
    public Component getListCellRendererComponent(
        JList list,
        Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus)
    {
        setText(value.toString());
        setBackground(isSelected ? Color.red : Color.white);
        setForeground(isSelected ? Color.white : Color.black);
        return this;
    }
}

//Simple, set a custom ListCellRenderer to your JList using:
//list.setCellRenderer(myListCellrenderer);
//
//Now inside the overridden method getListCellRendererComponent() do something like this:
//public Component getListCellRendererComponent(.....) {
//    Component c = super.getListCellRendererComponent();
//    c.setBackGround(Color.blue)
//    return c;
//}
//
//The above example assumed that your custom renderer overrid DefaultListCellRenderer