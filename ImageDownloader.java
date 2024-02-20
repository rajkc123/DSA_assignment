// q no 6

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageDownloader extends JFrame {
    private JTextField urlField;
    private JButton downloadButton;
    private JTextArea progressArea;
    private ExecutorService downloadExecutor;

    public ImageDownloader() {
        createUI();
        downloadExecutor = Executors.newFixedThreadPool(4); // A thread pool with 4 workers
    }

    private void createUI() {
        setTitle("Image Downloader");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        urlField = new JTextField();
        downloadButton = new JButton("Download");
        progressArea = new JTextArea();
        progressArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(progressArea);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(urlField, BorderLayout.NORTH);
        panel.add(downloadButton, BorderLayout.CENTER);

        downloadButton.addActionListener(this::onDownload);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void onDownload(ActionEvent event) {
        String urlString = urlField.getText();
        downloadExecutor.submit(() -> {
            try {
                URL url = new URL(urlString);
                String fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();

                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(response);
                fos.close();

                SwingUtilities.invokeLater(() -> progressArea.append("Downloaded " + fileName + "\n"));
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> progressArea.append("Error: " + e.getMessage() + "\n"));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageDownloader downloader = new ImageDownloader();
            downloader.setVisible(true);
        });
    }
}
