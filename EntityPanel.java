package view;

import interfaces.FormModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Generic panel for CRUD + Load/Save.
 * Each entity is wired with controller lambdas.
 */
public class EntityPanel<T extends FormModel> extends JPanel {

    private final String title;
    private final Supplier<List<T>> listSupplier;

    private final Consumer<File> loadAction;
    private final Consumer<File> saveAction;
    private final Consumer<T> addAction;
    private final Consumer<T> updateAction;
    private final Consumer<String> deleteAction;

    private final Function<Void, T> newInstance; // creates empty model instance
    private final Function<Integer, T> getByIndex; // rowIndex -> model

    private final DefaultTableModel tableModel;
    private final JTable table;

    private File currentFile;

    public EntityPanel(
            String title,
            String[] columns,
            Supplier<List<T>> listSupplier,
            Function<Void, T> newInstance,
            Function<Integer, T> getByIndex,
            Consumer<File> loadAction,
            Consumer<File> saveAction,
            Consumer<T> addAction,
            Consumer<T> updateAction,
            Consumer<String> deleteAction,
            File initialFileHint
    ) {
        super(new BorderLayout(10, 10));
        this.title = title;
        this.listSupplier = listSupplier;
        this.newInstance = newInstance;
        this.getByIndex = getByIndex;
        this.loadAction = loadAction;
        this.saveAction = saveAction;
        this.addAction = addAction;
        this.updateAction = updateAction;
        this.deleteAction = deleteAction;
        this.currentFile = initialFileHint;

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnLoad = new JButton("Load CSV");
        JButton btnSave = new JButton("Save CSV");
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");

        top.add(btnLoad);
        top.add(btnSave);
        top.add(btnAdd);
        top.add(btnEdit);
        top.add(btnDelete);
        top.add(btnRefresh);

        add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnLoad.addActionListener(e -> doLoad());
        btnSave.addActionListener(e -> doSave());
        btnRefresh.addActionListener(e -> refreshTable());

        btnAdd.addActionListener(e -> doAdd());
        btnEdit.addActionListener(e -> doEdit());
        btnDelete.addActionListener(e -> doDelete());

        refreshTable();
    }

    public void setCurrentFile(File f) { this.currentFile = f; }

    private void doLoad() {
        JFileChooser fc = new JFileChooser(currentFile != null ? currentFile.getParentFile() : null);
        fc.setDialogTitle("Select CSV for " + title);
        int res = fc.showOpenDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;

        File f = fc.getSelectedFile();
        try {
            loadAction.accept(f);
            currentFile = f;
            refreshTable();
            MessageUtil.info(this, "Loaded " + title + " from:\n" + f.getAbsolutePath());
        } catch (Exception ex) {
            MessageUtil.error(this, "Failed to load " + title, ex);
        }
    }

    private void doSave() {
        JFileChooser fc = new JFileChooser(currentFile != null ? currentFile.getParentFile() : null);
        fc.setDialogTitle("Save CSV for " + title);
        if (currentFile != null) fc.setSelectedFile(currentFile);

        int res = fc.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;

        File f = fc.getSelectedFile();
        try {
            saveAction.accept(f);
            currentFile = f;
            MessageUtil.info(this, "Saved " + title + " to:\n" + f.getAbsolutePath());
        } catch (Exception ex) {
            MessageUtil.error(this, "Failed to save " + title, ex);
        }
    }

    private void doAdd() {
        T empty = newModel();
        EntityFormDialog dlg = new EntityFormDialog(SwingUtilities.getWindowAncestor(this), "Add " + title, empty, true);
        dlg.setVisible(true);
        if (!dlg.isSaved()) return;

        try {
            empty.updateFromFieldMap(dlg.getFieldMap());
            addAction.accept(empty);
            refreshTable();
        } catch (Exception ex) {
            MessageUtil.error(this, "Failed to add " + title, ex);
        }
    }

    private void doEdit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessageUtil.info(this, "Select a row to edit.");
            return;
        }

        T original = getByIndex.apply(row);
        if (original == null) return;

        @SuppressWarnings("unchecked")
        T working = (T) original.copy();
        EntityFormDialog dlg = new EntityFormDialog(SwingUtilities.getWindowAncestor(this), "Edit " + title, working, false);
        dlg.setVisible(true);
        if (!dlg.isSaved()) return;

        try {
            working.updateFromFieldMap(dlg.getFieldMap());
            updateAction.accept(working);
            refreshTable();
        } catch (Exception ex) {
            MessageUtil.error(this, "Failed to update " + title, ex);
        }
    }

    private void doDelete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessageUtil.info(this, "Select a row to delete.");
            return;
        }

        T obj = getByIndex.apply(row);
        if (obj == null) return;

        boolean ok = MessageUtil.confirm(this, "Delete " + title + " record: " + obj.getId() + " ?");
        if (!ok) return;

        try {
            deleteAction.accept(obj.getId());
            refreshTable();
        } catch (Exception ex) {
            MessageUtil.error(this, "Failed to delete " + title, ex);
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<T> list = listSupplier.get();
        if (list == null) return;

        for (T t : list) {
            tableModel.addRow(t.toCsvRow());
        }
    }

    private T newModel() {
        return newInstance.apply(null);
    }
}
