package finalprojectcircularqueue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class flowchart extends JFrame {

    // List of courses for each term (from Term 1 to Term 12)
    private final String[][] courses = {
            {"GEPCM01X", "GEUTS01X", "GERPH01X", "PHYSED11", "CCINCOML", "CCPRGG1L"},   //Term 1
            {"GECTW01X", "GEMMW01X", "GESTS01X", "PHYSED12", "CTHASOPL", "CCPRGG2L"},   //Term 2
            {"GEETH01X", "GEENT01X", "MCWTS01X", "PHYSED13", "CCDISTR1", "CCOBJPGL"},   //Term 3
            {"GEFID01X", "CCMATAN1", "PHYSED14", "MCWTS02X", "CCDISTR2", "CCDATRCL"},   //Term 4
            {"GEACM01X", "CCMATAN2", "MCNAT01R", "CTINFMGL", "CCPHYS1L", "CCOMPORG"},   //Term 5
            {"CCQUAMET", "CTADVDBL", "CCALCOMP", "CTBASNTL", "CCPHYS2L", "        "},   //Term 6
            {"CCSFEN1L", "CCAUTOMA", "CCOPSYSL", "CCMACLRL", "GERIZ01X", "        "},   //Term 7
            {"CCADMACL", "CCSFEN2L", "CTINASSL", "GEITE01X", "        ", "        "},   //Term 8
            {"CCINTHCI", "CTAPDEVL", "CCDEPLRL", "CCMETHOD", "        ", "        "},   //Term 9
            {"CCTHESS1", "CTPRFISS", "CCPGLANG", "CCRNFLRL", "        ", "        "},   //Term 10
            {"CCTHESS2", "CCDATSCL", "        ", "        ", "        ", "        "},   //Term 11
            {"CCINTERN", "        ", "        ", "        ", "        ", "        "}    //Term 12
    };

    private final HashMap<String, String> preRequisites = new HashMap<>();

    public flowchart() {
        // Add pre-requisite subjects to the HashMap
        addPreRequisites();

        // Set the title and default close operation
        setTitle("Curriculum Flowchart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400); // Set window size to 800x500 pixels
        setLocationRelativeTo(null); // Center the window

        // Set background color of the frame
        getContentPane().setBackground(new Color(247, 247, 247)); // RGB [247, 247, 247]

        // Create the main panel with a GridBagLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0); // Remove spacing between components

        // Loop through terms and courses
        for (int term = 0; term < courses.length; term++) {
            gbc.gridx = term; // Set the column for the term
            gbc.gridy = 0;    // Reset row index for each term
            
            // Create a panel for the term label with a light blue background
            JPanel termPanel = new JPanel();
            termPanel.setBackground(new Color(0, 66, 118)); // Light blue color
            termPanel.setPreferredSize(new Dimension(120, 40)); // Same size as course codes
            termPanel.setLayout(new GridBagLayout()); // Centering layout for the term panel
            termPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            // Add a label for each term (Term 1, Term 2, etc.)
            JLabel termLabel = new JLabel("Term " + (term + 1), JLabel.CENTER);
            termLabel.setFont(new Font("Arial", Font.BOLD, 16));
            termLabel.setForeground(Color.WHITE); // Set text color to white

            // Center the term label within the term panel
            GridBagConstraints labelGbc = new GridBagConstraints();
            labelGbc.gridx = 0; // Center in the first column
            labelGbc.gridy = 0; // Center in the first row
            labelGbc.weightx = 1; // Expand in x direction
            labelGbc.weighty = 1; // Expand in y direction
            labelGbc.anchor = GridBagConstraints.CENTER; // Center alignment

            termPanel.add(termLabel, labelGbc); // Add the label to the term panel
            
            mainPanel.add(termPanel, gbc); // Add the term panel to the main panel
            
            // Add course codes for the current term
            for (String courseCode : courses[term]) {
                gbc.gridy++; // New row for each course code
                JPanel courseBox = createCourseBox(courseCode); // Create the course box
                mainPanel.add(courseBox, gbc);
            }
        }

        // Add mainPanel to the JFrame and make it visible
        add(new JScrollPane(mainPanel)); // Add scroll pane in case of large content
        setVisible(true);
    }
    


    // Helper method to create a course box with JLabel inside
    private JPanel createCourseBox(String preReq) {
        JPanel courseBox = new JPanel();
        courseBox.setBackground(Color.WHITE); // Set the background color to white
        courseBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Grey border
        courseBox.setPreferredSize(new Dimension(120, 40)); // Set preferred size of the course box

        JLabel label = new JLabel(preReq, JLabel.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center text horizontally
        label.setVerticalAlignment(SwingConstants.CENTER);   // Center text vertically

        // Set layout to center the label
        courseBox.setLayout(new GridBagLayout());
        courseBox.add(label); // Add the label to the course box

        // Add a mouse click listener to the course box
        courseBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Retrieve course details from the HashMap
                String grade = preRequisites.getOrDefault(preReq, "Grade not available");
                // Show a popup window when the course box is clicked
                JOptionPane.showMessageDialog(courseBox,
                    "Course: " + preReq + "\nGrade: " + grade,
                    "Course Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return courseBox;
    }
    
    private void addPreRequisites() {
        //Term 2 preRequisites
        preRequisites.put("CCPRGG2L", "CCPRGG1L");
        preRequisites.put("CTHASOPL", "CCINCOML");
        
        //Term 3 preRequisites
        preRequisites.put("CCOBJPGL", "CCPRGG2L");
        
        //Term 4 preRequisites
        preRequisites.put("CCDATRCL", "CCPRGG2L");
        preRequisites.put("CCDISTR2", "CCDISTR1");
        preRequisites.put("CCMATAN1", "CCDISTR1");
        
        //Term 5 preRequisites
        preRequisites.put("CCOMPORG", "CCOBJPGL");
        preRequisites.put("CCPHYS1L", "CCMATAN1");
        preRequisites.put("CTINFMGL", "CCDATRCL");
        preRequisites.put("CCMATAN2", "CCMATAN1");
        
        //Term 6 preRequisites
        preRequisites.put("CCQUAMET", "CCDISTR1");
        preRequisites.put("CTADVDBL", "CTINFMGL");
        preRequisites.put("CCALCOMP", "CCDATRCL");
        preRequisites.put("CTBASNTL", "CCOMPORG");
        preRequisites.put("CCPHYS2L", "CCPHYS1L");
        
        //Term 7 preRequisites
        preRequisites.put("CCSFEN1L", "CCDATRCL");
        preRequisites.put("CCAUTOMA", "CCALCOMP");
        preRequisites.put("CCOPSYSL", "CCOBJPGL");
        
        //Term 8 preRequisites
        preRequisites.put("CCADMACL", "CCMACLRL");
        preRequisites.put("CCSFEN2L", "CCSFEN1L");
        preRequisites.put("CTINASSL", "CTINFMGL");
        
        //Term 9 preRequisites
        preRequisites.put("CCINTHCI", "CCPRGG2L");
        preRequisites.put("CTAPDEVL", "CCOBJPGL");
        preRequisites.put("CCDEPLRL", "CCADMACL");
        preRequisites.put("CCMETHOD", "CCSFEN1L");
        
        //Term 10 preRequisites
        preRequisites.put("CCTHESS1", "CCMETHOD");
        preRequisites.put("CCPGLANG", "CCDATRCL");
        preRequisites.put("CCRNFLRL", "CCDEPLRL");
        
        //Term 10 preRequisites
        preRequisites.put("CCTHESS1", "CCMETHOD");
        preRequisites.put("CCPGLANG", "CCDATRCL");
        
        //Term 11 preRequisites
        preRequisites.put("CCTHESS2", "CCTHESS1");
        preRequisites.put("CCDATSCL", "CCRNFLRL");
        
        //Term 12 preRequisites
        preRequisites.put("CCINTERN", "CCTHESS1");
    }

    // Main method to run the GUI
    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(flowchart::new);
    }
}

/*
TO DO LIST

1. A way to enqueue the elements of each array one array at a time.
    - First, enqueue the elements in the 1st term array
    - When a [Enrolled Subjects] button is pressed, it should output the current coursed enqueued
        - [Enrolled Subjects] button also shows a way to add the grades.
        - The grades should be stored in a hashmap.
        - If the grades are either R, Inc, or Drp, do NOT dequeue
        - If grades are <= 1.0 && >= 4.00, dequeue
        - Once all courses have grades in them, dequeue
        - Enqueue the next array of courses

2. A way to store the grades in a hashmap so the grades can be viewed. 
    - Problem is a key can typically only store one value

3. Handle pre requsites
    - prerequisitesMap.put("CourseB", Arrays.asList("CourseA")); 
    where courseB has courseA as the prerequisite.
    - Keep track of the status of the course if passed or failed. I think this can be done
    by using a hashmap that has boolean values. True if passed and false if failed.



*/