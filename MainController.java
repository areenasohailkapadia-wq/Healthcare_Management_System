package controller;

import model.*;
import model.service.ReferralManager;
import model.util.CsvUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController implements ReferralManager.Lookup {

    private final DataStore store = new DataStore();

    // Remember last used CSV paths so "Save" can default to them.
    private File patientsFile;
    private File cliniciansFile;
    private File facilitiesFile;
    private File appointmentsFile;
    private File prescriptionsFile;
    private File referralsFile;
    private File staffFile;

    private final File outputsDir;

    public MainController(File outputsDir) {
        this.outputsDir = outputsDir;
        if (!this.outputsDir.exists()) this.outputsDir.mkdirs();
    }

    public DataStore getStore() { return store; }

    // ---- Lookup (for ReferralManager singleton) ----
    @Override public Patient findPatient(String id) { return findById(store.getPatients(), id); }
    @Override public Clinician findClinician(String id) { return findById(store.getClinicians(), id); }
    @Override public Facility findFacility(String id) { return findById(store.getFacilities(), id); }

    private <T extends interfaces.FormModel> T findById(List<T> list, String id) {
        if (id == null) return null;
        for (T t : list) {
            if (id.equalsIgnoreCase(t.getId())) return t;
        }
        return null;
    }

    // ---- Accessors ----
    public List<Patient> getPatients() { return store.getPatients(); }
    public List<Clinician> getClinicians() { return store.getClinicians(); }
    public List<Facility> getFacilities() { return store.getFacilities(); }
    public List<Appointment> getAppointments() { return store.getAppointments(); }
    public List<Prescription> getPrescriptions() { return store.getPrescriptions(); }
    public List<Referral> getReferrals() { return store.getReferrals(); }
    public List<Staff> getStaff() { return store.getStaff(); }

    // ---- Load ----
    public void loadPatients(File f) throws IOException {
        patientsFile = f;
        CsvUtil.CsvData data = CsvUtil.read(f);
        store.getPatients().clear();
        for (String[] r : data.rows) store.getPatients().add(Patient.fromCsvRow(r));
    }

    public void loadClinicians(File f) throws IOException {
        cliniciansFile = f;
        CsvUtil.CsvData data = CsvUtil.read(f);
        store.getClinicians().clear();
        for (String[] r : data.rows) store.getClinicians().add(Clinician.fromCsvRow(r));
    }

    public void loadFacilities(File f) throws IOException {
        facilitiesFile = f;
        CsvUtil.CsvData data = CsvUtil.read(f);
        store.getFacilities().clear();
        for (String[] r : data.rows) store.getFacilities().add(Facility.fromCsvRow(r));
    }

    public void loadAppointments(File f) throws IOException {
        appointmentsFile = f;
        CsvUtil.CsvData data = CsvUtil.read(f);
        store.getAppointments().clear();
        for (String[] r : data.rows) store.getAppointments().add(Appointment.fromCsvRow(r));
    }

    public void loadPrescriptions(File f) throws IOException {
        prescriptionsFile = f;
        CsvUtil.CsvData data = CsvUtil.read(f);
        store.getPrescriptions().clear();
        for (String[] r : data.rows) store.getPrescriptions().add(Prescription.fromCsvRow(r));
    }

    public void loadReferrals(File f) throws IOException {
        referralsFile = f;
        CsvUtil.CsvData data = CsvUtil.read(f);
        store.getReferrals().clear();
        for (String[] r : data.rows) store.getReferrals().add(Referral.fromCsvRow(r));
    }

    public void loadStaff(File f) throws IOException {
        staffFile = f;
        CsvUtil.CsvData data = CsvUtil.read(f);
        store.getStaff().clear();
        for (String[] r : data.rows) store.getStaff().add(Staff.fromCsvRow(r));
    }

    // ---- Save ----
    public void savePatients(File f) throws IOException { patientsFile = f; CsvUtil.write(f, new Patient().headers(), rows(store.getPatients())); }
    public void saveClinicians(File f) throws IOException { cliniciansFile = f; CsvUtil.write(f, new Clinician().headers(), rows(store.getClinicians())); }
    public void saveFacilities(File f) throws IOException { facilitiesFile = f; CsvUtil.write(f, new Facility().headers(), rows(store.getFacilities())); }
    public void saveAppointments(File f) throws IOException { appointmentsFile = f; CsvUtil.write(f, new Appointment().headers(), rows(store.getAppointments())); }
    public void savePrescriptions(File f) throws IOException { prescriptionsFile = f; CsvUtil.write(f, new Prescription().headers(), rows(store.getPrescriptions())); }
    public void saveReferrals(File f) throws IOException { referralsFile = f; CsvUtil.write(f, new Referral().headers(), rows(store.getReferrals())); }
    public void saveStaff(File f) throws IOException { staffFile = f; CsvUtil.write(f, new Staff().headers(), rows(store.getStaff())); }

    private <T extends interfaces.FormModel> List<String[]> rows(List<T> list) {
        List<String[]> out = new ArrayList<>();
        for (T t : list) out.add(t.toCsvRow());
        return out;
    }

    // ---- CRUD with ID generation ----
    public void addPatient(Patient p) {
        if (blank(p.getId())) p.setId(IdGenerator.next("P", ids(store.getPatients())));
        upsert(store.getPatients(), p);
    }

    public void updatePatient(Patient p) { upsert(store.getPatients(), p); }
    public void deletePatient(String id) { delete(store.getPatients(), id); }

    public void addClinician(Clinician c) {
        if (blank(c.getId())) c.setId(IdGenerator.next("C", ids(store.getClinicians())));
        upsert(store.getClinicians(), c);
    }
    public void updateClinician(Clinician c) { upsert(store.getClinicians(), c); }
    public void deleteClinician(String id) { delete(store.getClinicians(), id); }

    public void addFacility(Facility f) {
        if (blank(f.getId())) f.setId(IdGenerator.next("F", ids(store.getFacilities())));
        upsert(store.getFacilities(), f);
    }
    public void updateFacility(Facility f) { upsert(store.getFacilities(), f); }
    public void deleteFacility(String id) { delete(store.getFacilities(), id); }

    public void addAppointment(Appointment a) {
        if (blank(a.getId())) a.setId(IdGenerator.next("A", ids(store.getAppointments())));
        upsert(store.getAppointments(), a);
    }
    public void updateAppointment(Appointment a) { upsert(store.getAppointments(), a); }
    public void deleteAppointment(String id) { delete(store.getAppointments(), id); }

    public void addPrescription(Prescription p) throws IOException {
        if (blank(p.getId())) p.setId(IdGenerator.next("RX", ids(store.getPrescriptions())));
        upsert(store.getPrescriptions(), p);
        writePrescriptionOutput(p);
    }
    public void updatePrescription(Prescription p) throws IOException {
        upsert(store.getPrescriptions(), p);
        writePrescriptionOutput(p);
    }
    public void deletePrescription(String id) { delete(store.getPrescriptions(), id); }

    public void addReferral(Referral r) throws IOException {
        if (blank(r.getId())) r.setId(IdGenerator.next("R", ids(store.getReferrals())));
        upsert(store.getReferrals(), r);

        // Singleton referral manager handles queue + processing.
        ReferralManager mgr = ReferralManager.getInstance();
        mgr.enqueue(r);
        mgr.processAll(outputsDir, this);
    }
    public void updateReferral(Referral r) throws IOException {
        upsert(store.getReferrals(), r);
        ReferralManager mgr = ReferralManager.getInstance();
        mgr.enqueue(r);
        mgr.processAll(outputsDir, this);
    }
    public void deleteReferral(String id) { delete(store.getReferrals(), id); }

    public void addStaff(Staff s) {
        if (blank(s.getId())) s.setId(IdGenerator.next("ST", ids(store.getStaff())));
        upsert(store.getStaff(), s);
    }
    public void updateStaff(Staff s) { upsert(store.getStaff(), s); }
    public void deleteStaff(String id) { delete(store.getStaff(), id); }

    private boolean blank(String s) { return s == null || s.trim().isEmpty(); }

    private <T extends interfaces.FormModel> List<String> ids(List<T> list) {
        return list.stream().map(t -> t.getId()).collect(Collectors.toList());
    }

    private <T extends interfaces.FormModel> void upsert(List<T> list, T item) {
        if (item == null || blank(item.getId())) return;

        for (int i = 0; i < list.size(); i++) {
            if (item.getId().equalsIgnoreCase(list.get(i).getId())) {
                list.set(i, item);
                return;
            }
        }
        list.add(item);
    }

    private <T extends interfaces.FormModel> void delete(List<T> list, String id) {
        if (blank(id)) return;
        list.removeIf(x -> id.equalsIgnoreCase(x.getId()));
    }

    // ---- Output generation for prescriptions ----
    private void writePrescriptionOutput(Prescription rx) throws IOException {
        if (!outputsDir.exists()) outputsDir.mkdirs();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Patient p = findPatient(rx.getPatientId());
        Clinician c = findClinician(rx.getClinicianId());

        File out = new File(outputsDir, "prescription_" + rx.getPrescriptionId() + ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out, false))) {
            bw.write("PRESCRIPTION (TEXT OUTPUT)");
            bw.newLine();
            bw.write("Generated: " + LocalDateTime.now().format(dtf));
            bw.newLine();
            bw.newLine();

            bw.write("Prescription ID: " + rx.getPrescriptionId()); bw.newLine();
            bw.write("Appointment ID: " + safe(rx.toFieldMap().get("appointment_id"))); bw.newLine();
            bw.write("Prescription Date: " + safe(rx.toFieldMap().get("prescription_date"))); bw.newLine();
            bw.write("Status: " + safe(rx.toFieldMap().get("status"))); bw.newLine();
            bw.newLine();

            bw.write("PATIENT"); bw.newLine();
            if (p != null) {
                bw.write(p.toString()); bw.newLine();
                bw.write("NHS Number: " + safe(p.toFieldMap().get("nhs_number"))); bw.newLine();
                bw.write("DOB: " + safe(p.toFieldMap().get("date_of_birth"))); bw.newLine();
            } else {
                bw.write("Not found: " + safe(rx.getPatientId())); bw.newLine();
            }
            bw.newLine();

            bw.write("CLINICIAN"); bw.newLine();
            if (c != null) {
                bw.write(c.toString()); bw.newLine();
                bw.write("Speciality: " + safe(c.toFieldMap().get("speciality"))); bw.newLine();
            } else {
                bw.write("Not found: " + safe(rx.getClinicianId())); bw.newLine();
            }
            bw.newLine();

            bw.write("MEDICATION"); bw.newLine();
            bw.write("Medication: " + safe(rx.toFieldMap().get("medication_name"))); bw.newLine();
            bw.write("Dosage: " + safe(rx.toFieldMap().get("dosage"))); bw.newLine();
            bw.write("Frequency: " + safe(rx.toFieldMap().get("frequency"))); bw.newLine();
            bw.write("Duration (days): " + safe(rx.toFieldMap().get("duration_days"))); bw.newLine();
            bw.write("Quantity: " + safe(rx.toFieldMap().get("quantity"))); bw.newLine();
            bw.write("Instructions: " + safe(rx.toFieldMap().get("instructions"))); bw.newLine();
            bw.newLine();

            bw.write("PHARMACY"); bw.newLine();
            bw.write("Pharmacy: " + safe(rx.toFieldMap().get("pharmacy_name"))); bw.newLine();
            bw.write("Issue Date: " + safe(rx.toFieldMap().get("issue_date"))); bw.newLine();
            bw.write("Collection Date: " + safe(rx.toFieldMap().get("collection_date"))); bw.newLine();
        }
    }

    private String safe(String s) { return s == null ? "" : s; }

    // ---- Helpers for view ----
    public File getPatientsFile() { return patientsFile; }
    public File getCliniciansFile() { return cliniciansFile; }
    public File getFacilitiesFile() { return facilitiesFile; }
    public File getAppointmentsFile() { return appointmentsFile; }
    public File getPrescriptionsFile() { return prescriptionsFile; }
    public File getReferralsFile() { return referralsFile; }
    public File getStaffFile() { return staffFile; }

    public File getOutputsDir() { return outputsDir; }
}
