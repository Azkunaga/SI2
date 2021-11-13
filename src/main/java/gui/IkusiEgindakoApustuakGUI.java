package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezero;
import domain.BezeroAdapter;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IkusiEgindakoApustuakGUI extends JFrame {

	private JPanel contentPane;
	private MainGUI main;
	private MainBezeroGUI back;
	private IkusiEgindakoApustuakGUI frame;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public IkusiEgindakoApustuakGUI(MainGUI main, MainBezeroGUI back, Bezero b) {
		this.main = main;
		this.back = back;
		this.frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		System.out.println(b.getApustuak());
		BezeroAdapter ba = new BezeroAdapter(b.getApustuak());

		table = new JTable(ba);
		contentPane.add(table, BorderLayout.CENTER);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(31, 5, 380, 280);
		scrollPane.setPreferredSize(new Dimension(380, 280));
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(scrollPane);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("Atzera");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				back.setVisible(true);
			}
		});
		btnNewButton.setBounds(326, 301, 85, 21);
		panel.add(btnNewButton);
	}

}
