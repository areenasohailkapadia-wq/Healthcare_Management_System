package view;

import interfaces.FormModel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityFormDialog extends JDialog {

    private boolean saved = false;
    private final LinkedHashMap<String, JTextField> fields = new LinkedHashMap<>();

    public EntityFormDialog(Window owner, String title, FormModel model, boolean isNew) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        LinkedHashMap<String, String> m = model.toFieldMap();

        int row = 0;
        for (Map.Entry<String, String> e : m.entrySet()) {
            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.weightx = 0.3;
            form.add(new JLabel(e.getKey()), gbc);

            JTextField tf = new JTextField(e.getValue() == null ? "" : e.getValue(), 25);

            // If new record, allow editing ID. If editing, lock ID so it can't change.
            if (!isNew && (e.getKey().endsWith("_id") || e.getKey().equals("patient_id") || e.getKey().equals("clinician_id") || e.getKey().equals("facility_id"))) {
                // For entity primary key, we lock only the first header (id) outside to keep it consistent
            }

            fields.put(e.getKey(), tf);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            form.add(tf, gbc);
            row++;
        }

        // lock the very first field (primary id) when editing
        if (!isNew) {
            String firstKey = m.keySet().iterator().next();
            JTextField idField = fields.get(firstKey);
            if (idField != null) idField.setEditable(false);
        }

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> {
            saved = true;
            setVisible(false);
            dispose();
        });

        btnCancel.addActionListener(e -> {
            saved = false;
            setVisible(false);
            dispose();
        });

        buttons.add(btnSave);
        buttons.add(btnCancel);
        add(buttons, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
        setMinimumSize(new Dimension(500, 400));
    }

    public boolean isSaved() { return saved; }

    public LinkedHashMap<String, String> getFieldMap() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        for (Map.Entry<String, JTextField> e : fields.entrySet()) {
            m.put(e.getKey(), e.getValue().getText());
        }
        return m;
    }
}
