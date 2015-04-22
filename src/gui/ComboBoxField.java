package gui;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComboBoxField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lblInputDescription;
	private JComboBox cboInput;
	
	public ComboBoxField( String fieldDescription , String[] options) {
		this.lblInputDescription = new JLabel( fieldDescription );
		
		this.cboInput = new JComboBox();
		for ( String option : options ) {
			this.cboInput.addItem( option );
		}
		
		setLayout( new FlowLayout( FlowLayout.CENTER ) );
		add( this.lblInputDescription ) ;
		add( this.cboInput );
	}

	public String getSelectedItem() {
		return ( String ) this.cboInput.getSelectedItem();
	}
}
