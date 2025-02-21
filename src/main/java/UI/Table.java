package UI;

import DB.ColumnFetcher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Table<T> extends JComponent {
    private static final Logger LOGGER = Logger.getLogger(Table.class.getName());
    private DefaultTableModel tableModel;
    private JTable table;
    private ColumnFetcher columnFetcher;

    /**
     * Constructs a new Table instance with dynamic columns and data.
     *
     * @param tableName The name of the table in the database.
     * @param type      The type of objects to be represented in the table.
     */
    public Table(String tableName, Class<T> type) {
        columnFetcher = new ColumnFetcher();
        setLayout(new BorderLayout());

        List<T> dynamicObjects = columnFetcher.fetchAndCreateObjects(tableName, type);

        if (!dynamicObjects.isEmpty()) {
            T firstObject = dynamicObjects.get(0);
            List<String> columnNames = new ArrayList<>();
            for (Field field : firstObject.getClass().getDeclaredFields()) {
                columnNames.add(field.getName());
            }

            tableModel = new DefaultTableModel(new Object[0][], columnNames.toArray()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (T obj : dynamicObjects) {
                List<Object> rowData = new ArrayList<>();
                for (Field field : obj.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        rowData.add(field.get(obj));
                    } catch (IllegalAccessException e) {
                        LOGGER.log(Level.WARNING, "Error accessing field: " + e.getMessage());
                    }
                }
                tableModel.addRow(rowData.toArray());
            }
        } else {
            LOGGER.log(Level.SEVERE, "No data found for the specified table.");
            return;
        }

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
