import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MatriksGUI {
    private JFrame frame;
    private JTextField[][] fieldMatriksA;
    private JTextField[][] fieldMatriksB;
    private JTextArea areaHasil;
    private JTextArea areaHistory;
    private HistoryManager historyManager;
    
    public MatriksGUI() {
        historyManager = new HistoryManager();
        initialize();
    }
    
    private void initialize() {
        // Setup frame utama
        frame = new JFrame("Kalkulator Matriks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        
        // Panel header
        JPanel panelHeader = createHeaderPanel();
        
        // Panel input matriks
        JPanel panelInput = createInputPanel();
        
        // Panel operasi
        JPanel panelOperasi = createOperasiPanel();
        
        // Panel hasil
        JPanel panelHasil = createHasilPanel();
        
        // Panel history
        JPanel panelHistory = createHistoryPanel();
        
        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Kalkulator", createMainPanel(panelInput, panelOperasi, panelHasil));
        tabbedPane.addTab("History", panelHistory);
        
        frame.add(panelHeader, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);
        
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel label = new JLabel("Kalkulator Matriks");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        
        panel.add(label);
        return panel;
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Input Matriks"));
        
        fieldMatriksA = new JTextField[2][2];
        fieldMatriksB = new JTextField[2][2];
        
        panel.add(createMatriksPanel("Matriks A", fieldMatriksA, new Color(173, 216, 230)));
        panel.add(createMatriksPanel("Matriks B", fieldMatriksB, new Color(144, 238, 144)));
        
        return panel;
    }
    
    private JPanel createMatriksPanel(String title, JTextField[][] fields, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel labelTitle = new JLabel(title, JLabel.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        gridPanel.setBackground(color);
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                fields[i][j] = new JTextField("0");
                fields[i][j].setHorizontalAlignment(JTextField.CENTER);
                fields[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                gridPanel.add(fields[i][j]);
            }
        }
        
        panel.add(labelTitle, BorderLayout.NORTH);
        panel.add(gridPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createOperasiPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Pilih Operasi"));
        
        JButton btnPerkalian = createButton("Perkalian");
        JButton btnPengurangan = createButton("Pengurangan");
        JButton btnPenjumlahan = createButton("Penjumlahan");
        JButton btnClear = createButton("Clear");
        
        btnPerkalian.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitungOperasi("Perkalian");
            }
        });
        
        btnPengurangan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitungOperasi("Pengurangan");
            }
        });
        
        btnPenjumlahan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitungOperasi("Penjumlahan");
            }
        });
        
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearInput();
            }
        });
        
        panel.add(btnPerkalian);
        panel.add(btnPengurangan);
        panel.add(btnPenjumlahan);
        panel.add(btnClear);
        
        return panel;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 30));
        return button;
    }
    
    private JPanel createHasilPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Hasil Operasi"));
        
        areaHasil = new JTextArea(5, 20);
        areaHasil.setFont(new Font("Courier New", Font.BOLD, 14));
        areaHasil.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(areaHasil);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        areaHistory = new JTextArea();
        areaHistory.setFont(new Font("Courier New", Font.PLAIN, 11));
        areaHistory.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(areaHistory);
        
        JPanel buttonPanel = new JPanel();
        JButton btnRefresh = createButton("Refresh History");
        JButton btnClearHistory = createButton("Clear History");
        
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshHistory();
            }
        });
        
        btnClearHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearHistory();
            }
        });
        
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClearHistory);
        
        panel.add(new JLabel("History Operasi:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshHistory();
        return panel;
    }
    
    private JPanel createMainPanel(JPanel panelInput, JPanel panelOperasi, JPanel panelHasil) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        panelInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelOperasi.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHasil.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(panelInput);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelOperasi);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelHasil);
        
        return mainPanel;
    }
    
    private void hitungOperasi(String operasi) {
        try {
            Matriks A = bacaMatriksDariInput(fieldMatriksA);
            Matriks B = bacaMatriksDariInput(fieldMatriksB);
            Matriks hasil = null;
            
            if (operasi.equals("Perkalian")) {
                hasil = Matriks.perkalian(A, B);
            } else if (operasi.equals("Pengurangan")) {
                hasil = Matriks.pengurangan(A, B);
            } else if (operasi.equals("Penjumlahan")) {
                hasil = Matriks.penjumlahan(A, B);
            }
            
            if (hasil != null) {
                areaHasil.setText(hasil.toFormattedString());
                historyManager.tambahHistory(operasi, A, B, hasil);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, 
                "Input harus berupa angka!", 
                "Error Input", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Matriks bacaMatriksDariInput(JTextField[][] fields) {
        Matriks matriks = new Matriks();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int nilai = Integer.parseInt(fields[i][j].getText());
                matriks.setElement(i, j, nilai);
            }
        }
        return matriks;
    }
    
    private void clearInput() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                fieldMatriksA[i][j].setText("0");
                fieldMatriksB[i][j].setText("0");
            }
        }
        areaHasil.setText("");
    }
    
    private void refreshHistory() {
        java.util.ArrayList<String> history = historyManager.getHistory();
        if (history.isEmpty()) {
            areaHistory.setText("Belum ada history operasi.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String entry : history) {
                sb.append(entry).append("\n");
            }
            areaHistory.setText(sb.toString());
        }
    }
    
    private void clearHistory() {
        int confirm = JOptionPane.showConfirmDialog(frame, 
            "Apakah Anda yakin ingin menghapus semua history?", 
            "Konfirmasi", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            historyManager.clearHistory();
            refreshHistory();
        }
    }
    
    public void tampilkan() {
        frame.setVisible(true);
    }
}