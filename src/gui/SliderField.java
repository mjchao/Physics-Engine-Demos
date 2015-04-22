package gui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JLabel lblSliderDescription;
	protected String sliderDescription;
	
	protected JSlider sldrSlider;
	
	public SliderField( String fieldDescription , int sliderMin , int sliderMax ) {
		this.lblSliderDescription = new JLabel( fieldDescription + " (1)" );
		this.sliderDescription = fieldDescription;
		this.sldrSlider = new JSlider( sliderMin , sliderMax );
		
		setLayout( new FlowLayout( FlowLayout.CENTER ) );
		add( this.lblSliderDescription );
		add( this.sldrSlider );
		addSliderListener();
	}
	
	public SliderField( String fieldDescription , int sliderMin , int sliderMax , int sliderValue ) {
		this( fieldDescription , sliderMin , sliderMax );
		this.sldrSlider.setValue( sliderValue );
	}
	
	public SliderField( String fieldDescription , int sliderMin , int sliderMax , int sliderValue , int minorTickSpacing , int majorTickSpacing ) {
		this( fieldDescription , sliderMin , sliderMax , sliderValue );
		this.sldrSlider.setMinorTickSpacing( minorTickSpacing );
		this.sldrSlider.setMajorTickSpacing( majorTickSpacing );
		this.sldrSlider.setPaintLabels( true );
		this.sldrSlider.setPaintTicks( true );
	}
	
	public int getValue() {
		return this.sldrSlider.getValue();
	}
	
	public void addSliderListener() {
		this.sldrSlider.addChangeListener( new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				SliderField.this.lblSliderDescription.setText( SliderField.this.sliderDescription + " (" + SliderField.this.sldrSlider.getValue() + ")" );
			}
			
		});
	}
}
