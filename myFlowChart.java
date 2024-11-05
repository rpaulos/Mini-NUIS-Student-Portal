/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package finalprojectcircularqueue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Rae
 */
public class myFlowChart extends javax.swing.JFrame {
    private int selectedIndex;
    
    /**
     * Creates new form myFlowChart
     */
    public myFlowChart() {
        initComponents();
        hideTabHeaders();  
    }
  
    private void hideTabHeaders() {
        jTabbedPane2.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                return 0;  // Set tab area height to 0, effectively hiding the tabs
            }
        });
    }
    
    private String[][] selectedGrades = new String[12][6];
    private boolean[][] gradeResults = new boolean[12][6];
    private boolean isInitialized = false;
    private static int a = 0;
    private final LinkedHashMap<String, String> courseGradeMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, String> preReqMap = new LinkedHashMap<>();
    
    private final String[][] courses = {
            {"GEPCM01X", "GEUTS01X", "GERPH01X", "PHYSED11", "CCINCOML", "CCPRGG1L"},   //Term 1
            {"GECTW01X", "GEMMW01X", "GESTS01X", "PHYSED12", "CTHASOPL", "CCPRGG2L"},   //Term 2
            {"GEETH01X", "GEENT01X", "MCWTS01X", "PHYSED13", "CCDISTR1", "CCOBJPGL"},   //Term 3
            {"GEFID01X", "CCMATAN1", "PHYSED14", "MCWTS02X", "CCDISTR2", "CCDATRCL"},   //Term 4
            {"GEACM01X", "CCMATAN2", "MCNAT01R", "CTINFMGL", "CCPHYS1L", "CCOMPORG"},   //Term 5
            {"CCQUAMET", "CTADVDBL", "CCALCOMP", "CTBASNTL", "CCPHYS2L"},   //Term 6
            {"CCSFEN1L", "CCAUTOMA", "CCOPSYSL", "CCMACLRL", "GERIZ01X"},   //Term 7
            {"CCADMACL", "CCSFEN2L", "CTINASSL", "GEITE01X"},   //Term 8
            {"CCINTHCI", "CTAPDEVL", "CCDEPLRL", "CCMETHOD"},   //Term 9
            {"CCTHESS1", "CTPRFISS", "CCPGLANG", "CCRNFLRL"},   //Term 10
            {"CCTHESS2", "CCDATSCL"},   //Term 11
            {"CCINTERN"}    //Term 12
    };
    
    //Flowchart window
    public void finalFlowchart() {
        
        addPreReqValues();
        addGradeToHashMap();

        JFrame flowchartFrame = new JFrame("My Flowchart");
        flowchartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        flowchartFrame.setSize(700, 340);
        flowchartFrame.setLocationRelativeTo(null);

        flowchartFrame.getContentPane().setBackground(new Color(247, 247, 247));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Loop through terms and courses
        for (int term = 0; term < courses.length; term++) {
            gbc.gridx = term; // Set the column for the term
            gbc.gridy = 0;    // Reset row index for each term

            // Create a panel for the term label
            JPanel termPanel = new JPanel();
            termPanel.setBackground(new Color(0, 66, 118));
            termPanel.setPreferredSize(new Dimension(120, 40));
            termPanel.setLayout(new GridBagLayout());
            termPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            // Add a label for each term
            JLabel termLabel = new JLabel("Term " + (term + 1), JLabel.CENTER);
            termLabel.setFont(new Font("Arial", Font.BOLD, 16));
            termLabel.setForeground(Color.WHITE);

            // Center the term label
            GridBagConstraints labelGbc = new GridBagConstraints();
            labelGbc.gridx = 0;
            labelGbc.gridy = 0;
            labelGbc.weightx = 1;
            labelGbc.weighty = 1;
            labelGbc.anchor = GridBagConstraints.CENTER;

            termPanel.add(termLabel, labelGbc); // Add the label to the term panel

            mainPanel.add(termPanel, gbc); // Add the term panel to the main panel

            // Add course codes for the current term
            for (String courseCode : courses[term]) {
                gbc.gridy++; // New row for each course code
                gbc.insets = new Insets(0, 0, 0, 0); // Remove spacing between components
                JPanel courseBox = createCourseBox(courseCode); // Create the course box
                mainPanel.add(courseBox, gbc);
            }
        }
        flowchartFrame.add(new JScrollPane(mainPanel));
        flowchartFrame.setVisible(true);
    }
    
    //Adds design to the flowchart
    private JPanel createCourseBox(String courseCode) {
        JPanel courseBox = new JPanel();

        // Retrieve the grade from the HashMap
        String grade = courseGradeMap.getOrDefault(courseCode, "Grade not available");
        
        if (grade == null || grade.isEmpty()) {
            courseBox.setBackground(new Color(179, 224, 255));
        } else {
            courseBox.setBackground(switchColors(grade));
        }

        // Set the background color based on the grade
        courseBox.setBackground(switchColors(grade));
        courseBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Grey border
        courseBox.setPreferredSize(new Dimension(120, 40)); // Set preferred size of the course box

        JLabel label = new JLabel(courseCode, JLabel.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center text horizontally
        label.setVerticalAlignment(SwingConstants.CENTER);   // Center text vertically

        // Set layout to center the label
        courseBox.setLayout(new GridBagLayout());
        courseBox.add(label); // Add the label to the course box

        // Add a mouse click listener to the course box
        courseBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Show a popup window when the course box is clicked
                String preReq = preReqMap.get(courseCode);
                String preReqMessage = (preReq != null) ? preReq : "No prerequisite";

                // Show a popup window when the course box is clicked
                String message = "Course: " + courseCode + "\nGrade: " + grade + "\nPrerequisite: " + preReqMessage;

                JOptionPane.showMessageDialog(courseBox,
                    message,
                    "Course Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return courseBox;
    }

    //Switches the color of the courses based on the grades attached to the keys
    private Color switchColors (String grade) {
        switch (grade) {
            case "4.0":
            case "3.5":
            case "3.0":
            case "2.5":
            case "2.0":
            case "1.5":
            case "1.0":
                return new Color(255, 255, 255); //White for passed subject
            case "R":
                return new Color(255, 102, 102); //Red for failed subject
            case "Inc":
                return new Color(255, 255, 153); //Yellow for incomplete subjects
            case "Drp":
                return new Color(252, 195, 104); //Orange for droped subjects
            default:
                return new Color(179, 224, 255); //Blue for subject not yet taken
        }
    }
    
    //Adds the grades to the LinkedHashMap
    private void addGradeToHashMap() {
        // Adding keys and values to the LinkedHashMap
        for (int i = 0; i < courses.length; i++) {
            for (int j = 0; j < courses[i].length; j++) {
                // Add to map if the course code is not empty
                if (!courses[i][j].trim().isEmpty()) {
                    courseGradeMap.put(courses[i][j], selectedGrades[i][j]);
                }
            }
        }

        // Printing out the LinkedHashMap to verify
        for (String key : courseGradeMap.keySet()) {
            System.out.println("Course: " + key + ", Grade: " + courseGradeMap.get(key));
        }
    }
    
    private void addPreReqValues() {
        //Term 2 preRequisites
        preReqMap.put("CCPRGG2L", "CCPRGG1L");
        preReqMap.put("CTHASOPL", "CCINCOML");
        
        //Term 3 preRequisites
        preReqMap.put("CCOBJPGL", "CCPRGG2L");
        
        //Term 4 preRequisites
        preReqMap.put("CCDATRCL", "CCPRGG2L");
        preReqMap.put("CCDISTR2", "CCDISTR1");
        preReqMap.put("CCMATAN1", "CCDISTR1");
        
        //Term 5 preRequisites
        preReqMap.put("CCOMPORG", "CCOBJPGL");
        preReqMap.put("CCPHYS1L", "CCMATAN1");
        preReqMap.put("CTINFMGL", "CCDATRCL");
        preReqMap.put("CCMATAN2", "CCMATAN1");
        
        //Term 6 preRequisites
        preReqMap.put("CCQUAMET", "CCDISTR1");
        preReqMap.put("CTADVDBL", "CTINFMGL");
        preReqMap.put("CCALCOMP", "CCDATRCL");
        preReqMap.put("CTBASNTL", "CCOMPORG");
        preReqMap.put("CCPHYS2L", "CCPHYS1L");
        
        //Term 7 preRequisites
        preReqMap.put("CCSFEN1L", "CCDATRCL");
        preReqMap.put("CCAUTOMA", "CCALCOMP");
        preReqMap.put("CCOPSYSL", "CCOBJPGL");
        
        //Term 8 preRequisites
        preReqMap.put("CCADMACL", "CCMACLRL");
        preReqMap.put("CCSFEN2L", "CCSFEN1L");
        preReqMap.put("CTINASSL", "CTINFMGL");
        
        //Term 9 preRequisites
        preReqMap.put("CCINTHCI", "CCPRGG2L");
        preReqMap.put("CTAPDEVL", "CCOBJPGL");
        preReqMap.put("CCDEPLRL", "CCADMACL");
        preReqMap.put("CCMETHOD", "CCSFEN1L");
        
        //Term 10 preRequisites
        preReqMap.put("CCTHESS1", "CCMETHOD");
        preReqMap.put("CCPGLANG", "CCDATRCL");
        preReqMap.put("CCRNFLRL", "CCDEPLRL");
        
        //Term 10 preRequisites
        preReqMap.put("CCTHESS1", "CCMETHOD");
        preReqMap.put("CCPGLANG", "CCDATRCL");
        
        //Term 11 preRequisites
        preReqMap.put("CCTHESS2", "CCTHESS1");
        preReqMap.put("CCDATSCL", "CCRNFLRL");
        
        //Term 12 preRequisites
        preReqMap.put("CCINTERN", "CCTHESS1");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jComboBox17 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox74 = new javax.swing.JComboBox<>();
        jLabel91 = new javax.swing.JLabel();
        jComboBox75 = new javax.swing.JComboBox<>();
        jLabel92 = new javax.swing.JLabel();
        jComboBox76 = new javax.swing.JComboBox<>();
        jComboBox77 = new javax.swing.JComboBox<>();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jComboBox78 = new javax.swing.JComboBox<>();
        jLabel95 = new javax.swing.JLabel();
        jComboBox79 = new javax.swing.JComboBox<>();
        jButton15 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jComboBox80 = new javax.swing.JComboBox<>();
        jLabel98 = new javax.swing.JLabel();
        jComboBox81 = new javax.swing.JComboBox<>();
        jLabel99 = new javax.swing.JLabel();
        jComboBox82 = new javax.swing.JComboBox<>();
        jComboBox83 = new javax.swing.JComboBox<>();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jComboBox84 = new javax.swing.JComboBox<>();
        jLabel102 = new javax.swing.JLabel();
        jComboBox85 = new javax.swing.JComboBox<>();
        jButton16 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jComboBox86 = new javax.swing.JComboBox<>();
        jLabel105 = new javax.swing.JLabel();
        jComboBox87 = new javax.swing.JComboBox<>();
        jLabel106 = new javax.swing.JLabel();
        jComboBox88 = new javax.swing.JComboBox<>();
        jComboBox89 = new javax.swing.JComboBox<>();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jComboBox90 = new javax.swing.JComboBox<>();
        jLabel109 = new javax.swing.JLabel();
        jComboBox91 = new javax.swing.JComboBox<>();
        jButton17 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jComboBox92 = new javax.swing.JComboBox<>();
        jLabel112 = new javax.swing.JLabel();
        jComboBox93 = new javax.swing.JComboBox<>();
        jLabel113 = new javax.swing.JLabel();
        jComboBox94 = new javax.swing.JComboBox<>();
        jComboBox95 = new javax.swing.JComboBox<>();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jComboBox96 = new javax.swing.JComboBox<>();
        jButton18 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jComboBox98 = new javax.swing.JComboBox<>();
        jLabel119 = new javax.swing.JLabel();
        jComboBox99 = new javax.swing.JComboBox<>();
        jLabel120 = new javax.swing.JLabel();
        jComboBox100 = new javax.swing.JComboBox<>();
        jComboBox101 = new javax.swing.JComboBox<>();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jComboBox102 = new javax.swing.JComboBox<>();
        jButton19 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jComboBox104 = new javax.swing.JComboBox<>();
        jLabel126 = new javax.swing.JLabel();
        jComboBox105 = new javax.swing.JComboBox<>();
        jLabel127 = new javax.swing.JLabel();
        jComboBox106 = new javax.swing.JComboBox<>();
        jComboBox107 = new javax.swing.JComboBox<>();
        jLabel128 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jComboBox110 = new javax.swing.JComboBox<>();
        jLabel133 = new javax.swing.JLabel();
        jComboBox111 = new javax.swing.JComboBox<>();
        jLabel134 = new javax.swing.JLabel();
        jComboBox112 = new javax.swing.JComboBox<>();
        jComboBox113 = new javax.swing.JComboBox<>();
        jLabel135 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jComboBox116 = new javax.swing.JComboBox<>();
        jLabel140 = new javax.swing.JLabel();
        jComboBox117 = new javax.swing.JComboBox<>();
        jLabel141 = new javax.swing.JLabel();
        jComboBox118 = new javax.swing.JComboBox<>();
        jComboBox119 = new javax.swing.JComboBox<>();
        jLabel142 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jComboBox122 = new javax.swing.JComboBox<>();
        jLabel147 = new javax.swing.JLabel();
        jComboBox123 = new javax.swing.JComboBox<>();
        jButton23 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jComboBox128 = new javax.swing.JComboBox<>();
        jButton24 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Flow Chart");
        setMinimumSize(new java.awt.Dimension(800, 500));
        setPreferredSize(new java.awt.Dimension(870, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel2.setBackground(new java.awt.Color(20, 47, 101));

        jPanel3.setBackground(new java.awt.Color(255, 204, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 40, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Home > Students > My Flowchart");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Student Flowchart for AY 2024 - 2025 1st Term");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Semester:");

        jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox1.setForeground(new java.awt.Color(51, 51, 51));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Term 1", "Term 2", "Term 3", "Term 4", "Term 5", "Term 6", "Term 7", "Term 8", "Term 9", "Term 10", "Term 11", "Term 12" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(133, 185, 81));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Select Term to add grades");

        jButton2.setBackground(new java.awt.Color(255, 204, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel16.setBackground(new java.awt.Color(20, 47, 101));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TERM 1");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("GEPCM01X:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("GEUTS01X:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("GERPH01X:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("PHYSED11:");

        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("CCINCOML:");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("CCPRGG1L:");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(133, 185, 81));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(25, 25, 25))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        jTabbedPane2.addTab("tab1", jPanel4);

        jPanel20.setBackground(new java.awt.Color(20, 47, 101));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("TERM 2");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("GECTW01X:");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });

        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("GEMMW01X:");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox15ActionPerformed(evt);
            }
        });

        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("GESTS01X:");

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));

        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("PHYSED12:");

        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("CTHASOPL:");

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox18ActionPerformed(evt);
            }
        });

        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("CCPRGG2L:");

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox19ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(133, 185, 81));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Submit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(25, 25, 25))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab2", jPanel5);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel40.setBackground(new java.awt.Color(20, 47, 101));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("TERM 3");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("GEETH01X:");

        jComboBox74.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox74ActionPerformed(evt);
            }
        });

        jLabel91.setForeground(new java.awt.Color(51, 51, 51));
        jLabel91.setText("GEENT01X:");

        jComboBox75.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox75ActionPerformed(evt);
            }
        });

        jLabel92.setForeground(new java.awt.Color(51, 51, 51));
        jLabel92.setText("MCWTS01X:");

        jComboBox76.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox76ActionPerformed(evt);
            }
        });

        jComboBox77.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));

        jLabel93.setForeground(new java.awt.Color(51, 51, 51));
        jLabel93.setText("PHYSED13:");

        jLabel94.setForeground(new java.awt.Color(51, 51, 51));
        jLabel94.setText("CCDISTR1:");

        jComboBox78.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox78ActionPerformed(evt);
            }
        });

        jLabel95.setForeground(new java.awt.Color(51, 51, 51));
        jLabel95.setText("CCOBJPGL:");

        jComboBox79.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox79ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(133, 185, 81));
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Submit");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox75, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox76, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox77, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox74, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel94, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox78, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox79, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton15)
                .addGap(30, 30, 30))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel94)
                    .addComponent(jComboBox78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(11, 11, 11)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95)
                    .addComponent(jLabel91))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jButton15))
        );

        jPanel6.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 240));

        jTabbedPane2.addTab("tab3", jPanel6);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel42.setBackground(new java.awt.Color(20, 47, 101));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("TERM 4");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel97.setForeground(new java.awt.Color(51, 51, 51));
        jLabel97.setText("GEFID01X:");

        jComboBox80.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox80ActionPerformed(evt);
            }
        });

        jLabel98.setForeground(new java.awt.Color(51, 51, 51));
        jLabel98.setText("CCMATAN1:");

        jComboBox81.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox81ActionPerformed(evt);
            }
        });

        jLabel99.setForeground(new java.awt.Color(51, 51, 51));
        jLabel99.setText("PHYSED14:");

        jComboBox82.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox82ActionPerformed(evt);
            }
        });

        jComboBox83.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox83ActionPerformed(evt);
            }
        });

        jLabel100.setForeground(new java.awt.Color(51, 51, 51));
        jLabel100.setText("MCWTS02X:");

        jLabel101.setForeground(new java.awt.Color(51, 51, 51));
        jLabel101.setText("CCDISTR2:");

        jComboBox84.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox84ActionPerformed(evt);
            }
        });

        jLabel102.setForeground(new java.awt.Color(51, 51, 51));
        jLabel102.setText("CCDATRCL:");

        jComboBox85.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox85ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(133, 185, 81));
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Submit");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox81, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox82, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox83, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox80, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel101, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox84, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox85, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton16)
                .addGap(22, 22, 22))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel101)
                    .addComponent(jComboBox84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97))
                .addGap(11, 11, 11)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102)
                    .addComponent(jLabel98))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel100))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jButton16))
        );

        jPanel7.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 240));

        jTabbedPane2.addTab("tab4", jPanel7);

        jPanel44.setBackground(new java.awt.Color(20, 47, 101));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("TERM 5");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel104.setForeground(new java.awt.Color(51, 51, 51));
        jLabel104.setText("GEACM01X:");

        jComboBox86.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));

        jLabel105.setForeground(new java.awt.Color(51, 51, 51));
        jLabel105.setText("CCMATAN2:");

        jComboBox87.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));

        jLabel106.setForeground(new java.awt.Color(51, 51, 51));
        jLabel106.setText("MCNAT01R:");

        jComboBox88.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));

        jComboBox89.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox89.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox89ActionPerformed(evt);
            }
        });

        jLabel107.setForeground(new java.awt.Color(51, 51, 51));
        jLabel107.setText("CTINFMGL:");

        jLabel108.setForeground(new java.awt.Color(51, 51, 51));
        jLabel108.setText("CCPHYS1L:");

        jComboBox90.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox90ActionPerformed(evt);
            }
        });

        jLabel109.setForeground(new java.awt.Color(51, 51, 51));
        jLabel109.setText("CCOMPORG:");

        jComboBox91.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));

        jButton17.setBackground(new java.awt.Color(133, 185, 81));
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Submit");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox87, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox88, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox89, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox86, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel109, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox90, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox91, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton17)
                .addGap(25, 25, 25))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108)
                    .addComponent(jComboBox90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel104))
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton17)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel109)
                            .addComponent(jLabel105))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel106))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel107))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab5", jPanel8);

        jPanel46.setBackground(new java.awt.Color(20, 47, 101));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("TERM 6");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel111.setForeground(new java.awt.Color(51, 51, 51));
        jLabel111.setText("CCQUAMET:");

        jComboBox92.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox92.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox92ActionPerformed(evt);
            }
        });

        jLabel112.setForeground(new java.awt.Color(51, 51, 51));
        jLabel112.setText("CTADVDBL:");

        jComboBox93.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));

        jLabel113.setForeground(new java.awt.Color(51, 51, 51));
        jLabel113.setText("CCALCOMP:");

        jComboBox94.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox94.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox94ActionPerformed(evt);
            }
        });

        jComboBox95.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox95ActionPerformed(evt);
            }
        });

        jLabel114.setForeground(new java.awt.Color(51, 51, 51));
        jLabel114.setText("CTBASNTL:");

        jLabel115.setForeground(new java.awt.Color(51, 51, 51));
        jLabel115.setText("CCPHYS2L:");

        jComboBox96.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox96.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox96ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(133, 185, 81));
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Submit");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox93, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox94, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox95, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox92, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox96, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton18)
                .addGap(25, 25, 25))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel115)
                    .addComponent(jComboBox96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111))
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton18)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel112))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel113))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel114))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab6", jPanel9);

        jPanel48.setBackground(new java.awt.Color(20, 47, 101));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("TERM 7");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel118.setForeground(new java.awt.Color(51, 51, 51));
        jLabel118.setText("CCSFEN1L:");

        jComboBox98.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox98ActionPerformed(evt);
            }
        });

        jLabel119.setForeground(new java.awt.Color(51, 51, 51));
        jLabel119.setText("CCAUTOMA:");

        jComboBox99.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox99ActionPerformed(evt);
            }
        });

        jLabel120.setForeground(new java.awt.Color(51, 51, 51));
        jLabel120.setText("CCOPSYSL:");

        jComboBox100.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox100ActionPerformed(evt);
            }
        });

        jComboBox101.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox101ActionPerformed(evt);
            }
        });

        jLabel121.setForeground(new java.awt.Color(51, 51, 51));
        jLabel121.setText("CCMACLRL:");

        jLabel122.setForeground(new java.awt.Color(51, 51, 51));
        jLabel122.setText("GERIZ01X:");

        jComboBox102.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox102.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox102ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(133, 185, 81));
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Submit");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox99, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox100, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox101, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox98, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox102, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton19)
                .addGap(25, 25, 25))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox98, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel122)
                    .addComponent(jComboBox102, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel118))
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton19)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox99, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel119))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel120))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox101, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel121))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab7", jPanel10);

        jPanel50.setBackground(new java.awt.Color(20, 47, 101));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("TERM 8");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel20)
                .addContainerGap(283, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel125.setForeground(new java.awt.Color(51, 51, 51));
        jLabel125.setText("CCADMACL:");

        jComboBox104.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox104ActionPerformed(evt);
            }
        });

        jLabel126.setForeground(new java.awt.Color(51, 51, 51));
        jLabel126.setText("CCSFEN2L:");

        jComboBox105.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox105ActionPerformed(evt);
            }
        });

        jLabel127.setForeground(new java.awt.Color(51, 51, 51));
        jLabel127.setText("CTINASSL:");

        jComboBox106.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox106.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox106ActionPerformed(evt);
            }
        });

        jComboBox107.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox107.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox107ActionPerformed(evt);
            }
        });

        jLabel128.setForeground(new java.awt.Color(51, 51, 51));
        jLabel128.setText("GEITE01X:");

        jButton20.setBackground(new java.awt.Color(133, 185, 81));
        jButton20.setForeground(new java.awt.Color(255, 255, 255));
        jButton20.setText("Submit");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox105, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox106, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox107, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox104, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel49Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton20)
                .addGap(25, 25, 25))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox104, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel125))
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton20)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox105, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel126))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox106, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel127))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox107, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel128))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab8", jPanel11);

        jPanel52.setBackground(new java.awt.Color(20, 47, 101));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("TERM 9");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel27)
                .addContainerGap(283, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel132.setForeground(new java.awt.Color(51, 51, 51));
        jLabel132.setText("CCINTHCI:");

        jComboBox110.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox110ActionPerformed(evt);
            }
        });

        jLabel133.setForeground(new java.awt.Color(51, 51, 51));
        jLabel133.setText("CTAPDEVL:");

        jComboBox111.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox111ActionPerformed(evt);
            }
        });

        jLabel134.setForeground(new java.awt.Color(51, 51, 51));
        jLabel134.setText("CCDEPLRL:");

        jComboBox112.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox112ActionPerformed(evt);
            }
        });

        jComboBox113.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox113.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox113ActionPerformed(evt);
            }
        });

        jLabel135.setForeground(new java.awt.Color(51, 51, 51));
        jLabel135.setText("CCMETHOD:");

        jButton21.setBackground(new java.awt.Color(133, 185, 81));
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("Submit");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addComponent(jLabel133, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox111, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox112, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox113, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox110, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton21)
                .addGap(25, 25, 25))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox110, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel132))
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton21)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel133))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox112, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel134))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel135))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab9", jPanel12);

        jPanel54.setBackground(new java.awt.Color(20, 47, 101));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("TERM 10");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel28)
                .addContainerGap(273, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel139.setForeground(new java.awt.Color(51, 51, 51));
        jLabel139.setText("CCTHESS1:");

        jComboBox116.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox116.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox116ActionPerformed(evt);
            }
        });

        jLabel140.setForeground(new java.awt.Color(51, 51, 51));
        jLabel140.setText("CTPRFISS:");

        jComboBox117.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox117.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox117ActionPerformed(evt);
            }
        });

        jLabel141.setForeground(new java.awt.Color(51, 51, 51));
        jLabel141.setText("CCPGLANG:");

        jComboBox118.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox118.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox118ActionPerformed(evt);
            }
        });

        jComboBox119.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox119.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox119ActionPerformed(evt);
            }
        });

        jLabel142.setForeground(new java.awt.Color(51, 51, 51));
        jLabel142.setText("CCRNFLRL:");

        jButton22.setBackground(new java.awt.Color(133, 185, 81));
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setText("Submit");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox117, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel141, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox118, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox119, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox116, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton22)
                .addGap(25, 25, 25))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel139))
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton22)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox117, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel140))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox118, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel141))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox119, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel142))
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab10", jPanel13);

        jPanel56.setBackground(new java.awt.Color(20, 47, 101));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("TERM 11");

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel29)
                .addContainerGap(273, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel146.setForeground(new java.awt.Color(51, 51, 51));
        jLabel146.setText("CCTHESS2:");

        jComboBox122.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox122.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox122ActionPerformed(evt);
            }
        });

        jLabel147.setForeground(new java.awt.Color(51, 51, 51));
        jLabel147.setText("CCDATSCL:");

        jComboBox123.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox123.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox123ActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(133, 185, 81));
        jButton23.setForeground(new java.awt.Color(255, 255, 255));
        jButton23.setText("Submit");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox123, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox122, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton23)
                .addGap(25, 25, 25))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel146))
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addComponent(jButton23)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox123, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel147))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab11", jPanel14);

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel58.setBackground(new java.awt.Color(20, 47, 101));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("TERM 12");

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel30)
                .addContainerGap(271, Short.MAX_VALUE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel153.setForeground(new java.awt.Color(51, 51, 51));
        jLabel153.setText("CCINTERN:");

        jComboBox128.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0", "R", "Inc", "Drp" }));
        jComboBox128.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox128ActionPerformed(evt);
            }
        });

        jButton24.setBackground(new java.awt.Color(133, 185, 81));
        jButton24.setForeground(new java.awt.Color(255, 255, 255));
        jButton24.setText("Submit");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox128, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel57Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton24)
                .addGap(25, 25, 25))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox128, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel153))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jButton24)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab12", jPanel15);

        jButton4.setBackground(new java.awt.Color(185, 176, 176));
        jButton4.setForeground(new java.awt.Color(51, 51, 51));
        jButton4.setText("Print");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Inputs dummy grade for each course
    private void initializeArray() {
        for (int i = 0; i < selectedGrades.length; i++) {
            for (int j = 0; j < selectedGrades[i].length; j++) {
                selectedGrades[i][j] = "0";  // Assign "0" to each element
            }
        }
    }
    
    //Stores the grades in the 2d array of grades. Used for the LinkedHashMap courseGradeMap
    private void storeSelectedGrades(int termIndex, JComboBox<String>[] comboBoxes) {
        for (int i = 0; i < comboBoxes.length; i++) {
            selectedGrades[termIndex][i] = (String) comboBoxes[i].getSelectedItem();
        }
        checkGradeValidity(termIndex);
        
        System.out.println("Term " + (termIndex + 1) + (" Grades: " + Arrays.toString(selectedGrades[termIndex]) + "storesSelectedGrades\n"));
    }
    
    //Linked with isValidGrade method. If grade is 4.0 to 1.0 it returns true. If something else, return false. It assigns the boolean values into a 2d array.
    private void checkGradeValidity(int termIndex) {
        for (int i = 0; i < selectedGrades[termIndex].length; i++) {
            String grade = selectedGrades[termIndex][i];
            gradeResults[termIndex][i] = isValidGrade(grade);
        }
    }
    
    //A method that checks if you can access the next term. Checks if the previous term grades is empty.
    private boolean isEmpty(int termIndex) {
        boolean[] defaultArray = new boolean[6]; 
        return Arrays.equals(gradeResults[termIndex], defaultArray);
    }
    
    //Used by the checkGradeValidity. Returns true for passed grades and false for not passed
    private boolean isValidGrade(String grade) {
        switch(grade) {
            case "4.0":
            case "3.5":
            case "3.0":
            case "2.5":
            case "2.0":
            case "1.5":
            case "1.0":
                return true;
            case "R":
            case "Inc":
            case "Drp":
                return false;
            default:
                return false;
        }
    }
    
    //Back button
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        students students = new students();
        students.setVisible(true);
        students.pack();
        students.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    //Select term submit button. Handles if you can access the term or not
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedIndex = jComboBox1.getSelectedIndex();
        
        
        if (selectedIndex == 0) {
            jTabbedPane2.setSelectedIndex(0);    
        } else if (selectedIndex == 1) {
            if (isEmpty(0)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 2", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(1);
            }
        } else if (selectedIndex == 2) {
            if (isEmpty(1)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 3", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(2);
            }
        } else if (selectedIndex == 3) {
            if (isEmpty(2)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 4", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(3);
            }
        } else if (selectedIndex == 4) {
            if (isEmpty(3)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 5", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(4);
            }
        } else if (selectedIndex == 5) {
            if (isEmpty(4)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 6", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(5);
            }
        } else if (selectedIndex == 6) {
            if (isEmpty(5)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 7", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(6);
            }
        } else if (selectedIndex == 7) {
            if (isEmpty(6)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 8", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(7);
            }
        } else if (selectedIndex == 8) {
            if (isEmpty(7)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 9", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(8);
            }
        } else if (selectedIndex == 9) {
            if (isEmpty(8)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 10", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(9);
            }
        } else if (selectedIndex == 10) {
            if (isEmpty(9)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 11", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(10);
            }
        } else if (selectedIndex == 11) {
            if (isEmpty(10)) {
                JOptionPane.showMessageDialog(null, "Can't Access Term 12", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                jTabbedPane2.setSelectedIndex(11);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    //Term 1
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        // Term 1 submit button

        // Create an array of combo boxes for Term 1
        JComboBox<String>[] comboBoxesTerm1 = new JComboBox[] {
            jComboBox2, jComboBox3, jComboBox4, jComboBox5, jComboBox6, jComboBox7
        };
        storeSelectedGrades(0, comboBoxesTerm1);
    }//GEN-LAST:event_jButton3ActionPerformed
    
    //Term 2
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        // Term 2 submit button
        
        System.out.println("Term 2 submit button clicked.");

        // Create an array of combo boxes for Term 2
        JComboBox<String>[] comboBoxesTerm2 = new JComboBox[] {
            jComboBox14, jComboBox15, jComboBox16, jComboBox17, jComboBox18, jComboBox19
        };
        
        String jComboBox6Grade = (String) jComboBox6.getSelectedItem();
        if (jComboBox6Grade != null && 
            (jComboBox6Grade.equals("R") || jComboBox6Grade.equals("Inc") || jComboBox6Grade.equals("Drp") || jComboBox6Grade.equals("0"))) {

            jComboBox18.setSelectedItem("0");

            courseGradeMap.put("CCINCOML", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCINCOML is unmet. Pass CTHASOPL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }

        String jComboBox7Grade = (String) jComboBox7.getSelectedItem();
        if (jComboBox7Grade != null && 
            (jComboBox7Grade.equals("R") || jComboBox7Grade.equals("Inc") || jComboBox7Grade.equals("Drp") || jComboBox7Grade.equals("0"))) {

            jComboBox19.setSelectedItem("0");

            courseGradeMap.put("CCPRGG2L", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCPRGG2L is unmet. Pass CCPRGG1L first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        storeSelectedGrades(1, comboBoxesTerm2); // Term 2 corresponds to index 1
    }//GEN-LAST:event_jButton5ActionPerformed

    //Term 4
    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        // Term 4 submit button

        // Create an array of combo boxes for Term 4
        JComboBox<String>[] comboBoxesTerm4 = new JComboBox[] {
            jComboBox80, jComboBox81, jComboBox82, jComboBox83, jComboBox84, jComboBox85
        };
        
        String jComboBox78Grade = (String) jComboBox78.getSelectedItem();
        if (jComboBox78Grade != null && 
            (jComboBox78Grade.equals("R") || jComboBox78Grade.equals("Inc") || jComboBox78Grade.equals("Drp") || jComboBox78Grade.equals("0"))) {

            jComboBox81.setSelectedItem("0");

            courseGradeMap.put("CCMATAN1", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCMATAN1 is unmet. Pass CCDISTR1 first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }

        if (jComboBox78Grade != null && 
            (jComboBox78Grade.equals("R") || jComboBox78Grade.equals("Inc") || jComboBox78Grade.equals("Drp") || jComboBox78Grade.equals("0"))) {

            jComboBox84.setSelectedItem("0");

            courseGradeMap.put("CCDISTR2", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCDISTR2 is unmet. Pass CCDISTR1 first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox19Grade = (String) jComboBox19.getSelectedItem();
        if (jComboBox19Grade != null && 
            (jComboBox19Grade.equals("R") || jComboBox19Grade.equals("Inc") || jComboBox19Grade.equals("Drp") || jComboBox19Grade.equals("0"))) {

            jComboBox85.setSelectedItem("0");

            courseGradeMap.put("CCDATRCL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCDATRCL is unmet. Pass CCPRGG2L first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        storeSelectedGrades(3, comboBoxesTerm4);
    }//GEN-LAST:event_jButton16ActionPerformed

    //Term 5
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        // Term 5 submit button

        // Create an array of combo boxes for Term 5
        JComboBox<String>[] comboBoxesTerm5 = new JComboBox[] {
            jComboBox86, jComboBox87, jComboBox88, jComboBox89, jComboBox90, jComboBox91
        };
        
        String jComboBox81Grade = (String) jComboBox81.getSelectedItem();
        if (jComboBox81Grade != null && 
            (jComboBox81Grade.equals("R") || jComboBox81Grade.equals("Inc") || jComboBox81Grade.equals("Drp") || jComboBox81Grade.equals("0"))) {

            jComboBox87.setSelectedItem("0");

            courseGradeMap.put("CCMATAN2", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCMATAN2 is unmet. Pass CCMATAN1 first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox85Grade = (String) jComboBox85.getSelectedItem();
        if (jComboBox85Grade != null && 
            (jComboBox85Grade.equals("R") || jComboBox85Grade.equals("Inc") || jComboBox85Grade.equals("Drp") || jComboBox85Grade.equals("0"))) {

            jComboBox89.setSelectedItem("0");

            courseGradeMap.put("CTINFMGL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CTINFMGL is unmet. Pass CCDATRCL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        if (jComboBox81Grade != null && 
            (jComboBox81Grade.equals("R") || jComboBox81Grade.equals("Inc") || jComboBox81Grade.equals("Drp") || jComboBox81Grade.equals("0"))) {

            jComboBox90.setSelectedItem("0");

            courseGradeMap.put("CCPHYS1L", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCPHYS1L is unmet. Pass CCMATAN1 first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox79Grade = (String) jComboBox79.getSelectedItem();
        if (jComboBox79Grade != null && 
            (jComboBox79Grade.equals("R") || jComboBox79Grade.equals("Inc") || jComboBox79Grade.equals("Drp") || jComboBox79Grade.equals("0"))) {

            jComboBox91.setSelectedItem("0");

            courseGradeMap.put("CCOMPORG", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCOMPORG is unmet. Pass CCOBJPGL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }

        storeSelectedGrades(4, comboBoxesTerm5); // Term 5 corresponds to index 4

    }//GEN-LAST:event_jButton17ActionPerformed

    //Term 6
    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        // Term 6 submit button

        // Create an array of combo boxes for Term 6
        JComboBox<String>[] comboBoxesTerm6 = new JComboBox[] {
            jComboBox92, jComboBox93, jComboBox94, jComboBox95, jComboBox96
        };
        
        String jComboBox78Grade = (String) jComboBox78.getSelectedItem();
        if (jComboBox78Grade != null && 
            (jComboBox78Grade.equals("R") || jComboBox78Grade.equals("Inc") || jComboBox78Grade.equals("Drp") || jComboBox78Grade.equals("0"))) {

            jComboBox92.setSelectedItem("0");

            courseGradeMap.put("CCQUAMET", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCQUAMET is unmet. Pass CCDISTR1 first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox89Grade = (String) jComboBox89.getSelectedItem();
        if (jComboBox89Grade != null && 
            (jComboBox89Grade.equals("R") || jComboBox89Grade.equals("Inc") || jComboBox89Grade.equals("Drp") || jComboBox89Grade.equals("0"))) {

            jComboBox93.setSelectedItem("0");

            courseGradeMap.put("CTADVDBL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CTADVDBL is unmet. Pass CTINFMGL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox85Grade = (String) jComboBox85.getSelectedItem();
        if (jComboBox85Grade != null && 
            (jComboBox85Grade.equals("R") || jComboBox85Grade.equals("Inc") || jComboBox85Grade.equals("Drp") || jComboBox85Grade.equals("0"))) {

            jComboBox94.setSelectedItem("0");

            courseGradeMap.put("CCALCOMP", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCALCOMP is unmet. Pass CCDATRCL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox91Grade = (String) jComboBox91.getSelectedItem();
        if (jComboBox91Grade != null && 
            (jComboBox91Grade.equals("R") || jComboBox91Grade.equals("Inc") || jComboBox91Grade.equals("Drp") || jComboBox91Grade.equals("0"))) {

            jComboBox95.setSelectedItem("0");

            courseGradeMap.put("CTBASNTL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CTBASNTL is unmet. Pass CCOMPORG first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox90Grade = (String) jComboBox90.getSelectedItem();
        if (jComboBox90Grade != null && 
            (jComboBox90Grade.equals("R") || jComboBox90Grade.equals("Inc") || jComboBox90Grade.equals("Drp") || jComboBox90Grade.equals("0"))) {

            jComboBox96.setSelectedItem("0");

            courseGradeMap.put("CCPHYS2L", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCPHYS2L is unmet. Pass CCPHYS1L first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }

        selectedGrades[5][5] = (String) "  ";

        storeSelectedGrades(5, comboBoxesTerm6);
    }//GEN-LAST:event_jButton18ActionPerformed

    //Term 7
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        // Term 7 submit button

        // Create an array of combo boxes for Term 7
        JComboBox<String>[] comboBoxesTerm7 = new JComboBox[] {
            jComboBox98, jComboBox99, jComboBox100, jComboBox101, jComboBox102
        };
        
        String jComboBox85Grade = (String) jComboBox85.getSelectedItem();
        if (jComboBox85Grade != null && 
            (jComboBox85Grade.equals("R") || jComboBox85Grade.equals("Inc") || jComboBox85Grade.equals("Drp") || jComboBox85Grade.equals("0"))) {

            jComboBox98.setSelectedItem("0");

            courseGradeMap.put("CCSFEN1L", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCSFEN1L is unmet. Pass CCDATRCL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox94Grade = (String) jComboBox94.getSelectedItem();
        if (jComboBox94Grade != null && 
            (jComboBox94Grade.equals("R") || jComboBox94Grade.equals("Inc") || jComboBox94Grade.equals("Drp") || jComboBox94Grade.equals("0"))) {

            jComboBox99.setSelectedItem("0");

            courseGradeMap.put("CCAUTOMA", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCAUTOMA is unmet. Pass CCALCOMP first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox79Grade = (String) jComboBox79.getSelectedItem();
        if (jComboBox79Grade != null && 
            (jComboBox79Grade.equals("R") || jComboBox79Grade.equals("Inc") || jComboBox79Grade.equals("Drp") || jComboBox79Grade.equals("0"))) {

            jComboBox100.setSelectedItem("0");

            courseGradeMap.put("CCOPSYSL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCOPSYSL is unmet. Pass CCOBJPGL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        selectedGrades[6][4] = (String) "  ";
        selectedGrades[6][5] = (String) "  ";

        storeSelectedGrades(6, comboBoxesTerm7);
    }//GEN-LAST:event_jButton19ActionPerformed

    //Term 8
    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        // Term 8 submit button

        // Create an array of combo boxes for Term 8
        JComboBox<String>[] comboBoxesTerm8 = new JComboBox[] {
            jComboBox104, jComboBox105, jComboBox106, jComboBox107
        };
        
        String jComboBox101Grade = (String) jComboBox101.getSelectedItem();
        if (jComboBox101Grade != null && 
            (jComboBox101Grade.equals("R") || jComboBox101Grade.equals("Inc") || jComboBox101Grade.equals("Drp") || jComboBox101Grade.equals("0"))) {

            jComboBox104.setSelectedItem("0");

            courseGradeMap.put("CCADMACL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCADMACL is unmet. Pass CCMACLRL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox98Grade = (String) jComboBox98.getSelectedItem();
        if (jComboBox98Grade != null && 
            (jComboBox98Grade.equals("R") || jComboBox98Grade.equals("Inc") || jComboBox98Grade.equals("Drp") || jComboBox98Grade.equals("0"))) {

            jComboBox105.setSelectedItem("0");

            courseGradeMap.put("CCSFEN2L", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCSFEN2L is unmet. Pass CCSFEN1L first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox89Grade = (String) jComboBox89.getSelectedItem();
        if (jComboBox89Grade != null && 
            (jComboBox89Grade.equals("R") || jComboBox89Grade.equals("Inc") || jComboBox89Grade.equals("Drp") || jComboBox89Grade.equals("0"))) {

            jComboBox106.setSelectedItem("0");

            courseGradeMap.put("CTINASSL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CTINASSL is unmet. Pass CTINFMGL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        selectedGrades[7][4] = (String) "  ";
        selectedGrades[7][5] = (String) "  ";

        storeSelectedGrades(7, comboBoxesTerm8);
    }//GEN-LAST:event_jButton20ActionPerformed

    //Term 9
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        // Term 9 submit button

        // Create an array of combo boxes for Term 9
        JComboBox<String>[] comboBoxesTerm9 = new JComboBox[] {
            jComboBox110, jComboBox111, jComboBox112, jComboBox113
        };
        
        String jComboBox19Grade = (String) jComboBox19.getSelectedItem();
        if (jComboBox19Grade != null && 
            (jComboBox19Grade.equals("R") || jComboBox19Grade.equals("Inc") || jComboBox19Grade.equals("Drp") || jComboBox19Grade.equals("0"))) {

            jComboBox110.setSelectedItem("0");

            courseGradeMap.put("CCINTHCI", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCINTHCI is unmet. Pass CCPRGG2L first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox79Grade = (String) jComboBox79.getSelectedItem();
        if (jComboBox79Grade != null && 
            (jComboBox79Grade.equals("R") || jComboBox79Grade.equals("Inc") || jComboBox79Grade.equals("Drp") || jComboBox79Grade.equals("0"))) {

            jComboBox111.setSelectedItem("0");

            courseGradeMap.put("CTAPDEVL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CTAPDEVL is unmet. Pass CCOBJPGL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox104Grade = (String) jComboBox104.getSelectedItem();
        if (jComboBox104Grade != null && 
            (jComboBox104Grade.equals("R") || jComboBox104Grade.equals("Inc") || jComboBox104Grade.equals("Drp") || jComboBox104Grade.equals("0"))) {

            jComboBox112.setSelectedItem("0");

            courseGradeMap.put("CCDEPLRL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCDEPLRL is unmet. Pass CCADMACL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox98Grade = (String) jComboBox98.getSelectedItem();
        if (jComboBox98Grade != null && 
            (jComboBox98Grade.equals("R") || jComboBox98Grade.equals("Inc") || jComboBox98Grade.equals("Drp") || jComboBox98Grade.equals("0"))) {

            jComboBox113.setSelectedItem("0");

            courseGradeMap.put("CCMETHOD", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCMETHOD is unmet. Pass CCSFEN1L first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        selectedGrades[8][4] = (String) "  ";
        selectedGrades[8][5] = (String) "  ";

        storeSelectedGrades(8, comboBoxesTerm9);
    }//GEN-LAST:event_jButton21ActionPerformed

    //Term 10
    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        // Term 10 submit button

        // Create an array of combo boxes for Term 10
        JComboBox<String>[] comboBoxesTerm10 = new JComboBox[] {
            jComboBox116, jComboBox117, jComboBox118, jComboBox119
        };
        
        String jComboBox113Grade = (String) jComboBox113.getSelectedItem();
        if (jComboBox113Grade != null && 
            (jComboBox113Grade.equals("R") || jComboBox113Grade.equals("Inc") || jComboBox113Grade.equals("Drp") || jComboBox113Grade.equals("0"))) {

            jComboBox116.setSelectedItem("0");

            courseGradeMap.put("CCTHESS1", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCTHESS1 is unmet. Pass CCMETHOD first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox85Grade = (String) jComboBox85.getSelectedItem();
        if (jComboBox85Grade != null && 
            (jComboBox85Grade.equals("R") || jComboBox85Grade.equals("Inc") || jComboBox85Grade.equals("Drp") || jComboBox85Grade.equals("0"))) {

            jComboBox118.setSelectedItem("0");

            courseGradeMap.put("CCPGLANG", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCPGLANG is unmet. Pass CCDATRCL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox112Grade = (String) jComboBox112.getSelectedItem();
        if (jComboBox112Grade != null && 
            (jComboBox112Grade.equals("R") || jComboBox112Grade.equals("Inc") || jComboBox112Grade.equals("Drp") || jComboBox112Grade.equals("0"))) {

            jComboBox119.setSelectedItem("0");

            courseGradeMap.put("CCRNFLRL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCRNFLRL is unmet. Pass CCDEPLRL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        selectedGrades[9][4] = (String) "  ";
        selectedGrades[9][5] = (String) "  ";

        storeSelectedGrades(9, comboBoxesTerm10);
    }//GEN-LAST:event_jButton22ActionPerformed

    //Term 11
    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        // Term 11 submit button

        // Create an array of combo boxes for Term 11
        JComboBox<String>[] comboBoxesTerm11 = new JComboBox[] {
            jComboBox122, jComboBox123 // Adjust based on your actual combo boxes for Term 11
        };
        
        String jComboBox116Grade = (String) jComboBox116.getSelectedItem();
        if (jComboBox116Grade != null && 
            (jComboBox116Grade.equals("R") || jComboBox116Grade.equals("Inc") || jComboBox116Grade.equals("Drp") || jComboBox116Grade.equals("0"))) {

            jComboBox122.setSelectedItem("0");

            courseGradeMap.put("CCTHESS2", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCTHESS2 is unmet. Pass CCTHESS1 first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        String jComboBox119Grade = (String) jComboBox119.getSelectedItem();
        if (jComboBox119Grade != null && 
            (jComboBox119Grade.equals("R") || jComboBox119Grade.equals("Inc") || jComboBox119Grade.equals("Drp") || jComboBox119Grade.equals("0"))) {

            jComboBox123.setSelectedItem("0");

            courseGradeMap.put("CCTHESS2", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCDATSCL is unmet. Pass CCRNFLRL first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        selectedGrades[10][2] = (String) "  ";
        selectedGrades[10][3] = (String) "  ";
        selectedGrades[10][4] = (String) "  ";
        selectedGrades[10][5] = (String) "  ";

        storeSelectedGrades(10, comboBoxesTerm11);
    }//GEN-LAST:event_jButton23ActionPerformed

    //Term 12
    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        // Term 12 submit button

        // Create an array of combo boxes for Term 12
        JComboBox<String>[] comboBoxesTerm12 = new JComboBox[] {
            jComboBox128 // Adjust based on your actual combo box for Term 12
        };
        
        String jComboBox116Grade = (String) jComboBox116.getSelectedItem();
        if (jComboBox116Grade != null && 
            (jComboBox116Grade.equals("R") || jComboBox116Grade.equals("Inc") || jComboBox116Grade.equals("Drp") || jComboBox116Grade.equals("0"))) {

            jComboBox128.setSelectedItem("0");

            courseGradeMap.put("CCINTERN", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCINTERN is unmet. Pass CCTHESS1 first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        selectedGrades[11][1] = (String) "  ";
        selectedGrades[11][2] = (String) "  ";
        selectedGrades[11][3] = (String) "  ";
        selectedGrades[11][4] = (String) "  ";
        selectedGrades[11][5] = (String) "  ";

        storeSelectedGrades(11, comboBoxesTerm12);
    }//GEN-LAST:event_jButton24ActionPerformed

    //Term 3
    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        // Term 3 submit button

        // Create an array of combo boxes for Term 3
        JComboBox<String>[] comboBoxesTerm3 = new JComboBox[] {
            jComboBox74, jComboBox75, jComboBox76, jComboBox77, jComboBox78, jComboBox79
        };
        
        String jComboBox19Grade = (String) jComboBox19.getSelectedItem();
        if (jComboBox19Grade != null && 
            (jComboBox19Grade.equals("R") || jComboBox19Grade.equals("Inc") || jComboBox19Grade.equals("Drp") || jComboBox19Grade.equals("0"))) {

            jComboBox79.setSelectedItem("0");

            courseGradeMap.put("CCOBJPGL", "0");

            JOptionPane.showMessageDialog(
                null, // Center on the screen
                "Prerequisite for CCOBJPGL is unmet. Pass CCPRGG2L first.",
                "Prerequisite Not Met",
                JOptionPane.ERROR_MESSAGE
            );
        }

        storeSelectedGrades(2, comboBoxesTerm3);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void jComboBox76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox76ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox76ActionPerformed

    private void jComboBox74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox74ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox74ActionPerformed

    private void jComboBox80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox80ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox80ActionPerformed

    private void jComboBox81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox81ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox81ActionPerformed

    private void jComboBox82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox82ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox82ActionPerformed

    private void jComboBox83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox83ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox83ActionPerformed

    private void jComboBox89ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox89ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox89ActionPerformed

    private void jComboBox95ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox95ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox95ActionPerformed

    private void jComboBox96ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox96ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox96ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox15ActionPerformed

    private void jComboBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox16ActionPerformed

    private void jComboBox75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox75ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox75ActionPerformed

    private void jComboBox79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox79ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox79ActionPerformed

    private void jComboBox85ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox85ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox85ActionPerformed

    private void jComboBox92ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox92ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox92ActionPerformed

    private void jComboBox98ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox98ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox98ActionPerformed

    private void jComboBox102ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox102ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox102ActionPerformed

    private void jComboBox99ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox99ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox99ActionPerformed

    private void jComboBox104ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox104ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox104ActionPerformed

    private void jComboBox107ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox107ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox107ActionPerformed

    private void jComboBox110ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox110ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox110ActionPerformed

    private void jComboBox113ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox113ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox113ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //Print button
        initializeArray();
        addGradeToHashMap();
        finalFlowchart();
        
        boolean allGradesPassed = true;

        for (String grade : courseGradeMap.values()) {
            if (grade.equals("R") || grade.equals("Inc") || grade.equals("Drp") || grade.equals("0")) {
                allGradesPassed = false;
                break;
            }
        }

        if (allGradesPassed) {
            JOptionPane.showMessageDialog(null, "Congratulations! You have passed all of your courses. Time for graduation!.", "Graduation Status", JOptionPane.INFORMATION_MESSAGE);
        } 
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox19ActionPerformed

    private void jComboBox18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox18ActionPerformed

    private void jComboBox78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox78ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox78ActionPerformed

    private void jComboBox84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox84ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox84ActionPerformed

    private void jComboBox90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox90ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox90ActionPerformed

    private void jComboBox94ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox94ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox94ActionPerformed

    private void jComboBox100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox100ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox100ActionPerformed

    private void jComboBox101ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox101ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox101ActionPerformed

    private void jComboBox105ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox105ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox105ActionPerformed

    private void jComboBox106ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox106ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox106ActionPerformed

    private void jComboBox112ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox112ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox112ActionPerformed

    private void jComboBox116ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox116ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox116ActionPerformed

    private void jComboBox119ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox119ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox119ActionPerformed

    private void jComboBox118ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox118ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox118ActionPerformed

    private void jComboBox122ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox122ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox122ActionPerformed

    private void jComboBox123ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox123ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox123ActionPerformed

    private void jComboBox128ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox128ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox128ActionPerformed

    private void jComboBox117ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox117ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox117ActionPerformed

    private void jComboBox111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox111ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox111ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(myFlowChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(myFlowChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(myFlowChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(myFlowChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new myFlowChart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox100;
    private javax.swing.JComboBox<String> jComboBox101;
    private javax.swing.JComboBox<String> jComboBox102;
    private javax.swing.JComboBox<String> jComboBox104;
    private javax.swing.JComboBox<String> jComboBox105;
    private javax.swing.JComboBox<String> jComboBox106;
    private javax.swing.JComboBox<String> jComboBox107;
    private javax.swing.JComboBox<String> jComboBox110;
    private javax.swing.JComboBox<String> jComboBox111;
    private javax.swing.JComboBox<String> jComboBox112;
    private javax.swing.JComboBox<String> jComboBox113;
    private javax.swing.JComboBox<String> jComboBox116;
    private javax.swing.JComboBox<String> jComboBox117;
    private javax.swing.JComboBox<String> jComboBox118;
    private javax.swing.JComboBox<String> jComboBox119;
    private javax.swing.JComboBox<String> jComboBox122;
    private javax.swing.JComboBox<String> jComboBox123;
    private javax.swing.JComboBox<String> jComboBox128;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox74;
    private javax.swing.JComboBox<String> jComboBox75;
    private javax.swing.JComboBox<String> jComboBox76;
    private javax.swing.JComboBox<String> jComboBox77;
    private javax.swing.JComboBox<String> jComboBox78;
    private javax.swing.JComboBox<String> jComboBox79;
    private javax.swing.JComboBox<String> jComboBox80;
    private javax.swing.JComboBox<String> jComboBox81;
    private javax.swing.JComboBox<String> jComboBox82;
    private javax.swing.JComboBox<String> jComboBox83;
    private javax.swing.JComboBox<String> jComboBox84;
    private javax.swing.JComboBox<String> jComboBox85;
    private javax.swing.JComboBox<String> jComboBox86;
    private javax.swing.JComboBox<String> jComboBox87;
    private javax.swing.JComboBox<String> jComboBox88;
    private javax.swing.JComboBox<String> jComboBox89;
    private javax.swing.JComboBox<String> jComboBox90;
    private javax.swing.JComboBox<String> jComboBox91;
    private javax.swing.JComboBox<String> jComboBox92;
    private javax.swing.JComboBox<String> jComboBox93;
    private javax.swing.JComboBox<String> jComboBox94;
    private javax.swing.JComboBox<String> jComboBox95;
    private javax.swing.JComboBox<String> jComboBox96;
    private javax.swing.JComboBox<String> jComboBox98;
    private javax.swing.JComboBox<String> jComboBox99;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}