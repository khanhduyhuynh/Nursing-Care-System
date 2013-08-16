//  Author: Khanh Duy Huynh
//  Date: 20/09/2011
//  File Name: NursingCareSys.java
import java.awt.*; // For GUI components support
import javax.swing.*; // For Swing support
import javax.swing.border.TitledBorder; // For TitledBorder support
import java.awt.event.*; // For event handling support
import java.util.*; // For ArrayList support
import java.io.*; // For IO support

public class NursingCareSys extends JFrame
{
	// Declare class components, variables and constants

	private JPanel pnMain;

	private JPanel pnData;
	private JPanel pnData_Top;
	private JPanel pnData_Bottom;

	private JPanel pnDisplay;
	private JPanel pnDisplay_Output;
	private JPanel pnDisplay_Button;

	private JPanel pnButton;

	private JLabel lbPatientName;
	private JLabel lbIdentityNo;
	private JLabel lbNutrition;
	private JLabel lbHygiene;
	private JLabel lbActivity;

	private JTextField txtPatientName;
	private JTextField txtIdentityNo;

	private JComboBox cboNutrition;
	private JComboBox cboHygiene;
	private JComboBox cboActivity;

	private JTextArea txaDisplay;
	private JScrollPane sbrDisplay;

	private JButton btnSortName;
	private JButton btnSortCategory;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnClassify;
	private JButton btnClear;
	private JButton btnExit;

	private final String[] ARR_NUTRITION = {"None", "Feed Assistance", "Complete Feeding"};
	private final String[] ARR_HYGIENE = {"None", "Bath seft", "Bath with assistance"};
	private final String[] ARR_ACTIVITY = {"None", "Up without assistance", "Up with assistance", "Complete Immobility"};

	private ArrayList<Patient> pList;
	private ArrayList<Patient> pListTemp;

	private final String FILE_NAME = "PatientData.csv";

	private final int NUTRITION_ASSISTANCE = 3;
	private final int NUTRITION_COMPLETE= 6;

	private final int HYGIENE_SELF = 3;
	private final int HYGIENE_ASSISTANCE = 6;

	private final int ACTIVITY_NO_ASSISTANCE = 3;
	private final int ACTIVITY_ASSISTANCE = 6;
	private final int ACTIVITY_COMPLETE = 12;

	private final int CATEGORY_MINIMUM = 1;
	private final int CATEGORY_MODERATE = 2;
	private final int CATEGORY_COMPLEX = 3;

	private final int CARE_HOURS_MINIMUM = 3;
	private final int CARE_HOURS_MODERATE = 6;
	private final int CARE_HOURS_COMPLEX = 9;

	private final int POINTS_MINIMUM_MAX = 9;
	private final int POINTS_MODERATE_MAX = 18;

	// Create an instance of the class
	public static void main (String[] args)
	{
		JFrame My_NursingCareSys = new NursingCareSys ();
	}

