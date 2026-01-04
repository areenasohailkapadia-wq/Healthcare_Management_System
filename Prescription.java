package model;

import java.util.LinkedHashMap;
import interfaces.FormModel;
import model.util.ParseUtil;

public class Prescription implements FormModel {
    private String prescriptionId;
    private String patientId;
    private String clinicianId;
    private String appointmentId;
    private String prescriptionDate;
    private String medicationName;
    private String dosage;
    private String frequency;
    private int durationDays;
    private String quantity;
    private String instructions;
    private String pharmacyName;
    private String status;
    private String issueDate;
    private String collectionDate;

    public Prescription() {}

    public Prescription(String prescriptionId, String patientId, String clinicianId, String appointmentId,
                        String prescriptionDate, String medicationName, String dosage, String frequency,
                        int durationDays, String quantity, String instructions, String pharmacyName, String status,
                        String issueDate, String collectionDate) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.appointmentId = appointmentId;
        this.prescriptionDate = prescriptionDate;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.quantity = quantity;
        this.instructions = instructions;
        this.pharmacyName = pharmacyName;
        this.status = status;
        this.issueDate = issueDate;
        this.collectionDate = collectionDate;
    }

    public static Prescription fromCsvRow(String[] r) {
        // prescription_id, patient_id, clinician_id, appointment_id, prescription_date, medication_name, dosage,
        // frequency, duration_days, quantity, instructions, pharmacy_name, status, issue_date, collection_date
        Prescription p = new Prescription();
        p.prescriptionId = get(r, 0);
        p.patientId = get(r, 1);
        p.clinicianId = get(r, 2);
        p.appointmentId = get(r, 3);
        p.prescriptionDate = get(r, 4);
        p.medicationName = get(r, 5);
        p.dosage = get(r, 6);
        p.frequency = get(r, 7);
        p.durationDays = ParseUtil.toInt(get(r, 8), 0);
        p.quantity = get(r, 9);
        p.instructions = get(r, 10);
        p.pharmacyName = get(r, 11);
        p.status = get(r, 12);
        p.issueDate = get(r, 13);
        p.collectionDate = get(r, 14);
        return p;
    }

    private static String get(String[] r, int i) { return i < r.length ? ParseUtil.safe(r[i]) : ""; }

    @Override public String getId() { return prescriptionId; }
    @Override public void setId(String id) { this.prescriptionId = id; }

    public String getPrescriptionId() { return prescriptionId; }
    public String getPatientId() { return patientId; }
    public String getClinicianId() { return clinicianId; }
    public String getAppointmentId() { return appointmentId; }
    public String getMedicationName() { return medicationName; }

    @Override
    public LinkedHashMap<String, String> toFieldMap() {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("prescription_id", prescriptionId);
        m.put("patient_id", patientId);
        m.put("clinician_id", clinicianId);
        m.put("appointment_id", appointmentId);
        m.put("prescription_date", prescriptionDate);
        m.put("medication_name", medicationName);
        m.put("dosage", dosage);
        m.put("frequency", frequency);
        m.put("duration_days", String.valueOf(durationDays));
        m.put("quantity", quantity);
        m.put("instructions", instructions);
        m.put("pharmacy_name", pharmacyName);
        m.put("status", status);
        m.put("issue_date", issueDate);
        m.put("collection_date", collectionDate);
        return m;
    }

    @Override
    public void updateFromFieldMap(LinkedHashMap<String, String> f) {
        prescriptionId = ParseUtil.safe(f.get("prescription_id"));
        patientId = ParseUtil.safe(f.get("patient_id"));
        clinicianId = ParseUtil.safe(f.get("clinician_id"));
        appointmentId = ParseUtil.safe(f.get("appointment_id"));
        prescriptionDate = ParseUtil.safe(f.get("prescription_date"));
        medicationName = ParseUtil.safe(f.get("medication_name"));
        dosage = ParseUtil.safe(f.get("dosage"));
        frequency = ParseUtil.safe(f.get("frequency"));
        durationDays = ParseUtil.toInt(f.get("duration_days"), 0);
        quantity = ParseUtil.safe(f.get("quantity"));
        instructions = ParseUtil.safe(f.get("instructions"));
        pharmacyName = ParseUtil.safe(f.get("pharmacy_name"));
        status = ParseUtil.safe(f.get("status"));
        issueDate = ParseUtil.safe(f.get("issue_date"));
        collectionDate = ParseUtil.safe(f.get("collection_date"));
    }

    @Override
    public FormModel copy() {
        return new Prescription(prescriptionId, patientId, clinicianId, appointmentId, prescriptionDate,
                medicationName, dosage, frequency, durationDays, quantity, instructions, pharmacyName, status, issueDate, collectionDate);
    }

    @Override
    public String[] toCsvRow() {
        return new String[] {
                prescriptionId, patientId, clinicianId, appointmentId, prescriptionDate, medicationName, dosage,
                frequency, String.valueOf(durationDays), quantity, instructions, pharmacyName, status, issueDate, collectionDate
        };
    }

    @Override
    public String[] headers() {
        return new String[] {
                "prescription_id","patient_id","clinician_id","appointment_id","prescription_date","medication_name",
                "dosage","frequency","duration_days","quantity","instructions","pharmacy_name","status","issue_date","collection_date"
        };
    }

    @Override
    public String toString() {
        return prescriptionId + " - " + medicationName;
    }
}
