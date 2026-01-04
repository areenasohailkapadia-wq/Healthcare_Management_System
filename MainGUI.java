package view;

import controller.MainController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainGUI extends JFrame {

    private final MainController controller;

    private EntityPanel<Patient> patientsPanel;
    private EntityPanel<Clinician> cliniciansPanel;
    private EntityPanel<Facility> facilitiesPanel;
    private EntityPanel<Appointment> appointmentsPanel;
    private EntityPanel<Prescription> prescriptionsPanel;
    private EntityPanel<Referral> referralsPanel;
    private EntityPanel<Staff> staffPanel;

    public MainGUI(MainController controller) {
        super("Healthcare Management System");
        this.controller = controller;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        setJMenuBar(buildMenu());

        JTabbedPane tabs = new JTabbedPane();

        patientsPanel = buildPatientsPanel();
        cliniciansPanel = buildCliniciansPanel();
        facilitiesPanel = buildFacilitiesPanel();
        appointmentsPanel = buildAppointmentsPanel();
        prescriptionsPanel = buildPrescriptionsPanel();
        referralsPanel = buildReferralsPanel();
        staffPanel = buildStaffPanel();

        tabs.addTab("Patients", patientsPanel);
        tabs.addTab("Clinicians", cliniciansPanel);
        tabs.addTab("Facilities", facilitiesPanel);
        tabs.addTab("Appointments", appointmentsPanel);
        tabs.addTab("Prescriptions", prescriptionsPanel);
        tabs.addTab("Referrals", referralsPanel);
        tabs.addTab("Staff", staffPanel);

        add(tabs, BorderLayout.CENTER);

        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JMenuBar buildMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem loadAll = new JMenuItem("Load All CSVs (from /data folder)");
        JMenuItem saveAll = new JMenuItem("Save All CSVs (to /data folder)");
        JMenuItem openOutputs = new JMenuItem("Show Outputs Folder");
        JMenuItem exit = new JMenuItem("Exit");

        loadAll.addActionListener(e -> loadAllFromDataFolder());
        saveAll.addActionListener(e -> saveAllToDataFolder());
        openOutputs.addActionListener(e -> MessageUtil.info(this, controller.getOutputsDir().getAbsolutePath()));
        exit.addActionListener(e -> System.exit(0));

        file.add(loadAll);
        file.add(saveAll);
        file.addSeparator();
        file.add(openOutputs);
        file.addSeparator();
        file.add(exit);

        bar.add(file);
        return bar;
    }

    private JPanel buildFooter() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel l = new JLabel("Outputs are generated in: " + controller.getOutputsDir().getAbsolutePath());
        l.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    private void loadAllFromDataFolder() {
        try {
            File base = new File("data");
            controller.loadPatients(new File(base, "patients.csv"));
            controller.loadClinicians(new File(base, "clinicians.csv"));
            controller.loadFacilities(new File(base, "facilities.csv"));
            controller.loadAppointments(new File(base, "appointments.csv"));
            controller.loadPrescriptions(new File(base, "prescriptions.csv"));
            controller.loadReferrals(new File(base, "referrals.csv"));
            controller.loadStaff(new File(base, "staff.csv"));

            // set current file hints for Save dialogs
            patientsPanel.setCurrentFile(new File(base, "patients.csv"));
            cliniciansPanel.setCurrentFile(new File(base, "clinicians.csv"));
            facilitiesPanel.setCurrentFile(new File(base, "facilities.csv"));
            appointmentsPanel.setCurrentFile(new File(base, "appointments.csv"));
            prescriptionsPanel.setCurrentFile(new File(base, "prescriptions.csv"));
            referralsPanel.setCurrentFile(new File(base, "referrals.csv"));
            staffPanel.setCurrentFile(new File(base, "staff.csv"));

            refreshAllTables();
            MessageUtil.info(this, "Loaded all CSVs from:\n" + base.getAbsolutePath());
        } catch (Exception ex) {
            MessageUtil.error(this, "Failed to load all CSVs. Ensure the 'data' folder exists next to the project run directory.", ex);
        }
    }

    private void saveAllToDataFolder() {
        try {
            File base = new File("data");
            if (!base.exists()) base.mkdirs();

            controller.savePatients(new File(base, "patients.csv"));
            controller.saveClinicians(new File(base, "clinicians.csv"));
            controller.saveFacilities(new File(base, "facilities.csv"));
            controller.saveAppointments(new File(base, "appointments.csv"));
            controller.savePrescriptions(new File(base, "prescriptions.csv"));
            controller.saveReferrals(new File(base, "referrals.csv"));
            controller.saveStaff(new File(base, "staff.csv"));

            MessageUtil.info(this, "Saved all CSVs to:\n" + base.getAbsolutePath());
        } catch (Exception ex) {
            MessageUtil.error(this, "Failed to save all CSVs.", ex);
        }
    }

    private void refreshAllTables() {
        patientsPanel.refreshTable();
        cliniciansPanel.refreshTable();
        facilitiesPanel.refreshTable();
        appointmentsPanel.refreshTable();
        prescriptionsPanel.refreshTable();
        referralsPanel.refreshTable();
        staffPanel.refreshTable();
    }

    private EntityPanel<Patient> buildPatientsPanel() {
        return new EntityPanel<>(
                "Patients",
                new Patient().headers(),
                () -> controller.getPatients(),
                v -> new Patient(),
                idx -> controller.getPatients().get(idx),
                f -> { try { controller.loadPatients(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                f -> { try { controller.savePatients(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                p -> controller.addPatient(p),
                p -> controller.updatePatient(p),
                id -> controller.deletePatient(id),
                controller.getPatientsFile()
        );
    }

    private EntityPanel<Clinician> buildCliniciansPanel() {
        return new EntityPanel<>(
                "Clinicians",
                new Clinician().headers(),
                () -> controller.getClinicians(),
                v -> new Clinician(),
                idx -> controller.getClinicians().get(idx),
                f -> { try { controller.loadClinicians(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                f -> { try { controller.saveClinicians(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                c -> controller.addClinician(c),
                c -> controller.updateClinician(c),
                id -> controller.deleteClinician(id),
                controller.getCliniciansFile()
        );
    }

    private EntityPanel<Facility> buildFacilitiesPanel() {
        return new EntityPanel<>(
                "Facilities",
                new Facility().headers(),
                () -> controller.getFacilities(),
                v -> new Facility(),
                idx -> controller.getFacilities().get(idx),
                f -> { try { controller.loadFacilities(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                f -> { try { controller.saveFacilities(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                fac -> controller.addFacility(fac),
                fac -> controller.updateFacility(fac),
                id -> controller.deleteFacility(id),
                controller.getFacilitiesFile()
        );
    }

    private EntityPanel<Appointment> buildAppointmentsPanel() {
        return new EntityPanel<>(
                "Appointments",
                new Appointment().headers(),
                () -> controller.getAppointments(),
                v -> new Appointment(),
                idx -> controller.getAppointments().get(idx),
                f -> { try { controller.loadAppointments(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                f -> { try { controller.saveAppointments(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                a -> controller.addAppointment(a),
                a -> controller.updateAppointment(a),
                id -> controller.deleteAppointment(id),
                controller.getAppointmentsFile()
        );
    }

    private EntityPanel<Prescription> buildPrescriptionsPanel() {
        return new EntityPanel<>(
                "Prescriptions",
                new Prescription().headers(),
                () -> controller.getPrescriptions(),
                v -> new Prescription(),
                idx -> controller.getPrescriptions().get(idx),
                f -> { try { controller.loadPrescriptions(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                f -> { try { controller.savePrescriptions(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                rx -> { try { controller.addPrescription(rx); } catch (Exception ex) { throw new RuntimeException(ex); } },
                rx -> { try { controller.updatePrescription(rx); } catch (Exception ex) { throw new RuntimeException(ex); } },
                id -> controller.deletePrescription(id),
                controller.getPrescriptionsFile()
        );
    }

    private EntityPanel<Referral> buildReferralsPanel() {
        return new EntityPanel<>(
                "Referrals",
                new Referral().headers(),
                () -> controller.getReferrals(),
                v -> new Referral(),
                idx -> controller.getReferrals().get(idx),
                f -> { try { controller.loadReferrals(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                f -> { try { controller.saveReferrals(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                r -> { try { controller.addReferral(r); } catch (Exception ex) { throw new RuntimeException(ex); } },
                r -> { try { controller.updateReferral(r); } catch (Exception ex) { throw new RuntimeException(ex); } },
                id -> controller.deleteReferral(id),
                controller.getReferralsFile()
        );
    }

    private EntityPanel<Staff> buildStaffPanel() {
        return new EntityPanel<>(
                "Staff",
                new Staff().headers(),
                () -> controller.getStaff(),
                v -> new Staff(),
                idx -> controller.getStaff().get(idx),
                f -> { try { controller.loadStaff(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                f -> { try { controller.saveStaff(f); } catch (Exception ex) { throw new RuntimeException(ex); } },
                s -> controller.addStaff(s),
                s -> controller.updateStaff(s),
                id -> controller.deleteStaff(id),
                controller.getStaffFile()
        );
    }
}
