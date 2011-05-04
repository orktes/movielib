package org.vatvit.movielib.views.ui.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MenuList extends JList {
	private ActionListener al;

	public class MenuItemRenderer implements ListCellRenderer {
		protected final Border noFocusBorder = new EmptyBorder(15, 1, 1, 1);

		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			JLabel renderer = (JLabel) defaultRenderer
					.getListCellRendererComponent(list, value, index,
							isSelected, cellHasFocus);
			if (cellHasFocus) {
				renderer.setFont(new Font("Lucida Grande", Font.PLAIN, 40));

			}
			renderer.setBorder(noFocusBorder);

			return renderer;
		}

	}

	public MenuList() {

		this.setBackground(new Color(0, 0, 0));
		this.setForeground(new Color(180, 180, 180));
		this.setSelectionBackground(new Color(0, 0, 0));
		this.setSelectionForeground(new Color(255, 255, 255));
		this.setFont(new Font("Lucida Grande", Font.PLAIN, 36));
		this.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setCellRenderer(new MenuItemRenderer());
		MenuList self = this;
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (al == null)
					return;
				Object ob[] = getSelectedValues();
				if (ob.length > 1)
					return;
				if (me.getClickCount() == 1) {
					al.actionPerformed(new ActionEvent(this,
							ActionEvent.ACTION_PERFORMED, ob[0].toString()));
					me.consume();
				}
			}
		});

		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (hasFocus()) {
					if (al == null)
						return;
					if (ke.getKeyCode() == KeyEvent.VK_ENTER
							|| ke.getKeyCode() == KeyEvent.VK_RIGHT) {
						Object ob[] = getSelectedValues();
						if (ob.length > 1)
							return;

						al.actionPerformed(new ActionEvent(this,
								ActionEvent.ACTION_PERFORMED, ob[0].toString()));
						ke.consume();
					} else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE
							|| ke.getKeyCode() == KeyEvent.VK_LEFT) {
						al.actionPerformed(new ActionEvent(this,
								ActionEvent.ACTION_PERFORMED, "back"));
					} else {
						for (int i = 0; i < getModel().getSize(); i++) {
							if (getModel().getElementAt(i).toString()
									.toLowerCase().toCharArray()[0] == ke
									.getKeyChar()) {
								setSelectedIndex(i);
								break;
							}
						}
					}
				}

			}
		});

	}

	public ActionListener getActionListener() {
		return al;
	}

	public void setActionListener(ActionListener al) {
		this.al = al;
	}

}
