package javaCrud;

import java.awt.EventQueue;


import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class JavaCrud {

	private JFrame frmBook;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frmBook.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();		
		
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	

	 public void Connect()
	    {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root","");
	        }
	        catch (ClassNotFoundException ex) 
	        {
	         
	        }
	        catch (SQLException ex) 
	        {
	              
	        }

	    }
	 
	 public void table_load() {
		    try {
		        String sql = "SELECT * FROM book";
		        PreparedStatement pst = con.prepareStatement(sql);
		        ResultSet rs = pst.executeQuery();

		        // Create a DefaultTableModel with appropriate column names
		        DefaultTableModel model = new DefaultTableModel();
		        ResultSetMetaData metaData = rs.getMetaData();
		        int columnCount = metaData.getColumnCount();
		        for (int i = 1; i <= columnCount; i++) {
		            model.addColumn(metaData.getColumnName(i));
		        }

		        
		        while (rs.next()) {
		            Object[] rowData = new Object[columnCount];
		            for (int i = 1; i <= columnCount; i++) {
		                rowData[i - 1] = rs.getObject(i);
		            }
		            model.addRow(rowData);
		        }

		        
		        table.setModel(model);

		        
		        rs.close();
		        pst.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        
		    }
		}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBook = new JFrame();
		frmBook.setTitle("BOOK");
		frmBook.setBounds(100, 100, 1007, 591);
		frmBook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBook.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(405, 11, 166, 54);
		frmBook.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 206, 235));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(32, 95, 415, 236);
		frmBook.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(33, 37, 106, 29);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(33, 101, 88, 29);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(33, 159, 88, 29);
		panel.add(lblNewLabel_1_2);
		
		txtbname = new JTextField();
		txtbname.setBounds(158, 39, 201, 29);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(158, 103, 201, 29);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(158, 161, 201, 29);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    String bname,edition,price;
				    
				    bname = txtbname.getText();
				    edition = txtedition.getText();
				    price = txtprice.getText();
				                
				     try {
				        pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
				        pst.setString(1, bname);
				        pst.setString(2, edition);
				        pst.setString(3, price);
				        pst.executeUpdate();
				        JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
				        table_load();
				                       
				        txtbname.setText("");
				        txtedition.setText("");
				        txtprice.setText("");
				        txtbname.requestFocus();
				       }

				    catch (SQLException e1) 
				        {            
				       e1.printStackTrace();
				    }
				
			}
		});
		btnNewButton.setBounds(51, 342, 121, 66);
		frmBook.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(Color.CYAN);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(182, 342, 121, 66);
		frmBook.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBackground(Color.RED);
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtbname.setText("");
		        txtedition.setText("");
		        txtprice.setText("");
		        txtbname.requestFocus();
				
			}
		});
		btnClear.setBounds(314, 343, 121, 66);
		frmBook.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(484, 103, 448, 305);
		frmBook.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(135, 206, 235));
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(32, 430, 415, 113);
		frmBook.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Book ID");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1_1.setBounds(65, 11, 88, 29);
		panel_1.add(lblNewLabel_1_1_1);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				
	                
	                 try {
	                      
	                        String id = txtbid.getText();

	                            pst = con.prepareStatement("select name,edition,price from book where id = ?");
	                            pst.setString(1, id);
	                            ResultSet rs = pst.executeQuery();

	                        if(rs.next()==true)
	                        {
	                          
	                            String name = rs.getString(1);
	                            String edition = rs.getString(2);
	                            String price = rs.getString(3);
	                            
	                            txtbname.setText(name);
	                            txtedition.setText(edition);
	                            txtprice.setText(price);
	    
	                        }   
	                        else
	                        {
	                            txtbname.setText("");
	                            txtedition.setText("");
	                            txtprice.setText("");
	                             
	                        }
	                    } 
	                
	                 catch (SQLException ex) {
	                       
	                    }
	            }
				
				
			
		});
		txtbid.setBounds(171, 14, 170, 26);
		panel_1.add(txtbid);
		txtbid.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(135, 206, 250));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                String bname,edition,price,bid;
                
                
                bname = txtbname.getText();
                edition = txtedition.getText();
                price = txtprice.getText();
                bid  = txtbid.getText();
                
                 try {
                        pst = con.prepareStatement("update book set name= ?,edition=?,price=? where id =?");
                        pst.setString(1, bname);
                        pst.setString(2, edition);
                        pst.setString(3, price);
                        pst.setString(4, bid);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record Update!!!!!");
                        table_load();
                       
                        txtbname.setText("");
                        txtedition.setText("");
                        txtprice.setText("");
                        txtbname.requestFocus();
                    }

                    catch (SQLException e1) {
                        
                        e1.printStackTrace();
                    }
			}
		});
		btnUpdate.setBounds(488, 446, 121, 66);
		frmBook.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.RED);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
		           String bid;
		           bid  = txtbid.getText();
		           
		            try {
		                   pst = con.prepareStatement("delete from book where id =?");
		           
		                   pst.setString(1, bid);
		                   pst.executeUpdate();
		                   JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
		                   table_load();
		                  
		                   txtbname.setText("");
		                   txtedition.setText("");
		                   txtprice.setText("");
		                   txtbname.requestFocus();
		               }

		               catch (SQLException e1) {
		                   
		                   e1.printStackTrace();
		               }
				
			}
		});
		btnDelete.setBounds(635, 446, 121, 66);
		frmBook.getContentPane().add(btnDelete);
	}
}
