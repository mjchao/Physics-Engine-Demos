package gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lblInputDescription;
	private JTextField txtInput = new JTextField( 5 );
	
	public InputField( String inputLabel ) {
		
		setLayout( new FlowLayout( FlowLayout.CENTER ) );
		this.lblInputDescription = new JLabel( inputLabel );
		add( this.lblInputDescription );
		add( this.txtInput );
	}
	
	public InputField( String inputLabel , String initialInput ) {
		this( inputLabel );
		this.txtInput.setText( initialInput );
	}
	
	public InputField( String inputLabel , String initialInput , int textFieldColumns ) {
		this( inputLabel , initialInput );
		this.txtInput.setColumns( textFieldColumns );
	}
	public String getInput() {
		return this.txtInput.getText();
	}
}
