
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class UserQueryCSVDialog {

    private JFrame mainFrame;
    private GenericDataReaderIntoMap ourGenericDataReaderIntoMap;
    private String userInput;
    private JTextArea resultsArea;
    private JTextField inputField;
    private JLabel queryLabel;
    private static final String DEFAULT_DIRECTORY = "C:\\Users\\tqbar\\TBLocal\\ComputerScience\\IntellijProjects\\Chapter10-MapDataReading-Solution\\DataFiles\\ThirdPartyDataCSVFiles";
    private static final String DEFAULT_FILE = "TopSongs5000Edited.csv";

    public UserQueryCSVDialog() {
        showFileChooser();
    }

    private void showFileChooser() {
        // Set up the JFileChooser
        JFileChooser fileChooser = new JFileChooser(DEFAULT_DIRECTORY);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Optional: add a file filter for CSV
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        // Preselect the default file if it exists
        File defaultFile = new File(DEFAULT_DIRECTORY, DEFAULT_FILE);
        if (defaultFile.exists()) {
            fileChooser.setSelectedFile(defaultFile);
        }

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            openUserQueryDialog(selectedFile.getAbsolutePath());
        } else {
            System.exit(0); // Exit if cancelled
        }
    }

    private void openUserQueryDialog(String filename) {
        // Instantiate the GenericDataReaderIntoMap with the selected file
        ourGenericDataReaderIntoMap = new GenericDataReaderIntoMap(filename);

        // Prepare the main dialog window
        mainFrame = new JFrame("User Query CSV Dialog");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700, 400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        // Panel at the top for input and submit button
        JPanel inputPanel = new JPanel(new BorderLayout(8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Label using keyTitle
        String labelText = ourGenericDataReaderIntoMap.keyTitle + ": <Enter key>";
        queryLabel = new JLabel(labelText);
        queryLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 16));

        // Submit button action
        submitButton.addActionListener(e -> handleUserInput());

        // Allow Enter key to trigger submit
        inputField.addActionListener(e -> handleUserInput());

        inputPanel.add(queryLabel, BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);

        // Results area in center
        resultsArea = new JTextArea();
        resultsArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resultsArea.setLineWrap(true);
        resultsArea.setWrapStyleWord(true);
        resultsArea.setEditable(false);
        JScrollPane resultsScroll = new JScrollPane(resultsArea);

        // Panel at the bottom for Clear button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("SansSerif", Font.PLAIN, 16));

        clearButton.addActionListener(e -> {
            inputField.setText("");
            resultsArea.setText("");
            inputField.requestFocusInWindow();
        });

        bottomPanel.add(clearButton);

        mainFrame.add(inputPanel, BorderLayout.NORTH);
        mainFrame.add(resultsScroll, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    private void handleUserInput() {
        userInput = inputField.getText().trim();
        if (userInput.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a key to look up.", "Input Required", JOptionPane.WARNING_MESSAGE);
            inputField.requestFocusInWindow();
            return;
        }
        // Attempt to query the CSV using keyValueLookup
        String result = ourGenericDataReaderIntoMap.keyValueLookup(userInput);
        if (result != null) {
            appendResult("> " + userInput);
            appendResult(result);
        } else {
            // Try keyValueNearby if not found
            String nearby = ourGenericDataReaderIntoMap.keyValueNearby(userInput);
            appendResult("> " + userInput);
            appendResult(nearby != null ? nearby : "No result or close matches found.");
        }
        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    private void appendResult(String text) {
        resultsArea.append(text + "\n");
        resultsArea.setCaretPosition(resultsArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        // For better cross-platform appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(UserQueryCSVDialog::new);
    }
}
