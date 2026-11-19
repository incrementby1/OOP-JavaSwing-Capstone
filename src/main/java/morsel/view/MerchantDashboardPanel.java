package morsel.view;

import morsel.model.actors.Merchant;
import morsel.model.data.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Merchant dashboard panel for managing restaurant operations.
 */
public class MerchantDashboardPanel extends BaseDashboardPanel {
    
    private JTextArea ordersTextArea;
    private JTable menuTable;
    private DefaultTableModel menuTableModel;
    private JButton refreshButton;
    private JButton addItemButton;
    private JButton editItemButton;
    private JButton removeItemButton;
    private final Merchant merchant;
    
    public MerchantDashboardPanel(Merchant merchant, Runnable onLogout, Runnable onProfile) {
        super(merchant, onLogout, onProfile);
        this.merchant = merchant;
    }
    
    @Override
    protected String getDashboardTitle() {
        return "Merchant Dashboard";
    }
    
    @Override
    protected String getActorDisplayName() {
        return "Merchant";
    }
    
    @Override
    protected JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header with refresh button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        refreshButton = new JButton("Refresh");
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Orders tab
        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel ordersLabel = new JLabel("Pending Orders");
        ordersLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ordersPanel.add(ordersLabel, BorderLayout.NORTH);
        
        ordersTextArea = new JTextArea();
        ordersTextArea.setEditable(false);
        ordersTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane ordersScrollPane = new JScrollPane(ordersTextArea);
        ordersPanel.add(ordersScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Orders", ordersPanel);
        
        // Menu tab
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel menuLabel = new JLabel("Your Menu Items");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuPanel.add(menuLabel, BorderLayout.NORTH);
        
        // Create table model for menu items
        String[] columnNames = {"Name", "Description", "Price", "Category"};
        menuTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        menuTable = new JTable(menuTableModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane menuScrollPane = new JScrollPane(menuTable);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel menuButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addItemButton = new JButton("Add Menu Item");
        editItemButton = new JButton("Edit Menu Item");
        removeItemButton = new JButton("Remove Menu Item");
        
        menuButtonPanel.add(addItemButton);
        menuButtonPanel.add(editItemButton);
        menuButtonPanel.add(removeItemButton);
        menuPanel.add(menuButtonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Menu Management", menuPanel);
        
        // Analytics tab
        JPanel analyticsPanel = new JPanel(new BorderLayout());
        analyticsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel analyticsLabel = new JLabel("Sales Analytics");
        analyticsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        analyticsPanel.add(analyticsLabel, BorderLayout.NORTH);
        
        JTextArea analyticsTextArea = new JTextArea();
        analyticsTextArea.setEditable(false);
        analyticsTextArea.setText("Sales analytics will be displayed here.");
        analyticsTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane analyticsScrollPane = new JScrollPane(analyticsTextArea);
        analyticsPanel.add(analyticsScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Analytics", analyticsPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    @Override
    protected void onDashboard() {
        refreshOrders();
        refreshMenu();
    }
    
    public void refreshOrders() {
        ordersTextArea.setText("Loading orders...");
        ordersTextArea.setText("No pending orders.\n\nOrders will appear here when customers place them.");
    }
    
    public void refreshMenu() {
        menuTableModel.setRowCount(0); // Clear existing rows
        // This will be populated by the controller
    }
    
    public void setOrdersText(String text) {
        ordersTextArea.setText(text);
    }
    
    public void setMenuItems(List<MenuItem> items) {
        menuTableModel.setRowCount(0); // Clear existing rows
        for (MenuItem item : items) {
            Object[] rowData = {
                item.getName(),
                item.getDescription(),
                String.format("$%.2f", item.getPrice()),
                item.getCategory()
            };
            menuTableModel.addRow(rowData);
        }
    }
    
    public void addMenuItem(MenuItem item) {
        Object[] rowData = {
            item.getName(),
            item.getDescription(),
            String.format("$%.2f", item.getPrice()),
            item.getCategory()
        };
        menuTableModel.addRow(rowData);
    }
    
    public void removeMenuItem(int row) {
        if (row >= 0 && row < menuTableModel.getRowCount()) {
            menuTableModel.removeRow(row);
        }
    }
    
    public void updateMenuItem(int row, MenuItem item) {
        if (row >= 0 && row < menuTableModel.getRowCount()) {
            menuTableModel.setValueAt(item.getName(), row, 0);
            menuTableModel.setValueAt(item.getDescription(), row, 1);
            menuTableModel.setValueAt(String.format("$%.2f", item.getPrice()), row, 2);
            menuTableModel.setValueAt(item.getCategory(), row, 3);
        }
    }
    
    public int getSelectedMenuItemRow() {
        return menuTable.getSelectedRow();
    }
    
    public Merchant getMerchant() {
        return merchant;
    }
    
    public JButton getRefreshButton() {
        return refreshButton;
    }
    
    public JButton getAddItemButton() {
        return addItemButton;
    }
    
    public JButton getEditItemButton() {
        return editItemButton;
    }
    
    public JButton getRemoveItemButton() {
        return removeItemButton;
    }
}
