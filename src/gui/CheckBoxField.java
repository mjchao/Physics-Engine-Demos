package gui;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CheckBoxField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox chkInput = new JCheckBox();
	
	public CheckBoxField( String description ) {
		setLayout( new FlowLayout( FlowLayout.CENTER ) );
		this.chkInput.setText( description ); 
		this.add( this.chkInput );
	}
	
	public boolean isChecked() {
		return this.chkInput.isSelected();
	}
}
