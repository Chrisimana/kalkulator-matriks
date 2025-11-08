import java.util.*;
import java.io.*;

public class HistoryManager {
    private ArrayList<String> history;
    private static final String FILE_NAME = "history_matriks.txt";
    
    public HistoryManager() {
        history = new ArrayList<String>();
        loadHistory();
    }
    
    public void tambahHistory(String operasi, Matriks A, Matriks B, Matriks hasil) {
        String timestamp = new Date().toString();
        String entry = "Waktu: " + timestamp + "\n" +
                      "Operasi: " + operasi + "\n" +
                      "Matriks A:\n" + A.toFormattedString() + "\n" +
                      "Matriks B:\n" + B.toFormattedString() + "\n" +
                      "Hasil:\n" + hasil.toFormattedString() + "\n" +
                      "========================================\n";
        
        history.add(0, entry);
        saveHistory();
        
        // Batasi history maksimal 50 entri
        if (history.size() > 50) {
            history.remove(history.size() - 1);
        }
    }
    
    public ArrayList<String> getHistory() {
        return new ArrayList<String>(history);
    }
    
    public void clearHistory() {
        history.clear();
        saveHistory();
    }
    
    private void saveHistory() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));
            for (String entry : history) {
                writer.print(entry);
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error menyimpan history: " + e.getMessage());
        }
    }
    
    private void loadHistory() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder entry = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("========================================")) {
                        if (entry.length() > 0) {
                            history.add(entry.toString());
                            entry = new StringBuilder();
                        }
                    } else {
                        entry.append(line).append("\n");
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.err.println("Error loading history: " + e.getMessage());
            }
        }
    }
}