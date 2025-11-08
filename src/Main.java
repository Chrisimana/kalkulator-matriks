public class Main {
    public static void main(String[] args) {
        // Menjalankan aplikasi GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MatriksGUI().tampilkan();
            }
        });
    }
}