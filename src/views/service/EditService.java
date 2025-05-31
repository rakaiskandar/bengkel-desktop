/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.service;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import models.ServiceDetail;
import services.VehicleService;
import services.ServiceRecordService;
import models.Session;
import models.Vehicle;
import models.ServiceRecord;
import models.SparePart;
import services.SparePartService;
import views.CustomerView;
import views.DashboardView;
import views.Login;
import views.ServiceView;
import views.SparepartView;
import views.VehicleView;

/**
 *
 * @author sandy
 */
public class EditService extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    private int serviceRecordId;

    private javax.swing.JPanel panelUsedSparepart;
    private javax.swing.JButton btnAddSparepart;
    private JScrollPane scrollPaneSparepart;

    public EditService() {
        initComponents();
        String username = Session.getUser().getUsername();
        jLabel8.setText("Welcome, " + username);
        loadVehicle();
        jButton1.addActionListener(e -> saveData());

        getContentPane().setBackground(new java.awt.Color(239, 239, 239));
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/assets/sibengkel.png"));
        Image image = originalIcon.getImage().getScaledInstance(200, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        jLabel1.setIcon(resizedIcon);
        
        initSparepartPanel();
    }

    private void initSparepartPanel() {
        panelUsedSparepart = new javax.swing.JPanel();
        panelUsedSparepart.setLayout(new BoxLayout(panelUsedSparepart, BoxLayout.Y_AXIS));

        scrollPaneSparepart = new JScrollPane(panelUsedSparepart);
        scrollPaneSparepart.setBounds(770, 250, 300, 240);
        scrollPaneSparepart.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPaneSparepart);

        btnAddSparepart = new javax.swing.JButton("Tambah Sparepart");
        btnAddSparepart.setBounds(770, 200, 200, 27);
        getContentPane().add(btnAddSparepart);

        btnAddSparepart.addActionListener(e -> addSparepartRow(new ServiceDetail()));
    }

    public EditService(int id) {
        this();
        this.serviceRecordId = id;
        loadData(id);
    }

    private void loadData(int id) {
        ServiceRecordService service = new ServiceRecordService();
        ServiceRecord sr = service.getById(id);
        if (sr != null) {
            jComboBox1.setSelectedItem(sr.getVehicleId());
            jTextField1.setText(sr.getType());
            jTextArea1.setText(sr.getDescription());
            jTextField3.setText(String.valueOf(sr.getCost()));

            // Tampilkan spareparts yang digunakan sebelumnya
            List<ServiceDetail> details = sr.getDetails(); // Pastikan ServiceRecord punya method ini
            if (details != null) {
                for (ServiceDetail detail : details) {
                    addSparepartRow(detail);
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Data sparepart tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void saveData() {
        Vehicle selectedVehicle = (Vehicle) jComboBox1.getSelectedItem();
        String servType = jTextField1.getText();
        String desc = jTextArea1.getText();

        if (selectedVehicle == null || servType.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil detail dari panelUsedSparepart
        List<ServiceDetail> serviceDetails = new ArrayList<>();
        for (Component comp : panelUsedSparepart.getComponents()) {
            if (comp instanceof JPanel rowPanel) {
                JComboBox<SparePart> combo = null;
                JTextField qtyField = null;

                for (Component rowComp : rowPanel.getComponents()) {
                    if (rowComp instanceof JComboBox<?> c && c.getSelectedItem() instanceof SparePart) {
                        combo = (JComboBox<SparePart>) c;
                    } else if (rowComp instanceof JTextField t) {
                        qtyField = t;
                    }
                }

                if (combo != null && qtyField != null) {
                    try {
                        SparePart selectedPart = (SparePart) combo.getSelectedItem();
                        int qty = Integer.parseInt(qtyField.getText());

                        ServiceDetail detail = new ServiceDetail(0, selectedPart.getId(), qty);
                        detail.setSparePartId(selectedPart.getId());
                        detail.setQuantity(qty);

                        serviceDetails.add(detail);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Qty harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }

        if (serviceDetails.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Minimal 1 sparepart harus dipilih!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hitung total biaya sparepart
        ServiceRecordService service = new ServiceRecordService();
        double totalSpareCost = service.calculateSpareCost(serviceDetails);

        // Buat dan simpan ServiceRecord
        ServiceRecord sr = new ServiceRecord(selectedVehicle.getId(), servType, desc, totalSpareCost);
        sr.setId(serviceRecordId);
        sr.setVehicleId(selectedVehicle.getId());
        sr.setType(servType);
        sr.setDescription(desc);
        sr.setCost(totalSpareCost);

        boolean success = service.updateService(sr);

        if (success) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
            ServiceView s = new ServiceView();
            s.setLocationRelativeTo(null);
            s.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadVehicle() {
        VehicleService vehicleService = new VehicleService();
        List<Vehicle> vehicleList = vehicleService.getAllVehicles();

        DefaultComboBoxModel<Vehicle> model = new DefaultComboBoxModel<>();
        for (Vehicle v : vehicleList) {
            model.addElement(v);
        }
        jComboBox1.setModel(model);
    }

    private void addSparepartRow(ServiceDetail detail) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel labelNama = new JLabel("Sparepart:");
        JComboBox<SparePart> comboSparepart = new JComboBox<>();

        // Ambil data sparepart dari database
        SparePartService sparePartService = new SparePartService();
        List<SparePart> spareParts = sparePartService.getAllSpareParts();
        SparePart selectedPart = null;
        for (SparePart sp : spareParts) {
            comboSparepart.addItem(sp);
            if (sp.getId() == detail.getSparePartId()) {
                selectedPart = sp;
            }
        }

        if (selectedPart != null) {
            comboSparepart.setSelectedItem(selectedPart);
        }

        JLabel labelQty = new JLabel("Qty:");
        JTextField qtyField = new JTextField(5);
        qtyField.setText(String.valueOf(detail.getQuantity()));

        JButton deleteButton = new JButton("X");
        deleteButton.setForeground(Color.RED);
        deleteButton.setFocusable(false);
        deleteButton.setMargin(new Insets(2, 5, 2, 5));
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.addActionListener(e -> {
            panelUsedSparepart.remove(rowPanel);
            panelUsedSparepart.revalidate();
            panelUsedSparepart.repaint();
        });

        labelNama.setFont(new java.awt.Font("Segoe UI", 0, 14));
        labelQty.setFont(new java.awt.Font("Segoe UI", 0, 14));

        rowPanel.add(labelNama);
        rowPanel.add(comboSparepart);
        rowPanel.add(labelQty);
        rowPanel.add(qtyField);
        rowPanel.add(deleteButton);

        panelUsedSparepart.add(rowPanel);
        panelUsedSparepart.revalidate();
        panelUsedSparepart.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jFrame1 = new javax.swing.JFrame();
        jMenuItem2 = new javax.swing.JMenuItem();
        jFrame2 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenuItem2.setText("jMenuItem2");

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Service - Edit Service");
        setPreferredSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(27, 92, 131));
        jPanel1.setAlignmentX(0.0F);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("      Logout");
        jLabel3.setToolTipText("");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("      Home");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("      Sparepart");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("      Vehicle");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("      Service");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("      Customer");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(277, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 280, 840);

        jPanel7.setBackground(new java.awt.Color(34, 116, 165));
        jPanel7.setAlignmentX(0.0F);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Selamat Datang, ");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel8)
                .addContainerGap(1064, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(36, 36, 36))
        );

        getContentPane().add(jPanel7);
        jPanel7.setBounds(280, 0, 1270, 108);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setText("Edit Service");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(320, 110, 210, 80);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setText("Used Sparepart");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(770, 140, 230, 32);

        jComboBox1.setFocusCycleRoot(true);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(480, 200, 240, 26);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Vehicle");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(320, 200, 60, 20);
        getContentPane().add(jTextField1);
        jTextField1.setBounds(480, 250, 240, 26);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Service Type");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(320, 250, 110, 20);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(480, 300, 238, 130);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Description");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(320, 300, 110, 20);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Total Cost");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(320, 460, 110, 20);

        jTextField3.setToolTipText("");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField3);
        jTextField3.setBounds(480, 460, 240, 26);

        jButton1.setBackground(new java.awt.Color(242, 208, 164));
        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(320, 540, 90, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:                                     
        Session.clear();
        Login lgn = new Login();
        lgn.setLocationRelativeTo(null);
        lgn.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        SparepartView spv = new SparepartView();
        spv.setLocationRelativeTo(null);
        spv.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        VehicleView vhc = new VehicleView();
        vhc.setLocationRelativeTo(null);
        vhc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        ServiceView svv = new ServiceView();
        svv.setLocationRelativeTo(null);
        svv.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        DashboardView dsh = new DashboardView();
        dsh.setLocationRelativeTo(null);
        dsh.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        CustomerView cst = new CustomerView();
        cst.setLocationRelativeTo(null);
        cst.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

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
            java.util.logging.Logger.getLogger(EditService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditService view = new EditService();
                view.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<Vehicle> jComboBox1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
