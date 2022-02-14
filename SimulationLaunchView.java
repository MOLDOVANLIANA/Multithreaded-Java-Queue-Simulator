import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class SimulationLaunchView {

	private JFrame frame;
	private JTextField NrClientsField;
	private JTextField NrQueuesField;
	private JTextField TMaxSimulationField;
	private JTextField MinArrivalTimeField;
	private JTextField MaxArrivalTimeField;
	private JTextField MinProcessingTimeField;
	private JTextField MaxProcessingTimeField;
	private JButton SimulationStartButton;
	private JTextField FileNameField;
	private JLabel lblNewLabel_1_2_2;
	private JList RealTimeList;
	private DefaultListModel ListModel;
	private JTextField textField;
	private JLabel lblNewLabel_2;
	private JTextField DeltaTimeField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimulationLaunchView window = new SimulationLaunchView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimulationLaunchView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 890, 516);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		NrClientsField = new JTextField();
		NrClientsField.setBounds(173, 65, 114, 36);
		panel.add(NrClientsField);
		NrClientsField.setColumns(10);
		
		NrQueuesField = new JTextField();
		NrQueuesField.setBounds(173, 134, 114, 36);
		NrQueuesField.setColumns(10);
		panel.add(NrQueuesField);
		
		TMaxSimulationField = new JTextField();
		TMaxSimulationField.setBounds(173, 197, 114, 36);
		TMaxSimulationField.setColumns(10);
		panel.add(TMaxSimulationField);
		
		MinArrivalTimeField = new JTextField();
		MinArrivalTimeField.setBounds(430, 73, 86, 20);
		MinArrivalTimeField.setColumns(10);
		panel.add(MinArrivalTimeField);
		
		MaxArrivalTimeField = new JTextField();
		MaxArrivalTimeField.setBounds(740, 73, 86, 20);
		MaxArrivalTimeField.setColumns(10);
		panel.add(MaxArrivalTimeField);
		
		MinProcessingTimeField = new JTextField();
		MinProcessingTimeField.setBounds(430, 115, 86, 20);
		MinProcessingTimeField.setColumns(10);
		panel.add(MinProcessingTimeField);
		
		MaxProcessingTimeField = new JTextField();
		MaxProcessingTimeField.setBounds(740, 107, 86, 20);
		MaxProcessingTimeField.setColumns(10);
		panel.add(MaxProcessingTimeField);
		
		JLabel lblNewLabel = new JLabel("Number of clients:");
		lblNewLabel.setBounds(8, 59, 144, 49);
		panel.add(lblNewLabel);
		
		JLabel lblNumberOfQueues = new JLabel("Number of queues:");
		lblNumberOfQueues.setBounds(10, 145, 153, 14);
		panel.add(lblNumberOfQueues);
		
		JLabel lblTmaxSimulation = new JLabel("TMax Simulation");
		lblTmaxSimulation.setBounds(8, 208, 144, 14);
		panel.add(lblTmaxSimulation);
		
		JLabel lblNewLabel_1 = new JLabel("TMinArrival");
		lblNewLabel_1.setBounds(297, 71, 114, 25);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("TMinProcessing");
		lblNewLabel_1_1.setBounds(299, 111, 121, 28);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("TMaxArrival");
		lblNewLabel_1_2.setBounds(553, 71, 154, 25);
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("TMaxProcessing");
		lblNewLabel_1_2_1.setBounds(553, 113, 154, 25);
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_2_1);
		
		SimulationStartButton = new JButton("Start Simulation");
		SimulationStartButton.setBounds(318, 410, 168, 36);
		SimulationStartButton.addActionListener(this::OnSimulationStart);
		panel.add(SimulationStartButton);
		
		FileNameField = new JTextField();
		FileNameField.setBounds(483, 201, 160, 28);
		panel.add(FileNameField);
		FileNameField.setColumns(10);
		
		lblNewLabel_1_2_2 = new JLabel("FileName");
		lblNewLabel_1_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_2.setBounds(317, 203, 154, 25);
		panel.add(lblNewLabel_1_2_2);
		
		ListModel = new DefaultListModel<>();
		
		JList list = new JList(ListModel);
		list.setBounds(33, 258, 793, 128);
		panel.add(list);
		
		DeltaTimeField = new JTextField();
		DeltaTimeField.setEditable(false);
		DeltaTimeField.setBounds(740, 224, 86, 20);
		panel.add(DeltaTimeField);
		DeltaTimeField.setColumns(10);
		
		lblNewLabel_2 = new JLabel("Current Time:");
		lblNewLabel_2.setBounds(653, 224, 77, 23);
		panel.add(lblNewLabel_2);
		
	}
	
	void OnSimulationStart(ActionEvent e)
	{
		SimulationStartButton.setEnabled(false);
		int NrClients = Integer.parseInt(NrClientsField.getText());
		int NrQueues = Integer.parseInt(NrQueuesField.getText());
		int TMaxSimulation = Integer.parseInt(TMaxSimulationField.getText());
		int MinArrivalTime = Integer.parseInt(MinArrivalTimeField.getText());
		int MaxArrivalTime = Integer.parseInt(MaxArrivalTimeField.getText());
		int MinProcessingTime = Integer.parseInt(MinProcessingTimeField.getText());
		int MaxProcessingTime = Integer.parseInt(MaxProcessingTimeField.getText());
		
		SimulationParameters SParameters = new SimulationParameters(NrClients, NrQueues, TMaxSimulation, MinArrivalTime, MaxArrivalTime, MinProcessingTime, MaxProcessingTime, FileNameField.getText());
		SimulationManager SManager = new SimulationManager(SParameters, this);
		Thread T = new Thread(SManager);
		T.start();
	}
	public void SetListContent(List<String> ListContent)
	{
		ListModel.clear();
		for(String S:ListContent)
		{
			ListModel.addElement(S);
		}
		
	}
	public void SetDeltaTime(int DeltaTime)
	{
		DeltaTimeField.setText(Integer.toString(DeltaTime));
	}
}