	// Use Constructor to initialize and add GUI components to frame, setup / add listeners for button clicks
	public NursingCareSys()
	{
		pList = new ArrayList<Patient>();
		pListTemp = new ArrayList<Patient>();

		pnMain = new JPanel();
		pnMain.setBorder(createTitleBorder("NURSING CARE SYSTEM"));
		pnMain.setLayout(new BorderLayout());

		pnData = new JPanel();
		pnData.setBorder(createTitleBorder("Patient Data"));
		pnData.setLayout(new GridLayout(2, 1));

		pnData_Top = new JPanel();
		pnData_Top.setLayout(new FlowLayout(0,10,4));

		lbPatientName = new JLabel("Patient Full Name:");
		txtPatientName = new JTextField(20);

		lbIdentityNo = new JLabel("Identity No:");
		txtIdentityNo = new JTextField(15);

		pnData_Top.add(lbPatientName);
		pnData_Top.add(txtPatientName);
		pnData_Top.add(lbIdentityNo);
		pnData_Top.add(txtIdentityNo);

		pnData_Bottom = new JPanel();
		pnData_Bottom.setLayout(new FlowLayout());

		lbNutrition = new JLabel("Nutrition: ");
		cboNutrition = new JComboBox();
		for(int i = 0; i < ARR_NUTRITION.length; i++)
		{
			cboNutrition.addItem(ARR_NUTRITION[i]);
		}

		pnData_Bottom.add(lbNutrition);
		pnData_Bottom.add(cboNutrition);

		lbHygiene = new JLabel("Hygiene: ");
		cboHygiene = new JComboBox();
		for(int i = 0; i < ARR_HYGIENE.length; i++)
		{
			cboHygiene.addItem(ARR_HYGIENE[i]);
		}

		pnData_Bottom.add(lbHygiene);
		pnData_Bottom.add(cboHygiene);

		lbActivity = new JLabel("Activity: ");
		cboActivity = new JComboBox();
		for(int i = 0; i < ARR_ACTIVITY.length; i++)
		{
			cboActivity.addItem(ARR_ACTIVITY[i]);
		}

		pnData_Bottom.add(lbActivity);
		pnData_Bottom.add(cboActivity);

		pnData.add(pnData_Top);
		pnData.add(pnData_Bottom);

		pnDisplay = new JPanel();
		pnDisplay.setBorder(createTitleBorder("Display Area"));
		pnDisplay.setLayout(new BorderLayout(40,100));

		pnDisplay_Output = new JPanel();
		pnDisplay_Output.setLayout(new FlowLayout(0,30,0));

		txaDisplay = new JTextArea("", 20, 56);
		txaDisplay.setEditable(false);
		txaDisplay.setForeground(Color.BLACK);
		sbrDisplay = new JScrollPane(txaDisplay);
		pnDisplay_Output.add(sbrDisplay);

		readFile();
	   	loadData();

		pnDisplay_Button = new JPanel();
		pnDisplay_Button.setLayout(new GridLayout(4,1,0,30));

		btnSortName = createSortNameButton();
		pnDisplay_Button.add(btnSortName);

		btnSortCategory = createSortCategoryButton();
		pnDisplay_Button.add(btnSortCategory);

		btnDelete = createDeleteButton();
		pnDisplay_Button.add(btnDelete);

		btnSave = createSaveButton();
		pnDisplay_Button.add(btnSave);

		pnDisplay.add(pnDisplay_Output, BorderLayout.CENTER);
		pnDisplay.add(pnDisplay_Button, BorderLayout.EAST);

		pnButton = new JPanel();
		pnButton.setBorder(createTitleBorder("Buttons"));
		pnButton.setLayout(new FlowLayout(0, 80,0));

		btnClassify = createClassifyButton();
		btnClear = createClearButton();
		btnExit = createExitButton();

		pnButton.add(btnClassify);
		pnButton.add(btnClear);
		pnButton.add(btnExit);

		pnMain.add(pnData, BorderLayout.NORTH);
		pnMain.add(pnDisplay, BorderLayout.CENTER);
		pnMain.add(pnButton, BorderLayout.SOUTH);


		this.setLayout(new GridLayout(1, 1));
		add(pnMain);

		setTitle ("Nursing Care System");
		pack();
		setLocationRelativeTo(null);
		setSize(870, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   	setVisible(true);

	}

	// Create TitledBorder for panel
	private TitledBorder createTitleBorder(String str)
	{
		Font font = new Font("Serif", Font.ITALIC, 18);
		TitledBorder titled = new TitledBorder(null, str,TitledBorder.LEFT, TitledBorder.TOP, font, Color.BLUE);
		return titled;
	}

	// Act on mouse clicks on Sort by Name buttons
	private JButton createSortNameButton()
	{
		JButton btnSortName = new JButton("Sort by Name");
		class SortNameListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				doSorting("Name");
			}
		}
		ActionListener listenerSortName = new SortNameListener();
		btnSortName.addActionListener(listenerSortName);
		return btnSortName;
	}

	// Act on mouse clicks on Sort by Category buttons
	private JButton createSortCategoryButton()
	{
		JButton btnSortCategory = new JButton("Sort by Category");
		class SortCategoryListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				doSorting("Category");
			}
		}
		ActionListener listenerSortCategory = new SortCategoryListener();
		btnSortCategory.addActionListener(listenerSortCategory);
		return btnSortCategory;
	}

	// Act on mouse clicks on Delete buttons
	private JButton createDeleteButton()
	{
		JButton btnDelete = new JButton("Delete Patient");
		class DeleteListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				if(txtIdentityNo.getText().equals(""))
				{
					showErrorMessage("Please enter Patien Id you want to delete");
					txtIdentityNo.requestFocus();
				}
				else
				{
					int index = doSearching(txtIdentityNo.getText());
					if(index == -1)
					{
						showErrorMessage("This patient Id doesn't exist, please enter Patien Id you want to delete again");
					}
					else
					{
						int intYesNoOption;
						intYesNoOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete patient ", JOptionPane.YES_NO_OPTION);
						if(intYesNoOption == 0)
						{

							try
							{
								String strName = pList.get(index).getPatientName();
								String strId = pList.get(index).getPatientId();
								int intCategory = pList.get(index).getPatientCategory();
								int intCareHours = pList.get(index).getCareHours();

								pList.remove(index);
								writeFile();

								txaDisplay.setText("");
								String strOutput = "This patient has been deleted:" + "\n\n";
								strOutput = strOutput + "Patient Name: " + strName + "\t" + "Id No: " + strId + "\t" + "Category: " + intCategory + "\n\n";
								strOutput = strOutput + "Required care hours: " + intCareHours;
								txaDisplay.setText(strOutput);

								reset();
							}
							catch(Exception e)
							{
								showErrorMessage("Deleting error");
							}
						}
					}
				}
			}
		}
		ActionListener listenerDelete = new DeleteListener();
		btnDelete.addActionListener(listenerDelete);
		return btnDelete;
	}

	// Act on mouse clicks on Save buttons
	private JButton createSaveButton()
	{
		JButton btnSave = new JButton("Save to File");
		class SaveListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				int numTemp = pListTemp.size();
				if(numTemp == 0)
				{
					showErrorMessage("You have not classified any new patient");
				}
				else
				{
					String strOutputDialog = "";
					String strOutput = "";
					if(pListTemp.size() == 1)
					{
						strOutput = "This patient has been classified:" + "\n\n";
					}
					else
					{
						strOutput = "These " + pListTemp.size() + " patients have been classified:" + "\n\n";
					}
					for(int i = 0; i < pListTemp.size(); i++)
					{
						strOutput = strOutput + "Patient Name: " + pListTemp.get(i).getPatientName() + "     " + "Id No: " + pListTemp.get(i).getPatientId() + "     " + "Category: " + pListTemp.get(i).getPatientCategory() + "\n\n";
						strOutput = strOutput + "Required care hours: " + pListTemp.get(i).getCareHours() + "\n\n";
					}

					strOutputDialog = strOutput + "Are you sure you want to save?";

					int intYesNoOption = JOptionPane.showConfirmDialog(null, strOutputDialog, "Save classified patient ", JOptionPane.YES_NO_OPTION);
					if(intYesNoOption == 0)
					{
						for(int i = 0; i < numTemp; i++)
						{
							Patient p = pListTemp.get(i);
							pList.add(p);
						}

						writeFile();

						for(int i = 0; i < pListTemp.size(); i++)
						{
							pList.add(pListTemp.get(i));
						}

						pListTemp.clear();

						txaDisplay.setText("");
						strOutput = strOutput.replace( "classified","saved" );
						txaDisplay.setText(strOutput);

					}
				}

			}

		}
		ActionListener listenerSave = new SaveListener();
		btnSave.addActionListener(listenerSave);
		return btnSave;
	}

	// Act on mouse clicks on Classify buttons
	private JButton createClassifyButton()
	{
		JButton btnClassify = new JButton("Classify Patient");
		btnClassify.setMargin(new Insets(10, 10, 10, 10));
		class ClassifyListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				String strName = txtPatientName.getText();
				String strIdentity = txtIdentityNo.getText();
				int indexNutrition = cboNutrition.getSelectedIndex();
				int indexHygiene = cboHygiene.getSelectedIndex();
				int indexActivity = cboActivity.getSelectedIndex();
				boolean blCheck = checkError(strName, strIdentity, indexNutrition, indexHygiene, indexActivity);
				if(blCheck == false)
				{
					int intNutritionValue = getNutritionValue(indexNutrition);
					int intHygieneValue = getHygieneValue(indexHygiene);
					int intActivityValue = getActivityValue(indexActivity);

					int intCategory = 0;
					int intCareHours = 0;
					int totalPoint = intNutritionValue + intHygieneValue + intActivityValue;

					if(totalPoint <= POINTS_MINIMUM_MAX)
					{
						intCategory = CATEGORY_MINIMUM;
						intCareHours = CARE_HOURS_MINIMUM;
					}
					else if(totalPoint > POINTS_MINIMUM_MAX && totalPoint <= POINTS_MODERATE_MAX)
					{
						intCategory = CATEGORY_MODERATE;
						intCareHours = CARE_HOURS_MODERATE;
					}
					else
					{
						intCategory = CATEGORY_COMPLEX;
						intCareHours = CARE_HOURS_COMPLEX;
					}

					Patient patient = new Patient(strName, strIdentity, indexNutrition, indexHygiene, indexActivity, intCategory, intCareHours);
					pListTemp.add(patient);

					txaDisplay.setText("");
					String strOutput = "";
					if(pListTemp.size() == 1)
					{
						strOutput = "This patient has been classified:" + "\n\n";
					}
					else
					{
						strOutput = "These " + pListTemp.size() + " patients have been classified:" + "\n\n";
					}
					for(int i = 0; i < pListTemp.size(); i++)
					{
						strOutput = strOutput + "Patient Name: " + pListTemp.get(i).getPatientName() + "\t" + "Id No: " + pListTemp.get(i).getPatientId() + "\t" + "Category: " + pListTemp.get(i).getPatientCategory() + "\n\n";
						strOutput = strOutput + "Required care hours: " + pListTemp.get(i).getCareHours() + "\n\n";
					}
					txaDisplay.setText(strOutput);

					reset();
				}
			}
		}
		ActionListener listenerClassify = new ClassifyListener();
		btnClassify.addActionListener(listenerClassify);
		return btnClassify;
	}

	// Act on mouse clicks on Clear buttons
	private JButton createClearButton()
	{
		JButton btnClear = new JButton("Clear");
		btnClear.setMargin(new Insets(10, 36, 10, 36));
		class ClearListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				int intYesNoOption;
				intYesNoOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear data?", "Clear data ", JOptionPane.YES_NO_OPTION);
				if(intYesNoOption == 0)
				{
					txaDisplay.setText("");
					reset();
				}

			}
		}
		ActionListener listenerClear = new ClearListener();
		btnClear.addActionListener(listenerClear);
		return btnClear;
	}

	// Act on mouse clicks on Exit buttons
	private JButton createExitButton()
	{
		JButton btnExit = new JButton("Exit");
		btnExit.setMargin(new Insets(10, 36, 10, 36));
		class ExitListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				int intYesNoOption;
				intYesNoOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit program ", JOptionPane.YES_NO_OPTION);
				if(intYesNoOption == 0)
				{
					System.exit(0);
				}
			}
		}
		ActionListener listenerExit = new ExitListener();
		btnExit.addActionListener(listenerExit);
		return btnExit;
	}

	// Read data from Excel file and add data to ArrayList
	private void readFile()
	{
		try
		{
			FileReader reader = new FileReader(FILE_NAME);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine())
			{
				String input = in.nextLine();
				String[] temp = input.split(",");
				int intNutrition = Integer.parseInt(temp[2]);
				int intHygiene = Integer.parseInt(temp[3]);
				int intActivity = Integer.parseInt(temp[4]);
				int intCategory = Integer.parseInt(temp[5]);
				int intCareHours = Integer.parseInt(temp[6]);

				Patient patient = new Patient(temp[0], temp[1], intNutrition, intHygiene, intActivity, intCategory, intCareHours);
				pList.add(patient);
			}

		}
		catch(Exception e)
		{
			showErrorMessage("File not found.");
		}
	}

	// Write data from ArrayList to Excel file
	private void writeFile()
	{
		try
		{
			PrintWriter out = null;
			out = new PrintWriter (new FileOutputStream(FILE_NAME));
			for (int i=0; i < pList.size(); i++)
			{
				out.println(pList.get(i).getPatientName() + "," + pList.get(i).getPatientId() + ","+pList.get(i).getNutritionIndex() + "," + pList.get(i).getHygieneIndex() + "," + pList.get(i).getActivityIndex() + "," + pList.get(i).getPatientCategory() + "," + pList.get(i).getCareHours());
			}
			out.close();
		}
		catch(FileNotFoundException exception)
		{
			showErrorMessage("File not found");
		}
	}

	// Display data from ArrayList on text area
	private void loadData()
	{
		txaDisplay.setText("");
		String strOutput = (" " + "Name" + " \t " + "Patient Id" + " \t " + "Nutrition" + " \t " + "Hygiene" + " \t " + "Activity" + " \t " + "Category" + " \t " + "Care Hours" + " \n");
		for(int i = 0; i < pList.size(); i++)
		{
			strOutput = strOutput + "\n" + " " + pList.get(i).getPatientName() + " \t " + pList.get(i).getPatientId() + " \t " + pList.get(i).getNutritionIndex() + " \t " + pList.get(i).getHygieneIndex() + " \t " + pList.get(i).getActivityIndex() + " \t " + pList.get(i).getPatientCategory() + " \t " + pList.get(i).getCareHours();
		}
		txaDisplay.setText(strOutput);
	}

	// Sorting
	private void doSorting(String strType)
	{
		try
		{
			pList.clear();
			readFile();

			MergeSorter sorter = new MergeSorter(pList, strType);
			pList = sorter.sort();

			loadData();
		}
		catch(Exception e)
		{
			showErrorMessage(strType + " sorting error");
		}
	}

	// Searching
	private int doSearching(String strId)
	{
		pList.clear();
		readFile();

		MergeSorter sorter = new MergeSorter(pList, "Id");
		pList = sorter.sort();

		BinarySearcher searcher = new BinarySearcher(pList);
		int index = searcher.search(strId);
		return index;

	}

	// Check and show the message if input data is not valid
	private boolean checkError(String strName, String strIdentity, int indexNutrition, int indexHygiene, int indexActivity)
	{
		boolean inputError = false;
		if(!(strName.equals("")))
		{
			if(!(strIdentity.equals("")))
			{
				int intCheck = doSearching(strIdentity);
				if(intCheck != -1)
				{
					showErrorMessage("This patient Id already existed ");
					txtIdentityNo.requestFocus();
					inputError = true;
				}
				else
				{

					if(indexNutrition == 0 && indexHygiene == 0 && indexActivity == 0)
					{
						showErrorMessage("At least one of three combox boxs: Nutrition, Hygiene, Activity must be selected ");
						inputError = true;
					}

				}
			}
			else
			{
				showErrorMessage("Patient Id can not be blank");
				txtIdentityNo.requestFocus();
				inputError = true;
			}
		}
		else
		{
			showErrorMessage("Patient Name can not be blank");
			txtPatientName.requestFocus();
			inputError = true;
		}
		return inputError;
	}

	// Get points of nutrition care need selected
	private int getNutritionValue(int indexNutrition)
	{
		int nutritionValue = 0;
		if(indexNutrition == 0)
		{
			nutritionValue = 0;
		}
		if(indexNutrition == 1)
		{
			nutritionValue = NUTRITION_ASSISTANCE;
		}
		if(indexNutrition == 2)
		{
			nutritionValue = NUTRITION_COMPLETE;
		}
		return nutritionValue;
	}

	// Get points of hygiene care need selected
	private int getHygieneValue(int indexHygiene)
	{
		int hygieneValue = 0;
		if(indexHygiene == 0)
		{
			hygieneValue = 0;
		}
		if(indexHygiene == 1)
		{
			hygieneValue = HYGIENE_SELF;
		}
		if(indexHygiene == 2)
		{
			hygieneValue = HYGIENE_ASSISTANCE;
		}
		return hygieneValue;
	}

	// Get points of activity care need selected
	private int getActivityValue(int indexActivity)
	{
		int activityValue = 0;
		if(indexActivity == 0)
		{
			activityValue = 0;
		}
		if(indexActivity == 1)
		{
			activityValue = ACTIVITY_NO_ASSISTANCE;
		}
		if(indexActivity == 2)
		{
			activityValue = ACTIVITY_ASSISTANCE;
		}
		if(indexActivity == 3)
		{
			activityValue = ACTIVITY_COMPLETE;
		}
		return activityValue;
	}

	// Clear data
	private void reset()
	{
		txtPatientName.setText("");
		txtIdentityNo.setText("");
		cboNutrition.setSelectedIndex(0);
		cboHygiene.setSelectedIndex(0);
		cboActivity.setSelectedIndex(0);
	}

	// Show error message
	private void showErrorMessage(String str)
	{
		JOptionPane.showMessageDialog(this, str, "Error", JOptionPane.ERROR_MESSAGE);
	}
}