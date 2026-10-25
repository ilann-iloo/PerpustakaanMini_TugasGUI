import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PerpustakaanMini extends JFrame
        implements ActionListener, ItemListener {

    // Warna tema pastel
    private final Color pink = new Color(255, 218, 230);
    private final Color lavender = new Color(232, 222, 248);
    private final Color mint = new Color(218, 242, 226);
    private final Color babyBlue = new Color(218, 235, 250);
    private final Color cream = new Color(255, 248, 235);
    private final Color darkText = new Color(75, 65, 85);
    private final Color white = new Color(255, 255, 255);

    // Menyimpan data buku
    private final ArrayList<Buku> daftarBuku = new ArrayList<>();

    // Komponen input
    private final JTextField inputJudul = new JTextField(12);
    private final JTextField inputPenulis = new JTextField(12);
    private final JTextField inputCari = new JTextField(12);

    // Tombol
    private final JButton tombolTambah =
            new JButton("Tambah Buku");
    private final JButton tombolCari =
            new JButton("Cari");
    private final JButton tombolStatus =
            new JButton("Pinjam / Kembalikan");

    // Filter status buku
    private final JComboBox<String> filterStatus =
            new JComboBox<>(new String[]{
                "Semua", "Tersedia", "Dipinjam"
            });

    // Tabel buku
    private final DefaultTableModel modelTabel =
            new DefaultTableModel(
                new String[]{"Judul Buku", "Penulis", "Status"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable tabelBuku = new JTable(modelTabel);
    private final JLabel labelInfo = new JLabel("Total buku: 0");

    // Class untuk objek buku
    private static class Buku {
        String judul;
        String penulis;
        String status;

        Buku(String judul, String penulis) {
            this.judul = judul;
            this.penulis = penulis;
            this.status = "Tersedia";
        }
    }

    // Constructor
    public PerpustakaanMini() {
        setTitle("Perpustakaan Mini");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelUtama =
                new JPanel(new BorderLayout(10, 10));
        panelUtama.setBackground(cream);
        panelUtama.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel judulAplikasi =
                new JLabel("PERPUSTAKAAN MINI",
                           SwingConstants.CENTER);
        judulAplikasi.setFont(
                new Font("Arial", Font.BOLD, 20));
        judulAplikasi.setForeground(darkText);

        JPanel panelInput =
                new JPanel(new GridLayout(2, 2, 5, 5));
        panelInput.setBackground(cream);

        panelInput.add(new JLabel("Judul Buku:"));
        panelInput.add(inputJudul);
        panelInput.add(new JLabel("Nama Penulis:"));
        panelInput.add(inputPenulis);

        JPanel panelAtas =
                new JPanel(new BorderLayout(5, 5));
        panelAtas.setBackground(cream);
        panelAtas.add(judulAplikasi, BorderLayout.NORTH);
        panelAtas.add(panelInput, BorderLayout.CENTER);
        panelAtas.add(tombolTambah, BorderLayout.SOUTH);

        JPanel panelCari =
                new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCari.setBackground(lavender);

        panelCari.add(new JLabel("Cari Judul:"));
        panelCari.add(inputCari);
        panelCari.add(tombolCari);
        panelCari.add(new JLabel("Filter:"));
        panelCari.add(filterStatus);

        // Tampilan tabel
        tabelBuku.setBackground(white);
        tabelBuku.setForeground(darkText);
        tabelBuku.setSelectionBackground(pink);
        tabelBuku.setSelectionForeground(darkText);
        tabelBuku.setGridColor(lavender);
        tabelBuku.setRowHeight(20);

        tabelBuku.getTableHeader().setBackground(mint);
        tabelBuku.getTableHeader().setForeground(darkText);
        tabelBuku.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12));
        tabelBuku.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelBuku);
        scrollPane.getViewport().setBackground(white);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(lavender));

        JPanel panelTengah =
                new JPanel(new BorderLayout(5, 5));
        panelTengah.setBackground(cream);
        panelTengah.add(panelCari, BorderLayout.NORTH);
        panelTengah.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBawah =
                new JPanel(new BorderLayout(5, 5));
        panelBawah.setBackground(cream);
        panelBawah.add(tombolStatus, BorderLayout.CENTER);
        panelBawah.add(labelInfo, BorderLayout.SOUTH);

        labelInfo.setForeground(darkText);
        labelInfo.setFont(
                new Font("Arial", Font.BOLD, 12));

        aturTombol(tombolTambah, pink);
        aturTombol(tombolCari, babyBlue);
        aturTombol(tombolStatus, mint);

        panelUtama.add(panelAtas, BorderLayout.NORTH);
        panelUtama.add(panelTengah, BorderLayout.CENTER);
        panelUtama.add(panelBawah, BorderLayout.SOUTH);

        add(panelUtama);

        tombolTambah.addActionListener(this);
        tombolCari.addActionListener(this);
        tombolStatus.addActionListener(this);

        filterStatus.addItemListener(this);

        // Data contoh
        daftarBuku.add(
                new Buku("Tentang Kamu", "Tere Liye"));
        daftarBuku.add(
                new Buku("Bungkam Suara", "J.S Khairen"));
        daftarBuku.add(
                new Buku("Gokurakugai", "Yuto Sano"));

        perbaruiTabel();
        setVisible(true);
    }

    private void aturTombol(JButton tombol, Color warna) {
        tombol.setBackground(warna);
        tombol.setForeground(darkText);
        tombol.setFocusPainted(false);
        tombol.setOpaque(true);
        tombol.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sumber = e.getSource();

        if (sumber == tombolTambah) {
            String judul = inputJudul.getText().trim();
            String penulis = inputPenulis.getText().trim();

            if (judul.isEmpty() || penulis.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Judul dan penulis harus diisi!"
                );
                return;
            }

            daftarBuku.add(new Buku(judul, penulis));

            inputJudul.setText("");
            inputPenulis.setText("");

            perbaruiTabel();

        } else if (sumber == tombolCari) {
            perbaruiTabel();

        } else if (sumber == tombolStatus) {
            int baris = tabelBuku.getSelectedRow();

            if (baris == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Pilih buku terlebih dahulu!"
                );
                return;
            }

            String judul =
                    modelTabel.getValueAt(baris, 0).toString();
            String penulis =
                    modelTabel.getValueAt(baris, 1).toString();

            for (Buku buku : daftarBuku) {
                if (buku.judul.equals(judul)
                        && buku.penulis.equals(penulis)) {

                    if (buku.status.equals("Tersedia")) {
                        buku.status = "Dipinjam";
                    } else {
                        buku.status = "Tersedia";
                    }

                    break;
                }
            }

            perbaruiTabel();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            perbaruiTabel();
        }
    }

    private void perbaruiTabel() {
        modelTabel.setRowCount(0);

        String kataKunci =
                inputCari.getText().trim().toLowerCase();
        String pilihanStatus =
                filterStatus.getSelectedItem().toString();

        int jumlahTampil = 0;

        for (Buku buku : daftarBuku) {
            boolean cocokJudul =
                    buku.judul.toLowerCase().contains(kataKunci);

            boolean cocokStatus =
                    pilihanStatus.equals("Semua")
                    || buku.status.equals(pilihanStatus);

            if (cocokJudul && cocokStatus) {
                modelTabel.addRow(new Object[]{
                    buku.judul,
                    buku.penulis,
                    buku.status
                });

                jumlahTampil++;
            }
        }

        labelInfo.setText(
                "Total buku: " + daftarBuku.size()
                + " | Ditampilkan: " + jumlahTampil
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new PerpustakaanMini());
    }
}
