package org.vatvit.movielib.views.ui.editor.panels;

import java.awt.GridBagLayout;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.Dimension;

public class FileSelectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField fileField = null;
	private JButton fileSelectButton = null;
	private JFileChooser fc = null;  //  @jve:decl-index=0:visual-constraint="10,220"
	private ActionListener al = null;

	/**
	 * This is the default constructor
	 */
	public FileSelectPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		fc = new JFileChooser();

		fc.setSize(new Dimension(581, 344));
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 0;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridx = 0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getFileField(), gridBagConstraints);
		this.add(getFileSelectButton(), gridBagConstraints1);
	}

	/**
	 * This method initializes fileField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFileField() {
		if (fileField == null) {
			fileField = new JTextField();
		}
		return fileField;
	}

	/**
	 * This method initializes fileSelectButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFileSelectButton() {
		if (fileSelectButton == null) {
			fileSelectButton = new JButton();
			fileSelectButton.setText("Valitse");
			fileSelectButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						File f = new File(new File(fileField.getText()).getCanonicalPath());
						fc.setCurrentDirectory(f);
					} catch (IOException e) {
					}
					int returnVal = fc.showOpenDialog(null);

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			           File file = fc.getSelectedFile();
			           fileField.setText(file.getAbsolutePath());
			           if(al!=null) {
			        	   ActionEvent actionEvent = new ActionEvent(this,
			        			   ActionEvent.ACTION_PERFORMED, fileField.getText());
			        	   al.actionPerformed(actionEvent);
			           }
			           
			        } 

				}
				
			});
		}
		return fileSelectButton;
	}

	public void setFileName(String fileName) {
		fileField.setText(fileName);
		if(al!=null) {
     	   ActionEvent actionEvent = new ActionEvent(this,
     			   ActionEvent.ACTION_PERFORMED, fileField.getText());
     	   al.actionPerformed(actionEvent);
        }
	}
	public String getFileName() {
		return fileField.getText();
	}
	
	public void setFileSelectedListener(ActionListener al) {
		this.al = al;
	}
	
}
