package pl.vrp.swing.menu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pl.vrp.swing.listener.VrpExecutor;

public class MenuBar extends JMenuBar {
	
    private static final long serialVersionUID = 8506007364487645290L;
    
    private VrpExecutor startActionListener;
    
    private static final String[] menuNames = { "Plik", "Pomoc" };
    private static final String[] itemNames = { "Wczytaj konfiguracjê", "Zapisz konfiguracjê", "O aplikacji" };
    private static final String[] itemCompName = { "LoadCFG", "SaveCFG", "AboutProgram" };
    
    private static final int [] itemkeyEvents = {KeyEvent.VK_W,KeyEvent.VK_Z,KeyEvent.VK_A};
    private static final int[] keyEvents = {KeyEvent.VK_P, KeyEvent.VK_O };
    
    private List<JMenuItem> menuItems;
    
    public MenuBar(VrpExecutor startAction)
    {
        this.startActionListener = startAction;
        initMenuBar();
    }
    
    private void initMenuBar()
    {
        this.menuItems = new ArrayList<JMenuItem>();
        for (int i = 0; i < itemNames.length; i++) {
          this.menuItems.add(createMenuItem(itemNames[i], itemCompName[i], itemkeyEvents[i]));
        }
        for (int i = 0; i < menuNames.length; i++) {
          add(createJMenu(menuNames[i], keyEvents[i], true, this.menuItems, i));
        }
    }
    
    private JMenu createJMenu(String menuName, int keyEvent, boolean isActive, List<JMenuItem> menuItems, int id)
    {
        JMenu jMenu = new JMenu(menuName);
        jMenu.setMnemonic(keyEvent);
        jMenu.setEnabled(isActive);
        switch (id)
        {
        case 0: 
          jMenu.add((JMenuItem)menuItems.get(0));
          jMenu.add((JMenuItem)menuItems.get(1));
          break;
        case 1: 
          jMenu.add((JMenuItem)menuItems.get(2));
        }
        return jMenu;
    }
    
    private JMenuItem createMenuItem(String itemName, String name, int key)
    {
        JMenuItem menuItem = new JMenuItem(itemName);
        menuItem.addActionListener(this.startActionListener);
        menuItem.setName(name);
        menuItem.setMnemonic(key);
        
        return menuItem;
    }
}
