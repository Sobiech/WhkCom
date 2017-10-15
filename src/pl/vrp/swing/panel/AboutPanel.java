package pl.vrp.swing.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class AboutPanel extends JDialog {
  
	private static final long serialVersionUID = 1L;

	private final String col = "30px,40px,pref:grow,40px,30px";
	private final String row = "10px,30px,5px,30px,10px,pref:grow,10px,"
							 + "30px,15px,40px,10px,80px,30px,"
							 + "4px,20px,10px";
	
	private JTextPane universityName;
	private JTextPane wydzialName;
	
	private JTextPane titlePane;
	private JTextPane projectNamePane;

	private JTextPane rulesPane;
	private JTextPane contactPane;
	
	private JLabel logoImage;
  
	private Font smallDescriptionFont = new Font("Calibri", 0, 12);
	private Font descriptionFont = new Font("Calibri", 1, 13);
	private Font rightsFont = new Font("Calibri", 0 , 10);
  
	private Font titleFont = new Font("Calibri", 0, 20);
	private Font subTitleFont = new Font("Calibri",0, 18);

	public AboutPanel(){
		initPanel();
	}
  
	private void initComponents(){
	  
		this.universityName = 	createPane("POLITECHNIKA KOSZALIŃSKA", this.titleFont);
		this.wydzialName = 		createPane("Wydział Elektroniki i Informatyki", this.subTitleFont);
	  	
		this.titlePane = 		createPane("PRACA DYPLOMOWA INŻYNIERSKA", this.titleFont);
		this.projectNamePane= 	createPane("Genetyczna optymalizacja trasy przejazdu samochodów\n"
	  								+ " w środowisku z wieloma klientami i wieloma magazynami", this.descriptionFont );
	  
		this.rulesPane = 		createPane("Promotor: dr hab. inż. Adam Słowik, prof. nadzw. PK\n"
	  								+ "Rodzaj studiów: Stacjonarne\n"
	  								+ "Kierunek: Informatyka\n"
	  								+ "Specjalność: Programowanie Komputerów i Sieci Informatycznych",this.smallDescriptionFont);

		this.contactPane =		createPane("  Kontakt: kacper.sobisz@gmail.com                                                                    All Rights Reserved by: Sobiech 2016/2017", this.rightsFont);
	}
  
	private JTextPane createPane(String str, Font font) {
		JTextPane pane = new JTextPane();
		pane.setEditable(false);
		pane.setFont(font);
		pane.setText(str);
	
		return pane;
	}
  
	private void initPanel(){
	 	setTitle("Informacje o aplikacji");
		setModal(true);
		setSize(500, 570);
		setResizable(false);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				AboutPanel.this.setVisible(false);
			}
		});
		
		Dimension dialogSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		if (dialogSize.height > screenSize.height) {
		  dialogSize.height = screenSize.height;
		}
		
		if (dialogSize.width > screenSize.width) {
		  dialogSize.height = screenSize.width;
		}
		
		setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
		
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BorderLayout());
		
		try {
		  this.logoImage = new JLabel(new ImageIcon(getClass().getResource("logo.png")));
		} catch (Exception e) {
		  this.logoImage = new JLabel();
		  e.printStackTrace();
		}
		
		initComponents();
		contentPane.add(createPanel(), "Center");
	}
  
	public JPanel createPanel() {
		JPanel pane = new JPanel();
    
		FormLayout formLayout = new FormLayout(col, row);
		pane.setLayout(formLayout);
		pane.setBackground(Color.WHITE);
	  
		CellConstraints cc = new CellConstraints();
		CellConstraints.Alignment align = CellConstraints.CENTER;
	  
		pane.add(this.universityName, cc.xyw(2, 2, 4, align, align));
		pane.add(this.wydzialName, cc.xyw(2, 4, 4, align, align));
	  
		pane.add(this.logoImage, cc.xy(3, 6, align, align));
	  
	  
		pane.add(this.titlePane, cc.xyw(2, 8, 4, align, align));
		pane.add(this.projectNamePane, cc.xyw(2, 10, 4, align, align));

	  
		pane.add(this.rulesPane, cc.xyw(2, 12, 4, align, align));
	  
		pane.add(new JSeparator(0), cc.xyw(2, 14, 3, CellConstraints.FILL, align));
		pane.add(this.contactPane, cc.xyw(1, 15, 5, align, align));
	  
		return pane;
	}
}
